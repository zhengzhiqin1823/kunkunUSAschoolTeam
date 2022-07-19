<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2022/7/11
  Time: 17:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="shortcut icon" href="../main.ico" type="image/x-icon" />
    <link rel="stylesheet" href="css/ReadTeamReport.css">
    <link rel="stylesheet" href="./css/demo-navigation.css">
    <link rel="stylesheet" href="./css/demo-button1.css ">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/jquery.slim.min.js"></script>
    <script src="js/bootstrap.bundle.min.js"></script>
    <script src="scripts/demo-navigation1.js"></script>
    <title>报告提交详情</title>
</head>
<body onload="init(${submitID},${taskid})">
<!--
    首部栏: header
-->
<div class="demo-navigation2">
    <div class="logo">坤坤的美国校队</div>
    <nav>
        <ul>
            <li onclick="admin_team()">Team</li>
            <li onclick="admin_tutor()">Tutor</li>
            <li onclick="admin_project()">Task</li>
        </ul>
    </nav>
    <div class="logo2" onclick="logout()">退出登陆</div>
</div>

<div class="d-md-flex color-bg-inset" style="min-height: 100vh;">
    <!--
        左侧边栏:   aside-container
    -->
    <aside id="aside-container" class="left">
        <div class="project_title">
            项目详情
        </div>
        <div class="table" >
            <table id="SubmissionDetail">
            </table>
        </div>

    </aside>

    <!--
        显示项目概览的部分
    -->
    <div class="flex-auto col-md-8 col-lg-8 px-3 px-lg-5">

        <!--
            项目选项栏, 用于选择全部项目, 未结题项目等: project-tab
        -->
        <nav>
            <div id="project-tab" class="nav nav-tabs" role="tablist">
                <a id="adminreport" class="nav-link active" data-toggle="tab" href="#report_admin" role="tab" aria-controls="nav-home" aria-selected="true">项目提交详情</a>
            </div>
        </nav>
        <!--
            项目页表
        -->
        <div id="project-tabContent" class="tab-content" style="width: 1270px">
            <div style="display: none;margin-left:165px;margin-top:145px;width: 750px;height: 450px;z-index: 20;position: fixed;background-color: #c8cbcf;" id="Tutorselect">
                <table id="tutors" style="width: 670px;height:360px;margin-top: 20px;margin-left: 40px">
                </table>
                <div style="bottom: 0;position:relative;">
                    <tr>
                        <td>
                            <input class='demo-button3' style="margin-left: 10px"  value="确定" id="JudgeLinkGenerateConfirm">
                        </td>
                    </tr>
                </div>
            </div>
            <!--
           隐藏修改框
        -->
            <div id="report_admin" class="tab-pane fade show active" role="tabpanel" aria-labelledby="nav-home-tab"style="z-index: 1;">
                <div style="display:none;margin-top: 1%;margin-left: 5%;width: 850px;height: 660px;z-index:20;position: fixed ;background-color: white;border: 3px;
                    border: 3px solid #1d2124;border-radius: 5px;font-family: 华文宋体;font-size: 20px;overflow-y: auto;" id="ReportDetail">
                    <table id="TaskInfoHeaderDetail" cellspacing="20" cellpadding="10px" style="width: 800px">
                        <th>报告编号</th><th >团队编号</th><th >提交时间</th>
                        <tr>
                            <td><input id="ReportDetailId" type="text" readonly="readonly" style="width:100px;border: 0;outline: 0;background-color: rgba(0, 0, 0, 0);text-align: center"></td>
                            <td><input id="ReportDetailTeamId" type="text" readonly="readonly" style="width:100px;border: 0;outline: 0;background-color: rgba(0, 0, 0, 0);text-align: center"></td>
                            <td><input id="ReportDetailSubmitTime" type="text" readonly="readonly" style="width:160px;border: 0;outline: 0;background-color: rgba(0, 0, 0, 0);text-align: center"></td>
                        </tr>
                    </table><br>
                    <table id="TaskInfoReportDetail" readonly="readonly"></table>
                    <h1 style="font-family: 华文宋体;margin-left:15px">报告详细:</h1>
                    <textarea id="ReportDetailInfo" style="margin-left: 40px;width:760px;margin-right: 40px;height: 480px;
                    resize: none; overflow-y:scroll;" readonly="readonly">
                    </textarea>
                    <table>
                        <tr>
                            <td><input class="demo-button3" type="button" id="ReportDetailClose" style="width: 75px;margin-left: 760px;margin-top: 5px" value="关闭"></td>
                        </tr>
                    </table>
                </div>
                <!--这里显示学生表-->
                <div class="card">
                    <table style="width: 1270px" id="submissionReports"></table>
                </div>
                <br/>
            </div>

        </div>

    </div>
</div>
<!--
    网页末尾
    如果要加备案信息在这里加
-->
<footer id="footer" class="footer py-3 bd-navbar border-top bg-white fixed-bottom" style="height: 90px">
                    <table style="margin-left: 150px">
                        <td>
                            <input type="text" style="border: #343a40 solid 2px;border-radius: 2px;" value="未选择评审导师" readonly="readonly" id="selectedTutorId">
                        </td>
                        <td>
                            <input type="button" class="demo-button1" value="选择评审导师" id="selectTutor" style="margin-left: 300px;margin-top: -10px;width: 200px;height: 70px">
                        </td>
                        <td>
                            <input type="button" class ="demo-button1"value="生成评审连接" id="JudgeLinkConfirm" style="margin-left: 300px;margin-top: -10px;width: 200px;height: 70px">
                        </td>
                        <td>
                            <input type="text" style="border: #343a40 solid 2px;border-radius: 2px;margin-top: -15px;margin-left: 100px;width: 300px" value="未新建链接" readonly="readonly" id="CreatNearLink">
                        </td>
                    </table>
                    <input type="text" style="display: none" id='ThisSubmitID' value=${submitID}>
                    <input type="text" style="display: none" id='Thistaskid' value=${taskid}>
</footer>
<script>
    //初始化页面
    function init(sid,tid){
        let xhttp;
        if (window.XMLHttpRequest) {
            xhttp = new XMLHttpRequest();
        } else {
            // code for IE6, IE5
            xhttp = new ActiveXObject("Microsoft.XMLHTTP");
        }
        xhttp.open("GET","http://localhost:8080/0628JavaWebExercise_war/AdminServlet?ret=8&tid="+tid+"&sid="+sid);
        xhttp.send();
        xhttp.onreadystatechange=function ()
        {
            if (this.readyState == 4 && this.status == 200) {
                let str=this.responseText;
                let student=str.split(";")
                let i;
                let table="";
                for(i=0;i<student.length-1;i++)
                {
                    let studentinfo=student[i].split(",");
                    table+="<tr><td >项目编号:"+studentinfo[0]+"</td></tr>"
                    table+="<tr><td >项目名称:"+studentinfo[1]+"</td></tr>"
                    table+="<tr><td >报告类型名称:"+studentinfo[2]+"</td></tr>"
                    table+="<tr><td >报告提交开始时间:"+studentinfo[3]+"</td></tr>"
                    table+="<tr><td >报告提交结束时间:"+studentinfo[4]+"</td></tr>"
                    table+="<tr></tr>"
                    table+="<tr><td>项目描述:</td></tr>"
                    table+="<tr><td>"
                    table+="<textarea style='margin-left: 0px;width:440px;height: 500px;resize: none; overflow-x:auto;' readonly='readonly'>"+studentinfo[5]+" </textarea>"
                    table+="</td></tr>"
                    document.getElementById("SubmissionDetail").innerHTML=table;
                }
            }
        }
        let xhtp;
        if (window.XMLHttpRequest) {
            xhtp = new XMLHttpRequest();
        } else {
            // code for IE6, IE5
            xhtp = new ActiveXObject("Microsoft.XMLHTTP");
        }
        xhtp.open("GET","http://localhost:8080/0628JavaWebExercise_war/reportServlet?ret=4&sid="+sid);
        xhtp.send();
        xhtp.onreadystatechange=function ()
        {
            if (this.readyState == 4 && this.status == 200) {
                let str=this.responseText;
                let student=str.split(";")
                let i;
                let table="";
                table+="<tr><th>批量选择</th><th>报告编号</th><th>团队编码</th><th>报告提交时间</th><th>评审导师</th><th>审批链接</th></tr>"
                if(student.length==1)
                {
                    table+="<tr>该报告暂无团队提交</tr>"
                }
                else
                {
                    for(i=0;i<student.length-1;i++)
                    {
                        let studentinfo=student[i].split(",");
                        table+="<tr style='height:40px ;'>"
                        table+="<td><input type='checkbox' name='rid' value=\""+studentinfo[2]+"\"></td>"
                        table+="<td>"+studentinfo[2]+"</td>";
                        table+="<td>"+studentinfo[0]+"</td>";
                        table+="<td>"+studentinfo[1]+"</td>";
                        table+="<td>"+studentinfo[5]+"</td>";
                        table+="<td>"+studentinfo[6]+"</td>";
                        table+="<td><input  class='demo-button2' type='button' style='margin-top: 0px;'value='报告内容' onclick='reportDetail("+studentinfo[2]+")'></td>"
                        table+="<td><form action='http://localhost:8080/0628JavaWebExercise_war/submit' method='get'>"
                        table+="<input style='display: none'; type='text' name='rid' value=\""+studentinfo[0]+"\">"
                        table+="<input style='display: none'; type='text' name='taskID' value=\""+tid+"\">"
                        table+="<input style='display: none'; type='text' name='submitID' value=\""+sid+"\">"
                        table+="<input style='display: none'; type='text' name='teamID' value=\""+studentinfo[0]+"\">"
                        table+="<input class='demo-button2' style='margin-top: 0px;margin-left:-100px ' type='submit' value='审批详情' ></form></td>";
                        table+="</tr>"
                    }
                }
                document.getElementById("submissionReports").innerHTML=table;
            }
        }
        let xhp;
        if (window.XMLHttpRequest) {
            xhp = new XMLHttpRequest();
        } else {
            // code for IE6, IE5
            xhp = new ActiveXObject("Microsoft.XMLHTTP");
        }
        xhp.open("GET","http://localhost:8080/0628JavaWebExercise_war/AdminServlet?ret=2");
        xhp.send();
        xhp.onreadystatechange=function ()
        {
            if (this.readyState == 4 && this.status == 200) {
                let str=this.responseText;
                let student=str.split(";")
                let i;
                let j;
                let table="";
                table+="<tr style='width: 670px;height: 60px'><th>请选择评审导师</th><th>导师号</th><th>导师姓名</th></tr>"
                for(i=0;i<student.length-1;i++)
                {
                    let studentinfo=student[i].split(",");
                    table+="<tr style='width: 670px;height: 60px'>"
                    table+="<td style='height: 40px'><input type='radio' name='tutor' value=\""+studentinfo[0]+"\" ></td>"
                    table += "<td style='height: 40px'>" + studentinfo[0] + "</td>";
                    table += "<td style='height: 40px'>" + studentinfo[2] + "</td>";
                    table+="</tr>"
                }
                document.getElementById("tutors").innerHTML=table;
            }
        }
    }
    //生成导师链接
    document.getElementById("selectTutor").onclick=function (){
        document.getElementById("Tutorselect").style.display="block";
    }
    document.getElementById("JudgeLinkGenerateConfirm").onclick=function (){
        let tids=document.getElementsByName("tutor");
        for(let i=0;i<tids.length;i++)
        {
            if(tids[i].checked==true)
            {
                document.getElementById("selectedTutorId").value=tids[i].value;
            }

        }
        document.getElementById("Tutorselect").style.display="none";
    }
    document.getElementById("JudgeLinkConfirm").onclick=function (){
        let rids=document.getElementsByName("rid");
        let sid=document.getElementById("ThisSubmitID").value;
        let tid=document.getElementById("Thistaskid").value;
        let ridList="";
        let j=1;
        for(let i=0;i<rids.length;i++)
        {
            if(rids[i].checked==true)
            {
                if(j==1)
                { ridList+=rids[i].value;
                    j=0;
                }
                else
                {ridList+=",";
                    ridList+=rids[i].value;
                }
            }
        }
        let tutorId=document.getElementById("selectedTutorId").value;
        if(ridList=="")
        {
            alert("请勾选需要审评的报告")
            return;
        }
        if(tutorId=="未选择评审导师")
        {
            alert("请选择评审导师");
            return;
        }
        let xhttp;
        if (window.XMLHttpRequest) {
            xhttp = new XMLHttpRequest();
        } else {
            // code for IE6, IE5
            xhttp = new ActiveXObject("Microsoft.XMLHTTP");
        }
        xhttp.open("POST","http://localhost:8080/0628JavaWebExercise_war/linkGenerateServlet");
        xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhttp.send("rids="+ridList+"&submitID="+sid+"&taskid="+tid+"&tid="+tutorId);
        xhttp.onreadystatechange=function ()
        {
            if (this.readyState == 4 && this.status == 200) {
                let i=this.responseText;
                let info=i.split(";");
                let infos=info[0].split(",")
                if(infos[0]=="1")
                {
                    alert("成功生成链接")
                    document.getElementById("CreatNearLink").value=info[1];
                }
                else
                {
                    alert("tips:已自动取消已生成链接的报告，请再次选择")
                    for(let j=1;j<infos.length;j++)
                    {
                        let k="";
                        k+=infos[j];
                        for(let i=0;i<rids.length;i++)
                        {
                            if(rids[i].checked==true&&rids[i].value==k)
                            {
                                rids[i].checked=false;
                            }
                        }
                    }
                }
            }
        }
    }
    //查看报告的内容
    function reportDetail(rid)
    {
        document.getElementById("ReportDetail").style.display="block";
        let xhttp;
        if (window.XMLHttpRequest) {
            xhttp = new XMLHttpRequest();
        } else {
            // code for IE6, IE5
            xhttp = new ActiveXObject("Microsoft.XMLHTTP");
        }
        xhttp.open("GET","http://localhost:8080/0628JavaWebExercise_war/reportServlet?ret=2&rid="+rid);
        xhttp.send();
        xhttp.onreadystatechange=function ()
        {
            if (this.readyState == 4 && this.status == 200) {
                let str=this.responseText;
                let student=str.split(";")
                let i;
                for(i=0;i<student.length-1;i++)
                {
                    let studentinfo=student[i].split(",");
                    document.getElementById("ReportDetailId").value=studentinfo[0];
                    document.getElementById("ReportDetailTeamId").value=studentinfo[1];
                    document.getElementById("ReportDetailSubmitTime").value=studentinfo[2];
                }
            }
        }
        let xhtp;
        if (window.XMLHttpRequest) {
            xhtp = new XMLHttpRequest();
        } else {
            // code for IE6, IE5
            xhtp = new ActiveXObject("Microsoft.XMLHTTP");
        }
        xhtp.open("GET","http://localhost:8080/0628JavaWebExercise_war/selReportServlet?rid="+rid);
        xhtp.send();
        xhtp.onreadystatechange=function ()
        {
            if (this.readyState == 4 && this.status == 200) {
                let str=this.responseText;
                document.getElementById("ReportDetailInfo").value=str;
            }
        }
    }
    //关闭报告内容查看窗口
    document.getElementById("ReportDetailClose").onclick=function (){
        document.getElementById("ReportDetail").style.display="none";
    }
</script>
</body>
</html>
