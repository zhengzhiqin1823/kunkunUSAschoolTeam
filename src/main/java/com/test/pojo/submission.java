package com.test.pojo;

public class submission {
    String submitID;
    String name;
    String submitStatus;
    String judgeStatus;
    String next;
    String startTime;
    String deadLine;

    @Override
    public String toString() {
        return "submission{" +
                "submitID='" + submitID + '\'' +
                ", name='" + name + '\'' +
                ", submitStatus='" + submitStatus + '\'' +
                ", judgeStatus='" + judgeStatus + '\'' +
                ", next='" + next + '\'' +
                ", startTime='" + startTime + '\'' +
                ", deadLine='" + deadLine + '\'' +
                ", submitTeams='" + submitTeams + '\'' +
                '}';
    }

    public submission(String submitID, String name, String submitStatus, String judgeStatus, String next, String startTime, String deadLine, String submitTeams) {
        this.submitID = submitID;
        this.name = name;
        this.submitStatus = submitStatus;
        this.judgeStatus = judgeStatus;
        this.next = next;
        this.startTime = startTime;
        this.deadLine = deadLine;
        this.submitTeams = submitTeams;
    }

    public String getSubmitID() {
        return submitID;
    }

    public void setSubmitID(String submitID) {
        this.submitID = submitID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubmitStatus() {
        return submitStatus;
    }

    public void setSubmitStatus(String submitStatus) {
        this.submitStatus = submitStatus;
    }

    public String getJudgeStatus() {
        return judgeStatus;
    }

    public void setJudgeStatus(String judgeStatus) {
        this.judgeStatus = judgeStatus;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine;
    }

    public String getSubmitTeams() {
        return submitTeams;
    }

    public void setSubmitTeams(String submitTeams) {
        this.submitTeams = submitTeams;
    }

    String submitTeams;
}
