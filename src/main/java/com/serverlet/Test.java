package com.serverlet;

import com.mapper.fragmentMapper;
import com.mapper.opinionTutorCahceMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    public static void main(String[] args) throws Exception {
        storeTextbrief("1","tutor001",99,"尼玛死了");
    }

    public static String storeTextTofragment(String text) throws Exception
    {
        //首先要查到fragment中的第一个fmid
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        fragmentMapper tm = sqlSession.getMapper(fragmentMapper.class);

        long maxFmid = -1;
        for(int i = 0; i < tm.selectAllFmid().size(); i++)
        {
            if(Long.parseLong(tm.selectAllFmid().get(i)) > maxFmid)
            {
                maxFmid = Long.parseLong(tm.selectAllFmid().get(i));
            }
        }
        long mark=maxFmid;
        long startFmid=maxFmid;

        //有了startFmid,然后找到next，找到data
        String[] data=text.split("\\s{1,127}");
        //遍历data
        for(int i=0;i<data.length;i++)
        {
            //查找next
            startFmid+=1;
            String next=data[i];
            if(i!=data.length-1)
            {
                tm.insert(""+startFmid,""+(startFmid+1),next);
            }
            else
            {
                tm.insert(""+startFmid,null,next);
            }
        }
        sqlSession.commit();
        sqlSession.close();
        return ""+(mark+1);
    }

    public static void storeTextbrief(String rid,String tid,int score,String text) throws Exception
    {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        opinionTutorCahceMapper tm=sqlSession.getMapper(opinionTutorCahceMapper.class);

        System.out.println(tm.selectByKey(rid,tid));

        if(tm.selectByKey(rid,tid)!=null)//说明这里面有rid，tid，要先删再插入
        {
            tm.deleteByKey(rid,tid);
        }
        tm.insert(rid,tid,score,null,storeTextTofragment(text));


        sqlSession.commit();
        sqlSession.close();
    }

}