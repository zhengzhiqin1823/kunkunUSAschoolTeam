package com.mapper;

import com.test.pojo.user;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface userMapper {
    @Select("select * from user where username=#{username} and password=#{password};")
       List<user>  selectByUserName(@Param("username") String username, @Param("password") String password);
}
