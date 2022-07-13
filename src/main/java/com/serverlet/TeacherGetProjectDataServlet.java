package com.serverlet;

import com.mapper.*;
import com.test.pojo.opiniontutor;
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
import java.util.stream.Collectors;

@WebServlet("/teacherGetProjectData")
public class TeacherGetProjectDataServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String need=req.getParameter("need").toString();
        if(need.equals("rid")){
            HttpSession session = req.getSession();
            String tID = session.getAttribute("id").toString();
            if(tID==null) {
                resp.sendRedirect("/0628JavaWebExercise_war/index.html");
            }

            List<String> rids = null;
            try {
                rids= getrIDbytID(tID);
            } catch (Exception e) {
                e.printStackTrace();
            }

            String rIDs="[";
            for(String rid:rids){
                rIDs = rIDs+"\""+rid.toString()+"\",";
            }

            rIDs=rIDs.substring(0,rIDs.length()-1);
            rIDs=rIDs+"]";

            resp.setContentType("text/text;charset=utf-8");
            resp.setCharacterEncoding("utf-8");
            resp.setStatus(200);
            PrintWriter printWriter = resp.getWriter();

            printWriter.write(rIDs);
        }
        else if(need.equals("demo")) {
            HttpSession session = req.getSession();
            String tID = session.getAttribute("id").toString();
            if (tID == null) {
                resp.sendRedirect("/0628JavaWebExercise_war/index.html");
            }

            String rID = req.getParameter("rid").toString();
            String description="";
            String name="";

            try {
                description = getdescriptionByrid(rID);
            } catch (Exception e) {
                System.out.println(1 + "error");
                e.printStackTrace();
            }

            try {
                name = getnameByrid(rID);
            } catch (Exception e) {
                System.out.println(2 + "error");
                e.printStackTrace();
            }

            resp.setContentType("text/text;charset=utf-8");
            resp.setCharacterEncoding("utf-8");
            resp.setStatus(200);
            PrintWriter printWriter = resp.getWriter();

            printWriter.write("{ \"description\":\"" + description + "\", \"name\":\"" + name + "\" }");

        }
        else if(need.equals("all")){
            HttpSession session = req.getSession();
            String tID = session.getAttribute("id").toString();
            if (tID == null) {
                resp.sendRedirect("/0628JavaWebExercise_war/index.html");
            }

            String rID = req.getParameter("rid").toString();
            System.out.println(rID);
            String name="";
            String description="";
            String details="";

            try {
                name = getnameByrid(rID);
            } catch (Exception e) {
                System.out.println(2 + "error");
                e.printStackTrace();
            }

            try {
                description = getdescriptionByrid(rID);
            } catch (Exception e) {
                System.out.println(1 + "error");
                e.printStackTrace();
            }

            try{
                details=getTextByrid(rID);
            }catch (Exception e){
                System.out.println(0 + "error");
                e.printStackTrace();
            }

            resp.setContentType("text/text;charset=utf-8");
            resp.setCharacterEncoding("utf-8");
            resp.setStatus(200);
            PrintWriter printWriter = resp.getWriter();

            printWriter.write("{\"name\":\""+name+"\",\"description\":\""+description+"\",\"details\":\""+details+"\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    public static List<String> getrIDbytID(String tID) throws Exception{//这个的作业是通过tID在opiniontutor表中找到rID
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        opiniontutorMapper tm = sqlSession.getMapper(opiniontutorMapper.class);
        List<opiniontutor> s=tm.selectBytID(tID);
        sqlSession.close();
        return s.stream().map(opiniontutor::getrID).collect(Collectors.toList());
    }

    public static String getSubmitIDByrID(String rid) throws Exception
    {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        reportMapper tm = sqlSession.getMapper(reportMapper.class);
        String s=tm.selectByKey(rid).get(0).getSubmitID();
        sqlSession.close();;
        return s;
    }

    public static String gettaskIDByrID(String rid) throws Exception{
        String submitID=getSubmitIDByrID(rid);

        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        submissionMapper tm=sqlSession.getMapper(submissionMapper.class);
        String taskID=tm.selectByKey(submitID).get(0).getTaskID();
        sqlSession.close();
        return taskID;
    }
    public static String getnameByrid(String rid) throws Exception{
        String taskID=gettaskIDByrID(rid);

        String resource = "mybatis-config.xml";
        InputStream inputStream=Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        taskMapper tm=sqlSession.getMapper(taskMapper.class);
        String name=tm.selectByKey(taskID).get(0).getName();
        sqlSession.close();
        return name;
    }

    public static String getdescriptionByrid(String rid) throws Exception{
        String taskID=gettaskIDByrID(rid);

        String resource = "mybatis-config.xml";
        InputStream inputStream=Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        taskMapper tm=sqlSession.getMapper(taskMapper.class);
        String name=tm.selectByKey(taskID).get(0).getDescription();
        sqlSession.close();
        return name;
    }


    public static String getfmidByrid(String rid) throws IOException//这个是通过rid去查report表中的fmid
    {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        reportMapper tm = sqlSession.getMapper(reportMapper.class);
        String s=tm.selectByKey(rid).get(0).getFirstFm();
        sqlSession.close();
        return s;
    }

    public static String getTextByStartfmid(String startfmid) throws IOException {
        //7.6桂庆薪 这个函数传入fmid，然后返回整个文章，原版在Tutor里面
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        fragmentMapper tm = sqlSession.getMapper(fragmentMapper.class);

        String text = "";
        for (String s = startfmid; (s!=null); s = tm.selectByKey(s).get(0).getNext())
            text += tm.selectByKey(s).get(0).getData();
        sqlSession.close();
        return text;
    }


    public static String getTextByrid(String rid) throws IOException {//可以通过rid返回文章内容
        return getTextByStartfmid(getfmidByrid(rid));
    }


}


