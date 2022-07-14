package com.test.pojo;

public class report {
    String rid;
    String submitID;
    String teamid;
    String toyalsize;
    String firstFm;
    String submitTime;
    String taskID;

    public String getRid() {
        return rid;
    }

    public String getSubmitID() {
        return submitID;
    }

    public String getTeamid() {
        return teamid;
    }

    public String getToyalsize() {
        return toyalsize;
    }

    public String getFirstFm() {
        return firstFm;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public void setSubmitID(String submitID) {
        this.submitID = submitID;
    }

    public void setTeamid(String teamid) {
        this.teamid = teamid;
    }

    public void setToyalsize(String toyalsize) {
        this.toyalsize = toyalsize;
    }

    public void setFirstFm(String firstFm) {
        this.firstFm = firstFm;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    @Override
    public String toString() {
        return "report{" +
                "rid='" + rid + '\'' +
                ", submitID='" + submitID + '\'' +
                ", teamid='" + teamid + '\'' +
                ", toyalsize='" + toyalsize + '\'' +
                ", firstFm='" + firstFm + '\'' +
                ", submitTime='" + submitTime + '\'' +
                '}';
    }
}
