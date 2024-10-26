package com.forum.service;

import com.forum.controller.request.CreateReplyParams;
import com.forum.controller.response.ReplyInfo;
import com.forum.entity.Reply;

import java.util.List;

public interface ReplyService {

    Reply createReply(CreateReplyParams createReplyParams);

    List<ReplyInfo> getReplyList(Long commentId, Boolean orderByDesc, Integer page, Integer pageSize);

//    List<ReplyInfo> replies(Long commentId, Boolean orderByDesc, Integer page, Integer pageSize);

    ReplyInfo getReplyById(Long replyId);

    List<ReplyInfo> getReplyByUserId(String userId, Integer page, Integer pageSize);

    void deleteReply(Long replyId);

}
