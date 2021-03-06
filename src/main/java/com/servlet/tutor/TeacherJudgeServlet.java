package com.servlet.tutor;

import com.mapper.*;
import com.test.pojo.OpinionTutorCache;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/teacherJudgeServlet")
public class TeacherJudgeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("get");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        System.out.println("post");
        HttpSession session = req.getSession();
        String tID = session.getAttribute("t").toString();
        if(tID!=null){
            System.out.println(tID);
        }else{
            System.out.println("tID error");
        }
        String rID = req.getParameter("rid").toString();
        if(rID!=null){
            System.out.println(rID);
        }else{
            System.out.println("rID error");
        }

        int score = Integer.parseInt(req.getParameter("score").toString());
        String judge_text=req.getParameter("judge_text").toString();
        if(judge_text!=null){
            System.out.println(judge_text);
        }else{
            System.out.println("judge_text error");
        }

        String type=req.getParameter("type").toString();
        if(type.equals("save")) {
            try {
                updateText(rID, tID, score, judge_text);
                System.out.println("??????");
            } catch (Exception e) {
                System.out.println("??????");
                e.printStackTrace();
            }
        }else if(type.equals("cache")){
            try {
                storeTextbrief(rID,tID,score,judge_text);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

//    private static String addFrontZero(long a)//???a??????10???????????????????????????a??????0
//    {
//        String s = String.valueOf(a);
//        while(s.length()<10)
//            s = "0"+s;
//        return s;
//    }

//    private static String findStartFmid() throws IOException {
//        String resource = "mybatis-config.xml";
//        InputStream inputStream = Resources.getResourceAsStream(resource);
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//
//        fragmentMapper tm = sqlSession.getMapper(fragmentMapper.class);
//
//        long maxFmid = -1;
//        for(int i = 0; i < tm.selectAllFmid().size(); i++)
//        {
//            if(Long.parseLong(tm.selectAllFmid().get(i)) > maxFmid)
//            {
//                maxFmid = Long.parseLong(tm.selectAllFmid().get(i));
//            }
//        }
//        long startFmid=maxFmid+1;
//        sqlSession.close();
//        return addFrontZero(startFmid);
//    }
//    public static void storeTextTofragment(String text) throws Exception {
//        //???????????????fragment???????????????fmid
//        String resource = "mybatis-config.xml";
//        InputStream inputStream = Resources.getResourceAsStream(resource);
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//
////        fragmentMapper tm = sqlSession.getMapper(fragmentMapper.class);
//
//        long maxFmid = -1;
//        for(int i = 0; i < tm.selectAllFmid().size(); i++)
//        {
//            if(Long.parseLong(tm.selectAllFmid().get(i)) > maxFmid)
//            {
//                maxFmid = Long.parseLong(tm.selectAllFmid().get(i));
//            }
//        }
//        long startFmid=maxFmid;
//
//        //??????startFmid,????????????next?????????data
//        String[] data=text.split("\\s{1,127}");
//        //??????data
//        for(int i=0;i<data.length;i++)
//        {
//            //??????next
//            startFmid+=1;
//            String next=data[i];
//            if(i!=data.length-1)
//            {
//                tm.insert(""+startFmid,""+(startFmid+1),next);
//            }
//            else
//            {
//                tm.insert(""+startFmid,"",next);
//            }
//        }
//        sqlSession.commit();
//        sqlSession.close();
//    }

//    public static String getFirstFmidbyrid(String rid) throws Exception
//    {
//        String resource = "mybatis-config.xml";
//        InputStream inputStream = Resources.getResourceAsStream(resource);
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//        reportMapper tm=sqlSession.getMapper(reportMapper.class);
//        String res=tm.selectByKey(rid).get(0).getFirstFm();
//        sqlSession.close();
//        return res;
//    }

//    private static String getFmid() throws IOException {
//        String resource = "mybatis-config.xml";
//        InputStream inputStream = Resources.getResourceAsStream(resource);
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//
////        fragmentMapper tm = sqlSession.getMapper(fragmentMapper.class);
//
//        long maxFmid = -1;
//        for(int i = 0; i < tm.selectAllFmid().size(); i++)
//        {
//            if(Long.parseLong(tm.selectAllFmid().get(i)) > maxFmid)
//            {
//                maxFmid = Long.parseLong(tm.selectAllFmid().get(i));
//            }
//        }
//        sqlSession.close();
//        return ""+maxFmid;
//    }

//    public static String storeCacheTextTofragment(String text) throws Exception {
//        //???????????????fragment???????????????fmid
//        String resource = "mybatis-config.xml";
//        InputStream inputStream = Resources.getResourceAsStream(resource);
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//
//        fragmentMapper tm = sqlSession.getMapper(fragmentMapper.class);
//
//        long maxFmid = -1;
//        for(int i = 0; i < tm.selectAllFmid().size(); i++)
//        {
//            if(Long.parseLong(tm.selectAllFmid().get(i)) > maxFmid)
//            {
//                maxFmid = Long.parseLong(tm.selectAllFmid().get(i));
//            }
//        }
//        long mark=maxFmid;
//        long startFmid=maxFmid;
//
//        //??????startFmid,????????????next?????????data
//        String[] data=text.split("\\s{1,127}");
//        //??????data
//        for(int i=0;i<data.length;i++)
//        {
//            //??????next
//            startFmid+=1;
//            String next=data[i];
//            if(i!=data.length-1)
//            {
//                tm.insert(""+startFmid,""+(startFmid+1),next);
//            }
//            else
//            {
//                tm.insert(""+startFmid,"",next);
//            }
//        }
//        sqlSession.commit();
//        sqlSession.close();
//        return ""+(mark+1);
//    }

//    public static void storeText(String rid,String tid,int score,String text) throws Exception {
//        String resource = "mybatis-config.xml";
//        InputStream inputStream = Resources.getResourceAsStream(resource);
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//
//        opiniontutorMapper tm = sqlSession.getMapper(opiniontutorMapper.class);
//
//        int f = Integer.valueOf(getFmid()) + 1;
//
//        storeTextTofragment(text);
//
//        tm.insert(rid, tid, score, "" + f, null, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//
//        sqlSession.commit();
//        sqlSession.close();
//    }

    public static void storeTextbrief(String rid,String tid,int score,String text) throws Exception {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        OpinionTutorCahceMapper tm=sqlSession.getMapper(OpinionTutorCahceMapper.class);

//        System.out.println(tm.selectByKey(rid,tid));
        List<OpinionTutorCache> opinionTutorCaches = tm.selectByKey(rid, tid);
        if(opinionTutorCaches.size()!=0)//??????????????????rid???tid?????????????????????
        {
            tm.deleteByKey(rid,tid);
        }
        tm.insert(rid,tid,score,null,text);


        sqlSession.commit();
        sqlSession.close();
    }

//    public static String storeTextTofragmentForce(String text) throws Exception {
//        //???????????????fragment???????????????fmid
//        String resource = "mybatis-config.xml";
//        InputStream inputStream = Resources.getResourceAsStream(resource);
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//
//        fragmentMapper tm = sqlSession.getMapper(fragmentMapper.class);
//
//        long maxFmid = -1;
//        for(int i = 0; i < tm.selectAllFmid().size(); i++)
//        {
//            if(Long.parseLong(tm.selectAllFmid().get(i)) > maxFmid)
//            {
//                maxFmid = Long.parseLong(tm.selectAllFmid().get(i));
//            }
//        }
//        long mark=maxFmid;
//        long startFmid=maxFmid;
//
//        //??????startFmid,????????????next?????????data
//        String[] data=text.split("\\s{1,127}");
//        //??????data
//        for(int i=0;i<data.length;i++)
//        {
//            //??????next
//            startFmid+=1;
//            String next=data[i];
//            if(i!=data.length-1)
//            {
//                tm.insert(""+startFmid,""+(startFmid+1),next);
//            }
//            else
//            {
//                tm.insert(""+startFmid,"",next);
//            }
//        }
//        sqlSession.commit();
//        sqlSession.close();
//
//        System.out.println("mark:"+mark);
//
//        return ""+(mark+1);
//    }

    public static void updateText(String rid,String tid,int score,String text) throws Exception {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        OpiniontutorMapper tm=sqlSession.getMapper(OpiniontutorMapper.class);

        // System.out.println(tm.selectByKey(rid,tid));

        if(tm.selectByKey(rid,tid)!=null)//??????????????????rid???tid?????????????????????
        {
            tm.deleteByKey(rid,tid);
        }

        tm.insert(rid,tid,score,null,
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),text);

        sqlSession.commit();
        sqlSession.close();
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
        System.out.println("report:"+tm.selectByKey(rid).get(0));
        int s = Integer.parseInt(tm.selectByKey(rid).get(0).getTotalsize());
        sqlSession.close();
        return s;
    }

}
