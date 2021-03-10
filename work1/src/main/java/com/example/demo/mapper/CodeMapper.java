package com.example.demo.mapper;

import com.example.demo.util.Code;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CodeMapper {
    @Insert("insert into code(content,user_id) values (#{content},#{userId})")
    public void saveCode(Code code);

    @Delete("delete from code where id = #{id}")
    public void deleteCodeById(int id);

    @Select("select * from code where user_id=#{userId}")
    public Code selectCodeByUserId(int id);

    @Delete("delete from code where user_id = #{userId}")
    public void deleteCodeByUserId(int userId);
}
