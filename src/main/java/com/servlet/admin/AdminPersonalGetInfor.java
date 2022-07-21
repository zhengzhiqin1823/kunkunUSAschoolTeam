package com.servlet.admin;

import com.mapper.AdminMapper;
import com.test.pojo.Admin;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;


@WebServlet("/admin/personal/getInfor")
public class AdminPersonalGetInfor extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder json = new StringBuilder();
        HttpSession session = req.getSession();
        Object id = session.getAttribute("id");
        if(id==null)
        {
            resp.sendRedirect("/0628JavaWebExercise_war");
            return;
        }

        /*访问数据库，生成一个HTML文件*/
        String resource = "mybatis-config.xml";
        InputStream is = Resources.getResourceAsStream(resource);
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
        //获取SqlSession对象，来执行sql
        SqlSession sqs = factory.openSession();
        AdminMapper adminMapper = sqs.getMapper(AdminMapper.class);
        Admin admin = adminMapper.selectById(id.toString()).get(0);


        json.append("{" +
                "\"teamID\":\""+id.toString()+"\"," +
                "\"email\":\""+admin.getEmail()+"\"," +
                "\"name\":\""+admin.getName()+"\"," +
                "\"tel\":\""+admin.getTel() +
                "\"}");
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        writer.write(json.toString());
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
