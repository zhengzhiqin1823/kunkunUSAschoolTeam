package com.test.pojo;

public class Admin {
     public String  aid;
     public String password;
     public  String name;
     public  String email;
     public  String tel;

    public String getAid() {
        return aid;
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

    public void setAid(String aid) {
        this.aid = aid;
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
        return "admin{" +
                "aid='" + aid + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }
}
