package com.serverlet;

import com.mapper.judgelinkMapper;
import com.test.pojo.judgelink;
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
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "linkJudge", value = "/linkJudge")
public class LinkJudgeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String link=request.getParameter("link");
        //System.out.println(link);

        //获取SqlSession对象，来执行sql
        String resource = "mybatis-config.xml";
        InputStream is= Resources.getResourceAsStream(resource);
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
        SqlSession sqs=factory.openSession();
        judgelinkMapper judgelinkMapper = sqs.getMapper(judgelinkMapper.class);
        List<judgelink> judgelinks = judgelinkMapper.selectByLink(link);
        List<judgelink> judgelinksss = judgelinkMapper.selectAll();

        //System.out.println(judgelinksss.toString());

        //respomnse

        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        //PrintWriter writer = response.getWriter();

        if(judgelinks.size()==0) {
            response.sendRedirect("/0628JavaWebExercise_war/teacherError.html");
            return;
        }

        String tid=judgelinks.get(0).getTid();

        HttpSession session = request.getSession();
        session.setAttribute("t",tid);

        //System.out.println("tid ok");

        List<String> rids = new ArrayList<>();

        for (judgelink j:judgelinks){
            String rid=j.getrID();
            rids.add(rid);
        }

        //System.out.println("rid ready");

        session.setAttribute("r",rids);
        sqs.close();

        //System.out.println("rid ok");

        //System.out.println("session ok");
//        response.sendRedirect("/0628JavaWebExercise_war/teacherHome.html");
        request.getRequestDispatcher("/WEB-INF/teacherHome.html").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        doGet(request,response);
    }
}
