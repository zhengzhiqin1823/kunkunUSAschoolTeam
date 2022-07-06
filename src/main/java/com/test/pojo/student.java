package com.test.pojo;

public class student {
    public String  sid;
    public String password;
    public  String name;
    public  String email;
    public  String tel;

    public String getSid() {
        return sid;
    }


    public void setSid(String sid) {
        this.sid = sid;
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

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getTel() {
        return tel;
    }

    @Override
    public String toString() {
        return sid+","+name+","+email+","+tel+";";
    }
}
