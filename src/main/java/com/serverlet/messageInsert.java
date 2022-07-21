package com.serverlet;

import com.mapper.MessageMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class messageInsert {
    public  static void Insert_message(String messageId,String From,String To,String Title,String firstFm,String totalSize,String isRead,String time) throws IOException {
        String resource = "mybatis-config.xml";
        InputStream is= Resources.getResourceAsStream(resource);
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
        //获取SqlSession对象，来执行sql
        SqlSession sqs=factory.openSession();
        MessageMapper mapper = sqs.getMapper(MessageMapper.class);
        mapper.insert(messageId,From,To,Title,firstFm,totalSize,isRead,time);
        sqs.commit();
        sqs.close();
    }
}
