function checkLogin() {
    let xmlHttpLogin=new XMLHttpRequest();
    xmlHttpLogin.open("GET", "/0628JavaWebExercise_war/login", true)
    xmlHttpLogin.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    xmlHttpLogin.onreadystatechange = function (){
        if(xmlHttpLogin.readyState==4) {
            if (xmlHttpLogin.status == 200) {
                console.log(xmlHttpLogin.responseText)
            }else if(xmlHttpLogin.status==401){
                console.log("Unauthorized")
            }
        }
    }
    xmlHttpLogin.send();
}

function login(){
    let xmlHttpLogin=new XMLHttpRequest();
    xmlHttpLogin.open("POST", "/0628JavaWebExercise_war/login", true)
    xmlHttpLogin.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    xmlHttpLogin.onreadystatechange = function (){
        if(xmlHttpLogin.readyState==4) {
            if (xmlHttpLogin.status == 200) {
                console.log(xmlHttpLogin.responseText)
                if(xmlHttpLogin.responseText=="team") {
                    location.assign("/0628JavaWebExercise_war/team/home")
                }else if(xmlHttpLogin.responseText=="tutor"){
                    location.assign("/0628JavaWebExercise_war/teacherHome.html")
                }
                else if(xmlHttpLogin.responseText=="admin"){
                    location.assign("/0628JavaWebExercise_war/admin/team")
                }
            }else if(xmlHttpLogin.status==401){
                alert("用户名或密码错误")
            }
        }
    }
    let id=document.getElementById("id").value
    let  password=document.getElementById("password").value
    if(id!=""&&password!="") {
        xmlHttpLogin.send("id=" + id.toString() + "&password=" + password.toString());
    }else{
        alert("用户名与密码不能为空!")
    }
}