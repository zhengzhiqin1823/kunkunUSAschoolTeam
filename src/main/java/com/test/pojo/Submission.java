package com.test.pojo;

public class Submission {
    String submitID;
    String name;
    String submitStatus;
    String judgeStatus;
    String next;
    String startTime;
    String deadLine;
    String submitTeams;
    String description;
    String taskID;

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
                ", description='" + description + '\'' +
                ", taskID='" + taskID + '\'' +
                '}';
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
