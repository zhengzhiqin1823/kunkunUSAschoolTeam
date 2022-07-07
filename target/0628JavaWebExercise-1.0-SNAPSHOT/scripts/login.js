function checkLogin() {
    let xmlHttpLogin=new XMLHttpRequest();
    xmlHttpLogin.open("GET", "/login", true)
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
    xmlHttpLogin.open("POST", "/login", true)
    xmlHttpLogin.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    xmlHttpLogin.onreadystatechange = function (){
        if(xmlHttpLogin.readyState==4) {
            if (xmlHttpLogin.status == 200) {
                location.assign("/studentHome.html")
            }else if(xmlHttpLogin.status==401){
                alert("用户名或密码错误")
            }
        }
    }
    let id=document.getElementById("id").value
    let  password=document.getElementById("password").value
    if(id!=""&&password!="") {
        alert("登录中！")
        xmlHttpLogin.send()//"id=" + id.toString() + "&password=" + password.toString());
    }else{
        alert("用户名与密码不能为空!")
    }
}