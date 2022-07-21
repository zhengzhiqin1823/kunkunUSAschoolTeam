package com.serverlet;

import com.mapper.SubmissionMapper;
import com.test.pojo.submission;
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

@WebServlet(name = "submissionServlet", value = "/submissionServlet")
public class SubmissionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //检测用户是否登陆
        boolean isLogin = false;
        HttpSession session = request.getSession();
        Cookie[] cookies = request.getCookies();
        Object id = session.getAttribute("id");
        Object type = session.getAttribute("type");
        if(id!=null&&type!=null) {
            if(type.equals("admin")) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("id")) {
                        if (cookie.getValue().equals(id)) {
                            isLogin = true;
                            break;
                        }
                    }
                }
            }
        }
        //如果没有登陆,则返回登陆
        if (!isLogin) {
            response.sendRedirect("/0628JavaWebExercise_war");
            return;
        }
        String sid=request.getParameter("sid");
        String ret = request.getParameter("ret");
        String resource = "mybatis-config.xml";
        InputStream is= Resources.getResourceAsStream(resource);
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
        //获取SqlSession对象，来执行sql
        SqlSession sqs=factory.openSession();
        SubmissionMapper mapper = sqs.getMapper(SubmissionMapper.class);
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        switch (ret)
        {
            case "1":{
                List<submission> submissions = mapper.selectByKey(sid);
                String s=submissions.get(0).getSubmitStatus();
                if("1".equals(s))
                {
                    mapper.CloseSubmit(sid);
                }
                else
                {
                    mapper.OpenSubmit(sid);
                }
                break;
            }
            case "2":{
                List<submission> submissions = mapper.selectByKey(sid);
                String s=submissions.get(0).getJudgeStatus();
                if("1".equals(s))
                {
                    mapper.CloseTutorJudge(sid);
                }
                else
                {
                    mapper.OpenTutorJudge(sid);
                }
                break;
            }
            case "3":{
                List<submission> submissions = mapper.selectByKey(sid);
                String s=submissions.get(0).getDescription();
                String ss=submissions.get(0).getName();
                String st=submissions.get(0).getStartTime();
                String dt=submissions.get(0).getDeadLine();
                writer.write(s+","+ss+","+st+","+dt);
                break;
            }
        }
        sqs.commit();
        sqs.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }
}
