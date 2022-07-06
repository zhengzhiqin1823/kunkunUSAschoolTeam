package com.mapper;

import com.test.pojo.judgelink;
import com.test.pojo.message;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface messageMapper {
    List<message> selectByKey(@Param("messageID") String messageID);
    List<message> selectAll();
    void insert(@Param("messageID") String messageID,@Param("From") String From,@Param("To") String To,@Param("Title") String Title,@Param("firstFm") String firstFm,@Param("totalSize") String totalSize,@Param("isRead") String isRead,@Param("time") String time);
    void deleteByKey(@Param("messageID") String messageID);
}
