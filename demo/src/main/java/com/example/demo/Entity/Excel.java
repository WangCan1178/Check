package com.example.demo.Entity;

public class Excel {
    String userid;
    String memname;
    String result;
    String photo;
    int taskid;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getMemname() {
        return memname;
    }

    public void setMemname(String memname) {
        this.memname = memname;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getTaskid() {
        return taskid;
    }

    public void setTaskid(int taskid) {
        this.taskid = taskid;
    }

    @Override
    public String toString() {
        return "Excel{" +
                "userid='" + userid + '\'' +
                ", memname='" + memname + '\'' +
                ", result='" + result + '\'' +
                ", photo='" + photo + '\'' +
                ", taskid=" + taskid +
                '}';
    }
}
