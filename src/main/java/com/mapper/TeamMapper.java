package com.mapper;

import com.test.pojo.Team;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeamMapper {
    void insert(@Param("teamid") String teamid,
                @Param("name") String name,
                @Param("password") String password,
                @Param("email") String email,
                @Param("tel") String tel,
                @Param("taskid") String taskid);

    void updateName(@Param("teamid") String teamid,@Param("name") String name);
    void updatePassword(@Param("teamid") String teamid,@Param("password") String password);
    void updateEmail(@Param("teamid") String teamid,@Param("email") String email);
    void updateTel(@Param("teamid") String teamid,@Param("tel") String tel);

    List<Team> selectByTaskID(@Param("taskid") String taskid);
    List<Team> selectByKey(@Param("teamid")String teamid);
    List<Team> selectAll();
    void deleteByKey(@Param("teamid")String teamid);
    void updatePass(@Param("teamid") String teamid);
}
