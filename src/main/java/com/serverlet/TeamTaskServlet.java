package com.serverlet;

import com.mapper.*;
import com.test.pojo.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet("/team/home")
public class TeamTaskServlet extends HttpServlet {
    private String teamName;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        boolean isLogin = false;
        HttpSession session = req.getSession();
        Cookie[] cookies = req.getCookies();
        Object teamID = session.getAttribute("id");
        System.out.println(teamID);
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("id")) {
                if (cookie.getValue().equals(teamID)) {
                    isLogin = true;
                    break;
                }
            }
        }
        //如果没有登陆,则返回登陆
        if (!isLogin) {
            resp.sendRedirect("/0628JavaWebExercise_war");
            return;
        }

//      获取请求参数taskID
        String taskID;
        

        /*访问数据库，生成一个HTML文件*/
        String resource = "mybatis-config.xml";
        InputStream is = Resources.getResourceAsStream(resource);
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
        //获取SqlSession对象，来执行sql
        SqlSession sqs = factory.openSession();
        //执行sql
        TaskMapper taskMapper = sqs.getMapper(TaskMapper.class);
        SubmissionMapper submissionMapper = sqs.getMapper(SubmissionMapper.class);
        ReportMapper reportMapper = sqs.getMapper(ReportMapper.class);

        TeamMapper teamMapper = sqs.getMapper(TeamMapper.class);
        team team = teamMapper.selectByKey((String) teamID).get(0);
        teamName = team.getName();
        taskID = team.getTaskID();
        OpiniontutorMapper opiniontutorMapper = sqs.getMapper(OpiniontutorMapper.class);

        List<task> tasks = taskMapper.selectByKey(taskID);
        task t = tasks.get(0);
        String num = t.getSubmitNum();
        int submitNum = Integer.parseInt(num);
        List<submission> submissionArr = new ArrayList<>();
        if (submitNum > 0) {
            String firstsm = t.getFirstsm();
            List<submission> submissions = submissionMapper.selectByKey(firstsm);
            submissionArr.add(submissions.get(0));
            submitNum--;
            while (submitNum > 0) {
                String next = submissions.get(0).getNext();
                submissions = submissionMapper.selectByKey(next);
                submissionArr.add(submissions.get(0));
                submitNum--;
            }
        }


        List<MySubmission> mySubList = new ArrayList<>();
        int index = 0;
        for (submission s : submissionArr) {
            MySubmission mySubmission = new MySubmission();
            mySubmission.name = s.getName();
            mySubmission.submitID=s.getSubmitID();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis());
            String time = formatter.format(date);

//            是否开始
            if (time.compareTo(s.getDeadLine()) > 0 && s.getSubmitStatus().equals("0")) {
                mySubmission.status = "已结束";
                mySubmission.statusDate = s.getDeadLine();
            } else if (s.getSubmitStatus().equals("1")) {
                mySubmission.status = "进行中";
                mySubmission.statusDate = s.getDeadLine();
            } else {
                mySubmission.status = "未开始";
                mySubmission.statusDate = s.getStartTime();
            }

//            是否提交和评审
            List<report> reports = reportMapper.selectByTeamIDAndSubmitID((String) teamID, s.getSubmitID());
            if (reports.size() == 0) {
                mySubmission.isSubmit = "未提交";
                mySubmission.submitDate = "";
                mySubmission.isJudged = "未评审";
                mySubmission.judgeDate = "";
                mySubmission.score = "";
            } else {
                mySubmission.isSubmit = "已提交";
                mySubmission.submitDate = reports.get(0).getSubmitTime().substring(0, 10);
                List<opiniontutor> opiniontutors = opiniontutorMapper.selectByrID(reports.get(0).getRid());
                if (opiniontutors.size() == 0) {
                    mySubmission.isJudged = "未评审";
                    mySubmission.judgeDate = "";
                    mySubmission.score = "";
                } else {
                    mySubmission.isJudged = "已评审";
                    mySubmission.judgeDate = opiniontutors.get(0).getSubmitTime().substring(0, 10);
                    mySubmission.score = opiniontutors.get(0).getScore()+"";
                    if(true)
                    {
                        List<report> reports1 = reportMapper.selectBySubId(s.getSubmitID());
                        TreeSet<Integer> scoreTree = new TreeSet<>();
                        scoreTree = (TreeSet<Integer>) scoreTree.descendingSet();
                        for(report r : reports1){
                            List<opiniontutor> opiniontutors1 = opiniontutorMapper.selectByrID(r.getRid());
                            if(opiniontutors1.size()!=0)
                            {
                                scoreTree.add(opiniontutors1.get(0).getScore());
                            }
                        }
                        int size = scoreTree.size();
                        int i =1;
                        for(int score:scoreTree)
                        {
                            if(score==opiniontutors.get(0).getScore())
                            {
                                break;
                            }
                            else i++;
                        }
                        mySubmission.score+="<strong>  "+i+"/"+size+"名</strong>";
                    }
                }
            }

            mySubList.add(mySubmission);
        }
        //重要代码
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();

        writeHtml(writer, t, mySubList);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    private void writeHtml(PrintWriter writer, task t, List<MySubmission> list) {
        writer.write("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>团队项目页面</title>\n" +
                "    <link rel=\"stylesheet\" href=\"../css/studentProject.css\"/>\n" +
                "    <link rel=\"stylesheet\" href=\"../css/demo-navigation.css\"/>\n" +
                "    <script type=\"module\" src=\"https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js\"></script>\n" +
                "    <script nomodule src=\"https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js\"></script>\n" +
                "    <link rel=\"stylesheet\" href=\"../css/demo-button1.css\"/>\n" +
                "    <script src=\"../scripts/studentTask.js\"></script>"+
                "    <script src=\"../scripts/demo-navigation1.js\"></script>"+
                "    <link type=\"text/css\" rel=\"stylesheet\" href=\"../css/scroll.css\">"+
                "\n" +
                "   </head>\n" +
                "\n" +
                "   <body>\n" +
                "<div class=\"demo-navigation1\" >\n" +
                "        <div class=\"logo\">坤坤的美国校队</div>\n" +
                "        <nav>\n" +
                "            <ul>\n" +
                "                <li onclick = Home_click()>Project</li>\n" +
                "                <li onclick = Personal_click()>Personal</li>\n" +
                "            </ul>\n" +
                "        </nav>\n" +
                "        <div class=\"logo2\" onclick=\"logout()\">"+teamName+"|退出登陆</div>" +
                "</div>" +
                "\n" +
                "<div class=\"top\">\n" +
                "    <p>" + t.getName() + "</p>\n" +
                "    <div class=\"head\">\n" +
                "        <div class=\"description\">\n" +
                "            &ensp;&ensp;&ensp;&ensp;" +
                t.getDescription() +
                "        </div>\n" +
                "    </div>\n" +
                "    <br>\n" +
                "</div>\n" +


                //以下为Submission部分
                "<div class=\"container\">\n");

        for (MySubmission m : list) {
            writer.write("    <div class=\"container__column\">\n" +
                    "        <div class=\"container__content\">\n" +
                    "            <div class=\"title\">\n" +
                    "                <h3>" + m.name + "</h3>\n" +
                    "            </div>\n" +
                    "            <div class=\"father\">\n" +
                    "                <div class=\"inner-container\">\n" +
                    "                    <div class=\"menu\">\n" +
                    "                        <li>\n" +
                    "                            <span class=\"icon\">\n" +
                    "                              <ion-icon name=\"time-outline\"></ion-icon>\n" +
                    "                            </span>\n" +
                    "                            <span class=\"status\">" + m.status + "</span>\n" +
                    "                            <span class=\"time\">" + m.statusDate + "</span>\n" +
                    "                        </li>\n" +
                    "                        <li>\n" +
                    "                            <span class=\"icon\">\n" +
                    "                              <ion-icon name=\"time-outline\"></ion-icon>\n" +
                    "                            </span>\n" +
                    "                            <span class=\"status\">" + m.isSubmit + "</span>\n" +
                    "                            <span class=\"time\">" + m.submitDate + "</span>\n" +
                    "                        </li>\n" +
                    "                        <li>\n" +
                    "                            <span class=\"icon\">\n" +
                    "                              <ion-icon name=\"time-outline\"></ion-icon>\n" +
                    "                            </span>\n" +
                    "                            <span class=\"status\">" + m.isJudged + "</span>\n" +
                    "                            <span class=\"time\">" + m.judgeDate + "</span>\n" +
                    "                        </li>\n" +
                    "                        <li>\n" +
                    "                <span class=\"icon\">\n" +
                    "                  <ion-icon name=\"moon-outline\"></ion-icon>\n" +
                    "                </span>\n" +
                    "                            <span class=\"status\">成绩：</span>\n" +
                    "                            <span class=\"time\">" + m.score + "</span>\n" +
                    "\n" +
                    "                        </li>\n" +
                    "                    </div>\n" +
                    "                </div>\n" +
                    "            </div>\n" +
                    "            <div class=\"inner-button\">\n"+
                    "<button class=\"demo-button1\" onclick=\"read_report('/0628JavaWebExercise_war/" +
                    "submit?submitID="+ m.submitID + "&taskID="+ t.getTaskid() +"')\">查看详情</button>" +
                    "            </div>\n" +
                    "        </div>\n" +
                    "    </div>\n");
        }


        writer.write(
                "</div>\n" +
                        "</body>\n" +
                        "\n" +
                        "</html>");

    }

    class MySubmission {
        public String submitID;
        public String name;
        public String status;         /* 0 1 2 */
        public String statusDate;
        public String isSubmit;     /* false true */
        public String submitDate;
        public String isJudged;
        public String judgeDate;
        public String score;
        public String rank;

        @Override
        public String toString() {
            return "MySubmission{" +
                    "name='" + name + '\'' +
                    ", status=" + status +
                    ", statusDate='" + statusDate + '\'' +
                    ", isSubmit=" + isSubmit +
                    ", submitDate='" + submitDate + '\'' +
                    ", isJudged=" + isJudged +
                    ", judgeDate='" + judgeDate + '\'' +
                    ", score='" + score + '\'' +
                    '}';
        }
    }

}
