package com.servlet;

import com.mapper.submissionMapper;
import com.test.pojo.submission;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@WebServlet(name = "SubmissionReportResultServlet", value = "/SubmissionReportResultServlet")
public class SubmissionReportResultServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String submitID = request.getParameter("submitID");
        String taskid=request.getParameter("taskid");
        request.setAttribute("taskid",taskid);
        request.setAttribute("submitID",submitID);
        request.getRequestDispatcher("report.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     this.doGet(request,response);
    }
}
