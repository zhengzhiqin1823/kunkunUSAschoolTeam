package com.servlet;

import com.mapper.studentMapper;
import com.mapper.tutorMapper;
import com.test.pojo.student;
import com.test.pojo.tutor;
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

@WebServlet(name = "tutorInsertServlet", value = "/tutorInsertServlet")
public class tutorInsertServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tid=request.getParameter("tid");
        String name=request.getParameter("name");
        String email=request.getParameter("email");
        String tel=request.getParameter("tel");
        String resource = "mybatis-config.xml";
        InputStream is= Resources.getResourceAsStream(resource);
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
        //获取SqlSession对象，来执行sql
        SqlSession sqs=factory.openSession();
        //执行sql
        tutorMapper mapper = sqs.getMapper(tutorMapper.class);
        String id="";
        if(tid.equals("default"))
        {
            List<tutor> tutors = mapper.selectAll();
            tutor t=tutors.get(tutors.size()-1);
            String s=t.getTid();
            int num=Integer.parseInt(s);
            num++;
            id+=num;
            System.out.println(id);
        }
        else
        {
            List<tutor> tutors = mapper.selectAll();
            for(tutor s:tutors)
            {
                if(tid.equals(s.getTid()))
                {
                    PrintWriter writer = response.getWriter();
                    writer.write("1");
                    return;
                }
            }
        }
        mapper.insert(id,"123456",name,email,tel);
        sqs.commit();
        sqs.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       this.doGet(request,response);
    }
}