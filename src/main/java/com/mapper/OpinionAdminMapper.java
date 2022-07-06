package com.mapper;

import com.test.pojo.OpinionAdmin;
import com.test.pojo.admin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpinionAdminMapper {
    List<OpinionAdmin> selectByKey(@Param("teamID") String teamID,@Param("taskID")String taskID);
    List<OpinionAdmin> selectAll();
    void insert(@Param("teamID") String teamID,@Param("taskID") String taskID,@Param("aID") String aID,@Param("score") String score,@Param("totalsize") String totalsize,@Param("fristFm") String fristFm,@Param("submitTime") String submitTime);
    void deleteByKey(@Param("teamID") String teamID,@Param("taskID")String taskID);
}
