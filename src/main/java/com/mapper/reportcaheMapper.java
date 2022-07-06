package com.mapper;

import com.test.pojo.OpinionAdmin;
import com.test.pojo.reportcahe;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface reportcaheMapper {
    List<reportcahe> selectByKey(@Param("cacheID") String cacheID);
    List<reportcahe> selectAll();
    void insert(@Param("cacheID") String cacheID,@Param("submitID") String submitID,@Param("teamID") String teamID,@Param("firstfm") String firstfm,@Param("totalsize") String totalsize);
    void deleteByKey(@Param("cacheID") String cacheID);
}
