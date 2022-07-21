package com.mapper;

import com.test.pojo.opinionTutorCache;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OpinionTutorCahceMapper {
    List<opinionTutorCache> selectByKey(@Param("rID") String rID, @Param("tID")String tID);
    List<opinionTutorCache> selectAll();
    void insert(@Param("rID") String rID,@Param("tID") String tID,@Param("score") int score,
                @Param("totalsize") String totalsize,@Param("data") String data);
    void deleteByKey(@Param("rID") String rID, @Param("tID")String tID);
    @Select("select * from opinion_tutor_cache where tID=#{tID}")
    List<opinionTutorCache> selectBytID(String tid);
}
