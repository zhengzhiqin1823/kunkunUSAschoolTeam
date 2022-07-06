package com.mapper;

import com.test.pojo.sc;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ScMapper {
    List<sc> selectALL(String  id);

    List<sc> selectByCid(String cid);

    List<sc> select(@Param("cid") String cid, @Param("cname") String cname);

    void insert(@Param("cid") String cid,@Param("cname") String cname);
}
