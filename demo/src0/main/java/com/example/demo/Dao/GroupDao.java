package com.example.demo.Dao;

import com.example.demo.Entity.Group;
import com.example.demo.Entity.Member;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
@Repository
public interface GroupDao {
    @Select("select `group`.id,`group`.`name` from `group`,member where member.memid=#{id} and member.groupid = `group`.id and role=1")
    public List<Group> getAddGroup(String id);

    @Delete("delete from member where memid=#{userid} and groupid=#{groupid}")
    public int delAddGroup(String userid,int groupid);

    @Insert("insert into member(memid,groupid,memname,role) values(#{memid},#{groupid},#{memname},#{role})")
    public int addGroup(String memid,int groupid,String memname,int role);

    @Select("select `group`.id,`group`.`name` from `group`,member where member.memid=#{id} and member.groupid = `group`.id and role=0")
    public List<Group> getManGroup(String id);

    @Insert("insert into `group`(name) values (#{name}) ")
    public int createGroup(String name);

    @Select("select `group`.id from `group` where `group`.`name`=#{name}")
    public int getGroupId(String name);

    @Insert("insert into member(memid,groupid,memname,role) values (#{memid},#{groupid},#{memname},0)")
    public int addManager(String memid,int groupid,String memname);

    @Delete("delete from `group` where id = #{groupid}")
    public int delGroup(int groupid);

    @Select("select `group`.name from `group` where `group`.id=#{id}")
    public String getGroupName(int id);

    @Select("select memname from member where groupid=#{id} and memid=#{userid}")
    public String getMemName(int id,String userid);

    @Update("update member set memname = #{name} where memid=#{userid} and groupid=#{groupid}")
    public int alterName(String name,String userid,int groupid);

    @Select("select * from member where groupid = #{id} and role=1")
    public List<Member> getMember(int id);

    @Delete("delete from member where groupid = #{groupid} and memid=#{userid}")
    public int delMember(String userid,int groupid);

}
