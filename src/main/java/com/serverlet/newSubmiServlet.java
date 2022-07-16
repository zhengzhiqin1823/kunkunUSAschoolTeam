package com.servlet;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import com.mapper.taskMapper;
import com.mapper.submissionMapper;
import com.test.pojo.submission;
import com.test.pojo.task;

@WebServlet(name = "newSubmiServlet", value = "/newSubmiServlet")
public class newSubmiServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String taskid=request.getParameter("taskid");
        String name=request.getParameter("name");
        String submitStatus="1";
        String judgeStatus="0";
        String startTime=request.getParameter("startTime");
        String deadLine=request.getParameter("deadline");
        String submitTeams="0";
        String description=request.getParameter("description");
        //获取SqlSession对象，来执行sql
        String resource = "mybatis-config.xml";
        InputStream is= Resources.getResourceAsStream(resource);
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
        SqlSession sqs=factory.openSession();

        taskMapper taskMapper = sqs.getMapper(taskMapper.class);
        submissionMapper submissionMapper = sqs.getMapper(submissionMapper.class);

        List<task> tasks = taskMapper.selectByKey(taskid);
        task t=tasks.get(0);
        int snum=Integer.valueOf(t.getSubmitNum());
        String fm=t.getFirstsm();
        for(int i=0;i<snum-1;i++){
            submission s = submissionMapper.selectoneByKey(fm);
            fm=s.getNext();
        }
        List<submission> submissions = submissionMapper.selectAll();
        String submitid=String.valueOf(Integer.valueOf(submissions.get(submissions.size()-1).getSubmitID())+1);
        submissionMapper.insert(submitid,name,submitStatus,judgeStatus,null,startTime,deadLine,submitTeams,description);
        submissionMapper.updateNextByKey(fm,submitid);

        String submitNum=String.valueOf(snum+1);
        taskMapper.updateSubmitnumByKey(taskid,submitNum);
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
