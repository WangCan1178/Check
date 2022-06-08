package com.example.check.Entity;

public class Isend {
    int taskid;
    String title;
    int groupid;
    String userid;
    int ifend;
    String result;
    int picid;
    String memname;

    @Override
    public String toString() {
        return "Isend{" +
                "taskid=" + taskid +
                ", title='" + title + '\'' +
                ", groupid=" + groupid +
                ", userid='" + userid + '\'' +
                ", ifend=" + ifend +
                ", result=" + result +
                ", picid=" + picid +
                ", memname='" + memname + '\'' +
                '}';
    }

    public String getMemname() {
        return memname;
    }

    public void setMemname(String memname) {
        this.memname = memname;
    }

    public int getPicid() {
        return picid;
    }

    public void setPicid(int picid) {
        this.picid = picid;
    }


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getTaskid() {
        return taskid;
    }

    public void setTaskid(int taskid) {
        this.taskid = taskid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public int getIfend() {
        return ifend;
    }

    public void setIfend(int ifend) {
        this.ifend = ifend;
    }

}
