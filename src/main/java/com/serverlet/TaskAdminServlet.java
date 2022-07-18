package com.serverlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "TaskAdminServlet", value = "/TaskAdminServlet")
public class TaskAdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean isLogin = false;
        HttpSession session = request.getSession();
        Cookie[] cookies = request.getCookies();
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
            response.sendRedirect("/0628JavaWebExercise_war");
            return;
        }
        String taskid = request.getParameter("taskid");
        request.setAttribute("taskid",taskid);
        request.getRequestDispatcher("TaskDetail.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       this.doGet(request,response);
    }
}
