package com.servlet.admin;

//import com.mapper.fragmentMapper;
import com.mapper.ReportMapper;
import com.test.pojo.report;
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

@WebServlet(name = "selReportServlet", value = "/selReportServlet")
public class SelReportServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rID=request.getParameter("rid");
        String resource = "mybatis-config.xml";
        InputStream is= Resources.getResourceAsStream(resource);
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
        SqlSession sqs=factory.openSession();
        ReportMapper reportMapper = sqs.getMapper(ReportMapper.class);
//        fragmentMapper fragmentMapper = sqs.getMapper(fragmentMapper.class);
        report r = reportMapper.selectoneByKey(rID);
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();

        if(r.getData()!=null) {
//            String firstFm = r.getFirstFm();
//            fragment fm = fragmentMapper.selectoneByKey(firstFm);
//            writer.write(fm.getData());
//            while (!fm.getNext().equals("")) {
//                fm = fragmentMapper.selectoneByKey(fm.getNext());
//                writer.write(fm.getData());
//            }
            writer.write(r.getData());
        }
        else {
            writer.write("暂无报告");
        }
        sqs.close();

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        doGet(request,response);
    }
}
