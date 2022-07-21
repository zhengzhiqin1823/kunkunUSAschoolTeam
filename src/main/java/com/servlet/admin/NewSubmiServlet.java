package com.servlet.admin;
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

import com.mapper.TaskMapper;
import com.mapper.SubmissionMapper;
import com.test.pojo.Submission;
import com.test.pojo.Task;

@WebServlet(name = "newSubmiServlet", value = "/newSubmiServlet")
public class NewSubmiServlet extends HttpServlet {
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

        TaskMapper taskMapper = sqs.getMapper(TaskMapper.class);
        SubmissionMapper submissionMapper = sqs.getMapper(SubmissionMapper.class);

        List<Task> tasks = taskMapper.selectByKey(taskid);
        Task t=tasks.get(0);
        int snum=Integer.valueOf(t.getSubmitNum());
        String fm=t.getFirstsm();
        for(int i=0;i<snum-1;i++){
            Submission s = submissionMapper.selectoneByKey(fm);
            fm=s.getNext();
        }
        List<Submission> submissions = submissionMapper.selectAll();
        String submitid=submissions.size()+1+"";
        submissionMapper.insert(
                submitid,
                name,
                submitStatus,
                judgeStatus,
                null,
                startTime,
                deadLine,
                submitTeams,
                description,
                taskid);

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
