package com.emall.userservice.mapper;

import com.emall.common.domain.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from user where username = #{username}")
    User loginCheck(String username);
}
