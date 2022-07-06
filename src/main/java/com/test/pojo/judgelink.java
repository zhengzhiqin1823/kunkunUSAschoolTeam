package com.test.pojo;

public class judgelink {
    String lid;
    String link;
    String submitID;
    String tid;

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

    @Override
    public String toString() {
        return "judgelink{" +
                "lid='" + lid + '\'' +
                ", link='" + link + '\'' +
                ", submitID='" + submitID + '\'' +
                ", tid='" + tid + '\'' +
                '}';
    }
}
