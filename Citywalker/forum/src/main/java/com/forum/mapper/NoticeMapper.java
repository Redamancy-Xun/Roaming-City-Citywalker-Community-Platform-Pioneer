package com.forum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.forum.controller.response.NoticeInfo;
import com.forum.entity.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {

    @Select("SELECT n.*,u.`name` AS 'user_name',u.`avatar` AS 'user_avatar' FROM `notice` n LEFT JOIN `user` u on u.id = n.sender_id WHERE n.delete_at IS NULL;")
    NoticeInfo selectNotice();
}
