package com.example.demo.Dao;

import com.example.demo.Entity.Pic;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Mapper
@Repository
public interface PicDao {
//    @Insert("insert into picture(url,result,taskid,userid) values(#{url},#{result},#{taskid},#{userid})")
//    public int insertPic(String url,String result, int taskid, String userid);



    @Select("select * from picture where taskid=#{id}")
    public List<Pic> getPics(int taskid);
    @Select("select * from picture where taskid=#{id} and result=0")
    public List<Pic> getPicUnPass(int taskid);
    @Select("select * from picture where taskid=#{id} and result=2")
    public List<Pic> getPicUnFinish(int taskid);
    @Select("select * from picture where taskid=#{id} and result=1")
    public List<Pic> getPicPass(int taskid);

//    @Update("update picture set result = #{result} where picid=#{picid}")
//    public int alterResult(int picid,String result);
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

//    @Delete("delete from picture where picid = #{picid}")
//    public int deletePic(int picid);

}
