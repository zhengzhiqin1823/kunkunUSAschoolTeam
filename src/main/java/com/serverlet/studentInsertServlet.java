package com.example.demo;

import com.mapper.studentMapper;
import com.test.pojo.student;
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

@WebServlet(name = "studentInsertServlet", value = "/studentInsertServlet")
public class studentInsertServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       String sid=request.getParameter("sid");
       String name=request.getParameter("name");
       String email=request.getParameter("email");
       String tel=request.getParameter("tel");
       String resource = "mybatis-config.xml";
       InputStream is= Resources.getResourceAsStream(resource);
       SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
       //获取SqlSession对象，来执行sql
        SqlSession sqs=factory.openSession();
        //执行sql
        studentMapper sm=sqs.getMapper(studentMapper.class);
        String id="";
        if(sid.equals("default"))
        {
            List<student> students = sm.selectAll();
            student student = students.get(students.size() - 1);
            String s=student.getSid();
            int num=Integer.parseInt(s);
            num++;
            id+=num;
        }
        else
        {
            List<student> students = sm.selectAll();
            for(student s:students)
            {
                if(sid.equals(s.getSid()))
                {
                    PrintWriter writer = response.getWriter();
                    writer.write("1");
                    return;
                }
            }
        }
        sm.insert(id,"123456",name,email,tel);
        sqs.commit();
        sqs.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
          this.doGet(request,response);
    }
}
