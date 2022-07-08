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
                if(xmlHttpLogin.responseText=="student") {
                    location.assign("/0628JavaWebExercise_war/studentHome.html")
                }else if(xmlHttpLogin.responseText=="tutor"){
                    location.assign("/0628JavaWebExercise_war/teacherHome.html")
                }else if(xmlHttpLogin.responseText=="admin") {
                    alert("请在创建adminHome页面后删除此行")
                    location.assign("/0628JavaWebExercise_war/adminHome.html")
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

function loginOut() {
    let xmlHttpLogin=new XMLHttpRequest();
    xmlHttpLogin.open("GET", "/0628JavaWebExercise_war/loginOut", true)
    xmlHttpLogin.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    xmlHttpLogin.onreadystatechange = function (){
        if(xmlHttpLogin.readyState==4) {
            if (xmlHttpLogin.status == 200) {
                location.assign("/0628JavaWebExercise_war/index.html")
            }
        }
    }
    xmlHttpLogin.send();
}