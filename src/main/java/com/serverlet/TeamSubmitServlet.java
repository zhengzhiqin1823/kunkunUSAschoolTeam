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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //检测用户是否登陆
        boolean isLogin = false;
        HttpSession session = req.getSession();
        Cookie[] cookies = req.getCookies();
        Object id = session.getAttribute("id"); //teamID
        System.out.println(id);
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
        reportMapper reportMapper = sqs.getMapper(reportMapper.class);
        List<task> tasks = taskMapper.selectByKey(taskID);
        task t = tasks.get(0);
        List<submission> submissions = submissionMapper.selectByKey(submitID);
        submission s = submissions.get(0);
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        List<report> reports = reportMapper.selectByTeamIDAndSubmitID((String) id, submitID);
        fragmentMapper fragmentMapper = sqs.getMapper(com.mapper.fragmentMapper.class);
        if (reports.size() == 0) {
            List<reportcahe> reportcahes = reportcaheMapper.selectByTeamIDAndSubmitID(id.toString(), submitID);
            if (reportcahes.size() == 0) {
                writeHtml(writer, t, s, "");
            } else {
                String text = StaticMethods.getTextByFirstFm(reportcahes.get(0).getFirstfm(), fragmentMapper);
                writeHtml(writer, t, s, text);
            }
        } else {
            String text = StaticMethods.getTextByFirstFm(reports.get(0).getFirstFm(), fragmentMapper);
            System.out.println(text);
//            text = text.substring(0,10);
            writeHtml(writer, t, s, text);
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

            fragmentMapper fragmentMapper = sqs.getMapper(com.mapper.fragmentMapper.class);

            List<fragment> fragments = fragmentMapper.selectAll();
            int size = fragments.size() + 1;
            int init_size = size;
            int length = text.toString().length();
            String totalsize = length + "";
            //insert into table fragment
            int now = 0;
            while (length > 255) {
                String data = text.substring(now, now + 255);
                fragmentMapper.insert(size + "", size + 1 + "", data);
                length -= 255;
                size++;
            }
            String data = text.substring(now, now + length);
            fragmentMapper.insert(size + "", "", data);

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
                    reportMapper.insert(rID, submitID, teamID, totalsize, String.valueOf(init_size), time);
                } else {
                    rID = reportss.get(0).getRid();
                    reportMapper.deleteByKey(rID);
                    reportMapper.insert(rID, submitID, teamID, totalsize, String.valueOf(init_size), time);
                }

            } else if (reqData.get("type").toString().equals("cache")) {
                List<reportcahe> reportcahes = reportcaheMapper.selectByTeamIDAndSubmitID(teamID, submitID);
                if (reportcahes.size() == 0) {
                    reportcaheMapper.insert(cacheID, submitID, teamID, String.valueOf(init_size), totalsize);
                } else {
                    cacheID = reportcahes.get(0).getCacheID();
                    reportcaheMapper.deleteByKey(cacheID);
                    reportcaheMapper.insert(cacheID, submitID, teamID, String.valueOf(init_size), totalsize);
                }
            }
            System.out.println(rID);
            System.out.println(submitID);
            System.out.println(teamID);
            System.out.println(totalsize);
            System.out.println(String.valueOf(size));
            System.out.println(time);
            sqs.commit();
            sqs.close();
            PrintWriter p = resp.getWriter();
            p.write("提交成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void writeHtml(PrintWriter writer, task t, submission s, String text) {
        writer.write(
                "<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <title>Title</title>\n" +
                        "    <link type=\"text/css\" rel=\"stylesheet\" href=\"./css/submit.css\">\n" +
                        "    <link type=\"text/css\" rel=\"stylesheet\" href=\"./css/demo-button1.css\">\n" +
                        "    <link type=\"text/css\" rel=\"stylesheet\" href=\"./css/demo-input1.css\">\n" +
                        "    <link type=\"text/css\" rel=\"stylesheet\" href=\"./css/demo-navigation.css\">\n" +
                        "    <script src=\"scripts/demo-navigation1.js\"></script>\n" +
                        "<script src=\"https://unpkg.com/axios/dist/axios.min.js\"></script>" +
                        "    <script type=\"text/javascript\" src=\"./scripts/vue.js\"></script>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "<div class=\"demo-navigation1\">\n" +
                        "    <nav>\n" +
                        "        <ul>\n" +
                        "            <li onclick=\"Home_click()\">Home</li>\n" +
                        "            <li onclick=\"Messages_click()\">Messages</li>\n" +
                        "            <li onclick=\"Projects_click()\">Projects</li>\n" +
                        "            <li onclick=\"Submits_click()\">Submits</li>\n" +
                        "            <li onclick=\"Personal_click()\">Personal</li>\n" +
                        "        </ul>\n" +
                        "    </nav>\n" +
                        "</div>\n" +
                        "\n" +
                        "<div class=\"body\">\n" +
                        "    <div class=\"body_mid\">\n" +
                        "        <ul>\n" +
                        "            <p><strong>项目名称：</strong>" + t.getName() + " " + s.getName() + "</p>\n" +
                        "            <p><strong>提交内容：</strong></p>\n" +
                        "        </ul>\n" +
                        "        <textarea class=\"demo-textarea\" v-model=\"report_text\">" +
                        text
                        + "</textarea>\n" +
                        "        <div class=\"button\">\n" +
                        "            <button class=\"demo-button1\" type=\"button\" @click=\"cache\">暂存</button>\n" +
                        "            <button class=\"demo-button1\" type=\"button\" @click=\"submit\">提交</button>\n" +
                        "        </div>\n" +
                        "    </div>\n" +
                        "    <script>\n" +
                        "        var app = new Vue({\n" +
                        "            el: '.body_mid',\n" +
                        "            data: {\n" +
                        "                submitID:" + s.getSubmitID() + "," +
                        "                report_text: '" + text + "',\n" +
                        "                upath: \"\"\n" +
                        "            },\n" +
                        "            methods: {\n" +
                        "                submit: function () {\n" +
                        "                    alert(\"submit!\")\n" +
                        "                    this.post_data()\n" +
                        "                },\n" +
                        "                cache: function () {\n" +
                        "                    alert(\"cache!\")\n" +
                        "                    this.post_cache()\n" +
                        "                },\n" +
                        "\n" +


                        "                //上传文件函数\n" +
                        "                upload: function () {\n" +
                        "                    var zipFormData = new FormData();\n" +
                        "                    zipFormData.append('filename', this.upath); //filename是键，file是值，就是要传的文件，test.zip是要传的文件名\n" +
                        "                    let config = {headers: {'Content-Type': 'multipart/form-data'}};\n" +
                        "                    this.uping = 1;\n" +
                        "                    this.$http.post('http://localhost:42565/home/up', zipFormData, config).then(function (response) {\n" +
                        "                        console.log(response);\n" +
                        "                        console.log(response.data);\n" +
                        "                        console.log(response.bodyText);\n" +
                        "                        var resultobj = response.data;\n" +
                        "                        this.uping = 0;\n" +
                        "                        this.result = resultobj.msg;\n" +
                        "                    })\n" +
                        "                },\n" +
                        "\n" +
                        "                //    post上传数据\n" +
                        "                post_data: function () {\n" +
                        "                    let data = {\n" +
                        "                        'type':'data'," +
                        "                        'submitID':this.submitID," +
                        "                        'text': this.report_text\n" +
                        "                    }\n" +
                        "                    axios\n" +
                        "                        .post('/0628JavaWebExercise_war/submit', data)\n" +
                        "                        .then((res) => {\n" +
                        "                            alert(res)\n" +
                        "                        })\n" +
                        "                },\n" +
                        "                post_cache: function () {\n" +
                        "                    let data = {\n" +
                        "                        'type':'cache',\n" +
                        "                        'submitID': this.submitID,\n" +
                        "                        'text': this.report_text\n" +
                        "                    }\n" +
                        "                    axios\n" +
                        "                        .post('/0628JavaWebExercise_war/submit', data)\n" +
                        "                        .then((res) => {\n" +
                        "                            alert(res)\n" +
                        "                        })\n" +
                        "                }\n" +
                        "            }\n" +
                        "        })\n" +
                        "\n" +
                        "    </script>\n" +
                        "</div>\n" +
                        "\n" +
                        "</body>\n" +
                        "</html>\n"

        );
    }

}