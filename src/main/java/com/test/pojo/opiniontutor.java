package com.test.pojo;

public class opiniontutor {
    String rID;
    String tID;
    int score;
    String totalsize;
    String submitTime;

    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return "opiniontutor{" +
                "rID='" + rID + '\'' +
                ", tID='" + tID + '\'' +
                ", score=" + score +
                ", totalsize='" + totalsize + '\'' +
                ", submitTime='" + submitTime + '\'' +
                ", data='" + data + '\'' +
                '}';
    }

    public void setData(String data) {
        this.data = data;
    }

    String data;

    public String getrID() {
        return rID;
    }

    public String gettID() {
        return tID;
    }

    public int getScore() {
        return score;
    }

    public String getTotalsize() {
        return totalsize;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setrID(String rID) {
        this.rID = rID;
    }

    public void settID(String tID) {
        this.tID = tID;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setTotalsize(String totalsize) {
        this.totalsize = totalsize;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

}
