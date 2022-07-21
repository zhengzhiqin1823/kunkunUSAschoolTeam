package com.servlet.team;

import com.mapper.TeamMapper;
import com.test.pojo.team;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;


@WebServlet("/team/personal/getInfor")
public class TeamGetInforSevlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder json = new StringBuilder();
        HttpSession session = req.getSession();
        Object id = session.getAttribute("id");

        /*访问数据库，生成一个HTML文件*/
        String resource = "mybatis-config.xml";
        InputStream is = Resources.getResourceAsStream(resource);
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
        //获取SqlSession对象，来执行sql
        SqlSession sqs = factory.openSession();
        TeamMapper tm = sqs.getMapper(TeamMapper.class);
        team team = tm.selectByKey(id.toString()).get(0);

        json.append("{" +
                "\"teamID\":\""+id+"\"," +
                "\"email\":\""+team.getEmail()+"\"," +
                "\"name\":\""+team.getName()+"\"," +
                "\"tel\":\""+team.getTel() +
                "\"}");
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        writer.write(json.toString());

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


    }
}
