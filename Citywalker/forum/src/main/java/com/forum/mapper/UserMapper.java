package com.forum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.forum.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT u.`id` FROM `user` u;")
    List<String> selectAllUserId();
}
