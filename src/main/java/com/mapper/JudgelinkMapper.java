package com.mapper;

import com.test.pojo.Judgelink;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface JudgelinkMapper {
    List<Judgelink> selectByKey(@Param("lid") String lid);
    List<Judgelink> selectAll();
    void insert(@Param("lid") String lid,@Param("link") String link,@Param("submitID") String submitID,@Param("tid") String tid,@Param("taskID") String taskID,@Param("rID") String rID);
    void deleteByKey(@Param("lid") String lid);
    List<Judgelink> selectByRid(@Param("rID") String rID);
    List<Judgelink> selectByLink(@Param("link") String link);
}