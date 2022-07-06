package com.serverlet;

import com.mapper.studentMapper;
import com.test.pojo.student;
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
import java.util.List;

@WebServlet("/login")   //登陆
public class StudentLoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String id = req.getParameter("id");
        String password = req.getParameter("password");
        System.out.println("用户名：" + id);
        System.out.println("密码：" + password);
        System.out.println("在数据库中匹配用户名与密码......");
        String resource = "mybatis-config.xml";
        InputStream is = Resources.getResourceAsStream(resource);
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
        //获取SqlSession对象，来执行sql
        SqlSession sqs = factory.openSession();
        //执行sql
        studentMapper mapper = sqs.getMapper(studentMapper.class);
        List<student> students = mapper.selectBySid(id);
        sqs.commit();
        int flag = 0;
        if (students.get(0).password.equals(password))
            flag = 1;
//        mapper.insert("20111","123","zzq","2925@qq.com","12345");
//        sqs.commit();
        sqs.close();
        req.getRequestDispatcher("studentHome.html").forward(req, resp);

    }
}
