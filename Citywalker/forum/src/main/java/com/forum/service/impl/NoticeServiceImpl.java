package com.forum.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.forum.controller.response.CommentInfo;
import com.forum.controller.response.PostInfo;
import com.forum.controller.response.ReplyInfo;
import com.forum.entity.Post;
import com.forum.entity.User;
import com.forum.service.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.forum.common.EnumExceptionType;
import com.forum.common.Page;
import com.forum.exception.MyException;
import com.forum.controller.response.NoticeInfo;
import com.forum.entity.Notice;
import com.forum.mapper.NoticeMapper;
import com.forum.mapper.UserMapper;
import com.forum.util.MessageUtil;
import com.forum.util.RedisUtils;
import com.forum.util.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ReplyService replyService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SessionUtils sessionUtils;

    @Autowired
    private RedisUtils redisUtils;

    private String getKey(String receiverId, Integer type) {
        return receiverId + ":" + type ;
    }

    /**
     * 手动给全体成员发送消息
     * @param msg 消息内容
     * @return 是否发送成功
     */
    @Override
    public Boolean SysSend(String msg) {
        return SysSend(null, null, 0, msg, null, null);
    }

    /**
     * 手动发送系统消息
     * @param msg 消息内容
     * @param receiverId 接收者id
     * @return 是否发送成功
     */
    @Override
    public Boolean SysSend(String msg, String receiverId) {
        return SysSend(null, receiverId, 0, msg, null, null);
    }

    /**
     * 手动发送系统消息
     * @param msg 消息内容
     * @param receiverId 接收者id
     * @param type 信息类型
     * @return 是否发送成功
     */
    @Override
    public Boolean SysSend(String msg, String receiverId, Integer type) {
        return SysSend(null, receiverId, type, msg, null, null);
    }

    /**
     * 系统自动发送点赞、评论消息
     * @param objectId 对象id
     * @param receiverId 接收者id
     * @param type 类型
     */
    @Override
    public void SysSend(Long objectId, String receiverId, Integer type, String senderId, String content) {
        SysSend(objectId, receiverId, type, null, senderId, content);
    }

    /**
        * @description: 发送消息
        * @param: [message, senderId, receiverId, type]
        * type: 0:系统 1:给帖子点赞 2:给帖子回复 3:给评论点赞 4:给评论回复 5:给回复点赞 6:给回复回复 7:给用户发信息
        * @return: void
     */
    @Transactional(rollbackFor = MyException.class)
    @Override
    public Boolean SysSend(Long objectId, String receiverId, Integer type, String msg, String senderId, String content) {
        if (type < 0 || type > 7) {
            throw new MyException(EnumExceptionType.PARAM_ERROR);
        }
        if (userService.getUserById(receiverId) == null) {
            throw new MyException(EnumExceptionType.USER_NOT_EXIST);
        }
        //如果是自己给自己发就不发了
        if (Objects.equals(receiverId, senderId)) {
            return false;
        }

        //如果是点赞消息，且之前已经发过了，就不发了
        if (type == 1 || type == 3 || type == 5) {
            QueryWrapper<Notice> wrapper = new QueryWrapper<>();
            wrapper.eq("object_id", objectId)
                    .eq("type", type)
                    .eq("sender_id", senderId)
                    .eq("receiver_id", receiverId)
                    .isNull("delete_at");
            if (noticeMapper.selectCount(wrapper) > 0) {
                return false;
            }
        }

        if (msg == null && objectId != null) {
            String senderName = userMapper.selectById(senderId).getUsername();
            msg = MessageUtil.getNoticeMessage(senderName, type, content);
        }

        if (receiverId == null) {
            List<String> ids = userMapper.selectAllUserId();
            for (String id : ids) {
                createAndSendNotice(objectId, msg, id, type, senderId);
            }
        } else {
            createAndSendNotice(objectId, msg, receiverId, type, senderId);
        }

        return true;
    }

    @Transactional(rollbackFor = MyException.class)
    public void createAndSendNotice(Long objectId, String msg, String receiverId, Integer type, String senderId) {
        Notice notice = Notice.builder()
                .sendAt(new Date())
                .receiverId(receiverId)
                .type(type)
                .senderId(senderId)
                .content(msg)
                .objectId(objectId)
                .build();

        noticeMapper.insert(notice);

        //特定类型的消息未读消息数+1
        String key = getKey(receiverId, type);
        if (redisUtils.get(key) == null) {
            if (!redisUtils.set(key, 1, 60 * 60 * 24 * 7)){
                throw new MyException(EnumExceptionType.REDIS_ERROR);
            }
        } else {
            redisUtils.incr(key, 1);
        }
    }

    private String getName(int type) {
        switch (type) {
            case 0:
                return "系统消息";
            case 1:
                return "帖子收到了点赞";
            case 2:
                return "帖子收到了回复";
            case 3:
                return "评论收到了点赞";
            case 4:
                return "评论收到了回复";
            case 5:
                return "回复收到了点赞";
            case 6:
                return "回复收到了回复";
            case 7:
                return "收到用户信息";
            case -1:
                return "总消息数";
            default:
                return "未知消息";
        }
    }

    /**
     * 获取未读消息数
     * @return 未读消息数
     * @throws MyException 通用异常
     */
    @Override
    public Map<String, Integer> hasUnread() throws MyException {
        Map<String,Integer> map = new HashMap<>();
        int total = 0;
        String userId = sessionUtils.getUserId();
        for (int x = 0 ;x < 8 ;x++) {
            String key = getKey(userId, x);
            Object obj = redisUtils.get(key);
            if (obj == null) {
                map.put(getName(x), 0);
            } else {
                int num = (Integer) obj;
                map.put(getName(x), num);
                total += num;
            }
        }
        map.put(getName(-1), total);

//        if(!(obj instanceof Integer)){
//            throw new MyException(EnumExceptionType.PARAM_ERROR);
//        }
        return map;
    }

//    /**
//     * 获取消息列表
//     * @param type 消息类型
//     * @param page 页码
//     * @param pageSize 每页大小
//     * @return 消息列表
//     */
//    @Override
//    public Page<Notice> getNoticeList(Integer type, Integer page, Integer pageSize) {
//        if (type < 0 || type > 6) {
//            throw new MyException(EnumExceptionType.PARAM_ERROR);
//        }
//        if (page == null || page < 1) {
//            page = 1;
//        }
//        if (pageSize == null || pageSize < 1){
//            pageSize = 10;
//        }
//
//        String key = getKey(sessionUtils.getUserId(),type);
//        if(redisUtils.get(key) != null){
//            redisUtils.del(key);
//        }
//
//        PageHelper.startPage(page,pageSize);
//        QueryWrapper<Notice> noticeQueryWrapper = new QueryWrapper<>();
//        noticeQueryWrapper.eq("receiver_id",sessionUtils.getUserId());
//        noticeQueryWrapper.eq("type",type);
//        noticeQueryWrapper.isNull("delete_at");
//
//        return new Page<>(new PageInfo<>(noticeMapper.selectList(noticeQueryWrapper)));
//    }

    /**
     * 获取消息详情（带用户信息）
     * @param type 消息类型
     * @param page 页码
     * @param pageSize 每页大小
     * @return 消息详情
     */
    @Override
    public List<NoticeInfo> getNotices(Integer type, Integer page, Integer pageSize) {
        if (type < 0 || type > 3) {
            throw new MyException(EnumExceptionType.PARAM_ERROR);
        }
        if (page == null || page < 1) {
            page = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }

        String userId = sessionUtils.getUserId();
        QueryWrapper<Notice> noticeQueryWrapper = new QueryWrapper<>();
        noticeQueryWrapper.eq("receiver_id", userId);
        noticeQueryWrapper.isNull("delete_at");

        if (type == 0) {
            String key = getKey(userId, type);
            if (redisUtils.get(key) != null) {
                redisUtils.del(key);
            }
            noticeQueryWrapper.eq("type", type);
        } else if (type == 1) {
            String key = getKey(userId, 1);
            if (redisUtils.get(key) != null) {
                redisUtils.del(key);
            }
            key = getKey(userId, 3);
            if (redisUtils.get(key) != null) {
                redisUtils.del(key);
            }
            key = getKey(userId, 5);
            if (redisUtils.get(key) != null) {
                redisUtils.del(key);
            }
            noticeQueryWrapper.in("type", 1, 3, 5);
        } else if (type == 2) {
            String key = getKey(userId, 2);
            if (redisUtils.get(key) != null) {
                redisUtils.del(key);
            }
            key = getKey(userId, 4);
            if (redisUtils.get(key) != null) {
                redisUtils.del(key);
            }
            key = getKey(userId, 6);
            if (redisUtils.get(key) != null) {
                redisUtils.del(key);
            }
            noticeQueryWrapper.in("type", 2, 4, 6);
        }

        noticeQueryWrapper.orderByDesc("send_at");
        PageHelper.startPage(page, pageSize);
        List<Notice> notices = new Page<>(new PageInfo<>(noticeMapper.selectList(noticeQueryWrapper))).getItems();
        List<NoticeInfo> noticeInfos = new ArrayList<>();
        if (type != 0) {
            for (Notice notice : notices) {
                User user = userMapper.selectById(notice.getSenderId());
                NoticeInfo noticeInfo = new NoticeInfo(notice, user);
                noticeInfos.add(noticeInfo);
            }
        } else {
            for (Notice notice : notices) {
                NoticeInfo noticeInfo = new NoticeInfo(notice);
                noticeInfos.add(noticeInfo);
            }
        }

        return noticeInfos;
    }

    /**
     * 获取我的信息列表
     * @param page 页码
     * @param pageSize 每页大小
     * @return 消息详情
     */
    @Override
    public List<NoticeInfo> getUserNotices(Integer page, Integer pageSize) {
        if (page == null || page < 1) {
            page = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }

        String userId = sessionUtils.getUserId();
        String key = getKey(userId, 7);
        if (redisUtils.get(key) != null) {
            redisUtils.del(key);
        }

        QueryWrapper<Notice> noticeQueryWrapper = new QueryWrapper<>();
        noticeQueryWrapper.eq("receiver_id", userId);
        noticeQueryWrapper.eq("type", 7);
        noticeQueryWrapper.isNull("delete_at");
        noticeQueryWrapper.orderByDesc("send_at");
        PageHelper.startPage(page, pageSize);
        List<Notice> notices = new Page<>(new PageInfo<>(noticeMapper.selectList(noticeQueryWrapper))).getItems();
        List<NoticeInfo> noticeInfos = new ArrayList<>();
        for (Notice notice : notices) {
            User user = userMapper.selectById(notice.getSenderId());
            NoticeInfo noticeInfo = new NoticeInfo(notice, user);
            noticeInfos.add(noticeInfo);
        }

        return noticeInfos;
    }

    /**
     * 获取赞和评论列表
     * @param page 页码
     * @param pageSize 每页大小
     * @return 消息详情
     */
    @Override
    public List<NoticeInfo> getLikeAndReplyNotices(Integer page, Integer pageSize) {
        if (page == null || page < 1) {
            page = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }

        String userId = sessionUtils.getUserId();
        for (int i = 1; i < 7; i++) {
            String key = getKey(userId, i);
            if (redisUtils.get(key) != null) {
                redisUtils.del(key);
            }
        }

        QueryWrapper<Notice> noticeQueryWrapper = new QueryWrapper<>();
        noticeQueryWrapper.eq("receiver_id", userId);
        noticeQueryWrapper.isNull("delete_at");
        noticeQueryWrapper.in("type", 1, 2, 3, 4, 5, 6);
        noticeQueryWrapper.orderByDesc("send_at");
        PageHelper.startPage(page, pageSize);
        List<Notice> notices = new Page<>(new PageInfo<>(noticeMapper.selectList(noticeQueryWrapper))).getItems();
        List<NoticeInfo> noticeInfos = new ArrayList<>();
        for (Notice notice : notices) {
            noticeInfos.add(getNoticeById(notice.getId()));
        }

        return noticeInfos;
    }

    /**
     * 查看信息详情
     * @param id 通知id
     * @return 通知详情
     * @throws MyException 通用异常
     */
    public NoticeInfo getNoticeById(Long id) {
        Notice notice = noticeMapper.selectById(id);
        User user = userMapper.selectById(notice.getSenderId());
        if (notice.getType() == 1 || notice.getType() == 2) {
            PostInfo post = postService.getPostById(notice.getObjectId());
            return new NoticeInfo(notice, user, post);
        } else if (notice.getType() == 3 || notice.getType() == 4) {
            CommentInfo comment = commentService.getCommentById(notice.getObjectId());
            return new NoticeInfo(notice, user, comment);
        } else if (notice.getType() == 5 || notice.getType() == 6) {
            ReplyInfo reply = replyService.getReplyById(notice.getObjectId());
            return new NoticeInfo(notice, user, reply);
        }

        return new NoticeInfo(notice, user);
    }
}


















