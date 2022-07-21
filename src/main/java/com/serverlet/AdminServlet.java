package com.serverlet;

import com.mapper.SubmissionMapper;
import com.mapper.TaskMapper;
import com.mapper.TutorMapper;
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
            case "2":
            {
                TutorMapper tm = sqs.getMapper(TutorMapper.class);
                List<tutor> tutors=tm.selectAll();
                PrintWriter writer = response.getWriter();
                for(tutor t:tutors)
                {
                    writer.write(t.toString());
                }
                break;
            }

            case "4":
            {
                String tid = request.getParameter("tid");
                TutorMapper tm = sqs.getMapper(TutorMapper.class);
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
                TaskMapper mapper = sqs.getMapper(TaskMapper.class);
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
                TaskMapper mapper = sqs.getMapper(TaskMapper.class);
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
                TaskMapper mapper = sqs.getMapper(TaskMapper.class);
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
                SubmissionMapper mapper = sqs.getMapper(SubmissionMapper.class);
                List<submission> submissions = mapper.selectByKey(sid);
                PrintWriter writer = response.getWriter();
                submission s=submissions.get(0);
                String reportType=s.getName();
                String startTime=s.getStartTime();
                String deadLine=s.getDeadLine();
                String id=request.getParameter("tid");
                TaskMapper tm = sqs.getMapper(TaskMapper.class);
                List<task> tasks = tm.selectByKey(id);
                task t=tasks.get(0);
                String des=t.getDescription();
                String name=t.getName();
                writer.write(id+","+name+","+reportType+","+startTime+","+deadLine+","+des+";");
                break;
            }
            case "9":
            {
                String tid = request.getParameter("tid");
                String email = request.getParameter("email");
                String tel = request.getParameter("tel");
                String name = request.getParameter("name");
                TutorMapper mapper = sqs.getMapper(TutorMapper.class);
                mapper.update(name,email,tid,tel);
                sqs.commit();
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
