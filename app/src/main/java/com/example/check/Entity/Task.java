package com.example.check.Entity;

import java.sql.Date;

public class Task {//自定义
    int taskid;
    String title;
    String photo;
    String recognition;
    String description;
    int groupid;
    Date publishtime;
    Date endtime;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getRecognition() {
        return recognition;
    }

    public void setRecognition(String recognition) {
        this.recognition = recognition;
    }

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    public Date getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(Date publishtime) {
        this.publishtime = publishtime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskid=" + taskid +
                ", title='" + title + '\'' +
                ", photo='" + photo + '\'' +
                ", recognition='" + recognition + '\'' +
                ", groupid=" + groupid +
                ", publishtime=" + publishtime +
                ", endtime=" + endtime +
                '}';
    }

}
