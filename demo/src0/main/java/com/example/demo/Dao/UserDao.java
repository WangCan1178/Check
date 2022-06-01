package com.example.demo.Dao;


import com.example.demo.Entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDao {
    @Select("select * from user where id=#{id} and PASSWORD=#{password}")
    public User login(String id, String password);

    @Insert("insert into user(id,name,password) values(#{id},#{name},#{password})")
    public int register(String id,String name,String password);

    @Select("select id from user where id=#{id}")
    public String phone(String id);

    @Update("update user set password = #{password} where id=#{id}")
    public int alterPsw(String id,String password);

    @Select("select name from user where id=#{id}")
    public String getUserName(String id);
}
