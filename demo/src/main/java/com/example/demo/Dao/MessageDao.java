package com.example.demo.Dao;

import com.example.demo.Entity.Isend;
import com.example.demo.Entity.Message;
import com.example.demo.Entity.Pic;
import com.example.demo.Entity.Task;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Mapper
@Repository
public interface MessageDao {

    // 传入任务号，给未完成的群发提醒消息
    @Select("call messend(#{tid})")
    public void messend(int taskid);

    // 每天定时执行未完成任务提醒
    @Select("call messenday")
    public void messenday();

    // 根据用户id查消息
    @Select("select * from message where userid=#{uid}")
    public List<Message> getMessage(String uid);

}
