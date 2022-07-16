package com.serverlet;

import com.mapper.studentMapper;
import com.mapper.submissionMapper;
import com.mapper.taskMapper;
import com.mapper.tutorMapper;
import com.test.pojo.*;
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

@WebServlet(name = "AdminServlet", value = "/AdminServlet")
public class AdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String resource = "mybatis-config.xml";
        String ret=request.getParameter("ret");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        InputStream is= Resources.getResourceAsStream(resource);
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
        //获取SqlSession对象，来执行sql
        SqlSession sqs=factory.openSession();
        switch (ret)
        {
            case "1":
            {
                studentMapper sM=sqs.getMapper(studentMapper.class);
                List<student> students=sM.selectAll();
                PrintWriter writer = response.getWriter();
                for(student s:students)
                {
                    writer.write(s.toString());
                }
                break;
            }
            case "2":
            {
                tutorMapper tm = sqs.getMapper(tutorMapper.class);
                List<tutor> tutors=tm.selectAll();
                PrintWriter writer = response.getWriter();
                for(tutor t:tutors)
                {
                    writer.write(t.toString());
                }
                break;
            }
            case "3":
            {
                String sid = request.getParameter("sid");
                studentMapper sM=sqs.getMapper(studentMapper.class);
                List<student> students=sM.selectAll();
                PrintWriter writer = response.getWriter();
                for(student s:students)
                {
                    if(s.getSid().contains(sid))
                        writer.write(s.toString());
                }
                break;
            }
            case "4":
            {
                String tid = request.getParameter("tid");
                tutorMapper tm = sqs.getMapper(tutorMapper.class);
                List<tutor> tutors=tm.selectAll();
                PrintWriter writer = response.getWriter();
                for(tutor t:tutors)
                {
                    if(t.getTid().contains(tid))
                        writer.write(t.toString());
                }
                break;
            }
            case "5":
            {
                taskMapper mapper = sqs.getMapper(taskMapper.class);
                List<task> tasks = mapper.selectAll();
                PrintWriter writer = response.getWriter();
                for(task t:tasks)
                {
                    writer.write(t.toString());
                }
                break;
            }
            case "6":
            {
                String id=request.getParameter("tid");
                taskMapper mapper = sqs.getMapper(taskMapper.class);
                List<task> tasks = mapper.selectAll();
                PrintWriter writer = response.getWriter();
                for(task t:tasks)
                {
                    if(t.getTaskid().contains(id))
                        writer.write(t.toString());
                }
                break;
            }
            case "7":
            {
                String tid=request.getParameter("tid");
                taskMapper mapper = sqs.getMapper(taskMapper.class);
                List<task> tasks = mapper.selectByKey(tid);
                PrintWriter writer = response.getWriter();
                for(task t:tasks)
                {
                    writer.write(t.toString());
                }
                break;
            }
            case "8":
            {
                String sid=request.getParameter("sid");
                submissionMapper mapper = sqs.getMapper(submissionMapper.class);
                List<submission> submissions = mapper.selectByKey(sid);
                PrintWriter writer = response.getWriter();
                submission s=submissions.get(0);
                String reportType=s.getName();
                String startTime=s.getStartTime();
                String deadLine=s.getDeadLine();
                String id=request.getParameter("tid");
                taskMapper tm = sqs.getMapper(taskMapper.class);
                List<task> tasks = tm.selectByKey(id);
                task t=tasks.get(0);
                String des=t.getDescription();
                String name=t.getName();
                writer.write(id+","+name+","+reportType+","+startTime+","+deadLine+","+des+";");
                break;
            }
        }
        sqs.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
          this.doGet(request,response);
    }
}
