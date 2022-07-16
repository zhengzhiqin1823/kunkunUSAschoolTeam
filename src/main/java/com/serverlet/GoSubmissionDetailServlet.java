package com.example.demo;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "GoSubmissionDetailServlet", value = "/GoSubmissionDetailServlet")
public class GoSubmissionDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sid = request.getParameter("sid");
        String tid = request.getParameter("taskid");
        request.setAttribute("tid",tid);
        request.setAttribute("sid",sid);
        request.getRequestDispatcher("SubmissionDetail.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }
}
