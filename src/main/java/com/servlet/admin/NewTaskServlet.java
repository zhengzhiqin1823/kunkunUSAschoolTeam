package com.servlet.admin;

import com.mapper.SubmissionMapper;
import com.mapper.TaskMapper;
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
public class NewTaskServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name=request.getParameter("name");
        String description=request.getParameter("description");
        String submitNum=request.getParameter("submitNum");
        String deadline=request.getParameter("deadline");
        String startedline=request.getParameter("startedline");
        //获取SqlSession对象，来执行sql
        String resource = "mybatis-config.xml";
        InputStream is= Resources.getResourceAsStream(resource);
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
        SqlSession sqs=factory.openSession();

        TaskMapper taskMapper = sqs.getMapper(TaskMapper.class);
        SubmissionMapper submissionMapper = sqs.getMapper(SubmissionMapper.class);
        List<task> tasks = taskMapper.selectAll();

        List<submission> submissions=submissionMapper.selectAll();
        String firstsm=submissions.size()+1+"";
        String taskid=tasks.size()+1+"";
        //submission表插入
        if((submitNum.equals("1")))
        {
            submissionMapper.insert(firstsm,null,"0","0",null,startedline,deadline,"0",null,taskid);
        }else {
            int nt=Integer.valueOf(firstsm)+1;
            int n=Integer.valueOf(submitNum);
            String next=String.valueOf(nt);
            String submitID=firstsm;
            for(int i=0;i<n-1;i++){
                submissionMapper.insert(submitID,null,"0","0",next,startedline,deadline,"0",null,taskid);
                submitID=String.valueOf(nt);
                nt++;
                next=String.valueOf(nt);
            }
            submissionMapper.insert(submitID,null,"0","0",null,startedline,deadline,"0",null,taskid);
        }

        //项目表插入

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
