package com.mapper;

import com.test.pojo.opinionTutorCache;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface opinionTutorCahceMapper {
    List<opinionTutorCache> selectByKey(@Param("rID") String rID, @Param("tID")String tID);
    List<opinionTutorCache> selectAll();
    void insert(@Param("rID") String rID,@Param("tID") String tID,@Param("score") String score,@Param("totalsize") String totalsize,@Param("firstFm") String firstFm);
    void deleteByKey(@Param("rID") String rID, @Param("tID")String tID);
}
