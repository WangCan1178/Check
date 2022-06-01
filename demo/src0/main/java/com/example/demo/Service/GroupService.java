package com.example.demo.Service;

import com.example.demo.Dao.GroupDao;
import com.example.demo.Entity.Group;
import com.example.demo.Entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    private GroupDao groupDao;

    public GroupDao getGroupDao() {
        return groupDao;
    }

    @Autowired
    public void setGroupDao(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    public List<Group> getAddGroup(String id){
        return groupDao.getAddGroup(id);
    }

    public int delAddGroup(String userid,int groupid){
        return groupDao.delAddGroup(userid,groupid);
    }

    public int addGroup(String memid,int groupid,String memname,int role){
        return groupDao.addGroup(memid,groupid,memname,role);
    }

    public List<Group> getManGroup(String id){
        return groupDao.getManGroup(id);
    }

    public int createGroup(String name){
        return groupDao.createGroup(name);
    }

    public int getGroupId(String name){
        return groupDao.getGroupId(name);
    }

    public int addManager(String memid,int groupid,String memname){
        return groupDao.addManager(memid,groupid,memname);
    }

    public int delGroup(int groupid){
        return groupDao.delGroup(groupid);
    }

    public String getGroupName(int id){
        return groupDao.getGroupName(id);
    }

    public String getMemName(int id,String userid){
        return groupDao.getMemName(id,userid);
    }

    public int alterName(String name,String userid,int groupid){
        return groupDao.alterName(name, userid, groupid);
    }

    public List<Member> getMember(int id){
        return groupDao.getMember(id);
    }

    public int delMember(String userid,int groupid){
        return groupDao.delMember(userid,groupid);
    }
}
