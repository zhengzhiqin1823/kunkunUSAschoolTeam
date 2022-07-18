package com.serverlet;

import com.jcraft.jsch.Session;
import com.mapper.*;
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
        String need = req.getParameter("need").toString();
        switch (need) {
            case "rid": {
                //System.out.println("need rid");
                HttpSession session = req.getSession();
                Object tID = session.getAttribute("t");

                if (tID == null) {
                    resp.sendRedirect("/0628JavaWebExercise_war");
                }

                List<String> Judged_rids = null;

                List<String> rids = (List<String>) session.getAttribute("r");
                try {
                    Judged_rids = getrIDbytID(tID.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }


                String rIDs = "[";
                String rIDs_judged = "[";
                for (String rid : rids) {
                    //System.out.println(isLegaltoaudit(rid));
                    if (rid != null && isLegaltoaudit(rid)) {
                        boolean flag = false;
                        for (String r : Judged_rids) {
//                            if (r.equals(rid)) {
//                                rIDs_judged = rIDs_judged + "\"" + rid.toString() + "\",";
//                            } else {
//                                rIDs = rIDs + "\"" + rid.toString() + "\",";
//                            }
                            if (r.equals(rid)) {
                                flag = true;
                                break;
                            }
                        }
                        if (flag) {
                            rIDs_judged = rIDs_judged + "\"" + rid.toString() + "\",";
                        } else {
                            rIDs = rIDs + "\"" + rid.toString() + "\",";
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

                printWriter.write("{\"rids\":" + rIDs + ",\"judged\":" + rIDs_judged + "}");
                //System.out.println("{\"rids\":" + rIDs + ",\"judged\":" + rIDs_judged + "}");
                //System.out.println("rid ok");
                break;
            }
            case "demo": {
                //System.out.println("need demo");
                HttpSession session = req.getSession();
                Object tID = session.getAttribute("t");

                if (tID == null) {
                    resp.sendRedirect("/0628JavaWebExercise_war");
                }
                String rID = req.getParameter("rid").toString();
                //System.out.println("rid=" + rID);
                String description = "";
                String name = "";

                try {
                    description = getReportDescriptionByrid(rID);
                } catch (Exception e) {
                    System.out.println(1 + "error");
                    e.printStackTrace();
                }

                try {
                    name = getTeamNameByrid(rID);
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
                break;
            }
            case "all": {
                //System.out.println("need all");
                HttpSession session = req.getSession();
                Object tID = session.getAttribute("t");

                if (tID == null) {
                    resp.sendRedirect("/0628JavaWebExercise_war");
                }

                String rID = req.getParameter("rid").toString();
                //System.out.println(rID);
                String name = "";
                String description = "";
                String details = "";
                String cache = "false";

                try {
                    List<String> cache_rids = getCacherIDbytID(tID.toString());
                    for (String rid : cache_rids) {
                        if (rid.equals(rID)) {
                            cache = "true";
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    name = getTaskNameByrid(rID);
                } catch (Exception e) {
                    System.out.println(2 + "error");
                    e.printStackTrace();
                }

                try {
                    description = getSubmissionDescriptionByrid(rID);
                } catch (Exception e) {
                    System.out.println(1 + "error");
                    e.printStackTrace();
                }

                try {
                    details = getTextByrid(rID);
                } catch (Exception e) {
                    System.out.println(0 + "error");
                    e.printStackTrace();
                }

                resp.setContentType("text/text;charset=utf-8");
                resp.setCharacterEncoding("utf-8");
                resp.setStatus(200);
                PrintWriter printWriter = resp.getWriter();

                printWriter.write("{\"name\":\"" + name + "\",\"description\":\"" + description + "\",\"details\":\"" + details + "\",\"cache\":\"" + cache + "\"}");
                //System.out.println("all ok");
                break;
            }
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
        if (status.equals("judged")) {
            //System.out.println("judged!");
            if (type.equals("score")) {
                String score = "" + getauditedscore(rID, tID);
                //System.out.println("score:" + score);
                printWriter.write(score);
                return;
            }
        } else {
            if (type.equals("score")) {
                String score = getCachescore(rID, tID);
                //System.out.println("score:" + score);
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
        opiniontutorMapper opiniontutorMapper = sqlSession.getMapper(opiniontutorMapper.class);
        opinionTutorCahceMapper opinionTutorCahceMapper = sqlSession.getMapper(opinionTutorCahceMapper.class);

        List<opinionTutorCache> opinionTutorCaches = opinionTutorCahceMapper.selectByKey(rID, tID);
        List<opiniontutor> opiniontutors = opiniontutorMapper.selectByKey(rID, tID);
        String text;

        if (opiniontutors.size() == 0) {
            if (opinionTutorCaches.size() == 0) {
                text = "";
            } else {
                text = opinionTutorCaches.get(0).getData();
            }
        }else {
            text = opiniontutors.get(0).getData();
        }


        text = text.replace("\r\n", "&#13;");
        text = text.replace("\n", "&#13;");
        //System.out.println("oh text!");

//        if (text != null) {
//            System.out.println("text:" + text);
//        } else {
//            System.out.println("text null");
//        }

        printWriter.write(text);
    }

    public static List<String> getrIDbytID(String tID) throws Exception {//这个的作业是通过tID在opiniontutor表中找到rID
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        opiniontutorMapper tm = sqlSession.getMapper(opiniontutorMapper.class);
        List<opiniontutor> s = tm.selectBytID(tID);
        sqlSession.close();
        return s.stream().map(opiniontutor::getrID).collect(Collectors.toList());
    }

    public static List<String> getCacherIDbytID(String tID) throws Exception {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        opinionTutorCahceMapper tm = sqlSession.getMapper(opinionTutorCahceMapper.class);
        List<opinionTutorCache> s = tm.selectBytID(tID);
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
        String s = tm.selectByKey(rid).get(0).getSubmitID();
        sqlSession.close();
        return s;
    }

    public static String getTaskNameByrid(String rid) throws Exception {
        String taskID = gettaskIDByrID(rid);
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        taskMapper taskMapper = sqlSession.getMapper(taskMapper.class);
        task task = taskMapper.selectByKey(taskID).get(0);
        return task.getName();
    }

    public static String gettaskIDByrID(String rid) throws Exception {
        String submitID = getSubmitIDByrID(rid);
        //System.out.println("submitID"+submitID);

        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        submissionMapper tm = sqlSession.getMapper(submissionMapper.class);
        String taskID = tm.selectByKey(submitID).get(0).getTaskID();
        sqlSession.close();
        return taskID;
    }

    public static String getTeamNameByrid(String rid) throws Exception {
        String taskID = gettaskIDByrID(rid);
        //System.out.println("taskID"+taskID);

        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        teamMapper teamMapper = sqlSession.getMapper(teamMapper.class);
        reportMapper reportMapper = sqlSession.getMapper(reportMapper.class);
        report report = reportMapper.selectByRid(rid).get(0);
        taskMapper tm = sqlSession.getMapper(taskMapper.class);

        team team = teamMapper.selectByKey(report.getTeamid()).get(0);

//        String name = tm.selectByKey(taskID).get(0).getName();
        String name = team.getName();
        sqlSession.close();
        return name;
    }

    public static String getReportDescriptionByrid(String rid) throws Exception {
        String taskID = gettaskIDByrID(rid);

        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        reportMapper reportMapper = sqlSession.getMapper(reportMapper.class);
        report report = reportMapper.selectByRid(rid).get(0);

        taskMapper tm = sqlSession.getMapper(taskMapper.class);
//        String name = tm.selectByKey(taskID).get(0).getDescription();
        String name = report.getData();
        if (name.length() > 240) {
            name = name.substring(0, 240);
            name += "...";
        }

        name = name.replace("\r\n", " ");
        name = name.replace("\n", " ");
        sqlSession.close();
        return name;
    }

    public static String getCachescore(String rid, String tid) throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        opinionTutorCahceMapper tm = sqlSession.getMapper(opinionTutorCahceMapper.class);
        String s = "" + tm.selectByKey(rid, tid).get(0).getScore();

        sqlSession.close();
        return s;
    }

    public static int getauditedscore(String rid, String tid) throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        opiniontutorMapper tm = sqlSession.getMapper(opiniontutorMapper.class);
        //System.out.println("rid:" + rid);
        //System.out.println("tid:" + tid);
        int s = tm.selectByKey(rid, tid).get(0).getScore();

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

    public static boolean isLegaltoaudit(String rid) throws IOException {
        //先去report里面找submitid，然后再返回1或0
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        reportMapper tm = sqlSession.getMapper(reportMapper.class);
        String submitID = tm.selectByKey(rid).get(0).getSubmitID();
        submissionMapper tm1 = sqlSession.getMapper(submissionMapper.class);
        int state = Integer.parseInt(tm1.selectByKey(submitID).get(0).getJudgeStatus());
        sqlSession.close();
        return state == 1 ? true : false;
    }

    public static String getTextByrid(String rid) throws IOException {//可以通过rid返回文章内容
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        reportMapper mapper = sqlSession.getMapper(reportMapper.class);
        String text = mapper.selectByRid(rid).get(0).getData();
        text = text.replace("\r\n", "<br/>");
        text = text.replace("\n", "<br/>");
        return text;
        //return getTextByStartfmid(getfmidByrid(rid));

    }

    public static String getSubmissionDescriptionByrid(String rid) throws Exception {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        reportMapper mapper = sqlSession.getMapper(reportMapper.class);
        submissionMapper submissionMapper = sqlSession.getMapper(submissionMapper.class);

        report report = mapper.selectByKey(rid).get(0);
        return submissionMapper.selectByKey(report.getSubmitID()).get(0).getDescription();
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
