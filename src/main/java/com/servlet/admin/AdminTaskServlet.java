package com.servlet.admin;

import com.mapper.SubmissionMapper;
import com.mapper.TaskMapper;
import com.test.pojo.Submission;
import com.test.pojo.Task;
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
        TaskMapper mapper = sqs.getMapper(TaskMapper.class);
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        switch (ret)
        {
            case "1":
            {
                List<Task> tasks = mapper.selectByKey(tid);
                Task t=tasks.get(0);
                sid=t.getFirstsm();
                SubmissionMapper submapper = sqs.getMapper(SubmissionMapper.class);
                do{
                    List<Submission> submissions = submapper.selectByKey(sid);
                    Submission submission = submissions.get(0);
                    submapper.CloseSubmit(sid);
                    sid=submission.getNext();
                }
                while(sid!=null);
            }
            case "2":
            {
                List<Task> tasks = mapper.selectByKey(tid);
                Task t=tasks.get(0);
                sid=t.getFirstsm();
                SubmissionMapper submapper = sqs.getMapper(SubmissionMapper.class);
                do{
                    List<Submission> submissions = submapper.selectByKey(sid);
                    Submission submission = submissions.get(0);
                    submapper.CloseTutorJudge(sid);
                    sid=submission.getNext();
                }
                while(sid!=null);
            }
            case "3":
            {
                List<Task> tasks = mapper.selectByKey(tid);
                Task t=tasks.get(0);
                sid=t.getFirstsm();
                int num=Integer.parseInt(t.getSubmitNum());
                SubmissionMapper submapper = sqs.getMapper(SubmissionMapper.class);
                for(int i=0;i<num;i++)
                {
                    List<Submission> submissions = submapper.selectByKey(sid);
                    Submission s = submissions.get(0);
                    String ss=s.getSubmitID()+","+s.getName()+","+s.getSubmitStatus()+","+s.getJudgeStatus()+","+s.getStartTime()+","+s.getDeadLine()+","+s.getSubmitTeams()+";";
                    writer.write(ss);
                    sid=s.getNext();
                }
            }
            case "4":
            {
                List<Task> tasks = mapper.selectByKey(tid);
                Task t=tasks.get(0);
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
