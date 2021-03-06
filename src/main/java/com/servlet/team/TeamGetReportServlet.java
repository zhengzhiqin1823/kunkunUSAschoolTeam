package com.servlet.team;


import com.mapper.*;
import com.servlet.methods.StaticMethods;
import com.test.pojo.*;
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

@WebServlet("/team/report/getInfor")
public class TeamGetReportServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String submitID = req.getParameter("submitID");
        Object id = session.getAttribute("id"); //teamID

        /*访问数据库，生成一个HTML文件*/
        String resource = "mybatis-config.xml";
        InputStream is = Resources.getResourceAsStream(resource);
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
        //获取SqlSession对象，来执行sql
        SqlSession sqs = factory.openSession();
        //执行sql
        TaskMapper taskMapper = sqs.getMapper(TaskMapper.class);
        SubmissionMapper submissionMapper = sqs.getMapper(SubmissionMapper.class);
        ReportcaheMapper reportcaheMapper = sqs.getMapper(ReportcaheMapper.class);
        ReportMapper reportMapper = sqs.getMapper(ReportMapper.class);

        List<Submission> submissions = submissionMapper.selectByKey(submitID);
        Submission s = submissions.get(0);
        resp.setContentType("text/html;charset=UTF-8");

        String text;
        List<Report> reports = reportMapper.selectByTeamIDAndSubmitID((String) id, submitID);

        if(s.getSubmitStatus().equals("0")) {
            req.getRequestDispatcher("TeamReport.html").forward(req,resp);
            return;
        }if (reports.size() == 0) {
            List<ReportCache> ReportCaches = reportcaheMapper.selectByTeamIDAndSubmitID(id.toString(), submitID);
            if (ReportCaches.size() == 0) {
                text="";
            } else {
                text = StaticMethods.getCacheByTeamIdAndSubmitID(id.toString(),submitID);
            }
        } else {
            text = StaticMethods.getReportByTeamIdAndSubmitID(id.toString(),submitID);
            System.out.println(text);
        }
        resp.setCharacterEncoding("UTF-8");//响应编码类型
        String json = "{'submitName':"+s.getName()+",'deadLine':"+s.getDeadLine()+
                ",'submitID:'"+s.getSubmitID()+
                ",'description':" + s.getDescription()+"'text:'"+text+"}";
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(json);

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
