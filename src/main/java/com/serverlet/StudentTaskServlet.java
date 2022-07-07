package com.serverlet;

import com.mapper.submissionMapper;
import com.mapper.taskMapper;
import com.test.pojo.submission;
import com.test.pojo.task;
import jdk.internal.util.xml.XMLStreamException;
import jdk.internal.util.xml.impl.XMLWriter;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/task")
public class StudentTaskServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        /*访问数据库，生成一个HTML文件*/
        String resource = "mybatis-config.xml";
        InputStream is = Resources.getResourceAsStream(resource);
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
        //获取SqlSession对象，来执行sql
        SqlSession sqs = factory.openSession();
        //执行sql
        taskMapper taskMapper = sqs.getMapper(taskMapper.class);
        submissionMapper submissionMapper = sqs.getMapper(submissionMapper.class);

        List<task> tasks = taskMapper.selectByKey(id);

        task t = tasks.get(0);

        PrintWriter printWriter = resp.getWriter();
        StringBuilder response = new StringBuilder();

        response.append("<root>\n" +
                "<name>项目A101：坤坤的偶像剧评鉴</name>\n" +
                "<startTime>2022-07-07</startTime>\n" +
                "<endTime>2022-08-07</endTime>\n" +
                "<description>\n" +
                t.getDescription() +
                "</description>\n");
        response.append("<submissions num=\"" + t.getSubmitNum() + "\">");
        int num = Integer.parseInt(t.getSubmitNum());
        List<submission> submissions = submissionMapper.selectByKey(t.getFirstsm());
//        while(true)
//        {
        response.append("<submission>");
        response.append("<submissionName>" + "submissions.get(0).getName()" + "</submissionName>");
        response.append("<data-father>\n" +
                "守护全世界最好的坤坤️守护全世界最好的坤坤️\n" +
                "</data-father>");
        response.append("<data-button>\n" +
                "                <button class=\"button-submitted\">已提交</button>\n" +
                "                <button class=\"button-submitted\">查看附件</button>\n" +
                "            </data-button>");
        response.append("</submission>");
//            if(submissions.get(0).getNext()==null)
//                break;
//        }
        sqs.commit();
        sqs.close();
        printWriter.write(String.valueOf(response));
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
