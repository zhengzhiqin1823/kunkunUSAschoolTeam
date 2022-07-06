package com.test.pojo;

public class tutor {
    public String  tid;
    public String password;
    public  String name;
    public  String email;
    public  String tel;

    public String getTid() {
        return tid;
    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getTel() {
        return tel;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return tid+","+name+","+email+","+tel+";";
    }
}
