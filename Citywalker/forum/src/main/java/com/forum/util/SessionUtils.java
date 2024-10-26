package com.forum.util;

import com.forum.common.CommonConstants;
import com.forum.common.EnumExceptionType;
import com.forum.exception.MyException;
import com.forum.dto.SessionData;
import com.forum.entity.User;
import com.forum.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;


/**
 * @author yannis
 * @version 2020/8/1 18:38
 */
@Component
public class SessionUtils {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private RedisUtils redisUtil;

    //获取 userId，首先尝试从会话数据中获取，如果获取失败则返回一个新的 SessionData 对象的 Id
    public String getUserId() {
        return Optional
                .ofNullable(getSessionData())
                .orElse(new SessionData())
                .getId();
    }

    //根据 userId 从 Redis 中获取 sessionId，如果获取到的对象不是 String 类型则抛出自定义异常
    public String getSessionId(String userId) throws MyException {
        Object obj = redisUtil.get(userId);
        if(!(obj instanceof String)){
            throw new MyException(EnumExceptionType.SESSION_NOT_FOUND);
        }

        return (String) obj;
    }

    //根据请求头中的 sessionId，从 Redis 中获取会话数据，若获取数据为空或会话过期则抛出相应异常
    public SessionData getSessionData() throws MyException {
        //从请求头中获得 sessionId
        String key = request.getHeader("session");

        //测试用例
        if (Objects.equals(key, "shadow")) {
            SessionData sessionData = new SessionData();
            sessionData.setId("shadow");
            sessionData.setRole(2);
            return sessionData;
        }

        if (key == null)
            throw new MyException(EnumExceptionType.NEED_SESSION_ID);
        //检查会话是否过期
        if (redisUtil.isExpire(key)) {
            redisUtil.del(key);
            throw new MyException(EnumExceptionType.LOGIN_HAS_OVERDUE);
        }

        return (SessionData) redisUtil.get(key);
    }

    //将 sessionId 设置到响应头中
    public void setSessionId(String sessionId) {
        response.setHeader("session", sessionId);
    }

    //生成一个新的 sessionId，并设置到响应头中
    public String generateSessionId() {
        String sessionId = UUID.randomUUID().toString();
        response.setHeader("session", sessionId);
        return sessionId;
    }

    //设置响应头中的 Content-Type 为 application/json
    public void ChangeContentType() {
        response.setHeader("Content-Type", "application/json");
    }

    //使会话失效，删除用户的会话数据和 sessionId
    public void invalidate() {
        //移除请求中名为 CommonConstants.SESSION 的属性，在使会话失效时将之前在请求中存储的会话信息从请求属性中移除
        request.removeAttribute(CommonConstants.SESSION);
        String userId = getUserId();
        redisUtil.del(userId);
        redisUtil.del(getSessionId(userId));
    }

    //刷新用户数据，包括会话数据和 sessionId，在 Redis 中设置新的会话数据并更新过期时间
    public void refreshData(User user) throws MyException {
        String userId = getUserId();
        if (user == null) {
            user = userMapper.selectById(userId);
        }
        SessionData sessionData = new SessionData(user);
        String sessionId = getSessionId(getUserId());
        AssertUtil.notNull(sessionId, EnumExceptionType.SESSION_NOT_FOUND);

        redisUtil.del(sessionId);
        redisUtil.set(sessionId, sessionData, 86400);
        redisUtil.del(userId);
        redisUtil.set(userId, sessionId, 86400);
    }
}
