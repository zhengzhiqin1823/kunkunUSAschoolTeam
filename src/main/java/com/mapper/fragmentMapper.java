package com.mapper;

import com.test.pojo.judgelink;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface fragmentMapper {
    List<judgelink> selectByKey(@Param("fmid") String fmid);
    List<judgelink> selectAll();
    void insert(@Param("fmid") String fmid,@Param("next") String next,@Param("data") String data);
    void deleteByKey(@Param("fmd") String fmd);
}
