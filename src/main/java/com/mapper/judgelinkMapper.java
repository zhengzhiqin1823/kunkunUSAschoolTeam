package com.mapper;

import com.test.pojo.OpinionAdmin;
import com.test.pojo.judgelink;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface judgelinkMapper {
    List<judgelink> selectByKey(@Param("lid") String lid);
    List<judgelink> selectAll();
    void insert(@Param("lid") String lid,@Param("link") String link,@Param("submitID") String submitID,@Param("tid") String tid,@Param("taskID") String taskID,@Param("rID") String rID);
    void deleteByKey(@Param("lid") String lid);

    List<judgelink> selectByLink(@Param("link") String link);
}