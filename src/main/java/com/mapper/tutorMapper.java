package com.mapper;
import com.test.pojo.tutor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface tutorMapper {
    void insert(@Param("tid") String tid, @Param("password") String password, @Param("name") String name,@Param("email") String email, @Param("tel") String tel);
    List<tutor> selectByTid(String tid);
    List<tutor> selectAll();
    void deleteByTid(@Param("tid") String tid);
}