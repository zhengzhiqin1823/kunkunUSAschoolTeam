package com.mapper;

import com.test.pojo.opinionTutorCache;
import com.test.pojo.opiniontutor;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface opiniontutorMapper {
    List<opiniontutor> selectByKey(@Param("rID") String rID, @Param("tID")String tID);
    List<opiniontutor> selectByrID(@Param("rID") String rID);
    List<opiniontutor> selectAll();
    @Select("select * from opinion_tutor where tID=#{tid}")
    List<opiniontutor> selectBytID(String tid);

    void insert(@Param("rID") String rID,@Param("tID") String tID,@Param("score") int score,@Param("totalsize") String totalsize,@Param("firstFm") String firstFm,@Param("submitTime") String submitTime);
    void deleteByKey(@Param("rID") String rID, @Param("tID")String tID);
}
