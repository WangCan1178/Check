package com.example.demo.Dao;


import com.example.demo.Entity.Pic;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PicDao {
    // 0不通过，1通过，2未提交
    @Insert("insert into picture(taskid, userid) values(#{taskid},#{userid})")
    public int register(int taskid,String userid);

    @Insert("insert into picture(url,result,taskid,userid) values(#{url},#{result},#{taskid},#{userid})")
    public int insertPic(String url,String result, int taskid, String userid);

    @Select("select * from picture where taskid=#{id}")
    public List<Pic> getPics(int taskid);

    @Update("update picture set result = #{result} where picid=#{picid}")
    public int alterResult(int picid,String result);

    @Update("update picture set url = #{url},result=#{result} where picid=#{picid}")
    public int alterPic(int picid,String url,String result);

    @Delete("delete from picture where picid = #{picid}")
    public int deletePic(int picid);
}
