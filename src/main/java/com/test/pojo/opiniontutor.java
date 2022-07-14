package com.test.pojo;

public class opiniontutor {
    String rID;
    String tID;
    int score;
    String firstFm;
    String totalsize;
    String submitTime;

    public String getrID() {
        return rID;
    }

    public String gettID() {
        return tID;
    }

    public int getScore() {
        return score;
    }

    public String getFirstFm() {
        return firstFm;
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

    public void setFirstFm(String firstFm) {
        this.firstFm = firstFm;
    }

    public void setTotalsize(String totalsize) {
        this.totalsize = totalsize;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    @Override
    public String toString() {
        return "opiniontutor{" +
                "rID='" + rID + '\'' +
                ", tID='" + tID + '\'' +
                ", score='" + score + '\'' +
                ", firstFm='" + firstFm + '\'' +
                ", totalsize='" + totalsize + '\'' +
                ", submitTime='" + submitTime + '\'' +
                '}';
    }
}
