package com.mapper;

import com.test.pojo.OpinionAdmin;
import com.test.pojo.submission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface submissionMapper {
    List<submission> selectByKey(@Param("submitID") String submitID);
    List<submission> selectAll();
    void insert(@Param("submitID") String submitID,@Param("name") String name,@Param("submitStatus") String submitStatus,@Param("judgeStatus") String judgeStatus,@Param("next") String next,@Param("startTime") String startTime,@Param("deadline") String deadLine,@Param("submitTeams") String submitTeams,@Param("description") String description);
    void deleteByKey(@Param("submitID") String submitID);
    void CloseSubmit(@Param("submitid")String submitid);
    void CloseTutorJudge(@Param("submitid")String submitid);
    void OpenSubmit(@Param("submitid")String submitid);
    void OpenTutorJudge(@Param("submitid")String submitid);
    void updateNext(@Param("submitid")String submitid,@Param("next") String next);
    void updateNextByKey(@Param("submitID") String submitID,@Param("next") String next);
    submission selectoneByKey(@Param("submitID") String submitID);
    void updateAllByKey(@Param("submitID") String submitID,@Param("name") String name,@Param("startTime") String startTime,@Param("deadLine") String deadLine,@Param("description") String description);
}
