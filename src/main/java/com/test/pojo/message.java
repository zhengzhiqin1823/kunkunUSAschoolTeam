package com.test.pojo;

public class message {
    String messageID;
    String From;
    String To;
    String Title;
    String firstFm;
    String totalSize;
    String isRead;
    String time;

    public String getMessageID() {
        return messageID;
    }

    public String getFrom() {
        return From;
    }

    public String getTo() {
        return To;
    }

    public String getTitle() {
        return Title;
    }

    public String getFirstFm() {
        return firstFm;
    }

    public String getIsRead() {
        return isRead;
    }

    public String getTime() {
        return time;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public void setFrom(String from) {
        From = from;
    }

    public void setTo(String to) {
        To = to;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setFirstFm(String firstFm) {
        this.firstFm = firstFm;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(String totalSize) {
        this.totalSize = totalSize;
    }

    @Override
    public String toString() {
        return "message{" +
                "messageID='" + messageID + '\'' +
                ", From='" + From + '\'' +
                ", To='" + To + '\'' +
                ", Title='" + Title + '\'' +
                ", firstFm='" + firstFm + '\'' +
                ", totalSize='" + totalSize + '\'' +
                ", isRead='" + isRead + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
