package com.test.pojo;

public class Report {
    String rid;
    String submitID;
    String teamid;
    int totalsize;
    String submitTime;
    String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getRid() {
        return rid;
    }

    public String getSubmitID() {
        return submitID;
    }

    public String getTeamid() {
        return teamid;
    }

    public String getTotalsize() {
        System.out.println("totalsize"+totalsize);
        return totalsize+"";
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

    public void setTotalsize(String toyalsize) {
        this.totalsize = totalsize;
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
                ", totalsize='" + totalsize + '\'' +

                ", submitTime='" + submitTime + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
