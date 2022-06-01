package com.example.demo.Entity;

public class Pic {
    private int picid;
    private String photo;
    private String result;
    private int taskid;
    private String userid;

    @Override
    public String toString() {
        return "Pic{" +
                "picid=" + picid +
                ", photo='" + photo + '\'' +
                ", result='" + result + '\'' +
                ", taskid=" + taskid +
                ", userid='" + userid + '\'' +
                '}';
    }

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
}
