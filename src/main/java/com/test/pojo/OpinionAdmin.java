package com.test.pojo;

public class OpinionAdmin {
    String teamID;
    String taskID;
    String aID;
    String score;
    String totalsize;
    String firstFm;
    String submitTime;


    public String getScore() {
        return score;
    }

    public String getTotalsize() {
        return totalsize;
    }

    public String getFirstFm() {
        return firstFm;
    }

    public String getSubmitTime() {
        return submitTime;
    }


    public void setScore(String score) {
        this.score = score;
    }

    public void setTotalsize(String totalsize) {
        this.totalsize = totalsize;
    }

    public void setFirstFm(String firstFm) {
        this.firstFm = firstFm;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    public String getTeamID() {
        return teamID;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getaID() {
        return aID;
    }

    public void setaID(String aID) {
        this.aID = aID;
    }

    @Override
    public String toString() {
        return "OpinionAdmin{" +
                "teamID='" + teamID + '\'' +
                ", taskID='" + taskID + '\'' +
                ", aID='" + aID + '\'' +
                ", score='" + score + '\'' +
                ", totalsize='" + totalsize + '\'' +
                ", firstFm='" + firstFm + '\'' +
                ", submitTime='" + submitTime + '\'' +
                '}';
    }
}
