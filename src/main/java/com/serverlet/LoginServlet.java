package com.serverlet;

import com.mapper.teamMapper;
import com.mapper.tutorMapper;
import com.mapper.adminMapper;
import com.test.pojo.team;
import com.test.pojo.tutor;
import com.test.pojo.admin;
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
import java.util.List;

@WebServlet("/login")   //登陆
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String loginStatus = checkLogin(req);

        resp.setContentType("text/text;charset=utf-8");
        resp.setCharacterEncoding("utf-8");
        if (loginStatus != null) {
            resp.setStatus(200);
            PrintWriter printWriter = resp.getWriter();
            printWriter.write(loginStatus);
        } else {
            resp.sendError(401);
        }
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
        int flag = 0;

        //执行sql
        try {
            adminMapper adminMapper = sqs.getMapper(adminMapper.class);
            List<admin> admins = adminMapper.selectById(id);
            sqs.commit();
            if (admins.size() > 0 && admins.get(0).password.equals(password)) {
                flag = 1;
            } else {
                tutorMapper tutormapper = sqs.getMapper(tutorMapper.class);
                List<tutor> tutors = tutormapper.selectByTid(id);
                sqs.commit();
                if (tutors.size() > 0 && tutors.get(0).password.equals(password)) {
                    flag = 2;
                }
                else {
                    teamMapper teamMapper = sqs.getMapper(teamMapper.class);
                    List<team> teams = teamMapper.selectByKey(id);
                    sqs.commit();
                    if (teams.size() > 0 && teams.get(0).getPassword().equals(password)) {
                        flag = 3;
                    }
                }
            }
            sqs.close();

            resp.setContentType("text/text;charset=utf-8");
            resp.setCharacterEncoding("utf-8");

            if (flag == 0) {
                resp.sendError(401);
            } else {

                resp.setStatus(200);
                PrintWriter printWriter = resp.getWriter();

                Cookie cookie = new Cookie("id", id);
                resp.addCookie(cookie);

                HttpSession session = req.getSession();
                session.setAttribute("id", id);

                if (flag == 1) {
                    printWriter.write("admin");
                    session.setAttribute("type", "admin");
                } else if (flag == 2) {
                    printWriter.write("tutor");
                    session.setAttribute("type", "tutor");
                } else if (flag == 3) {
                    printWriter.write("team");
                    session.setAttribute("type", "team");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //req.getRequestDispatcher("studentHome.html").forward(req, resp);
    }

    public static String checkLogin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("type") != null) {
            return session.getAttribute("type").toString();
        } else {
            return null;
        }
    }
}
