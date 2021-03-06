package com.servlet.admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/team")
public class AdminHomeServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //检测用户是否登陆
        boolean isLogin = false;
        HttpSession session = req.getSession();
        Cookie[] cookies = req.getCookies();
        System.out.println("id:"+session.getAttribute("id"));
        System.out.println("type:"+session.getAttribute("type"));
        Object id = session.getAttribute("id");
        Object type = session.getAttribute("type");

        if(id!=null&&type!=null) {
            if(type.equals("admin"))
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("id")) {
                    if (cookie.getValue().equals(id)) {
                        isLogin = true;
                        break;
                    }
                }
            }
        }
        //如果没有登陆,则返回登陆
        if (!isLogin) {
            resp.sendRedirect("/0628JavaWebExercise_war");
            return;
        }
        req.getRequestDispatcher("/WEB-INF/admin.html").forward(req, resp);

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
