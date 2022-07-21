package com.mapper;
import com.test.pojo.tutor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TutorMapper {
    void insert(@Param("tid") String tid, @Param("password") String password, @Param("name") String name,@Param("email") String email, @Param("tel") String tel);
    List<tutor> selectByTid(String tid);
    List<tutor> selectAll();
    void deleteByTid(@Param("tid") String tid);
    void updatePass(@Param("tid") String tid,@Param("password") String password);
    void update(@Param("name") String name,
                @Param("email")String email,
                @Param("tid") String tid,
                @Param("tel") String tel);
}