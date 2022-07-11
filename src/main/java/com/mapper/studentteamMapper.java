package com.mapper;

import com.test.pojo.studentteam;
import com.test.pojo.task;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface studentteamMapper {
    void insert(@Param("sid") String sid,@Param("teamID") String teamID);
    List<studentteam> selectByKey(@Param("sid") String sid,@Param("teamID") String teamID);
    List<studentteam> selectAll();
    void deleteByKey(@Param("sid") String sid,@Param("teamID") String teamID);
    List<studentteam> selectBySid(@Param("sid") String sid);


}
