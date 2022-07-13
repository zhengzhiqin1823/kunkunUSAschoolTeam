function getProjectData(rid) {

    console.log(rid)

    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", "/0628JavaWebExercise_war/teacherGetProjectData?need=all&rid="+rid.toString(), true)
    xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    xmlHttp.onreadystatechange=function (){
        if(xmlHttp.readyState==4&&xmlHttp.status==200) {
            console.log(xmlHttp.responseText)
            var rawData=JSON.stringify(xmlHttp.responseText)
            var data=JSON.parse(rawData)
            console.log(data)
        }
    }
    xmlHttp.send()
}

function openProject(rID) {
    location.assign("/0628JavaWebExercise_war/judgeProject.html?rid="+rID.toString())
}

function getAllProjects(){
    //alert("getAllProjects")
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", "/0628JavaWebExercise_war/teacherGetProjectData?need=rid", true)
    xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    xmlHttp.onreadystatechange=function (){
        if(xmlHttp.readyState==4&&xmlHttp.status==200){
            let rIDs = JSON.parse(xmlHttp.responseText)
            for(let index in rIDs){
                rID=rIDs[index]
                let xmlHttpDemo = new XMLHttpRequest()
                xmlHttpDemo.open("GET","/0628JavaWebExercise_war/teacherGetProjectData?need=demo&rid="+rID.toString(), true)
                xmlHttpDemo.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
                xmlHttpDemo.onreadystatechange=function (){
<<<<<<< Updated upstream
                    if(xmlHttpDemo.readyState==4&&xmlHttpDemo.status==200){
                        let x=JSON.parse(xmlHttpDemo.responseText)
=======
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
>>>>>>> Stashed changes
                    }
                }
                xmlHttpDemo.send();
            }
        }
    }
    xmlHttp.send()

}