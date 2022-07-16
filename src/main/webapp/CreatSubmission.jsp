<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2022/7/11
  Time: 17:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">

<head>
    <meta charset="UTF-8">
    <title>项目页面</title>
    <link rel="stylesheet" href="./css/评审信息.css" />
    <link rel="stylesheet" href="./css/导航栏.css" />
    <link rel="stylesheet" href="./css/按钮.css"></link>
</head>

<body>
<div class="demo-navigation1" >
    <nav>
        <ul>
            <li><a href="admin.html">TeamAdmin</a></li>
            <li><a href="adminTutor.html">TutorAdmin</a></li>
            <li><a href="adminTask.html">TaskAdmin</a></li>
        </ul>
    </nav>
</div>
<input style="display: none;" id="sid" value="${sid}">
<div class="bottom">
    <div class="report_name" id="taskName">报告详情</div>
    <div class="report" style="height:500px ">
        <div class="team_and_time">
            <div class="team" >
                <input style="border: #1d2124 solid 0px;width:250px;height: 40px;font-size: 20px;" id="submissionName" type="text" value="请输入报告名称">
            </div>
        </div>
        <%--        下面的div显示项目的描述--%>
        <div class="data" >
            <textarea id="submissionDescription" style="border: 0;margin-left: 40px;width:80%;margin-right: 40px;height: 300px;resize: none;font-size: 20px" >请输入项目描述</textarea>
        </div>
    </div>
    <div class="details">
        <div>
            <table id="submissionDetails" style="width: 370px;height: 150px">
                <tr><th>请输入报告提交开始时间</th><th>请输入报告提交结束时间</th></tr>
                <tr>
                    <td>
                        <input id="start"  style="border: #1d2124 solid 2px;width:150px;height: 40px;font-size: 15px;text-align: center;margin-left: 15px" type="text" value="yyyy-mm-dd">
                    </td>
                    <td>
                        <input id="deadline"  style="border: #1d2124 solid 2px;width:150px;height: 40px;font-size: 15px;text-align: center;margin-left: 15px" type="text" value="yyyy-mm-dd">
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <div>
        <button class="demo-button1" id="creatButton">提交</button>
        <form action="http://localhost:8080/0628JavaWebExercise_war/TaskAdminServlet" method="get">
            <input name="taskid" value="${tid}" style="display: none" id="tid">
            <input class="demo-button1" type="submit" value="返回"><br>
            <span id="msg" style="color: #dc3545"></span>
        </form>

    </div>
</div>


</body>
<script>
    document.getElementById("creatButton").onclick=function (){
        let des=document.getElementById("submissionDescription").value;
        let name=document.getElementById("submissionName").value;
        let st=document.getElementById("start").value;
        let dt=document.getElementById("deadline").value;
        let taskid=document.getElementById("tid").value;
        if(des=="请输入项目描述"||name=="请输入报告名称"||st=="yyyy-mm-dd"||dt=="yyyy-mm-dd")
        {
            document.getElementById("msg").innerText="请输入完整信息!";
            return;
        }
        let xhttp;
        if (window.XMLHttpRequest) {
            xhttp = new XMLHttpRequest();
        } else {
            // code for IE6, IE5
            xhttp = new ActiveXObject("Microsoft.XMLHTTP");
        }
        xhttp.open("GET","http://localhost:8080/0628JavaWebExercise_war/newSubmiServlet?taskid="+taskid+"&name="+name+"&startTime="+st+"&deadline="+dt+"&description="+des);
        xhttp.send();
        xhttp.onreadystatechange=function ()
        {
            if (this.readyState == 4 && this.status == 200) {
                let str=this.responseText;
                if(str=="1")
                {
                    document.getElementById("msg").innerText="创建成功!";
                }
                else
                {
                    document.getElementById("msg").innerText="创建失败!";
                }
            }
        }

    }
    document.getElementById("submissionDescription").onfocus=function () {
        let des=document.getElementById("submissionDescription").value;
        if(des=="请输入项目描述")
        {
            document.getElementById("submissionDescription").value="";
        }
    }
    document.getElementById("submissionDescription").onblur=function () {
        let des=document.getElementById("submissionDescription").value;
        if(des=="")
        {
            document.getElementById("submissionDescription").value="请输入项目描述";
        }
    }
    document.getElementById("submissionName").onfocus=function () {
        let name=document.getElementById("submissionName").value;
        if(name=="请输入报告名称")
        {
            document.getElementById("submissionName").value="";
        }
    }
    document.getElementById("submissionName").onblur=function () {
        let name=document.getElementById("submissionName").value;
        if(name=="")
        {
            document.getElementById("submissionName").value="请输入报告名称";
        }
    }
    document.getElementById("start").onfocus=function () {
        let st=document.getElementById("start").value;
        if(st=="yyyy-mm-dd")
        {
            document.getElementById("start").value="";
        }
    }
    document.getElementById("start").onblur=function () {
        let st=document.getElementById("start").value;
        if(st=="")
        {
            document.getElementById("start").value="yyyy-mm-dd";
        }
    }
    document.getElementById("deadline").onfocus=function () {
        let dt=document.getElementById("deadline").value;
        if(dt=="yyyy-mm-dd")
        {
            document.getElementById("deadline").value="";
        }
    }
    document.getElementById("deadline").onblur=function () {
        let dt=document.getElementById("deadline").value;
        if(dt=="")
        {
            document.getElementById("deadline").value="yyyy-mm-dd";
        }
    }
</script>
</html>
