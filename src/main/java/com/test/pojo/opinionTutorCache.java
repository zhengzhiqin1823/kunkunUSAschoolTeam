package com.test.pojo;

public class opinionTutorCache {
    String rID;
    String tID;
    int score;
    String totalsize;
    String firstFm;

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

    public String getFirstFm() {
        return firstFm;
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

    public void setFirstFm(String firstFm) {
        this.firstFm = firstFm;
    }

    @Override
    public String toString() {
        return "opinionTutorCache{" +
                "rID='" + rID + '\'' +
                ", tID='" + tID + '\'' +
                ", score='" + score + '\'' +
                ", totalsize='" + totalsize + '\'' +
                ", firstFm='" + firstFm + '\'' +
                '}';
    }
}
