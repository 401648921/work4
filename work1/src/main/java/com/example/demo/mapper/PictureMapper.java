package com.example.demo.mapper;

import com.example.demo.util.Package;
import com.example.demo.util.Picture;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PictureMapper {
    @Insert("insert into picture(name,place,user_id,time,package_id,examine) values (#{name},#{place},#{userId},#{time},#{packageId},#{examine})")
    public void savePicture(Picture picture);

    @Delete("delete from picture where id = #{id}")
    public void deletePictureById(int id);

    @Select("select * from picture where place=#{place}")
    public Picture selectPictureByPlace(String place);

    @Select("select * from picture where user_id=#{userId}")
    public List<Picture> selectPictureByUserId(int userId);

    @Delete("delete from picture where package_id = #{inPackageId}")
    public void deletePictureByInPackageId(int inPackageId);

    @Delete("delete from picture where place = #{place}")
    public void deletePictureByPlace(String place);

    @Select("select * from picture where user_id=#{userId} and package_id is null")
    public List<Picture> selectHeaderPictureByUserId(int userId);

    @Select("select * from picture where name=#{name} and package_id = #{inPackageId}")
    public Picture selectHeaderPictureByUserIdAndName(@Param("inPackageId")int inPackageId, @Param("name")String name);

    @Select("select * from picture where package_id=#{inPackageId}")
    public List<Picture> selectPictureByPackageId(int inPackageId);

    @Update("UPDATE picture SET examine=1 WHERE place=#{place}")
    public void updatePictureExamine(String place);
}
