package com.example.demo.mapper;

import com.example.demo.util.User;
import io.lettuce.core.dynamic.annotation.Key;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import javax.xml.ws.Action;

@Mapper
@Repository
public interface UserMapper {
    @Insert("insert into user(password,email,role,capacity) values (#{password},#{email},#{role},#{capacity})")
    public int saveUser(User user);

    @Select("select * from user where email=#{email}")
    public User selectByEmail(@Param("email") String email);

    @Update("UPDATE user SET role=#{role} WHERE id=#{id}")
    public void updateRole(@Param("id")int id,@Param("role")int role);

    @Update("UPDATE user SET password=#{password} WHERE id=#{id}")
    public void updatePassword(@Param("id")int id,@Param("password")String password);

    @Update("UPDATE user SET password=#{password} WHERE email=#{email}")
    public void updatePasswordByEmail(@Param("email")String email,@Param("password")String password);

    @Update("UPDATE user SET capacity=#{capacity} WHERE id=#{id}")
    public void updateUserCapacityByUserId(@Param("capacity")float capacity,@Param("id")int id);

}
