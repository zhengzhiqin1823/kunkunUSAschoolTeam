package com.serverlet;

import com.alibaba.fastjson.JSONObject;
import com.mapper.teamMapper;
import com.test.pojo.team;
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

@WebServlet("/team/personal/changePassword")
public class TeamChangePasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String s = LoginServlet.checkLogin(req);
        if (s.isEmpty()) {
            resp.sendRedirect("/0628JavaWebExercise_war");
            return;
        }

        req.setCharacterEncoding("UTF-8");//请求编码类型
        resp.setCharacterEncoding("UTF-8");//响应编码类型

        JSONObject reqData = HttpGetJson.getJson(req);
        PrintWriter p = resp.getWriter();

        String old = reqData.get("old").toString();
        String New = reqData.get("new").toString();

        String resource = "mybatis-config.xml";
        InputStream is = Resources.getResourceAsStream(resource);
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
        //获取SqlSession对象，来执行sql
        SqlSession sqs = factory.openSession();
        //执行sql
        teamMapper mapper = sqs.getMapper(teamMapper.class);

        HttpSession session = req.getSession();
        Object id = session.getAttribute("id");
        String teamID = id.toString();
        team team = mapper.selectByKey(teamID).get(0);
        if (team.getPassword().equals(old)) {
            mapper.updatePassword(teamID, New);
            sqs.commit();
            p.write("修改成功！");
        }
        else {
            p.write("原始密码错误！");
        }
        sqs.close();

    }
}