package com.mapper;

import com.test.pojo.OpinionAdmin;
import com.test.pojo.reportcahe;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface reportcaheMapper {
    List<reportcahe> selectByKey(@Param("cacheID") String cacheID);
    List<reportcahe> selectAll();
    List<reportcahe> selectByTeamIDAndSubmitID(@Param("teamID") String teamID,@Param("submitID") String submitID);
    void insert(@Param("cacheID") String cacheID,@Param("submitID") String submitID,
                @Param("teamID") String teamID,@Param("totalsize")
                        String totalsize,@Param("data") String data);
    void deleteByKey(@Param("cacheID") String cacheID);
    void deleteByTeamIDAndSubmitID(@Param("teamID") String teamID,@Param("submitID") String submitID);


}
