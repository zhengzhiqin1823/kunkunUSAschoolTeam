package com.serverlet;

import com.mapper.messageMapper;
import com.mapper.taskMapper;
import com.test.pojo.message;
import com.test.pojo.task;
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
import java.util.ArrayList;
import java.util.List;


@WebServlet("/home")
public class StudentHomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        boolean isLogin = false;
        HttpSession session = req.getSession();
        Cookie[] cookies = req.getCookies();
        Object id = session.getAttribute("id");
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

        /*访问数据库，生成一个HTML文件*/
        String resource = "mybatis-config.xml";
        InputStream is = Resources.getResourceAsStream(resource);
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
        //获取SqlSession对象，来执行sql
        SqlSession sqs = factory.openSession();
        //执行sql
        taskMapper mapper = sqs.getMapper(taskMapper.class);
        messageMapper message = sqs.getMapper(messageMapper.class);
        List<task> tasks = mapper.selectAll();
        List<message> mes = message.selectAll();
        List<message> messages = new ArrayList<>();
        for (message m : mes) {
            if (m.getTo().equals("0202201946")) {
                messages.add(m);
            }
        }

        sqs.commit();
        sqs.close();
        //重要代码
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        writeHtml(writer, tasks, messages);
//        req.getRequestDispatcher("studentProject.html").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    private void writeHtml(PrintWriter writer, List<task> tasks, List<message> messages) {
        writer.write("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "  <meta charset=\"UTF-8\">\n" +
                "  <title>首页</title>\n" +
                "  <link rel=\"stylesheet\" type=\"text/css\" href=\"./css/studentHome.css\">\n" +
                "<link rel=\"stylesheet\" type=\"text/css\" href=\"./css/demo-navigation.css\">" +
                "<script src=\"./scripts/studentHome.js\"></script>" +
                "<script src=\"./scripts/demo-navigation1.js\"></script>" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "\n" +
                "<div class=\"demo-navigation1\">\n" +
                "  <nav>\n" +
                "    <ul>\n" +
                "            <li onclick=\"Home_click()\">Home</li>\n" +
                "            <li onclick=\"Messages_click()\">Messages</li>\n" +
                "            <li onclick=\"Projects_click()\">Projects</li>\n" +
                "            <li onclick=\"Submits_click()\">Submits</li>\n" +
                "            <li onclick=\"Personal_click()\">Personal</li>" +
                "    </ul>\n" +
                "  </nav>\n" +
                "</div>" +
                "<div class=\"body\">\n" +
                "\n" +
                "  <div class=\"body_left\">\n" +
                "    <div class=\"body_left_one\">\n" +
                "      <h4>项目</h4>\n" +
                "      <button type=\"botton\">Find</button>\n" +
                "    </div>\n" +
                "    <div class=\"body_left_two\">\n" +
                "      <form>\n" +
                "        <input type=\"text\" placeholder=\"Find a repository...\">\n" +
                "      </form>\n" +
                "    </div>\n" +
                "    <div class=\"inform\">\n" +
                "      <h4>消息通知</h4>\n" +
                "    </div>\n");


        //消息部分
        int index = 1;
        for (message m : messages) {
            writer.write(
                    "    <div class=\"demo0" + index + "\">\n" +
                            "      <p>\n" +
                            "        <strong>" + m.getTitle() + "</strong>\n" +
                            "        <br/>\n" +
                            "（这是消息的详情信息！）" +
                            "        <br />\n" +
                            "      <p><time>" + m.getTime() + "</time></p>\n" +
                            "      </p>\n" +
                            "      <span>&times</span>\n" +
                            "    </div>\n");
            index++;
        }
        writer.write(
                "  </div>\n" +
                        "\n" +
                        "  <div class=\"body_right\">\n" +
                        "\n" +
                        "    <div class=\"submit\">\n" +
                        "      <input type=\"radio\" name=\"submit\" value=\"1\" id=\"submit\"> <label for=\"submit\">已提交</label>\n" +
                        "      <input type=\"radio\" name=\"submit\" value=\"2\" id=\"unsubmit\"> <label for=\"unsubmit\">未提交</label>\n" +
                        "    </div>\n");


        //写项目
        index = 1;
        for (task t : tasks) {
            writer.write(
                    "    <div class=\"right_mid\" taskID=\"" + t.getTaskID() + "\">\n" +
                            "      <h4>" + t.getName() + "</h4>\n" +
                            "      <p>" +
                            t.getDescription() +
                            "      </p>\n" +
                            "      <div class=\"bottom\">\n" +
                            "        <button type=\"button\" onclick=\"button_click(" + t.getTaskID() + ")\">进入项目</button>\n" +
                            "        <p class=\"deadline\">截止日期：" + t.getDeadline() + "</p>\n" +
                            "      </div>\n" +
                            "    </div>\n");
            index++;
        }

        writer.write(
                "  </div>\n" +
                        "\n" +
                        "  <div class=\"right_right\">\n" +
                        "    <ul class=\"timeline\">\n" +
                        "      <a href=\"#\">Latest change</a><br>\n" +
                        "      <li>\n" +
                        "        <div class=\"line\"></div>\n" +
                        "        <section class=\"body\">\n" +
                        "          <time class=\"time\">2022-07-05</time>\n" +
                        "          <a href=\"#\">项目名：<strong>姬霓太美</strong></a>\n" +
                        "          <p>\n" +
                        "            本歌曲/视频为知名流量明星蔡徐坤早年在偶像练习生节目中的一场歌（尬）舞。\n" +
                        "\n" +
                        "\n" +
                        "          </p><br>\n" +
                        "        </section>\n" +
                        "      </li>\n" +
                        "      <li>\n" +
                        "        <div class=\"line\"></div>\n" +
                        "        <section class=\"body\">\n" +
                        "          <time class=\"time\">2022-07-05</time>\n" +
                        "          <a href=\"#\">项目名：<strong>姬霓太美</strong></a>\n" +
                        "          <p>\n" +
                        "            在该歌舞视频中，其行云流水（不忍直视）的基础球技和精彩绝伦（匪夷所思）的动作\n" +
                        "          </p><br>\n" +
                        "        </section>\n" +
                        "      </li>\n" +
                        "      <li>\n" +
                        "        <div class=\"line\"></div>\n" +
                        "        <section class=\"body\">\n" +
                        "          <time class=\"time\">2022-07-05</time>\n" +
                        "          <a href=\"#\">项目名：<strong>姬霓太美</strong></a>\n" +
                        "          <p>\n" +
                        "            正片歌舞中第一句歌词“只因你太美”因语速过快发音不清故被黑粉丝小黑子空耳为本句“鸡你太美”。\n" +
                        "          </p><br>\n" +
                        "        </section>\n" +
                        "      </li>\n" +
                        "      <li>\n" +
                        "        <div class=\"line\"></div>\n" +
                        "        <section class=\"body\">\n" +
                        "          <time class=\"time\">2022-07-05</time>\n" +
                        "          <a href=\"#\">项目名：<strong>姬霓太美</strong></a>\n" +
                        "          <p>\n" +
                        "            生动到位（鬼畜）的表情令人刮目相看（大跌眼镜），因此在网络上红及一时，流传开来。\n" +
                        "          </p><br>\n" +
                        "        </section>\n" +
                        "      </li>\n" +
                        "      <li>\n" +
                        "        <div class=\"line\"></div>\n" +
                        "        <section class=\"body\">\n" +
                        "          <time class=\"time\">2022-07-05</time>\n" +
                        "          <a href=\"#\">项目名：<strong>姬霓太美</strong></a>\n" +
                        "          <p>\n" +
                        "            正片歌舞中第一句歌词“只因你太美”因语速过快发音不清故被黑粉丝小黑子空耳为本句“鸡你太美”。\n" +
                        "          </p>\n" +
                        "        </section>\n" +
                        "      </li>\n" +
                        "      <h3>...</h3>\n" +
                        "    </ul>\n" +
                        "  </div>\n" +
                        "\n" +
                        "</div>\n" +
                        "\n" +
                        "</body>\n" +
                        "</html>");
    }
}
