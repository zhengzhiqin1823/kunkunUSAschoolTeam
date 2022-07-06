package com.mapper;

import com.test.pojo.student;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface studentMapper {
    void insert(@Param("sid") String sid, @Param("password") String password, @Param("name") String name,@Param("email") String email, @Param("tel") String tel);
    List<student> selectBySid(String sid);
    List<student> selectAll();
    void deleteBySid(@Param("sid") String sid);
}
