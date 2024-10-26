package com.forum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.forum.entity.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

@Mapper
public interface PostMapper extends BaseMapper<Post> {

//    @Select("SELECT p.*,u.name AS 'user_Name',u.avatar AS 'user_avatar' FROM post p LEFT JOIN user u on u.id = p.user_id WHERE p.delete_at IS NULL;")
//    List<PostDto> selectPosts();
//
//    @Select("SELECT p.*,u.name AS 'user_name',u.avatar AS 'user_avatar' FROM post p LEFT JOIN user u on u.id = p.user_id WHERE u.id = #{id} AND p.delete_at IS NULL;")
//    List<PostDto> selectPostsByUserId(@Param("id") String userId);
//
//    @Select("SELECT p.*,u.name AS 'user_name',u.avatar AS 'user_avatar' FROM post p LEFT JOIN user u on u.id = p.user_id WHERE p.id = #{id} AND p.delete_at IS NULL;")
//    PostDto selectPostById(@Param("id") Long postId);
//
//    @Select("SELECT p.*,u.name AS 'user_name',u.avatar AS 'user_avatar' FROM post p LEFT JOIN user u on u.id = p.user_id WHERE p.id = #{id};")
//    PostDto selectPostByIdWithoutDeleteAt(@Param("id") Long postId);
//
//    @Select("SELECT p.*,u.name AS 'user_name FROM post p WHERE delete_at IS NULL AND id IN #{set};")
//    List<PostDto> selectByCollection(@Param("collection") Set<Long> set);

}
