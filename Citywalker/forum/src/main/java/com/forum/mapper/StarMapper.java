package com.forum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.forum.entity.Star;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StarMapper extends BaseMapper<Star> {

    @Select("select post_id from star where user_id = #{user_id} and delete_at is null order by star_created_at desc;")
    List<Long> getStarPostIdListByUserId(@Param("user_id") String userId);
}
