package com.forum.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.forum.common.Page;
import com.forum.controller.request.UpdateUserRequest;
import com.forum.controller.response.ShowUserResponse;
import com.forum.entity.*;
import com.forum.mapper.*;
import com.forum.service.NoticeService;
import com.forum.util.*;
import com.forum.common.EnumExceptionType;
import com.forum.controller.response.LoginInfo;
import com.forum.dto.SessionData;
import com.forum.exception.MyException;
import com.forum.service.UserService;
import com.forum.util.AssertUtil;
import com.forum.util.WechatUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.forum.common.CommonConstants.*;

@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private WalkerTeamMapper walkerTeamMapper;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private SessionUtils sessionUtils;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private MessageUtil messageUtil;

    @Value("${spring.mail.username}")
    private String sender;

    @Autowired
    JavaMailSender jms;

    @Autowired
    private RouteMapper routeMapper;

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    public UserServiceImpl() {
        log.info("call new UserServiceImpl()");
    }

    /**
     * 注册
     * @param openid 微信openid
     * @return User
     * @throws MyException 通用异常
     */
    @Override
    //@Transactional 注解标记该方法需要进行事务管理，在出现 MyException 异常时进行回滚操作
    @Transactional(rollbackFor = MyException.class)
    public User signUp(String openid) throws MyException {
        //创建一个 User 对象，设置用户的 id 为 openid，角色设置为 4，用户名随机生成，头像路径设定为固定的路径拼接上一个图片文件名
        User user = User.builder()
                .id(openid)
                .version(1)
                .role(4)
                .birthday(new Date())
                .age(0)
                .gender(2)
                .username("用户" + UUID.randomUUID().toString().substring(0, 5))
                .headportrait(IMAGE_PATH + "voecnu.png")
                .build();

        ShoppingCart shoppingCart = ShoppingCart.builder()
                .userId(openid)
                .build();

        //调用 userMapper 的 insert 方法将用户信息插入到数据库中
        userMapper.insert(user);
        shoppingCartMapper.insert(shoppingCart);

        return user;
    }

    /**
     * 登录
     * @param code 微信code
     * @return LoginInfo
     * @throws Exception 异常
     */
    //实现了微信小程序登录的逻辑，包括验证参数、获取用户 openid、查询用户信息、注册用户、设置 session 等步骤，并最终返回登录信息给前端
    @Override
    public LoginInfo login(String code) throws Exception {
        //使用 AssertUtil.notNull 方法来验证 code 参数不为空，如果为空则抛出自定义异常 EnumExceptionType.INPUT_NULL
        AssertUtil.notNull(code, EnumExceptionType.INPUT_NULL);

        //shadow test
        //当 code 等于 SHADOW_TEST 时，会进入这段逻辑，设置 session、缓存信息，并返回特定的 LoginInfo
        if (SHADOW_TEST.equals(code)) {
            sessionUtils.setSessionId(SHADOW_TEST);
            SessionData sessionData = new SessionData();
            sessionData.setId(SHADOW_TEST);
            redisUtils.set(SHADOW_TEST, sessionData, 86400);
            return new LoginInfo(SHADOW_TEST, 0, SHADOW_TEST, sessionData);
        }

        //调用 WechatUtils 工具类的 getOpenId 方法获取微信的 openid
        String openid = WechatUtils.getOpenId(code);

        //验证获取的 openid 不为空，如果为空则抛出自定义异常 EnumExceptionType.WECHAT_ERROR
        AssertUtil.notNull(openid, EnumExceptionType.WECHAT_ERROR);

        //构造一个 QueryWrapper 用于查询用户信息，根据 openid 从数据库中查询对应的用户信息
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("id", openid);
        User user = userMapper.selectOne(userQueryWrapper);

        //如果查询到的用户信息为 null，说明用户不存在，调用 signUp 方法进行用户注册
        if (user == null){
            user = signUp(openid);
        }

        //生成 sessionId 和 sessionData，分别存入 sessionUtils 和 redisUtils 中，设置过期时间为 86400 秒
        String sessionId = sessionUtils.generateSessionId();
        SessionData sessionData = new SessionData(user);
        sessionUtils.setSessionId(sessionId);
        redisUtils.set(openid, sessionId, 86400);
        redisUtils.set(sessionId, sessionData, 86400);

        //最后构造一个 LoginInfo 对象，包含 openid、用户角色信息、sessionId 和 sessionData，返回给前端作为登录信息
        return new LoginInfo(openid, user.getRole(), sessionId, sessionData);
    }

    /**
     * 确认隐私政策
     * @param userId 用户id
     * @return User
     * @throws MyException 通用异常
     */
    @Override
    public User confirmPrivacyPolicy(String userId) throws MyException{
        User user = getUserById(userId);

        user.setRole(3);
        if(userMapper.updateById(user) == 0){
            throw new MyException(EnumExceptionType.UPDATE_FAILED);
        }

        return user;
    }

    /**
     * 用户通过验证
     * @param userId 用户id
     * @return User
     * @throws MyException 通用异常
     */
    @Override
    public User verifyUser(String userId) throws MyException{
        User user = getUserById(userId);

        user.setRole(2);
        if(userMapper.updateById(user) == 0){
            throw new MyException(EnumExceptionType.UPDATE_FAILED);
        }

        return user;
    }

    /**
     * 用户注册
     * @param email 邮箱
     * @return User
     * @throws MyException 通用异常
     */
    @Override
    public Boolean sendEmail(String email) throws MyException {
        Map<String, Object> map = new HashMap<>();
        map.put("email", email);
        if (!userMapper.selectByMap(map).isEmpty()) {
            throw new MyException(EnumExceptionType.EMAIL_HAS_BEEN_SIGNED_UP);
        }
        //如果验证码过期，则删除该邮箱在 redis 中的验证码信息
        if (redisUtils.isExpire(email)) {
            redisUtils.del(email);
        }
        String code = RandomVerifyCodeUtil.getRandomVerifyCode();
        redisUtils.set(email, code, 900);
        try {
            //调用 messageUtil.sendMail 方法发送验证邮件，包括发件人 sender、收件人 email、标题 VERIFICATION_TITLE、邮件内容等信息
            messageUtil.sendMail(sender, email, VERIFICATION_TITLE, messageUtil.signUp(email, code),null, jms);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(EnumExceptionType.SEND_EMAIL_FAILED);
        }
        return true;
    }

    /**
     * 验证验证码
     * @param email 邮箱
     * @param code 验证码
     * @return  Boolean
     * @throws MyException 通用异常
     */
    @Override
    public Boolean verifyCode(String email, String code) throws MyException {
        if (redisUtils.isExpire(email))
            throw new MyException(EnumExceptionType.VERIFICATION_CODE_HAS_EXPIRED);
        return redisUtils.get(email).equals(code);
    }

    /**
     * 获取用户列表
     * @param page 页码
     * @param pageSize 每页数量
     * @return Page<User>
     * @throws MyException 通用异常
     */
    @Override
    public Page<User> getUserList(Integer page, Integer pageSize) throws MyException {
        if (page == null || page < 1) {
            page = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }

        PageHelper.startPage(page, pageSize);
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();

        return new Page<>(new PageInfo<>(userMapper.selectList(userQueryWrapper)));
    }

    /**
     * 根据userId获取用户信息
     * @param userId 用户id
     * @return  User
     * @throws MyException 通用异常
     */
    @Override
    public User getUserById(String userId) throws MyException{
        User user = userMapper.selectById(userId);
        if (user == null){
            throw new MyException(EnumExceptionType.USER_NOT_EXIST);
        }
        return user;
    }

    /**
     * 获取当前用户个人信息
     * @return 用户信息，帖子信息，路线信息
     * @throws MyException 通用异常
     */
    @Override
    public Map<String, Object> getUserInfo() throws MyException {
        Map<String, Object> userInfo = new HashMap<>();

        String userId = sessionUtils.getUserId();
        User user = userMapper.selectById(userId);
        userInfo.put("userInfo", new ShowUserResponse(user));

        QueryWrapper<WalkerTeam> walkerTeamQueryWrapper = new QueryWrapper<>();
        walkerTeamQueryWrapper.eq("user_id", userId);
        walkerTeamQueryWrapper.isNull("delete_at");
        userInfo.put("walkerTeam", walkerTeamMapper.selectList(walkerTeamQueryWrapper));

        QueryWrapper<Post> postQueryWrapper = new QueryWrapper<>();
        postQueryWrapper.eq("user_id", userId);
        postQueryWrapper.isNull("delete_at");
        userInfo.put("posts", postMapper.selectList(postQueryWrapper));

        return userInfo;
    }

    /**
     * 根据字符串搜索用户
     * @param name 用户名
     * @param page 页数
     * @param pageSize 每页大小
     * @return Page<User>
     */
    @Override
    public Page<User> searchByUserName(String name, Integer page, Integer pageSize) {
        if(page == null || page < 1){
            page = 1;
        }
        if(pageSize == null || pageSize < 1){
            pageSize = 10;
        }

        PageHelper.startPage(page, pageSize);

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.like("username","%"+ name +"%");

        return new Page<>(new PageInfo<>(userMapper.selectList(userQueryWrapper)));
    }

    /**
     * 更新用户信息
     * @param updateUserRequest 更新参数
     * @return User
     * @throws MyException 通用异常
     */
    @Transactional(rollbackFor = MyException.class)
    @Override
    public User updateUser(UpdateUserRequest updateUserRequest) throws MyException{
        User user = getUserById(sessionUtils.getUserId());

        if (updateUserRequest.getUsername() != null){
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("username",updateUserRequest.getUsername());
            if(userMapper.selectCount(userQueryWrapper) > 0){
                throw new MyException(EnumExceptionType.USERNAME_EXIST);
            }
            user.setUsername(updateUserRequest.getUsername());
        }
        if(updateUserRequest.getBirthday() != null){
            user.setBirthday(updateUserRequest.getBirthday());
        }
        if(updateUserRequest.getEmail() != null){
            user.setEmail(updateUserRequest.getEmail());
        }
        if(updateUserRequest.getGender() != null){
            user.setGender(updateUserRequest.getGender());
        }
        if(updateUserRequest.getAge() != null){
            user.setAge(updateUserRequest.getAge());
        }
        if(updateUserRequest.getLabel() != null){
            user.setLabel(JSON.toJSONString(updateUserRequest.getLabel()));
        }
        if(updateUserRequest.getHeadportrait() != null){
            user.setHeadportrait(updateUserRequest.getHeadportrait());
        }
        if(updateUserRequest.getSignature() != null){
            user.setSignature(updateUserRequest.getSignature());
        }

        if(userMapper.updateById(user) == 0){
            throw new MyException(EnumExceptionType.UPDATE_FAILED);
        }

        sessionUtils.refreshData(user);

        return user;
    }

    /**
     * 更新用户角色
     * @param userId 用户id
     * @param role 角色
     * @return User
     * @throws MyException 通用异常
     */
    @Transactional(rollbackFor = MyException.class)
    @Override
    public User updateUserRole(String userId, Integer role) throws MyException {
        User cur = getUserById(sessionUtils.getUserId());
        if(cur.getRole() != 0){
            throw new MyException(EnumExceptionType.PERMISSION_DENIED);
        }

        User user = getUserById(userId);
        user.setRole(role);
        if(userMapper.updateById(user) == 0){
            throw new MyException(EnumExceptionType.UPDATE_FAILED);
        }
        return user;
    }

    /**
     * 注销用户
     */
    @Override
    public void deleteUser() {
        String userId = sessionUtils.getUserId();
        User user = userMapper.selectById(userId);

        if(user.getRole() == 0){
            throw new MyException(EnumExceptionType.CAN_NOT_DELETE);
        }

        user.setUsername("用户" + UUID.randomUUID().toString().substring(0, 5));
        user.setLabel(null);
        user.setAge(null);
        user.setBirthday(null);
        user.setEmail("");
        user.setRole(4);
        user.setGender(2);
        user.setHeadportrait(IMAGE_PATH + "voecnu.png");

        if(userMapper.updateById(user) == 0){
            throw new MyException(EnumExceptionType.UPDATE_FAILED);
        }
    }

    /**
     * '我的'界面
     * @return 我的信息，我的订单，赞和评论
     * @throws MyException 通用异常
     */
    @Override
    public Map<String, Object> me() throws MyException {
        Map<String, Object> userInfo = new HashMap<>();
        Map<String, Integer> unreadMap = noticeService.hasUnread();
        Integer unread = 0;

        String userId = sessionUtils.getUserId();
        User user = userMapper.selectById(userId);
        userInfo.put("userInfo", new ShowUserResponse(user));

        userInfo.put("userNotices", unreadMap.get(userId + ":" + 7));
        for (int i = 1; i < 7; i++) {
            unread += unreadMap.get(noticeService.getName(i));
        }
        userInfo.put("likeAndReply", unread);

        return userInfo;
    }
}
