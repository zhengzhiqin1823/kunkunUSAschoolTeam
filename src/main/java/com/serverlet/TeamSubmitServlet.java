package com.serverlet;

import com.alibaba.fastjson.JSONObject;
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
import java.util.Date;
import java.util.List;


@WebServlet("/submit")
public class TeamSubmitServlet extends HttpServlet {


    private String teamName;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        //检测用户是否登陆
        boolean isLogin = false;
        HttpSession session = req.getSession();
        Cookie[] cookies = req.getCookies();
        Object type = session.getAttribute("type");
        Object id = session.getAttribute("id"); //teamID

        if (type!=null&& type.equals("admin")) {
            id = req.getParameter("teamID");
        } else {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("id")) {
                    if (cookie.getValue().equals(id)) {
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
        }
        String submitID = req.getParameter("submitID");
        String taskID = req.getParameter("taskID");

        /*访问数据库，生成一个HTML文件*/
        String resource = "mybatis-config.xml";
        InputStream is = Resources.getResourceAsStream(resource);
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
        //获取SqlSession对象，来执行sql
        SqlSession sqs = factory.openSession();
        //执行sql
        taskMapper taskMapper = sqs.getMapper(taskMapper.class);
        submissionMapper submissionMapper = sqs.getMapper(submissionMapper.class);
        reportcaheMapper reportcaheMapper = sqs.getMapper(com.mapper.reportcaheMapper.class);
        teamMapper teamMapper = sqs.getMapper(com.mapper.teamMapper.class);
        reportMapper reportMapper = sqs.getMapper(reportMapper.class);
        List<task> tasks = taskMapper.selectByKey(taskID);


        team team = teamMapper.selectByKey(id.toString()).get(0);
        teamName = team.getName();
        task t = tasks.get(0);
        List<submission> submissions = submissionMapper.selectByKey(submitID);
        submission s = submissions.get(0);
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        List<report> reports = reportMapper.selectByTeamIDAndSubmitID((String) id, submitID);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String time = formatter.format(date);
        int submitStatus;
        if (type.equals("admin") || (time.compareTo(s.getStartTime()) >= 0 && s.getSubmitStatus().equals("0"))) {
            submitStatus = 1;
        } else if (time.compareTo(s.getStartTime()) >= 0 || s.getSubmitStatus().equals("1")) {
            submitStatus = 0;
        } else {
            submitStatus = -1;
        }
        if (submitStatus == 1) {
            opiniontutorMapper opiniontutorMapper = sqs.getMapper(opiniontutorMapper.class);
            if(reports.size()==0)
            {
                writeHtml2(resp.getWriter(),t,s,team,"",new report(),"","");
                return;
            }

            List<opiniontutor> opiniontutors = opiniontutorMapper.selectByrID(reports.get(0).getRid());

            opiniontutor ot;
            String tutorName;
            String text;
            String sc;
            if (opiniontutors.size() == 0) {
                ot = new opiniontutor();
                ot.setScore(-1);
                tutorName = "等待评审";
                text = "";
                sc = "-1";
            } else {
                ot = opiniontutors.get(0);
//                text = StaticMethods.getTextByFirstFm(ot.getFirstFm(), mapper);
                text = ot.getData();
                text = text.replace("\n", "<br/>");
                tutorMapper tutorMapper = sqs.getMapper(tutorMapper.class);
                tutorName = tutorMapper.selectByTid(ot.gettID()).get(0).getName();
                sc = opiniontutors.get(0).getScore() + "";

            }
            if (!type.equals("admin"))
                writeHtml2(writer, t, s, team, tutorName, reports.get(0), text, sc);
            else {
                writeHtml3(writer, t, s, team, tutorName, reports.get(0), text, sc);
            }
            return;
        }


        if (reports.size() == 0) {
            List<reportcahe> reportcahes = reportcaheMapper.selectByTeamIDAndSubmitID(id.toString(), submitID);
            if (reportcahes.size() == 0) {
                writeHtml(writer, s, "", submitStatus);
            } else {
//                String text = StaticMethods.getTextByFirstFm(reportcahes.get(0).getFirstfm(), fragmentMapper);
                String text = reportcahes.get(0).getData();
                writeHtml(writer, s, text, submitStatus);
            }
        } else {
//            String text = StaticMethods.getTextByFirstFm(reports.get(0).getFirstFm(), fragmentMapper);

            String text = reports.get(0).getData();
//            text = text.substring(0,10);

            writeHtml(writer, s, text, submitStatus);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            String rID;
            String teamID;
            String submitID;

            //teamID
            HttpSession session = req.getSession();
            teamID = (String) session.getAttribute("id");


            req.setCharacterEncoding("UTF-8");//请求编码类型
            resp.setCharacterEncoding("UTF-8");//响应编码类型

            JSONObject reqData = HttpGetJson.getJson(req);

            //submitID
            submitID = String.valueOf(reqData.get("submitID"));

            String text = reqData.get("text").toString();

            /*访问数据库，生成一个HTML文件*/
            String resource = "mybatis-config.xml";
            InputStream is = Resources.getResourceAsStream(resource);
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
            //获取SqlSession对象，来执行sql
            SqlSession sqs = factory.openSession();
            //执行sql
            reportMapper reportMapper = sqs.getMapper(reportMapper.class);

            //rID
            List<report> reports = reportMapper.selectAll();
            int reportSize = reports.size();
            rID = reportSize + 1 + "";

//            fragmentMapper fragmentMapper = sqs.getMapper(com.mapper.fragmentMapper.class);
//
//            List<fragment> fragments = fragmentMapper.selectAll();
//            int size = fragments.size() + 1;
//            int init_size = size;
            int length = text.toString().length();
            String totalsize = length + "";
            //insert into table fragment
//            int now = 0;
//            while (length > 255) {
//                String data = text.substring(now, now + 255);
//                fragmentMapper.insert(size + "", size + 1 + "", data);
//                length -= 255;
//                size++;
//            }
//            String data = text.substring(now, now + length);
//            fragmentMapper.insert(size + "", "", data);

            //insert into table report
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            String time = formatter.format(date);
            time += ".0";

            String cacheID;
            reportcaheMapper reportcaheMapper = sqs.getMapper(reportcaheMapper.class);
            int cache_size = reportcaheMapper.selectAll().size();
            cacheID = cache_size + 1 + "";
            if (reqData.get("type").toString().equals("data")) {
                List<report> reportss = reportMapper.selectByTeamIDAndSubmitID(teamID, submitID);
                if (reportss.size() == 0) {
                    reportMapper.insert(rID, submitID, teamID, totalsize, time, text);
                } else {
                    rID = reportss.get(0).getRid();
                    reportMapper.deleteByKey(rID);
                    reportMapper.insert(rID, submitID, teamID, totalsize, time, text);
                }
            } else if (reqData.get("type").toString().equals("cache")) {
                List<reportcahe> reportcahes = reportcaheMapper.selectByTeamIDAndSubmitID(teamID, submitID);
                if (reportcahes.size() == 0) {
                    reportcaheMapper.insert(cacheID, submitID, teamID, totalsize, text);
                } else {
                    cacheID = reportcahes.get(0).getCacheID();
                    reportcaheMapper.deleteByKey(cacheID);
                    reportcaheMapper.insert(cacheID, submitID, teamID, totalsize, text);
                }
            }

            sqs.commit();
            sqs.close();
            PrintWriter p = resp.getWriter();
            p.write("提交成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void writeHtml(PrintWriter writer, submission s, String text, int submitStatus) {
        if (text == null)
            text = "";
        text = text.replace("\r\n", "&#13;");
        text = text.replace('\r', ' ');
        text = text.replace("\n", "&#13;");

        writer.write(
                "<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <title>项目提交页面</title>\n" +
                        "    <link type=\"text/css\" rel=\"stylesheet\" href=\"./css/submit.css\">\n" +
                        "    <link type=\"text/css\" rel=\"stylesheet\" href=\"./css/demo-button1.css\">\n" +
                        "    <link type=\"text/css\" rel=\"stylesheet\" href=\"./css/demo-input1.css\">\n" +
                        "    <link type=\"text/css\" rel=\"stylesheet\" href=\"./css/demo-navigation.css\">\n" +
                        "    <link type=\"text/css\" rel=\"stylesheet\" href=\"./css/scroll.css\">" +
                        "    <script src=\"scripts/demo-navigation1.js\"></script>\n" +
                        "    <script type=\"text/javascript\" src=\"./scripts/vue.js\"></script>\n" +
                        "    <script src=\"https://unpkg.com/axios/dist/axios.min.js\"></script>\n" +
                        "</head>\n" +
                        "\n" +
                        "<body>\n" +
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
                        "<div class=\"body\">\n" +
                        "    <div class=\"body_mid\">\n" +
                        "        <div class=\"report\" id=\"description\">\n" +
                        "            <div class=\"team_and_time\">\n" +
                        "                <div class=\"team\">" + s.getName() + "</div>\n" +
                        "                <div class=\"time\">" + s.getDeadLine() + "</div>\n" +
                        "            </div>\n" +
                        "            <div class=\"data\">\n" +
                        s.getDescription() +
                        "            </div>\n" +
                        "        </div>\n" +
                        "        <div class=\"text\">\n" +
                        "            <div class=\"text-title\">报告内容</div>\n" +
                        "        </div>\n" +


                        "<textarea id=\"text\" class=\"demo-textarea\"" +
                        "placeholder=\"请在这里输入报告内容\">" +
                        text +


                        "</textarea>\n" +
                        "    </div>\n");

        if (submitStatus == 0)
            writer.write(
                    "    <div class=\"button\">\n" +
                            "        <button class=\"demo-button1\" type=\"button\" @click=\"cache\">暂存</button>\n" +
                            "        <button class=\"demo-button1\" type=\"button\" @click=\"submit\">提交</button>\n" +
                            "    </div>\n");
        else
            writer.write("    <div class=\"button\">\n" +
                    "        <button class=\"demo-button1\" type=\"button\">未开始</button>\n" +
                    "    </div>\n");
        writer.write(
                "</div>\n" +
                        "</body>\n" +
                        "<script>\n" +
                        "    var app = new Vue({\n" +
                        "        el: '.body',\n" +
                        "        data: {\n" +
                        "            submitID: " + s.getSubmitID() + ",\n" +
                        "            report_text: '" + text + "',\n" +
                        "            upath: \"\"\n" +
                        "        },\n" +
                        "        methods: {\n" +
                        "            submit: function () {\n" +
                        "                alert(\"submit!\")\n" +
                        "                this.post_data()\n" +
                        "            },\n" +
                        "            cache: function () {\n" +
                        "                alert(\"cache!\")\n" +
                        "                this.post_cache()\n" +
                        "            },\n" +
                        "\n" +
                        "            //上传文件函数\n" +
                        "            upload: function () {\n" +
                        "                var zipFormData = new FormData();\n" +
                        "                zipFormData.append('filename', this.upath); //filename是键，file是值，就是要传的文件，test.zip是要传的文件名\n" +
                        "                let config = { headers: { 'Content-Type': 'multipart/form-data' } };\n" +
                        "                this.uping = 1;\n" +
                        "                this.$http.post('http://localhost:42565/home/up', zipFormData, config).then(function (response) {\n" +
                        "                    console.log(response);\n" +
                        "                    console.log(response.data);\n" +
                        "                    console.log(response.bodyText);\n" +
                        "                    var resultobj = response.data;\n" +
                        "                    this.uping = 0;\n" +
                        "                    this.result = resultobj.msg;\n" +
                        "                })\n" +
                        "            },\n" +
                        "\n" +
                        "            //    post上传数据\n" +
                        "            post_data: function () {\n" +
                        "                let element = document.querySelector(\"#text\");" +
                        "                let data = {\n" +
                        "                    'type': \"data\",\n" +
                        "                    'submitID': this.submitID,\n" +
                        "                    'text': element.value\n" +
                        "                }\n" +
                        "                   alert(element.value);" +
                        "                axios({\n" +
                        "                    method: \"post\",\n" +
                        "                    url: \"/0628JavaWebExercise_war/submit?type=data\",\n" +
                        "                    data\n" +
                        "                }).then((res) => {\n" +
                        "                    alert(res.data)\n" +
                        "                })\n" +
                        "            },\n" +
                        "            post_cache: function () {\n" +
                        "                let element = document.querySelector(\"#text\");" +
                        "                let data = {\n" +
                        "                    'type': \"cache\",\n" +
                        "                    'submitID': this.submitID,\n" +
                        "                    'text': element.value\n" +
                        "                }\n" +
                        "                alert(element.value);" +
                        "                axios\n" +
                        "                    .post('/0628JavaWebExercise_war/submit?type=cache', data)\n" +
                        "                    .then((res) => {\n" +
                        "                        alert(res.data)\n" +
                        "                    })\n" +
                        "            },\n" +
                        "             initinfor(){\n" +
                        "                let element = document.querySelector(\"#text\");\n" +
                        "                element.innerHTML =' " + text + "';\n" +
                        "            }\n" +
                        "        }\n" +
                        "    })\n" +

                        "\n" +
                        "</script>\n" +
                        "\n" +
                        "</html>"

        );
    }

    private void writeHtml2(PrintWriter writer, task t, submission s, team team,
                            String tutorName,
                            report report, String text, String score) {
        if (text == null)
            text = "";
        String s1 = report.getData();

        if (s1 != null)
            s1 = s1.replace("\n", "<br/>");
        else s1 = "";
        writer.write("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>项目页面</title>\n" +
                "    <link rel=\"stylesheet\" href=\"./css/TeamReport.css\" />\n" +
                "    <link rel=\"stylesheet\" href=\"./css/demo-navigation.css\" />\n" +
                "    <link rel=\"stylesheet\" href=\"./css/demo-button1.css\">\n" +
                "<link type=\"text/css\" rel=\"stylesheet\" href=\"../css/scroll.css\">" +
                "    <script src=\"scripts/demo-navigation1.js\"></script>" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
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

                "<div class=\"bottom\" >\n" +
                "    <div class=\"report_name\">" + t.getName() + "</div>\n" +
                "    <div class=\"report\" id=\"description\">\n" +
                "        <div class=\"team_and_time\">\n" +
                "            <div class=\"team\">" + s.getName() + "</div>\n" +
                "            <div class=\"time\">" + s.getDeadLine() + "</div>\n" +
                "        </div>\n" +
                "        <div class=\"data\">\n" +
                s.getDescription() +
                "\n" +
                "        </div>\n" +
                "    </div>\n" +
                "\n" +
                "    <div class=\"report\">\n" +
                "        <div class=\"team_and_time\">\n" +
                "            <div class=\"team\">" + team.getName() + "</div>\n" +
                "            <div class=\"time\">" + report.getSubmitTime() + "</div>\n" +
                "        </div>\n" +
                "        <div class=\"data\">\n" +
                s1 +
                "\n" +
                "        </div>\n" +
                "    </div>\n" +
                "\n" +
                "    <div class=\"tutor\">\n" +
                "        <div class=\"title\">\n" +
                "            <div class=\"t\">导师评价 <span>" +
                tutorName +
                "               </span></div>\n" +
                "            <div class=\"score\">得分：<span>");
        if (!score.equals("-1")) {
            writer.write(score);
        }
        writer.write(
                "</span></div>\n" +
                        "        </div>\n" +
                        "        <div class=\"data-judgement\">" +
                        text +
                        "        </div>\n" +
                        "    </div>\n" +
                        "    <div class=\"button\">\n" +
                        "        <button class=\"demo-button2\" onclick=\"Home_click()\">返回</button>\n" +
                        "    </div>" +
                        "</div>\n" +
                        "\n" +
                        "\n" +
                        "</body>\n" +
                        "\n" +
                        "</html>");

    }

    private void writeHtml3(PrintWriter writer, task t, submission s, team team,
                            String tutorName,
                            report report, String text, String score) {
        if (text == null)
            text = "";
        String s1 = report.getData();

        if (s1 != null)
            s1 = s1.replace("\n", "<br/>");
        else s1 = "";
        writer.write("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>报告页面</title>\n" +
                "    <link rel=\"stylesheet\" href=\"./css/TeamReport.css\" />\n" +
                "    <link rel=\"stylesheet\" href=\"./css/demo-navigation.css\" />\n" +
                "    <link rel=\"stylesheet\" href=\"./css/demo-button1.css\">\n" +
                "<link type=\"text/css\" rel=\"stylesheet\" href=\"../css/scroll.css\">" +
                "    <script src=\"scripts/demo-navigation1.js\"></script>" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "<div class=\"demo-navigation1\" >\n" +
                "        <div class=\"logo\">坤坤的美国校队</div>\n" +
                "        <nav>\n" +
                "            <ul>\n" +
                "                <li onclick = admin_team()>Team</li>\n" +
                "                <li onclick = admin_tutor()>Tutor</li>\n" +
                "                <li onclick = admin_project()>Project</li>\n" +
                "<li onclick=\"admin_personal()\">Personal</li>"+
                "            </ul>\n" +
                "        </nav>\n" +
                "        <div class=\"logo2\" onclick=\"logout()\">管理员001|退出登陆</div>" +
                "</div>" +

                "<div class=\"bottom\" >\n" +
                "    <div class=\"report_name\">" + t.getName() + "</div>\n" +
                "    <div class=\"report\" id=\"description\">\n" +
                "        <div class=\"team_and_time\">\n" +
                "            <div class=\"team\">" + s.getName() + "</div>\n" +
                "            <div class=\"time\">" + s.getDeadLine() + "</div>\n" +
                "        </div>\n" +
                "        <div class=\"data\">\n" +
                s.getDescription() +
                "\n" +
                "        </div>\n" +
                "    </div>\n" +
                "\n" +
                "    <div class=\"report\">\n" +
                "        <div class=\"team_and_time\">\n" +
                "            <div class=\"team\">" + team.getName() + "</div>\n" +
                "            <div class=\"time\">" + report.getSubmitTime().substring(0, 10) + "</div>\n" +
                "        </div>\n" +
                "        <div class=\"data\">\n" +
                s1 +
                "\n" +
                "        </div>\n" +

                "    </div>\n" +
                "\n" +
                "    <div class=\"tutor\">\n" +
                "        <div class=\"title\">\n" +
                "            <div class=\"t\">导师评价 <span>" +
                tutorName +
                "               </span></div>\n" +
                "            <div class=\"score\">得分：<span>");
        if (!score.equals("-1")) {
            writer.write(score);
        }
        writer.write(
                "</span></div>\n" +
                        "        </div>\n" +
                        "        <div class=\"data-judgement\">" +
                        text +
                        "        </div>\n" +
                        "    </div>\n" +
                        "    <div class=\"button\">\n" +
                        "        <button class=\"demo-button2\" onclick=\"admin_project()\">返回</button>\n" +
                        "    </div>" +
                        "</div>\n" +
                        "\n" +
                        "\n" +
                        "</body>\n" +
                        "\n" +
                        "</html>");

    }
}
