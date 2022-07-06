package com.test.pojo;

public class reportcahe {
    String cacheID;
    String submitID;
    String teamID;
    String firstfm;
    String totalsize;

    public String getCacheID() {
        return cacheID;
    }

    public String getSubmitID() {
        return submitID;
    }

    public String getTeamID() {
        return teamID;
    }

    public String getFirstfm() {
        return firstfm;
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

    public void setFirstfm(String firstfm) {
        this.firstfm = firstfm;
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
                ", firstfm='" + firstfm + '\'' +
                ", totalsize='" + totalsize + '\'' +
                '}';
    }
}
