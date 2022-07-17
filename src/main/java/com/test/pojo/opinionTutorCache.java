package com.test.pojo;

public class opinionTutorCache {
    String rID;
    String tID;
    int score;
    String totalsize;
    String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

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

    @Override
    public String toString() {
        return "opinionTutorCache{" +
                "rID='" + rID + '\'' +
                ", tID='" + tID + '\'' +
                ", score=" + score +
                ", totalsize='" + totalsize + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
