package com.example.demo.Entity;

public class Member {
    private String userid;
    private int groupid;
    private String memname;
    private int role;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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
                "userid='" + userid + '\'' +
                ", groupid=" + groupid +
                ", memname='" + memname + '\'' +
                ", role=" + role +
                '}';
    }
}
