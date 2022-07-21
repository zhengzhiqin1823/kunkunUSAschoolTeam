package com.mapper;

import com.test.pojo.admin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminMapper {
    List<admin> selectById(@Param("aid") String aid);
    List<admin> selectAll();
    void insert(@Param("aid") String aid,@Param("password") String password,@Param("name") String name,@Param("email") String email,@Param("tel") String tel);
    void deleteById(@Param("aid") String aid);

    void updateName(@Param("aid") String aid,@Param("name") String name);
    void updatePassword(@Param("aid") String aid,@Param("password") String password);
    void updateEmail(@Param("aid") String aid,@Param("email") String email);
    void updateTel(@Param("aid") String aid,@Param("tel") String tel);


}
