package com.test.pojo;

public class Judgelink {
    String lid;
    String link;
    String submitID;
    String tid;
    String taskID;
    String rID;

    @Override
    public String toString() {
        return "judgelink{" +
                "lid='" + lid + '\'' +
                ", link='" + link + '\'' +
                ", submitID='" + submitID + '\'' +
                ", tid='" + tid + '\'' +
                ", taskID='" + taskID + '\'' +
                ", rID='" + rID + '\'' +
                '}';
    }

    public void setrID(String rID) {
        this.rID = rID;
    }

    public String getrID() {
        return rID;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getLid() {
        return lid;
    }

    public String getLink() {
        return link;
    }

    public String getSubmitID() {
        return submitID;
    }

    public String getTid() {
        return tid;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setSubmitID(String submitID) {
        this.submitID = submitID;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

}
