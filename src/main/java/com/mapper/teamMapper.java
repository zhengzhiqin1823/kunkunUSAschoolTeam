package com.mapper;

import com.test.pojo.task;
import com.test.pojo.team;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface teamMapper {
    void insert(@Param("teamid") String teamid,@Param("name") String name,@Param("header") String header);
    List<team> selectByKey(@Param("teamid")String teamid);
    List<team> selectAll();
    void deleteByKey(@Param("teamid")String teamid);
}
