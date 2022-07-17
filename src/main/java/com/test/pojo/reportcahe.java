package com.test.pojo;

public class reportcahe {
    String cacheID;
    String submitID;
    String teamID;
    String totalsize;
    String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCacheID() {
        return cacheID;
    }

    public String getSubmitID() {
        return submitID;
    }

    public String getTeamID() {
        return teamID;
    }

    public String getTotalsize() {
        return totalsize;
    }

    public void setCacheID(String cacheID) {
        this.cacheID = cacheID;
    }

    public void setSubmitID(String submitID) {
        this.submitID = submitID;
    }

    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }

    public void setTotalsize(String totalsize) {
        this.totalsize = totalsize;
    }

    @Override
    public String toString() {
        return "reportcahe{" +
                "cacheID='" + cacheID + '\'' +
                ", submitID='" + submitID + '\'' +
                ", teamID='" + teamID + '\'' +
                ", totalsize='" + totalsize + '\'' +
                '}';
    }
}
