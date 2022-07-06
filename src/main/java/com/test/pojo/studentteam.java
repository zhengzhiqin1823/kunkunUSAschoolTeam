package com.test.pojo;

public class studentteam {
    String sid;
    String teamID;

    public String getSid() {
        return sid;
    }

    public String getTeamID() {
        return teamID;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }

    @Override
    public String toString() {
        return "studentteam{" +
                "sid='" + sid + '\'' +
                ", teamID='" + teamID + '\'' +
                '}';
    }
}
