package com.example.demo.Entity;

public class Pic {
    int picid;
    String photo;
    String result;
    int taskid;
    String userid;

    public int getPicid() {
        return picid;
    }

    public void setPicid(int picid) {
        this.picid = picid;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "picid=" + picid +
                ", url='" + photo + '\'' +
                ", result='" + result + '\'' +
                ", taskid=" + taskid +
                ", userid='" + userid + '\'' +
                '}';
    }
}
