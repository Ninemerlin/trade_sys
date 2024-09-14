package com.emall.userservice.mapper;

import com.emall.common.domain.pojo.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from user where username = #{username}")
    User getUserByUsername(String username);

    @Insert("insert into user (username, password, phone, create_time, update_time, balance)" +
            "VALUES (#{username}, #{password}, #{phone}, #{createTime}, #{updateTime}, #{balance})")
    boolean addUser(User user);

    @Delete("delete from user where id = #{userId}")
    boolean cancel(Long userId);
}
