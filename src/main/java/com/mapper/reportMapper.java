package com.mapper;

import com.test.pojo.report;
import com.test.pojo.studentteam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface reportMapper {
    void insert(@Param("rid") String rid);
    List<report> selectByKey(@Param("rid") String rid,@Param("submitID") String submitID,@Param("teamid") String teamid,@Param("totalsize") String totalsize,@Param("firstFm") String firstFm,@Param("submitTime") String submitTime);
    List<report> selectAll();
    List<report> selectByTeamIDAndSubmitID(@Param("teamID") String teamID, @Param("submitID") String submitID);
    void deleteByKey(@Param("rid") String rid);
}
