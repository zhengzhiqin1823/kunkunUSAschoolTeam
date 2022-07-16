package com.example.demo;

import com.mapper.submissionMapper;
import com.mapper.taskMapper;
import com.test.pojo.submission;
import com.test.pojo.task;
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

@WebServlet(name = "AdminTaskServlet", value = "/AdminTaskServlet")
public class AdminTaskServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ret=request.getParameter("ret");
        String tid = request.getParameter("tid");
        String resource = "mybatis-config.xml";
        InputStream is= Resources.getResourceAsStream(resource);
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
        //获取SqlSession对象，来执行sql
        SqlSession sqs=factory.openSession();
        String sid="";
        taskMapper mapper = sqs.getMapper(taskMapper.class);
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        switch (ret)
        {
            case "1":
            {
                List<task> tasks = mapper.selectByKey(tid);
                task t=tasks.get(0);
                sid=t.getFirstsm();
                submissionMapper submapper = sqs.getMapper(submissionMapper.class);
                do{
                    List<submission> submissions = submapper.selectByKey(sid);
                    submission submission = submissions.get(0);
                    submapper.CloseSubmit(sid);
                    sid=submission.getNext();
                }
                while(sid!=null);
            }
            case "2":
            {
                List<task> tasks = mapper.selectByKey(tid);
                task t=tasks.get(0);
                sid=t.getFirstsm();
                submissionMapper submapper = sqs.getMapper(submissionMapper.class);
                do{
                    List<submission> submissions = submapper.selectByKey(sid);
                    submission submission = submissions.get(0);
                    submapper.CloseTutorJudge(sid);
                    sid=submission.getNext();
                }
                while(sid!=null);
            }
            case "3":
            {
                List<task> tasks = mapper.selectByKey(tid);
                task t=tasks.get(0);
                sid=t.getFirstsm();
                int num=Integer.parseInt(t.getSubmitNum());
                submissionMapper submapper = sqs.getMapper(submissionMapper.class);
                for(int i=0;i<num;i++)
                {
                    List<submission> submissions = submapper.selectByKey(sid);
                    submission s = submissions.get(0);
                    String ss=s.getSubmitID()+","+s.getName()+","+s.getSubmitStatus()+","+s.getJudgeStatus()+","+s.getStartTime()+","+s.getDeadLine()+","+s.getSubmitTeams()+";";
                    writer.write(ss);
                    sid=s.getNext();
                }
            }
            case "4":
            {
                List<task> tasks = mapper.selectByKey(tid);
                task t=tasks.get(0);
                writer.write(t.getName());
                break;
            }
        }
        sqs.commit();
        sqs.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }
}
