package com.serverlet;

import com.jcraft.jsch.Session;
import com.mapper.*;
import com.test.pojo.fragment;
import com.test.pojo.opinionTutorCache;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/teacherGetProjectData")
public class TeacherGetProjectDataServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String need=req.getParameter("need").toString();
        if(need.equals("rid")) {
            //System.out.println("need rid");
            HttpSession session = req.getSession();
            String tID = session.getAttribute("t").toString();

            if (tID == null) {
                resp.sendRedirect("/0628JavaWebExercise_war/index.html");
            }

            List<String> Judged_rids = null;

            List<String> rids = (List<String>) session.getAttribute("r");
            try {
                Judged_rids = getrIDbytID(tID);
            } catch (Exception e) {
                e.printStackTrace();
            }


            String rIDs = "[";
            String rIDs_judged = "[";
            for (String rid : rids) {
                //System.out.println(isLegaltoaudit(rid));
                if (rid != null && isLegaltoaudit(rid)) {
                    for (String r : Judged_rids) {
                        if (r.equals(rid)) {
                            rIDs_judged = rIDs_judged + "\"" + rid.toString() + "\",";
                        } else {
                            rIDs = rIDs + "\"" + rid.toString() + "\",";
                        }
                    }
                }
            }
            if (rIDs.length() > 1) {
                rIDs = rIDs.substring(0, rIDs.length() - 1);
            }
            if (rIDs_judged.length() > 1) {
                rIDs_judged = rIDs_judged.substring(0, rIDs_judged.length() - 1);
            }
            rIDs = rIDs + "]";
            rIDs_judged = rIDs_judged + "]";

            resp.setContentType("text/text;charset=utf-8");
            resp.setCharacterEncoding("utf-8");
            resp.setStatus(200);
            PrintWriter printWriter = resp.getWriter();

            printWriter.write("{\"rids\":"+rIDs+",\"judged\":"+rIDs_judged+"}");
            //System.out.println("rid ok");
        }
        else if(need.equals("demo")) {
            //System.out.println("need demo");
            HttpSession session = req.getSession();
            String tID = session.getAttribute("t").toString();
            if (tID == null) {
                resp.sendRedirect("/0628JavaWebExercise_war/index.html");
            }

            String rID = req.getParameter("rid").toString();
            System.out.println("rid="+rID);
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
            //System.out.println("demo ok");
        }
        else if(need.equals("all")){
            //System.out.println("need all");
            HttpSession session = req.getSession();
            String tID = session.getAttribute("t").toString();
            if (tID == null) {
                resp.sendRedirect("/0628JavaWebExercise_war/index.html");
            }

            String rID = req.getParameter("rid").toString();
            //System.out.println(rID);
            String name="";
            String description="";
            String details="";
            String cache="false";

            try {
                List<String> cache_rids=getCacherIDbytID(tID);
                for(String rid:cache_rids){
                    if(rid.equals(rID)){
                        cache="true";
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

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

            printWriter.write("{\"name\":\""+name+"\",\"description\":\""+description+"\",\"details\":\""+details+"\",\"cache\":\""+cache+"\"}");
            //System.out.println("all ok");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        //System.out.println("oh post!");
        resp.setContentType("text/text;charset=utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setStatus(200);
        PrintWriter printWriter = resp.getWriter();

        String rID = req.getParameter("rid");
        HttpSession session = req.getSession();
        String tID = session.getAttribute("t").toString();
//        String fm = "";

        String status = req.getParameter("status");
        String type = req.getParameter("type");
        //System.out.println(status);
        if(status.equals("judged")) {
            System.out.println("judged!");
            if(type.equals("score")){
                String score = ""+getauditedscore(rID,tID);
                System.out.println("score:"+score);
                printWriter.write(score);
                return;
            }
        }else {
            if(type.equals("score")){
                String score = getCachescore(rID,tID);
                System.out.println("score:"+score);
                printWriter.write(score);
                return;
            }
        }
        //System.out.println("fm:"+fm);

        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
//        fragmentMapper tm = sqlSession.getMapper(fragmentMapper.class);

        //System.out.println("oh tm!");

//        String text = getTextByFirstFm(fm, tm);
        reportMapper reportMapper = sqlSession.getMapper(reportMapper.class);

        String text = reportMapper.selectByRid(rID).get(0).getData();

        //System.out.println("oh text!");

        if(text!=null) {
            System.out.println("text:"+text);
        }else{
            System.out.println("text null");
        }

        printWriter.write(text);
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

    public static List<String> getCacherIDbytID(String tID) throws Exception{
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        opinionTutorCahceMapper tm = sqlSession.getMapper(opinionTutorCahceMapper.class);
        List<opinionTutorCache> s=tm.selectBytID(tID);
        sqlSession.close();
        return s.stream().map(opinionTutorCache::getrID).collect(Collectors.toList());
    }

    public static String getSubmitIDByrID(String rid) throws Exception {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        reportMapper tm = sqlSession.getMapper(reportMapper.class);
        //System.out.println("rid"+rid);
        String s=tm.selectByKey(rid).get(0).getSubmitID();
        sqlSession.close();
        return s;
    }

    public static String gettaskIDByrID(String rid) throws Exception{
        String submitID=getSubmitIDByrID(rid);
        //System.out.println("submitID"+submitID);

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
        //System.out.println("taskID"+taskID);

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
        public static String getCachescore(String rid,String tid) throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        opinionTutorCahceMapper tm=sqlSession.getMapper(opinionTutorCahceMapper.class);
        String s=""+tm.selectByKey(rid,tid).get(0).getScore();

        sqlSession.close();
        return s;
    }

    public static int getauditedscore(String rid,String tid) throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        opiniontutorMapper tm=sqlSession.getMapper(opiniontutorMapper.class);
        System.out.println("rid:"+rid);
        System.out.println("tid:"+tid);
        int s=tm.selectByKey(rid,tid).get(0).getScore();

        sqlSession.close();
        return s;
    }

//    public static String getfmidByrid(String rid) throws IOException {
//        String resource = "mybatis-config.xml";
//        InputStream inputStream = Resources.getResourceAsStream(resource);
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//
//        reportMapper tm = sqlSession.getMapper(reportMapper.class);
//        String s=tm.selectByKey(rid).get(0).getFirstFm();
//        sqlSession.close();
//        return s;
//    }

//    public static String getTextByStartfmid(String startfmid) throws IOException {
//        String resource = "mybatis-config.xml";
//        InputStream inputStream = Resources.getResourceAsStream(resource);
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//        fragmentMapper tm = sqlSession.getMapper(fragmentMapper.class);
//
//        String text = "";
//        for (String s = startfmid; (s!=null||s.equals("")); s = tm.selectByKey(s).get(0).getNext()) {
//            System.out.println(tm.selectByKey(s).get(0).getData());
//            text += tm.selectByKey(s).get(0).getData().replace("\r\n", "<br>");
//        }
//        sqlSession.close();
//        System.out.println("text:"+text);
//        return text;
//    }

//    public static String getTextByFirstFm(String firstFm, fragmentMapper mapper) {
//        StringBuilder text = new StringBuilder();
//        fragment f1 = mapper.selectByKey(firstFm).get(0);
//        text.append(f1.getData());
//        while (!f1.getNext().equals("")) {
//            f1 = mapper.selectByKey(f1.getNext()).get(0);
//            text.append(f1.getData());
//        }
//        return text.toString();
//    }

    public static boolean isLegaltoaudit(String rid) throws IOException{
        //先去report里面找submitid，然后再返回1或0
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        reportMapper tm=sqlSession.getMapper(reportMapper.class);
        String submitID=tm.selectByKey(rid).get(0).getSubmitID();
        submissionMapper tm1=sqlSession.getMapper(submissionMapper.class);
        int state=Integer.parseInt(tm1.selectByKey(submitID).get(0).getJudgeStatus());
        sqlSession.close();
        return state==1?true:false;
    }

    public static String getTextByrid(String rid) throws IOException {//可以通过rid返回文章内容
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        reportMapper mapper = sqlSession.getMapper(reportMapper.class);
        return mapper.selectByRid(rid).get(0).getData();
        //return getTextByStartfmid(getfmidByrid(rid));

    }

//    public static String getFmidByridAndtid(String rid,String tid) throws IOException {
//        String resource = "mybatis-config.xml";
//        InputStream inputStream = Resources.getResourceAsStream(resource);
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//
//        opinionTutorCahceMapper tm=sqlSession.getMapper(opinionTutorCahceMapper.class);
//        String fmid=tm.selectByKey(rid,tid).get(0).getFirstFm();
//        sqlSession.close();
//
//        return fmid;
//    }

//    public static String getFirstfmidByrID(String rid) throws Exception{
//        String resource = "mybatis-config.xml";
//        InputStream inputStream = Resources.getResourceAsStream(resource);
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//
//        opiniontutorMapper tm=sqlSession.getMapper(opiniontutorMapper.class);
//        //找到rid对应的fmid
//        List<opiniontutor> l=tm.selectAll();
//        //遍历l，找到rID为rid的opiniontutor对象
//        String fmid=null;
//        for(opiniontutor o:l){
//            if(o.getrID().equals(rid)){
//                fmid=o.getFirstFm();
//                break;
//            }
//        }
//        sqlSession.close();
//        return fmid;
//    }
}
