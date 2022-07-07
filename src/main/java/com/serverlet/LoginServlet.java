package com.serverlet;

import com.mapper.studentMapper;
import com.mapper.tutorMapper;
import com.mapper.adminMapper;
import com.test.pojo.student;
import com.test.pojo.tutor;
import com.test.pojo.admin;
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
import java.util.List;

@WebServlet("/login")   //登陆
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String loginStatus=checkLogin(req);

        resp.setContentType("text/text;charset=utf-8");
        resp.setCharacterEncoding("utf-8");
        if(loginStatus!=null) {
            resp.setStatus(200);
            PrintWriter printWriter = resp.getWriter();
            printWriter.write(loginStatus);
        }else{
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
        adminMapper adminMapper=sqs.getMapper(com.mapper.adminMapper.class);
        List<admin> admins=adminMapper.selectById(id);
        sqs.commit();
        if(admins.get(0).password.equals(password)) {
            flag = 1;
        }else{
            tutorMapper tutormapper = sqs.getMapper(tutorMapper.class);
            List<tutor> tutors=tutormapper.selectByTid(id);
            sqs.commit();
            if(tutors.get(0).password.equals(password)) {
                flag=2;
            }else{
                studentMapper studentMapper = sqs.getMapper(studentMapper.class);
                List<student> students = studentMapper.selectBySid(id);
                sqs.commit();
                if (students.get(0).password.equals(password)) {
                    flag = 3;
                }
            }
        }
        sqs.close();

        resp.setContentType("text/text;charset=utf-8");
        resp.setCharacterEncoding("utf-8");

        if(flag==0) {
            resp.sendError(401);
        }else{
            resp.setStatus(200);
            PrintWriter printWriter=resp.getWriter();

            HttpSession session = req.getSession();
            session.setAttribute("id", id);

            if(flag==1){
                printWriter.write("admin");
                session.setAttribute("type", "admin");
            }else if(flag==2){
                printWriter.write("tutor");
                session.setAttribute("type", "tutor");
            }else if(flag==3){
                printWriter.write("student");
                session.setAttribute("type", "student");
            }
        }
        //req.getRequestDispatcher("studentHome.html").forward(req, resp);
    }

    public static String checkLogin(HttpServletRequest request){
        HttpSession session=request.getSession();
        if(session.getAttribute("type")!=null) {
            return session.getAttribute("type").toString();
        }else{
            return null;
        }
    }
}
