package com.forum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.forum.controller.response.ReplyInfo;
import com.forum.entity.Reply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReplyMapper extends BaseMapper<Reply> {

    @Select("SELECT r.*,u.name AS 'user_name',u.avatar AS 'user_avatar' FROM reply r LEFT JOIN `user` u on u.id = r.user_id WHERE r.comment_id = #{comment_id} AND r.delete_at IS NULL ;")
    List<ReplyInfo> selectReplies(@Param("comment_id") Long commentId);

    @Select("SELECT r.*,u.name AS 'user_name',u.avatar AS 'user_avatar' FROM reply r LEFT JOIN `user` u on u.id = r.user_id WHERE r.id = #{id} AND r.delete_at IS NULL ;")
    ReplyInfo selectReplyById(@Param("id") Long replyId);

    @Select("SELECT r.*,u.name AS 'user_name',u.avatar AS 'user_avatar' FROM reply r LEFT JOIN `user` u on u.id = r.user_id WHERE r.id = #{id};")
    ReplyInfo selectReplyByIdWithoutDeleteAt(@Param("id") Long replyId);
}
