function getProjectData(rid) {
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", "/0628JavaWebExercise_war/teacherGetProjectData?need=description&rid="+rid.toString(), true)
    xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    xmlHttp.onreadystatechange=function (){
        if(xmlHttp.readyState==4) {
            if (xmlHttp.status == 200) {

            }
        }
    }
    xmlHttp.send()
}

function getAllProjects(){
    //alert("getAllProjects")
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", "/0628JavaWebExercise_war/teacherGetProjectData?need=rid", true)
    xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    xmlHttp.onreadystatechange=function (){
        if(xmlHttp.readyState==4&&xmlHttp.status==200){

            alert(xmlHttp.responseText)

            let rIDs = JSON.parse(xmlHttp.responseText)
            for(let rID in rIDs){
                let xmlHttpDemo = new XMLHttpRequest()
                xmlHttpDemo.open("GET","/0628JavaWebExercise_war/teacherGetProjectData?need=demo&rid="+rID.toString(), true)
                xmlHttpDemo.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
                xmlHttpDemo.onreadystatechange=function (){
                    if(xmlHttpDemo.readyState==4&&xmlHttpDemo.status==200){
                        let x=JSON.parse(xmlHttpDemo.responseText)
                    }
                }
                xmlHttpDemo.send();
            }
        }
    }
    xmlHttp.send()

}