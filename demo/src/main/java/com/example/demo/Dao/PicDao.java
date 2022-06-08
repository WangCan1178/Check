package com.example.demo.Dao;

import com.example.demo.Entity.Isend;
import com.example.demo.Entity.Pic;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Mapper
@Repository
public interface PicDao {


    @Select("select * from isend where taskid=#{id} and (result=0 or result=1 or  result=2)")
    public List<Isend> getPics(int taskid);
    @Select("select * from isend where taskid=#{id} and result=0")
    public List<Isend> getPicUnPass(int taskid);
    @Select("select * from isend where taskid=#{id} and result=2")
    public List<Isend> getPicUnFinish(int taskid);
    @Select("select * from isend where taskid=#{id} and result=1")
    public List<Isend> getPicPass(int taskid);

    // 0不通过，1通过，2未提交
    // 默认未提交
    @Update("update picture set photo = #{photo},result=#{result} where (userid=#{userid} and taskid=#{taskid})")
    public int alterPic(int taskid, String userid,String photo,String result);

    @Select("select * from picture where picid=#{picid}")
    public Pic getPicDetail(int picid);

    @Update("update picture set result=#{result} where picid=#{picid}")
    public int alterR(int picid,String result);

    @Select("select count(*) from picture where userid=#{userid} and result=2")
    public int getUserTask(String userid);

    @Select("select * from picture where userid=#{userid} and taskid=#{taskid}")
    public Pic second(String userid,int taskid);

}
