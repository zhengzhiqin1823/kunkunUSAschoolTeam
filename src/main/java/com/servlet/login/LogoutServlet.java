package com.servlet.login;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.removeAttribute("id");
        session.removeAttribute("type");
        System.out.println("logout!");
        System.out.println("id:"+session.getAttribute("id"));
        System.out.println("type:"+session.getAttribute("type"));
        resp.sendRedirect("/0628JavaWebExercise_war");
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
