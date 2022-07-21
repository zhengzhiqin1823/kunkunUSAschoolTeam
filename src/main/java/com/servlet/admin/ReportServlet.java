package com.servlet.admin;

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
public class ReportServlet extends HttpServlet {
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
                ReportMapper mapper = sqs.getMapper(ReportMapper.class);
                List<Report> reports = mapper.selectBySubId(sid);
                for(Report r:reports)
                {
                    writer.write(r.getTeamid()+","+r.getSubmitTime()+","+r.getRid()+","+"r.getFirstFm()"+","+r.getTotalsize()+";");
                }
                break;
            }
            case "2":
            {
                ReportMapper mapper = sqs.getMapper(ReportMapper.class);
                List<Report> reports = mapper.selectByRid(rid);
                Report r=reports.get(0);
                writer.write(r.getRid()+","+r.getTeamid()+","+r.getSubmitTime()+";");
                break;
            }
            case "3":
            {
                ReportMapper mapper = sqs.getMapper(ReportMapper.class);
                List<Report> reports = mapper.selectByRid(rid);
                Report r=reports.get(0);
//                String firstfm=r.getFirstFm();
//                fragmentMapper fm = sqs.getMapper(fragmentMapper.class);
//                String next="";
//                next=firstfm;
                String article= r.getData();
//                do {
//                    List<fragment> fragments = fm.selectByKey(next);
//                    fragment fragment = fragments.get(0);
//                    article+=fragment.getData();
//                    next=fragment.getNext();
//                } while(next!=null);
                writer.write(article);
                break;
            }
            case "4":
            {
                ReportMapper mapper = sqs.getMapper(ReportMapper.class);
                List<Report> reports = mapper.selectBySubId(sid);
                for(Report r:reports)
                {
                    JudgelinkMapper jm = sqs.getMapper(JudgelinkMapper.class);
                    TutorMapper tm = sqs.getMapper(TutorMapper.class);
                    OpiniontutorMapper opiniontutorMapper = sqs.getMapper(OpiniontutorMapper.class);
                    List<Judgelink> judgelinks = jm.selectByRid(r.getRid());
                    List<Opiniontutor> opiniontutors = opiniontutorMapper.selectByrID(r.getRid());
                    String opinionstatus;
                    if(opiniontutors.size()==0){
                        opinionstatus="未审批";
                    }else {
                        opinionstatus="已审批";
                    }
                    if(judgelinks.size()==0)
                    {
                        writer.write(r.getTeamid()+","+r.getSubmitTime().substring(0,10)+","+r.getRid()+","+"r.getFirstFm()"+","+r.getTotalsize()+","+"未选择审批导师"+","+"未生成链接"+","+opinionstatus+";");
                    }
                    else
                    {
                        Judgelink judgelink = judgelinks.get(0);
                        String tid = judgelink.getTid();
                        String link=judgelinks.get(0).getLink();
                        List<tutor> tutors = tm.selectByTid(tid);
                        tutor tutor = tutors.get(0);
                        writer.write(r.getTeamid()+","+r.getSubmitTime().substring(0,10)+","+r.getRid()+","+"r.getFirstFm()"+","+r.getTotalsize()+","+tutor.getName()+","+link+","+opinionstatus+";");
                    }
                }
                break;
            }
            case "5":{
                ReportMapper mapper = sqs.getMapper(ReportMapper.class);
                SubmissionMapper submissionMapper = sqs.getMapper(SubmissionMapper.class);
                TeamMapper teamMapper = sqs.getMapper(TeamMapper.class);
                List<Submission> submissions = submissionMapper.selectByKey(sid);
                String taskid=submissions.get(0).getTaskID();
                List<team> teams = teamMapper.selectByTaskID(taskid);
                int k=0;
                for(team t:teams){
                    List<Report> reports = mapper.selectByTeamIDAndSubmitID(t.getTeamid(), sid);
                    if(reports.size()==0){
                        writer.write(t.getTeamid()+","+"未提交"+","+"default"+","+t.getEmail()+","+t.getTel()+";");
                    }else {
                        k++;
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
