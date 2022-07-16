package com.mapper;

import com.test.pojo.OpinionAdmin;
import com.test.pojo.submission;
import com.test.pojo.task;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface taskMapper {
    void insert(@Param("taskID") String taskID,@Param("name") String name,@Param("description") String description,@Param("submitNum") String submitNum,@Param("firstsm") String firstsm,@Param("startedline") String startline,@Param("deadline") String deadline);
    List<task> selectByKey(@Param("taskID")String taskID);
    List<task> selectAll();
    void deleteByKey(@Param("taskID")String taskID);
    List<submission> getAllSubmission();
    void UpdateByKey(@Param("taskid") String taskID,@Param("name") String name,@Param("description") String description,@Param("startedline") String startline,@Param("deadline") String deadline,@Param("submitNum") String submitNum);
    void updateSubmitnumByKey(@Param("taskID") String taskID,@Param("submitNum") String submitNum);
}


