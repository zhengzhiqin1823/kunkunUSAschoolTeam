package com.test.pojo;

public class task {
    private String taskID;
    private String name;
    private String description;
    private String submitNum;
    private String firstsm;
    private String startedline;
    private String deadline;


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSubmitNum() {
        return submitNum;
    }

    public String getFirstsm() {
        return firstsm;
    }

    public String getStartedline() {
        return startedline;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSubmitNum(String submitNum) {
        this.submitNum = submitNum;
    }

    public void setFirstsm(String firstsm) {
        this.firstsm = firstsm;
    }

    public void setStartedline(String startedline) {
        this.startedline = startedline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    @Override
    public String toString() {
        return "task{" +
                "taskID='" + taskID + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", submitNum='" + submitNum + '\'' +
                ", firstsm='" + firstsm + '\'' +
                ", startedline='" + startedline + '\'' +
                ", deadline='" + deadline + '\'' +
                '}';
    }
}
