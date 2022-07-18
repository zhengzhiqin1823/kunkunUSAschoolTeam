
function getProjectData(rid,status) {
    //console.log(rid)
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", "/0628JavaWebExercise_war/teacherGetProjectData?need=all&rid="+rid.toString(), true)
    xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    xmlHttp.onreadystatechange=function (){
        if(xmlHttp.readyState==4&&xmlHttp.status==200) {
            //console.log(xmlHttp.responseText)
            var data=JSON.parse(xmlHttp.responseText)
            //console.log(data)
            let name=""+data['name']+""
            let description=""+data['description']+""
            let details=""+data['details']+""
            document.getElementById('project_name').insertAdjacentHTML('beforeend',name)
            document.getElementById('project_description').insertAdjacentHTML('beforeend',description)
            document.getElementById('project_details').insertAdjacentHTML('beforeend',details)

            let cache=data['cache']
            if(cache=='true'||status=="judged"){
                let xmlHttpRequest = new XMLHttpRequest()
                xmlHttpRequest.open("POST", "/0628JavaWebExercise_war/teacherGetProjectData", true)
                xmlHttpRequest.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
                xmlHttpRequest.onreadystatechange=function (){
                    if(xmlHttpRequest.readyState==4&&xmlHttpRequest.status==200) {
                        let judge_text=document.getElementById('judge_text')
                        judge_text.innerHTML=xmlHttpRequest.responseText;
                    }
                }
                xmlHttpRequest.send("rid="+rid+"&status="+status+"&type=text");

                let xmlHttp = new XMLHttpRequest()
                xmlHttp.open("POST", "/0628JavaWebExercise_war/teacherGetProjectData", true)
                xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
                xmlHttp.onreadystatechange=function (){
                    if(xmlHttp.readyState==4&&xmlHttp.status==200) {
                        let score=document.getElementById('score')
                        score.value=xmlHttp.responseText;
                    }
                }
                xmlHttp.send("rid="+rid+"&status="+status+"&type=score")
            }
        }
    }
    xmlHttp.send()
}

function openProject(rID) {
    location.assign("/0628JavaWebExercise_war/judgeProject.html?rid="+rID.toString()+"&status=unjudged")
}

function openJudgedProject(rID) {
    location.assign("/0628JavaWebExercise_war/judgeProject.html?rid="+rID.toString()+"&status=judged")
}

function getAllProjects(){
    //alert("getAllProjects")
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", "/0628JavaWebExercise_war/teacherGetProjectData?need=rid", true)
    xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    xmlHttp.onreadystatechange=function (){
        if(xmlHttp.readyState==4&&xmlHttp.status==200){
            //console.log(xmlHttp.responseText)
            let rIDs = JSON.parse(xmlHttp.responseText)
            for(let index in rIDs['rids']){
                let rID=rIDs['rids'][index]
                let xmlHttpDemo = new XMLHttpRequest()
                xmlHttpDemo.open("GET","/0628JavaWebExercise_war/teacherGetProjectData?need=demo&rid="+rID.toString(), true)
                xmlHttpDemo.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
                xmlHttpDemo.onreadystatechange=function (){
                    if(xmlHttpDemo.readyState==4&&xmlHttpDemo.status==200) {
                        console.log(xmlHttpDemo.responseText)
                        let x = JSON.parse(xmlHttpDemo.responseText)
                        console.log(x)
                        console.log(x["description"])
                        let projects_container = document.getElementById("projects_container")
                        let project_h = "<h4>" + x['name'].toString() + "</h4>"
                        let project_description = "<p>" + x['description'].toString() + "</p>"
                        let project_demo =
                            "<div class=\"right_mid\">"
                            + project_h
                            + project_description
                            + "<button type=\"button\" value=\"" + rID + "\" onclick=\"openProject(\'"+rID.toString()+"\')\">评审</button>"
                            + "</div>"
                        projects_container.insertAdjacentHTML('beforeend',project_demo)
                    }
                }
                xmlHttpDemo.send();
            }
            for(let index in rIDs['judged']){
                let rID=rIDs['judged'][index]
                let xmlHttpDemo = new XMLHttpRequest()
                xmlHttpDemo.open("GET","/0628JavaWebExercise_war/teacherGetProjectData?need=demo&rid="+rID.toString(), true)
                xmlHttpDemo.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
                xmlHttpDemo.onreadystatechange=function (){
                    if(xmlHttpDemo.readyState==4&&xmlHttpDemo.status==200) {
                        console.log(xmlHttpDemo.responseText)
                        let x = JSON.parse(xmlHttpDemo.responseText)
                        console.log(x)
                        console.log(x["description"])
                        let projects_container = document.getElementById("projects_container")
                        let project_h = "<h4>" + x['name'].toString() + "(已评审)</h4>"
                        let project_description = "<p>" + x['description'].toString() + "</p>"
                        let project_demo =
                            "<div class=\"right_mid\">"
                            + project_h
                            + project_description
                            + "<button type=\"button\" value=\"" + rID + "\" onclick=\"openJudgedProject(\'"+rID.toString()+"\')\">修改评审意见</button>"
                            + "</div>"
                        projects_container.insertAdjacentHTML('beforeend',project_demo)
                    }
                }
                xmlHttpDemo.send();
            }
        }
    }
    xmlHttp.send()
}

function getParams(key) {
    let reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
    let r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return unescape(r[2]);
    }
    return null;
};

function sendData(){
    let judge_text=document.getElementById('judge_text').value
    let score=document.getElementById('score').value
    let rid=getParams("rid")
    let xmlHttpRequest = new XMLHttpRequest()
    xmlHttpRequest.open("POST", "/0628JavaWebExercise_war/teacherJudgeServlet", true)
    xmlHttpRequest.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    xmlHttpRequest.onreadystatechange=function (){
        if(xmlHttpRequest.readyState==4&&xmlHttpRequest.status==200) {
            location.assign("/0628JavaWebExercise_war/teacherHome.html")
        }
    }
    console.log("rid:"+rid)
    console.log("score:"+score)
    console.log("judge_text:"+judge_text)
    xmlHttpRequest.send("rid="+rid+"&score="+score+"&judge_text="+judge_text+"&type=save")
}

function saveData(){
    let judge_text=document.getElementById('judge_text').value
    let score=document.getElementById('score').value
    let rid=getParams("rid")
    let xmlHttpRequest = new XMLHttpRequest()
    xmlHttpRequest.open("POST", "/0628JavaWebExercise_war/teacherJudgeServlet", true)
    xmlHttpRequest.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    xmlHttpRequest.onreadystatechange=function (){
        if(xmlHttpRequest.readyState==4&&xmlHttpRequest.status==200) {
            location.assign("/0628JavaWebExercise_war/teacherHome.html")
        }
    }
    console.log("rid:"+rid)
    console.log("score:"+score)
    console.log("judge_text:"+judge_text)
    xmlHttpRequest.send("rid="+rid+"&score="+score+"&judge_text="+judge_text+"&type=cache")
}
