package com.serverlet;

import com.mapper.SubmissionMapper;
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

@WebServlet(name = "submiUpdateServlet", value = "/submiUpdateServlet")
public class submiUpdateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String submitID=request.getParameter("submitID");
        String name=request.getParameter("name");
        String startTime=request.getParameter("startTime");
        String deadLine=request.getParameter("deadLine");
        String description=request.getParameter("description");
        //获取SqlSession对象，来执行sql
        String resource = "mybatis-config.xml";
        InputStream is= Resources.getResourceAsStream(resource);
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
        SqlSession sqs=factory.openSession();
        SubmissionMapper submissionMapper = sqs.getMapper(SubmissionMapper.class);
        submissionMapper.updateAllByKey(submitID,name,startTime,deadLine,description);
        response.getWriter().write("1");
        sqs.commit();
        sqs.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        doGet(request,response);
    }
}
