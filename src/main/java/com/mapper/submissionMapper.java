package com.mapper;

import com.test.pojo.OpinionAdmin;
import com.test.pojo.submission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface submissionMapper {
    List<submission> selectByKey(@Param("submitID") String submitID);
    List<submission> selectAll();
    void insert(@Param("submitID") String submitID,@Param("name") String name,@Param("submitStatus") String submitStatus,@Param("judgeStatus") String judgeStatus,@Param("next") String next,@Param("startTime") String startTime,@Param("deadLine") String deadLine,@Param("submitTeams") String submitTeams);
    void deleteByKey(@Param("submitID") String submitID);
}
