package com.serverlet;

import com.mapper.submissionMapper;
import com.mapper.taskMapper;
import com.test.pojo.submission;
import com.test.pojo.task;
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

@WebServlet(name = "UpdateTaskServlet", value = "/UpdateTaskServlet")
public class UpdateTaskServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String TaskId=request.getParameter("tid");
        String name=request.getParameter("name");
        String startline=request.getParameter("startline");
        String deadline=request.getParameter("deadline");
        String description=request.getParameter("description");
        String submitNum=request.getParameter("submitNum");
        int destSubmitNum=Integer.parseInt(submitNum);
        //获取SqlSession对象，来执行sql
        String resource = "mybatis-config.xml";
        InputStream is= Resources.getResourceAsStream(resource);
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);

        SqlSession sqs=factory.openSession();

        taskMapper mapper = sqs.getMapper(taskMapper.class);
        List<task> tasks = mapper.selectByKey(TaskId);
        task t=tasks.get(0);
        String firstsm = t.getFirstsm();
        int srcSubmitNum=Integer.parseInt(t.getSubmitNum());
        mapper.UpdateByKey(TaskId,name,description,startline,deadline,submitNum);
        sqs.commit();
        //随机加入或删除submission
        if(srcSubmitNum!=destSubmitNum)
        {
            submissionMapper sm = sqs.getMapper(submissionMapper.class);
            List<submission> submissions = sm.selectByKey(firstsm);
            submission s=submissions.get(0);
            String next=s.getNext();
            String sid="";
            while (next!=null)
            {
                sid=next;
                List<submission> submissions1 = sm.selectByKey(sid);
                submission ss=submissions1.get(0);
                next=ss.getNext();
            }
            for(int i=srcSubmitNum;i<destSubmitNum;i++)
            {
                List<submission> submissions1 = sm.selectAll();
                submission send=submissions1.get(submissions1.size()-1);
                String NewNext=send.getSubmitID();
                int num=Integer.parseInt(NewNext);
                num++;
                String NewId="";
                NewId+=num;
                sm.insert(NewId,"","1","1",null,"1970-07-01","1970-07-01","0",null,TaskId);
                sqs.commit();
                sm.updateNext(sid,NewId);
            }
        }
        sqs.commit();
        sqs.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
           this.doGet(request,response);
    }
}
