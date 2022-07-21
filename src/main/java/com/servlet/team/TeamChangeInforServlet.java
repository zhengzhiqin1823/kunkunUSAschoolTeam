package com.servlet.team;

import com.alibaba.fastjson.JSONObject;
import com.mapper.AdminMapper;
import com.mapper.TeamMapper;
import com.servlet.admin.HttpGetJson;
import com.servlet.login.LoginServlet;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;


@WebServlet("/team/personal/changeInfor")
public class TeamChangeInforServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String s = LoginServlet.checkLogin(req);
        if(s.isEmpty()) {
            resp.sendRedirect("/0628JavaWebExercise_war");
            return;
        }
        String type = req.getParameter("user");

        if(type==null) {
            req.setCharacterEncoding("UTF-8");//请求编码类型
            resp.setCharacterEncoding("UTF-8");//响应编码类型
            resp.setContentType("application/json");//响应数据类型
            JSONObject reqData = HttpGetJson.getJson(req);

            String name = reqData.get("name").toString();
            String email = reqData.get("email").toString();
            String tel = reqData.get("tel").toString();

            /*访问数据库，生成一个HTML文件*/
            String resource = "mybatis-config.xml";
            InputStream is = Resources.getResourceAsStream(resource);
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
            //获取SqlSession对象，来执行sql
            SqlSession sqs = factory.openSession();
            //执行sql
            TeamMapper mapper = sqs.getMapper(TeamMapper.class);

            HttpSession session = req.getSession();
            Object id = session.getAttribute("id");
            String teamID = id.toString();
            mapper.updateName(teamID, name);
            mapper.updateEmail(teamID, email);
            mapper.updateTel(teamID, tel);
            sqs.commit();
            sqs.close();
        }
        else {
            req.setCharacterEncoding("UTF-8");//请求编码类型
            resp.setCharacterEncoding("UTF-8");//响应编码类型
            resp.setContentType("application/json");//响应数据类型
            JSONObject reqData = HttpGetJson.getJson(req);

            String name = reqData.get("name").toString();
            String email = reqData.get("email").toString();
            String tel = reqData.get("tel").toString();

            /*访问数据库，生成一个HTML文件*/
            String resource = "mybatis-config.xml";
            InputStream is = Resources.getResourceAsStream(resource);
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
            //获取SqlSession对象，来执行sql
            SqlSession sqs = factory.openSession();
            //执行sql
            AdminMapper mapper = sqs.getMapper(AdminMapper.class);

            HttpSession session = req.getSession();
            Object id = session.getAttribute("id");
            String aid = id.toString();
            mapper.updateName(aid, name);
            mapper.updateEmail(aid, email);
            mapper.updateTel(aid, tel);
            sqs.commit();
            sqs.close();
        }

    }
}
