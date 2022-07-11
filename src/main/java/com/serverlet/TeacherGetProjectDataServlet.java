package com.serverlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/teacherGetProjectData")
public class TeacherGetProjectDataServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String need=req.getParameter("need").toString();
        if(need.equals("rid")){
            System.out.println("ok");
            resp.setContentType("text/text;charset=utf-8");
            resp.setCharacterEncoding("utf-8");
            resp.setStatus(200);
            PrintWriter printWriter = resp.getWriter();

            //getRids

            printWriter.write("rids");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }



}
