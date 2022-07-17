package com.serverlet;

import com.mapper.judgelinkMapper;
import com.test.pojo.judgelink;


import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@WebServlet(name = "linkGenerateServlet", value = "/linkGenerateServlet")
public class linkGenerateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tid=request.getParameter("tid");
        String submitID=request.getParameter("submitID");
        String taskid=request.getParameter("taskid");
        String ridString=request.getParameter("rids");
        String[] rids=ridString.split(",");
        String link = getRandomString(7);
        //获取SqlSession对象，来执行sql
        String resource = "mybatis-config.xml";
        InputStream is= Resources.getResourceAsStream(resource);
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
        SqlSession sqs=factory.openSession();
        //获取mapper
        judgelinkMapper judgelinkMapper=sqs.getMapper(com.mapper.judgelinkMapper.class);
        List<judgelink> judgelinks = judgelinkMapper.selectAll();
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        //插入judgelink表
        int n=1;
        if(judgelinks.size()!=0){
             n=Integer.valueOf(judgelinks.get(judgelinks.size()-1).getLid())+1;
        }
        String flag="1";
        List<String> ridstrue=new ArrayList<String>();
        StringBuilder alert1=new StringBuilder();
        StringBuilder alert2=new StringBuilder();
        for(String r:rids){
            if(judgelinkMapper.selectByRid(r).size()==0)
            {
                ridstrue.add(r);
            }
            else{
                if(flag.equals("1"))
                {
                    flag="2";
                    alert2.append(flag);
                }
                alert2.append(",");
                alert2.append(r);
                continue;
            }
        }
        if(flag.equals("2")){
            writer.write(alert2.toString());
        }
        else{
            String lid=String.valueOf(n);
            n++;
            for(String r:ridstrue){
                judgelinkMapper.insert(lid, link, submitID, tid, taskid, r);
                lid=String.valueOf(n);
                n++;
            }
            //respomnse
            String judgelink = "http://localhost:8080/0628JavaWebExercise_war/" + "linkJudge?link=" + link;
            alert1.append(flag);
            alert1.append(";");
            alert1.append(judgelink);
            writer.write(alert1.toString());
        }
        sqs.commit();
        sqs.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        doGet(request,response);
    }

    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
