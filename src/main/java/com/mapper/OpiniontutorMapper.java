package com.mapper;

import com.test.pojo.Opiniontutor;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OpiniontutorMapper {
    List<Opiniontutor> selectByKey(@Param("rID") String rID, @Param("tID")String tID);
    List<Opiniontutor> selectByrID(@Param("rID") String rID);
    List<Opiniontutor> selectAll();
    @Select("select * from opinion_tutor where tID=#{tid}")
    List<Opiniontutor> selectBytID(String tid);

    void insert(@Param("rID") String rID,@Param("tID") String tID,@Param("score") int score,
                @Param("totalsize") String totalsize,

                @Param("submitTime") String submitTime,
                @Param("data") String data);
    void deleteByKey(@Param("rID") String rID, @Param("tID")String tID);
}
