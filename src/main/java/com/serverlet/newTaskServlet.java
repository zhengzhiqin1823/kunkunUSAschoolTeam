package com.example.demo;

import com.mapper.submissionMapper;
import com.mapper.taskMapper;
import com.test.pojo.submission;
import com.test.pojo.task;
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
import java.util.List;

@WebServlet(name = "newTaskServlet", value = "/newTaskServlet")
public class newTaskServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name=request.getParameter("name");
        String description=request.getParameter("description");
        String submitNum=request.getParameter("submitNum");
        //获取服务器日期(不需要)
       /* LocalDate date=LocalDate.now();
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String startedline=date.format(formatter);*/
        String deadline=request.getParameter("deadline");
        String startedline=request.getParameter("startedline");
        System.out.println(name);
        //获取SqlSession对象，来执行sql
        String resource = "mybatis-config.xml";
        InputStream is= Resources.getResourceAsStream(resource);
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
        SqlSession sqs=factory.openSession();

        taskMapper taskMapper = sqs.getMapper(com.mapper.taskMapper.class);
        submissionMapper submissionMapper = sqs.getMapper(submissionMapper.class);
        List<task> tasks = taskMapper.selectAll();



        List<submission> submissions=submissionMapper.selectAll();
        String firstsm=String.valueOf(Integer.valueOf(submissions.get(submissions.size()-1).getSubmitID())+1);
        //submission表插入
        if((submitNum.equals("1")))
        {
            submissionMapper.insert(firstsm,null,"1","0",null,startedline,deadline,"0",null);
        }else {
            int nt=Integer.valueOf(firstsm)+1;
            int n=Integer.valueOf(submitNum);
            String next=String.valueOf(nt);
            String submitID=firstsm;
            for(int i=0;i<n-1;i++){
                submissionMapper.insert(submitID,null,"1","0",next,startedline,deadline,"0",null);
                submitID=String.valueOf(nt);
                nt++;
                next=String.valueOf(nt);
            }
            submissionMapper.insert(submitID,null,"1","0",null,startedline,deadline,"0",null);
        }

        //项目表插入

        String taskid=String.valueOf(Integer.valueOf(tasks.get(tasks.size()-1).getTaskid())+1);
        taskMapper.insert(taskid,name,description,submitNum,firstsm,startedline,deadline);
        PrintWriter writer = response.getWriter();
        writer.write("1");
        sqs.commit();
        sqs.close();



    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        doGet(request,response);
    }
}
