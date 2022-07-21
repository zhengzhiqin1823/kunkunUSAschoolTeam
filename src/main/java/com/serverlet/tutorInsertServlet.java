package com.serverlet;

import com.mapper.TutorMapper;
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
        TutorMapper mapper = sqs.getMapper(TutorMapper.class);
        String id="";
        if(tid.equals("default"))
        {
            List<tutor> tutors = mapper.selectAll();
            int num=tutors.size();
            num++;
            id+="tutor";
            id+=num;
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
            id+=tid;
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
