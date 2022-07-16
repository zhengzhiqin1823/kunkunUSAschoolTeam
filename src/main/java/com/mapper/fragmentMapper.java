package com.mapper;

import com.test.pojo.fragment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface fragmentMapper {
    List<fragment> selectByKey(@Param("fmid") String fmid);
    List<fragment> selectAll();
    @Select("select fmid from fragment;")
    List<String> selectAllFmid();
    fragment selectoneByKey(@Param("fmid") String fmid);
    void insert(@Param("fmid") String fmid,@Param("next") String next,@Param("data") String data);
    void deleteByKey(@Param("fmd") String fmd);
}

