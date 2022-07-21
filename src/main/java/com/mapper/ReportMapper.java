package com.mapper;

import com.test.pojo.Report;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReportMapper {
    void insert(@Param("rid") String rid,
                @Param("submitID") String submitID,
                @Param("teamid") String teamid,
                @Param("totalsize") String totalsize,
                @Param("submitTime") String submitTime,
                @Param("data") String data
    );

    void insert(@Param("rid") String rid);
    List<Report> selectByKey(@Param("rid") String rid);
    List<Report> selectAll();
    List<Report> selectByTeamIDAndSubmitID(@Param("teamID") String teamID, @Param("submitID") String submitID);
    void deleteByKey(@Param("rid") String rid);
    List<Report> selectBySubId(@Param("submitID") String submitID);
    List<Report> selectByRid(@Param("rid")String rid);
    Report selectoneByKey(@Param("rid") String rid);

}
