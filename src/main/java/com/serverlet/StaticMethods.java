package com.serverlet;

import com.mapper.reportMapper;
import com.mapper.reportcaheMapper;
import com.test.pojo.fragment;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class StaticMethods {

//    public static String getTextByFirstFm(String firstFm, fragmentMapper mapper) {
//        StringBuilder text = new StringBuilder();
//        fragment f1 = mapper.selectByKey(firstFm).get(0);
//        text.append(f1.getData());
//        while (!f1.getNext().equals("")) {
//            f1 = mapper.selectByKey(f1.getNext()).get(0);
//            text.append(f1.getData());
//        }
//        return text.toString();
//    }
//
//    public static void deleteFmByFirstFm(String firstFm, fragmentMapper mapper) {
//        fragment f1 = mapper.selectByKey(firstFm).get(0);
//        while (!f1.getNext().equals("")) {
//            String id = f1.getNext();
//            mapper.deleteByKey(f1.getFmid());
//            f1 = mapper.selectByKey(id).get(0);
//        }
//        mapper.deleteByKey(f1.getFmid());
//    }

    public static String getCacheByTeamIdAndSubmitID(String teamID,String submitID)
    {
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
        reportcaheMapper mapper = sqs.getMapper(reportcaheMapper.class);
        return mapper.selectByTeamIDAndSubmitID(teamID, submitID).get(0).getData();
    }


    public static String getReportByTeamIdAndSubmitID(String teamID,String submitID)
    {
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
        reportMapper mapper = sqs.getMapper(reportMapper.class);
        return mapper.selectByTeamIDAndSubmitID(teamID, submitID).get(0).getData();
    }


}
