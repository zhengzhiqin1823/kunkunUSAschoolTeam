package com.serverlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/teacherJudgeServlet")
public class teacherJudgeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("get");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        System.out.println("post");
        HttpSession session = req.getSession();
        String tID = session.getAttribute("id").toString();
        String rID = req.getParameter("rid").toString();
        int score = Integer.parseInt(req.getParameter("score").toString());
        String judge_text=req.getParameter("judge_text").toString();


    }
}
