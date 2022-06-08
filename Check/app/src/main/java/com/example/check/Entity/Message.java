package com.example.check.Entity;
import java.sql.Date;
public class Message {
    String userid;
    int isread;
    String time;
//    Date time;
    int groupid;
    String title;
    int taskid;
    int mesid;

    public int getMesid() {
        return mesid;
    }

    public void setMesid(int mesid) {
        this.mesid = mesid;
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

    public int getIsread() {
        return isread;
    }

    public void setIsread(int isread) {
        this.isread = isread;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
//    public Date getTime() {
//        return time;
//    }
//
//    public void setTime(Date time) {
//        this.time = time;
//    }

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Message{" +
                "userid='" + userid + '\'' +
                ", isread=" + isread +
                ", time='" + time + '\'' +
                ", groupid=" + groupid +
                ", title='" + title + '\'' +
                ", taskid=" + taskid +
                ", mesid=" + mesid +
                '}';
    }
}
