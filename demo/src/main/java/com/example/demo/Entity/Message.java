package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.ibatis.annotations.Select;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.util.Date;

public class Message {
    int mesid;
    String userid;
    int isread;
    int groupid;
    String title;
<<<<<<< HEAD
    int taskid;
=======
>>>>>>> 4dbc078c73d90357e0ca011f43ea8c1a8f5ba0fc

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd hh:mm:ss")
    Date time;

<<<<<<< HEAD

    @Override
    public String toString() {
        return "Message{" +
                "mesid=" + mesid +
                ", userid='" + userid + '\'' +
                ", isread=" + isread +
                ", groupid=" + groupid +
                ", title='" + title + '\'' +
                ", taskid=" + taskid +
=======
    @Override
    public String toString() {
        return "Message{" +
                "userid='" + userid + '\'' +
                ", isread=" + isread +
                ", groupid=" + groupid +
>>>>>>> 4dbc078c73d90357e0ca011f43ea8c1a8f5ba0fc
                ", time=" + time +
                '}';
    }

<<<<<<< HEAD
    public int getTaskid() {
        return taskid;
    }

    public void setTaskid(int taskid) {
        this.taskid = taskid;
    }

=======
>>>>>>> 4dbc078c73d90357e0ca011f43ea8c1a8f5ba0fc
    public int getMesid() {
        return mesid;
    }

    public void setMesid(int mesid) {
        this.mesid = mesid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public int getIsread() {
        return isread;
    }

    public void setIsread(int isread) {
        this.isread = isread;
    }

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
