package com.forum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.forum.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

//    @Select("SELECT c.*,u.name AS 'user_name',u.avatar AS 'user_avatar' FROM `comment` c LEFT JOIN `user` u on u.id = c.user_id WHERE c.post_id = #{post_id} AND c.delete_at IS NULL;")
//    List<CommentDto> selectCommentsByPostId(@Param("post_id") Long postId);
//
//    @Select("SELECT c.*,u.name AS 'user_name',u.avatar AS 'user_avatar' FROM `comment` c LEFT JOIN `user` u on u.id = c.user_id WHERE c.post_id = #{post_id};")
//    List<CommentDto> selectCommentsByPostIdWithoutDeleteAt(@Param("post_id") Long postId);
//
//    @Select("SELECT c.*,u.name AS 'user_name',u.avatar AS 'user_avatar' FROM `comment` c LEFT JOIN `user` u on u.id = c.user_id WHERE c.id = #{id} AND c.delete_at IS NULL;")
//    CommentDto selectCommentById(@Param("id") Long commentId);
//
//    @Select("SELECT c.*,u.name AS 'user_name',u.avatar AS 'user_avatar' FROM `comment` c LEFT JOIN `user` u on u.id = c.user_id WHERE c.id = #{id};")
//    CommentDto selectCommentByIdWithoutDeleteAt(@Param("id") Long commentId);
}
