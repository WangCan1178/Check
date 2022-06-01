package com.example.check.Entity;

public class Member {
    private String memid;
    private int groupid;
    private String memname;
    private int role;

    public String getMemid() {
        return memid;
    }

    public void setMemid(String memid) {
        this.memid = memid;
    }

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    public String getMemname() {
        return memname;
    }

    public void setMemname(String memname) {
        this.memname = memname;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Member{" +
                "memid='" + memid + '\'' +
                ", groupid=" + groupid +
                ", memname='" + memname + '\'' +
                ", role=" + role +
                '}';
    }
}
