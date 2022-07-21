package com.servlet.tutor;

import com.mapper.ReportMapper;
import com.mapper.SubmissionMapper;
import com.mapper.TaskMapper;
import com.mapper.TeamMapper;
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
import java.io.PrintWriter;

@WebServlet("/TeacherJudgeS")
public class TeacherJudgeSServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        resp.setContentType("text/text;charset=utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setStatus(200);
        PrintWriter printWriter = resp.getWriter();

        String need=req.getParameter("need");
        String rID=req.getParameter("rid");

        if(need.equals("submissionName")){
            String submissionName=getnameInsubmission(rID);
            printWriter.write(submissionName);
        }else if(need.equals("teamName")){
            String teamName=getnameInTeam(rID);
            printWriter.write(teamName);
        }else if(need.equals("totalSize")){
            int totalSize=getreportTotalsize(rID);
            System.out.println("totalSize:"+totalSize);
            printWriter.write(totalSize+"");
        }else if(need.equals("deadline")){
            try {
                String DDL = getdeadlin(rID);
                printWriter.write(DDL);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    public static String getnameInsubmission(String rid) throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        ReportMapper tm=sqlSession.getMapper(ReportMapper.class);
        String s=tm.selectByKey(rid).get(0).getSubmitID();
        SubmissionMapper tm1=sqlSession.getMapper(SubmissionMapper.class);
        String name=tm1.selectByKey(s).get(0).getName();
        sqlSession.close();
        return name;
    }

    public static String getnameInTeam(String rid) throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        ReportMapper tm=sqlSession.getMapper(ReportMapper.class);
        String s=tm.selectByKey(rid).get(0).getTeamid();
        TeamMapper tm1=sqlSession.getMapper(TeamMapper.class);
        String name=tm1.selectByKey(s).get(0).getName();
        sqlSession.close();
        return name;
    }

    public static int getreportTotalsize(String rid) throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        ReportMapper tm = sqlSession.getMapper(ReportMapper.class);
        //System.out.println("report:"+tm.selectByKey(rid).get(0));
        //System.out.printf(tm.selectByKey(rid).get(0));
        int s = tm.selectByKey(rid).get(0).getData().length();
        System.out.println("l:"+s);
        sqlSession.close();
        return s;
    }

    public static String getdeadlin(String rid) throws Exception{
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        ReportMapper tm=sqlSession.getMapper(ReportMapper.class);
        String s=tm.selectByKey(rid).get(0).getTeamid();
        TeamMapper tm1=sqlSession.getMapper(TeamMapper.class);
        String taskid=tm1.selectByKey(s).get(0).getTaskID();
        TaskMapper tm2=sqlSession.getMapper(TaskMapper.class);
        String res=tm2.selectByKey(taskid).get(0).getDeadline();
        sqlSession.close();
        sqlSession.close();
        return res;
    }

}