package com.test.pojo;

import com.mapper.submissionMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class task {
    private String taskID;
    private String name;
    private String description;
    private String submitNum;
    private String firstsm;
    private String startedline;
    private String deadline;

    public List<submission> getAllSubmissions()
    {
        /*访问数据库，生成一个HTML文件*/
        String resource = "mybatis-config.xml";
        InputStream is = null;
        try {
            is = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
        //获取SqlSession对象，来执行sql
        SqlSession sqs = factory.openSession();
        //执行sql
        submissionMapper subM = sqs.getMapper(submissionMapper.class);
        List<submission> list = new ArrayList<>();
        submission s = subM.selectByKey(firstsm).get(0);
        list.add(s);
        while(!s.getNext().equals(""))
        {
            s = subM.selectByKey(s.getNext()).get(0);
            list.add(s);
        }
        return list;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSubmitNum() {
        return submitNum;
    }

    public String getFirstsm() {
        return firstsm;
    }

    public String getStartedline() {
        return startedline;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSubmitNum(String submitNum) {
        this.submitNum = submitNum;
    }

    public void setFirstsm(String firstsm) {
        this.firstsm = firstsm;
    }

    public void setStartedline(String startedline) {
        this.startedline = startedline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    @Override
    public String toString() {
        return "task{" +
                "taskID='" + taskID + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", submitNum='" + submitNum + '\'' +
                ", firstsm='" + firstsm + '\'' +
                ", startedline='" + startedline + '\'' +
                ", deadline='" + deadline + '\'' +
                '}';
    }
}
