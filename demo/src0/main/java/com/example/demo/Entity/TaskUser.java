package com.example.demo.Entity;

public class TaskUser {
    private int t_taskid;
    private String t_tasktitle;
    private int t_groupid;
    private String t_memid;
    private int t_isfinish;
    private int t_isend;

    public int getT_taskid() {
        return t_taskid;
    }

    public void setT_taskid(int t_taskid) {
        this.t_taskid = t_taskid;
    }

    public String getT_tasktitle() {
        return t_tasktitle;
    }

    public void setT_tasktitle(String t_tasktitle) {
        this.t_tasktitle = t_tasktitle;
    }

    public int getT_groupid() {
        return t_groupid;
    }

    public void setT_groupid(int t_groupid) {
        this.t_groupid = t_groupid;
    }

    public String getT_memid() {
        return t_memid;
    }

    public void setT_memid(String t_memid) {
        this.t_memid = t_memid;
    }

    public int getT_isfinish() {
        return t_isfinish;
    }

    public void setT_isfinish(int t_isfinish) {
        this.t_isfinish = t_isfinish;
    }

    public int getT_isend() {
        return t_isend;
    }

    public void setT_isend(int t_isend) {
        this.t_isend = t_isend;
    }

    @Override
    public String toString() {
        return "TaskUser{" +
                "t_taskid=" + t_taskid +
                ", t_tasktitle='" + t_tasktitle + '\'' +
                ", t_groupid=" + t_groupid +
                ", t_memid='" + t_memid + '\'' +
                ", t_isfinish=" + t_isfinish +
                ", t_isend=" + t_isend +
                '}';
    }
}
