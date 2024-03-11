package com.forum.service;

import com.forum.common.Page;
import com.forum.controller.response.NoticeInfo;
import com.forum.entity.Notice;

import java.util.List;
import java.util.Map;

public interface NoticeService {

    Boolean SysSend(String msg);

    Boolean SysSend(String msg, String receiverId);

    Boolean SysSend(String msg, String receiverId, Integer type);

    void SysSend(Long objectId,String receiverId,Integer type,String senderId,String content);

    Boolean SysSend(Long objectId,String receiverId,Integer type,String msg,String senderId,String content);

    Map<String,Integer> hasUnread();

//    Page<Notice> getNoticeList(Integer type,Integer page,Integer pageSize);

    List<NoticeInfo> getNotices(Integer type, Integer page, Integer pageSize);

    List<NoticeInfo> getUserNotices(Integer page, Integer pageSize);

    List<NoticeInfo> getLikeAndReplyNotices(Integer page, Integer pageSize);

    NoticeInfo getNoticeById(Long id);

    String getName(int type);
}
