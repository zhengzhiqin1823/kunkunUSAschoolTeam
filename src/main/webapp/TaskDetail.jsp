<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2022/7/11
  Time: 17:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="shortcut icon" href="../main.ico" type="image/x-icon" />
    <link rel="stylesheet" href="./css/查看团队项目报告详情.css">
    <link rel="stylesheet" href="./css/导航栏.css">
    <link rel="stylesheet" href="./css/按钮.css ">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/jquery.slim.min.js"></script>
    <script src="js/bootstrap.bundle.min.js"></script>
    <title>项目详情</title>
</head>
<body onload="init(${taskid})">

<div class="demo-navigation1" >
    <nav>
        <ul>
            <li><a href="admin.html">TeamAdmin</a></li>
            <li><a href="adminTutor.html">TutorAdmin</a></li>
            <li><a href="adminTask.html">TaskAdmin</a></li>
        </ul>
    </nav>
</div>
    <div id="header-container" class="navbar-nav container-fluid d-flex flex-wrap">

        <div id="login-state-no" hidden="hidden" id="no-login-box" class="text-end">
            <button type="button" class="btn btn-outline-light me-2">Login</button>
            <button type="button" class="btn btn-warning">Sign-up</button>
        </div>
        <!--
            登录状态显示:   login-state-yes
        -->
    </div>
<%--</header>--%>

<div class="bottom" style="min-height: 100vh;">
    <!--
        左侧边栏:   aside-container
    -->
    <aside id="aside-container" class="left">
        <div class="project_title">
            项目详情
        </div>
        <div class="table" >
            <table id="ProjectDetail">
            </table>
        </div>
        <input id="ThisTaskId" style="display: none" value=${taskid} >
    </aside>

    <!--
        显示项目概览的部分
    -->
    <div class="flex-auto col-md-8 col-lg-8 px-3 px-lg-5">

        <!--
            项目选项栏, 用于选择全部项目, 未结题项目等: project-tab
        -->

        <!--
            项目页表
        -->
        <div id="project-tabContent" class="tab-content" style="width: 1370px">
            <!--
           隐藏修改框
        -->
            <div id="report_admin" class="tab-pane fade show active" role="tabpanel" aria-labelledby="nav-home-tab"style="z-index: 1">

    <div style="width: 40px">
        <dialog style="display: none;margin-top: 8%;margin-left: 50%;width: 400px;height: 185px;z-index:20;position: fixed ;" id="TeamInfoInsert">
            <div style="margin-left: 70px;margin-top: 10px">
                团队编号<input type="text" id="InsertTeamId" value="default"><br>
                团队名称<input type="text" id="InsertTeamName"><br>
                团队邮箱<input type="text" id="InsertTeamEmail"><br>
                团队电话<input type="text" id="InsertTeamTel"><br>
                <table style="margin-left: 10px">
                    <tr>
                        <td>
                            <input class="demo-button3" type="button" value="确认提交" id="TeamInfoInsertConfirm">
                        </td>
                        <td>
                            <input class="demo-button3" type="button" value="关闭窗口" id="TeamInfoInsertCancel">
                        </td>
                    </tr>
                </table><span id="InsertStudentMsg" style="margin-left: 30px;color: #dc3545"></span>
            </div>
        </dialog>

    </div>
                <div class="card">
                    <table id="submissions" style="width: 1280px;margin-top: 50px;"></table>
                </div>
                <div>
                    <!--这里显示一个详细按钮-->
                    <table style="margin-left: 480px">
                        <tr>
                            <td>
                                <button class="demo-button1" id="creatJoinTeam">
                                    新增参加团队
                                </button>
                            </td>
                            <td>
                                <form method="get" action="/0628JavaWebExercise_war/GoCreatSubmissionServlet">
                                <input value="${taskid}" name="tid" style="display: none">
                                    <input class="demo-button1" type="submit" value="新建报告">
                                </form>

                            </td>
                        </tr>
                    </table>

                </div>

            </div>

        </div>

    </div>
</div>
<!--
    网页末尾
    如果要加备案信息在这里加
-->
<script>
    function init(tid){
        let xhttp;
        if (window.XMLHttpRequest) {
            xhttp = new XMLHttpRequest();
        } else {
            // code for IE6, IE5
            xhttp = new ActiveXObject("Microsoft.XMLHTTP");
        }
        xhttp.open("GET","/0628JavaWebExercise_war/AdminServlet?ret=6&tid="+tid);
        xhttp.send();
        xhttp.onreadystatechange=function ()
        {
            if (this.readyState == 4 && this.status == 200) {
                let str=this.responseText;
                let student=str.split(";")
                let i;
                let j;
                let table="";
                for(i=0;i<student.length-1;i++)
                {
                    let studentinfo=student[i].split(",");
                    table+="<tr><td >项目编号:"+studentinfo[0]+"</td></tr>"
                    table+="<tr><td >项目名称:"+studentinfo[1]+"</td></tr>"
                    table+="<tr><td >项目提交开始时间:"+studentinfo[4]+"</td></tr>"
                    table+="<tr><td >项目提交结束时间:"+studentinfo[5]+"</td></tr>"
                    table+="<tr></tr>"
                    table+="<tr><td>项目描述:</td></tr>"
                    table+="<tr><td>"
                    table+="<textarea style='margin-left: 0px;width:440px;height: 500px;resize: none; overflow-x:auto;' readonly='readonly'>"+studentinfo[3]+" </textarea>"
                    table+="</td></tr>"
                    document.getElementById("ProjectDetail").innerHTML=table;
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
        xhtp.open("GET","/0628JavaWebExercise_war/AdminTaskServlet?ret=3&tid="+tid);
        xhtp.send();
        xhtp.onreadystatechange=function ()
        {
            if (this.readyState == 4 && this.status == 200) {
                let str=this.responseText;
                let student=str.split(";")
                let i;
                let table="";
                table+="<tr><th >报告名称</th><th >提交权限</th><th >审批权限</th><th >开始时间</th><th >结束时间</th><th>提交数量</th><th></th><th>查看报告详情</th><th>查看提交报告</th></tr>"
                for(i=0;i<student.length-1;i++)
                {
                    let studentinfo=student[i].split(",");
                    table+="<tr>"
                    table+="<td >"+"<input type='text' readonly='readonly' style='width:120px;border: 0;outline: 0;background-color: rgba(0, 0, 0, 0);margin-left: 5%;margin-right:5%;' id=\""+studentinfo[0]+"N\" value='"+studentinfo[1]+"'</td>";
                    if(studentinfo[2]=="1")
                    {
                        table+="<td>"+"<input class='demo-button3' style='width: 80px' type='button' value='可提交' id=\""+studentinfo[0]+"SS\" onclick='changeSS("+studentinfo[0]+")'></td>";
                    }
                    else
                    {
                        table+="<td>"+"<input class='demo-button3' style='width: 80px' type='button' value='不可提交' id=\""+studentinfo[0]+"SS\" onclick='changeSS("+studentinfo[0]+")'></td>";
                    }
                   // table+="<td >"+"<input type='text' readonly='readonly' style='width:120px;border: 0;outline: 0;background-color: rgba(0, 0, 0, 0);margin-left: 5%;margin-right:5%;' id=\""+studentinfo[0]+"SS\" value='"+studentinfo[2]+"'</td>";
                    if(studentinfo[3]=="1")
                    {
                        table+="<td>"+"<input class='demo-button3' style='width: 80px' type='button' value='可审批' id=\""+studentinfo[0]+"JS\" onclick='changeJS("+studentinfo[0]+")'></td>";
                    }
                    else
                    {
                        table+="<td>"+"<input class='demo-button3' style='width: 80px' type='button' value='不可审批' id=\""+studentinfo[0]+"JS\" onclick='changeJS("+studentinfo[0]+")'></td>";
                    }
                    //table+="<td >"+"<input type='text' readonly='readonly' style='width:120px;border: 0;outline: 0;background-color: rgba(0, 0, 0, 0);margin-left: 5%;margin-right:5%;' id=\""+studentinfo[0]+"JS\" value='"+studentinfo[3]+"'</td>";
                    table+="<td>"+"<input type='text' readonly='readonly' style='width:120px;border: 0;outline: 0;background-color: rgba(0, 0, 0, 0);margin-left: 5%;margin-right:5%;' id=\""+studentinfo[0]+"ST\" value='"+studentinfo[4]+"'</td>";
                    table+="<td >"+"<input type='text' readonly='readonly' style='width:120px;border: 0;outline: 0;background-color: rgba(0, 0, 0, 0);margin-left: 5%;margin-right:5%;' id=\""+studentinfo[0]+"DT\" value='"+studentinfo[5]+"'</td>";
                    table+="<td >"+"<input type='text' readonly='readonly' style='width:120px;border: 0;outline: 0;background-color: rgba(0, 0, 0, 0);' id=\""+studentinfo[0]+"Num\" value='"+studentinfo[6]+"'</td>";
                    table+="<td><input id=\'"+studentinfo[0]+"updateStatus\' style='display: none;' value='1'>"+"</td>"

                    table+="<td><form action='/demo_war_exploded/GoSubmissionDetailServlet' method='get'><input type='text' style='display: none' name='sid' value=\""+studentinfo[0]+"\">"
                    table+="<input style='display: none'; type='text' name='taskid' value=\""+tid+"\">"
                    table+="<input class='demo-button2' type='submit' value='查看报告详情'></td></form>"//这个表单查看详情

                    table+="<td><form action='/demo_war_exploded/SubmissionReportResultServlet' method='get'>"
                    table+="<input style='display: none'; type='text' name='submitID' value=\""+studentinfo[0]+"\">"
                    table+="<input style='display: none'; type='text' name='taskid' value=\""+tid+"\">"
                    table+="<input class='demo-button2' style='margin-top: 30px;' type='submit' value='查看提交报告' ></form></td>";
                    table+="</tr>"
                }
                document.getElementById("submissions").innerHTML=table;
            }
        }
    }
    //改变提交权限
    function changeSS(sid) {
        if(!check())
        {
            return;
        }
        let xhttp;
        if (window.XMLHttpRequest) {
            xhttp = new XMLHttpRequest();
        } else {
            // code for IE6, IE5
            xhttp = new ActiveXObject("Microsoft.XMLHTTP");
        }
        xhttp.open("GET","/0628JavaWebExercise_war/submissionServlet?ret=1&sid="+sid);
        xhttp.send();
        xhttp.onreadystatechange=function ()
        {
            if (this.readyState == 4 && this.status == 200) {
                let s=sid+"SS";
                let ss=document.getElementById(s).value;
                if(ss=="可提交")
                {
                    document.getElementById(s).value="不可提交"
                }
                else
                {
                    document.getElementById(s).value="可提交"
                }
            }
        }
    }
    //改变审批权限
    function changeJS(sid){
        if(!check())
        {
            return;
        }
        let xhttp;
        if (window.XMLHttpRequest) {
            xhttp = new XMLHttpRequest();
        } else {
            // code for IE6, IE5
            xhttp = new ActiveXObject("Microsoft.XMLHTTP");
        }
        xhttp.open("GET","/0628JavaWebExercise_war/submissionServlet?ret=2&sid="+sid);
        xhttp.send();

        xhttp.onreadystatechange=function ()
        {
            if (this.readyState == 4 && this.status == 200) {
                let s=sid+"JS";
                let ss=document.getElementById(s).value;
                if(ss=="可审批")
                {
                    document.getElementById(s).value="不可审批"
                }
                else
                {
                    document.getElementById(s).value="可审批"
                }
            }
        }
    }
    //确认框
    function check()
    {
        if(confirm("确认选择？"))
            return true;
        else
            return false;
    }


    //新增团队
    document.getElementById("creatJoinTeam").onclick=function (){
        document.getElementById("TeamInfoInsert").style.display="block";
        let s=document.getElementById("ThisTaskId").value;
    }
    document.getElementById("TeamInfoInsertCancel").onclick=function (){
        document.getElementById("TeamInfoInsert").style.display="none";
        document.getElementById("InsertStudentMsg").innerText=""
    }
    document.getElementById("TeamInfoInsertConfirm").onclick=function (){
        let taskid=document.getElementById("ThisTaskId").value;
        let tid=document.getElementById("InsertTeamId").value;
        let name=document.getElementById("InsertTeamName").value;
        let email=document.getElementById("InsertTeamEmail").value;
        let tel=document.getElementById("InsertTeamTel").value;
        if(tid==""||name==""||email==""||tel=="")
        {
            document.getElementById("InsertStudentMsg").innerText="请输入完整信息!"
            return;
        }
        let xhttp;
        if (window.XMLHttpRequest) {
            xhttp = new XMLHttpRequest();
        } else {
            // code for IE6, IE5
            xhttp = new ActiveXObject("Microsoft.XMLHTTP");
        }
        xhttp.open("GET","/0628JavaWebExercise_war/teamAdminServlet?ret=3&tid="+tid+"&name="+name+"&email="+email+"&tel="+tel+"&taskid="+taskid);
        xhttp.send();
        xhttp.onreadystatechange=function ()
        {
            if (this.readyState == 4 && this.status == 200) {
                let str=this.responseText;
                if(str=="1")
                    document.getElementById("InsertStudentMsg").innerText="团队已被注册"
                else
                {
                    document.getElementById("InsertStudentMsg").innerText="团队注册成功"
                }
            }
        }
    }

    //新增报告
    document.getElementById("creatSubmission").onclick=function (){
        let s=document.getElementById("ThisTaskId").value;
        alert(s);
    }

</script>
</body>
</html>
