package com.example.demo.Dao;


import com.example.demo.Entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDao {
    @Select("select * from user where userid=#{id} and PASSWORD=#{password}")
    public User login(String id, String password);

    @Insert("insert into user(userid,name,password) values(#{id},#{name},#{password})")
    public int register(String id,String name,String password);

    @Select("select userid from user where userid=#{id}")
    public String phone(String id);

    @Update("update user set password = #{password} where userid=#{id}")
    public int alterPsw(String id,String password);

    @Select("select name from user where userid=#{id}")
    public String getUserName(String id);

    @Delete("delete from user where userid = #{id}")
    public int delUser(String id);
}
