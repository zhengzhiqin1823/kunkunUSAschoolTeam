package com.example.demo;

import com.mapper.*;
import com.test.pojo.*;
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

@WebServlet(name = "reportServlet", value = "/reportServlet")
public class reportServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sid = request.getParameter("sid");
        String ret = request.getParameter("ret");
        String rid=request.getParameter("rid");
        String resource = "mybatis-config.xml";
        InputStream is= Resources.getResourceAsStream(resource);
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
        //获取SqlSession对象，来执行sql
        SqlSession sqs=factory.openSession();
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        switch (ret)
        {
            case "1":
            {
                reportMapper mapper = sqs.getMapper(reportMapper.class);
                List<report> reports = mapper.selectBySubId(sid);
                for(report r:reports)
                {
                    writer.write(r.getTeamid()+","+r.getSubmitTime()+","+r.getRid()+","+r.getFirstFm()+","+r.getToyalsize()+";");
                }
                break;
            }
            case "2":
            {
                reportMapper mapper = sqs.getMapper(reportMapper.class);
                List<report> reports = mapper.selectByRid(rid);
                report r=reports.get(0);
                writer.write(r.getRid()+","+r.getTeamid()+","+r.getSubmitTime()+";");
                break;
            }
            case "3":
            {
                reportMapper mapper = sqs.getMapper(reportMapper.class);
                List<report> reports = mapper.selectByRid(rid);
                report r=reports.get(0);
                String firstfm=r.getFirstFm();
                fragmentMapper fm = sqs.getMapper(fragmentMapper.class);
                String next="";
                next=firstfm;
                String article="";
                do {
                    List<fragment> fragments = fm.selectByKey(next);
                    fragment fragment = fragments.get(0);
                    article+=fragment.getData();
                    next=fragment.getNext();
                } while(next!=null);
                writer.write(article);
                break;
            }
            case "4":
            {
                reportMapper mapper = sqs.getMapper(reportMapper.class);
                List<report> reports = mapper.selectBySubId(sid);
                for(report r:reports)
                {
                    judgelinkMapper jm = sqs.getMapper(judgelinkMapper.class);
                    tutorMapper tm = sqs.getMapper(tutorMapper.class);
                    List<judgelink> judgelinks = jm.selectByRid(r.getRid());
                    if(judgelinks.size()==0)
                    {
                        writer.write(r.getTeamid()+","+r.getSubmitTime()+","+r.getRid()+","+r.getFirstFm()+","+r.getToyalsize()+","+"未选择审批导师"+","+"未生成链接"+";");
                    }
                    else
                    {
                        judgelink judgelink = judgelinks.get(0);
                        String tid = judgelink.getTid();
                        String link=judgelinks.get(0).getLink();
                        List<tutor> tutors = tm.selectByTid(tid);
                        tutor tutor = tutors.get(0);
                        writer.write(r.getTeamid()+","+r.getSubmitTime()+","+r.getRid()+","+r.getFirstFm()+","+r.getToyalsize()+","+tutor.getName()+","+link+";");
                    }
                }
                break;
            }
        }
        sqs.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     this.doGet(request,response);
    }
}
