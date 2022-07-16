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

<body onload="init(${sid},${tid})">
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
                <input style="border: #1d2124 solid 0px;font-size: 20px;font-family: 华文宋体" readonly="readonly" id="submissionName" type="text">
            </div>
        </div>
<%--        下面的div显示项目的描述--%>
        <div class="data" >
            <textarea id="submissionDescription" style="border: 0;margin-left: 40px;width:88%;margin-right: 40px;height: 300px;resize: none;font-size: 18px;font-family: 华文宋体" readonly="readonly">
            </textarea>
        </div>
    </div>
    <div class="details">
        <div>
            <table id="submissionDetails" style="width: 370px;height: 150px">
                <tr><th>报告提交开始时间</th><th>报告提交结束时间</th></tr>
                <tr>
                    <td>
                        <input id="start" readonly="readonly" style="border: #1d2124 solid 2px;width:150px;height: 40px;font-size: 15px;text-align: center;margin-left: 15px" type="text" value="yyyy-mm-dd">
                    </td>
                    <td>
                        <input id="deadline" readonly="readonly" style="border: #1d2124 solid 2px;width:150px;height: 40px;font-size: 15px;text-align: center;margin-left: 15px" type="text" value="yyyy-mm-dd">
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <div>
        <input type="button" class="demo-button1" id="updateButton" value="修改">
        <form action="http://localhost:8080/0628JavaWebExercise_war/TaskAdminServlet" method="get">
            <input id="asd" name="taskid" value="${tid}" style="display: none" >
            <input class="demo-button1" type="submit" value="返回"><br>
            <span id="msg" style="color: #dc3545"></span>
        </form>

    </div>
</div>


</body>
<script>
    function init(sid,tid){
        let xhttp;
        if (window.XMLHttpRequest) {
            xhttp = new XMLHttpRequest();
        } else {
            // code for IE6, IE5
            xhttp = new ActiveXObject("Microsoft.XMLHTTP");
        }
        xhttp.open("GET","http://localhost:8080/0628JavaWebExercise_war/submissionServlet?ret=3&sid="+sid);
        xhttp.send();
        xhttp.onreadystatechange=function ()
        {
            if (this.readyState == 4 && this.status == 200) {
                let str=this.responseText;
                let info=str.split(",");
                document.getElementById("submissionDescription").value=info[0];
                document.getElementById("submissionName").value=info[1];
                document.getElementById("start").value=info[2];
                document.getElementById("deadline").value=info[3];
            }
        }
        let xhtp;
        if (window.XMLHttpRequest) {
            xhtp = new XMLHttpRequest();
        } else {
            // code for IE6, IE5
            xhtp = new ActiveXObject("Microsoft.XMLHTTP");
        }
        xhtp.open("GET","http://localhost:8080/0628JavaWebExercise_war/AdminTaskServlet?ret=4&tid="+tid);
        xhtp.send();
        xhtp.onreadystatechange=function ()
        {
            if (this.readyState == 4 && this.status == 200) {
                let str=this.responseText;
                document.getElementById("taskName").innerText=str;
            }
        }
    }
  document.getElementById("updateButton").onclick=function (){
        let sssss=document.getElementById("updateButton").value;
        if(sssss=="修改"){
            document.getElementById("submissionDescription").removeAttribute("readonly");
            document.getElementById("submissionName").removeAttribute("readonly");
            document.getElementById("start").removeAttribute("readonly");
            document.getElementById("deadline").removeAttribute("readonly");
            document.getElementById("updateButton").value="提交"
        }

        if(sssss=="提交"){
            let des=document.getElementById("submissionDescription").value;
            let name=document.getElementById("submissionName").value;
            let st=document.getElementById("start").value;
            let dt=document.getElementById("deadline").value;
            let sid=document.getElementById("sid").value;
            if(des==""||name==""||st==""||dt=="")
            {
                document.getElementById("msg").innerText="报告属性不可以为空!";
                return;
            }
            let xhttp;
            if (window.XMLHttpRequest) {
                xhttp = new XMLHttpRequest();
            } else {
                // code for IE6, IE5
                xhttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
            xhttp.open("GET","http://localhost:8080/0628JavaWebExercise_war/submiUpdateServlet?submitID="+sid+"&name="+name+"&startTime="+st+"&deadLine="+dt+"&description="+des);
            xhttp.send();
            xhttp.onreadystatechange=function ()
            {
                if (this.readyState == 4 && this.status == 200) {
                    let str=this.responseText;
                    if(str=="1")
                    {
                        document.getElementById("msg").innerText="修改成功";
                    }
                }
            }
            document.getElementById("submissionDescription").setAttribute("readonly","true");
            document.getElementById("submissionName").setAttribute("readonly","true");
            document.getElementById("start").setAttribute("readonly","true");
            document.getElementById("deadline").setAttribute("readonly","true");
            document.getElementById("updateButton").value="修改"
        }

  }
</script>
</html>
