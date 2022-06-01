package com.example.demo.Entity;

public class Group {
    int groupid;
    String name;

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Group{" +
                "groupid=" + groupid +
                ", name='" + name + '\'' +
                '}';
    }
}
