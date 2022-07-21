package com.mapper;

import com.test.pojo.ReportCache;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReportcaheMapper {
    List<ReportCache> selectByKey(@Param("cacheID") String cacheID);
    List<ReportCache> selectAll();
    List<ReportCache> selectByTeamIDAndSubmitID(@Param("teamID") String teamID, @Param("submitID") String submitID);
    void insert(@Param("cacheID") String cacheID,@Param("submitID") String submitID,
                @Param("teamID") String teamID,@Param("totalsize") String totalsize
    ,@Param("data") String data);
    void deleteByKey(@Param("cacheID") String cacheID);
    void deleteByTeamIDAndSubmitID(@Param("teamID") String teamID,@Param("submitID") String submitID);


}
