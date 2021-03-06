package com.servlet.team;

import com.mapper.TeamMapper;
import com.servlet.methods.MD5Utils;
import com.test.pojo.Team;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "teamAdminServlet", value = "/teamAdminServlet")
public class TeamAdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String ret=request.getParameter("ret");
        String tid=request.getParameter("tid");
        String name=request.getParameter("name");
        String email=request.getParameter("email");
        String tel=request.getParameter("tel");
        String taskid=request.getParameter("taskid");
        String resource = "mybatis-config.xml";
        InputStream is= Resources.getResourceAsStream(resource);
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
        //获取SqlSession对象，来执行sql
        SqlSession sqs=factory.openSession();
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        TeamMapper mapper = sqs.getMapper(TeamMapper.class);
        switch (ret)
        {
            case "1":
            {
                List<Team> teams = mapper.selectAll();
                for(Team t:teams)
                {
                    writer.write(t.getTeamid()+","+t.getEmail()+","+t.getName()+","+t.getTel()+","+t.getTaskID()+";");
                }
                break;
            }
            case "2":
            {
                mapper.updatePass(tid);
                break;
            }
            case "3":
            {
                if("default".equals(tid))
                {
                    List<Team> teams =mapper.selectAll();

                    String id=teams.size()+1+"";
                    mapper.insert(
                            id,
                            name,
                            MD5Utils.stringToMD5("123456"),
                            email,
                            tel,
                            taskid);
                }
                else
                {
                    List<Team> teams =mapper.selectAll();
                    for(Team t:teams)
                    {
                        if(t.getTeamid().equals(tid))
                        {
                            writer.write("1");
                            return;
                        }
                    }
                    mapper.insert(tid,name,MD5Utils.stringToMD5("123456"),email,tel,taskid);
                }
            }
            case "4":
            {
                List<Team> teams =mapper.selectAll();
                for(Team t:teams)
                {
                    if(t.getTeamid().contains(tid))
                    {
                        writer.write(t.getTeamid()+","+t.getEmail()+","+t.getName()+","+t.getTel()+","+t.getTaskID()+";");
                    }
                }
                break;
            }
            case "5":
            {
                mapper.updateEmail(tid,email);
                mapper.updateName(tid,name);
                mapper.updateTel(tid,tel);
                writer.write("1");
                break;            }
        }
        sqs.commit();
        sqs.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       this.doGet(request,response);
    }
}
