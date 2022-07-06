package com.test.pojo;

public class fragment {
    String fmid;
    String next;
    String data;

    public String getFmid() {
        return fmid;
    }

    public String getNext() {
        return next;
    }

    public String getData() {
        return data;
    }

    public void setFmid(String fmid) {
        this.fmid = fmid;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "fragment{" +
                "fmid='" + fmid + '\'' +
                ", next='" + next + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
