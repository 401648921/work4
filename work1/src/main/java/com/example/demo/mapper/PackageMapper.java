package com.example.demo.mapper;

import com.example.demo.util.Package;
import com.example.demo.util.User;
import com.sun.xml.bind.v2.runtime.reflect.Lister;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
@Repository
public interface PackageMapper {
    @Insert("insert into package(in_package_id,user_id,name) values (#{inPackageId},#{userId},#{name})")
    public void savePackage(Package package1);

    @Delete("delete from package where id = #{id}")
    public void deletePackageById(int id);

    @Delete("delete from package where in_package_id = #{inPackageId}")
    public void deletePackageByInPackageId(int inPackageId);

    @Select("select * from package where id=#{id}")
    public Package selectPackageById(int id);

    @Select("select * from package where user_id=#{userId}")
    public List<Package> selectPackageByUserId(int userId);

    @Select("select * from package where user_id=#{userId} and in_package_id = 0")
    public Package selectHeadPackageByUserId(int userId);

    @Select("select * from package where in_package_id=#{inPackageId}")
    public List<Package> selectPackageByInPackageId(int inPackageId);

    @Select("select * from package where user_id=#{userId} and in_package_id is null")
    public List<Package> selectHeaderPackageByUserId(int userId);

    @Select("select * from package where in_package_id=#{inPackageId}")
    public List<Package> selectPackageByPackageId(int inPackageId);

    @Select("select in_package_id from package where id=#{inPackageId}")
    public int selectInPackageByPackageId(int inPackageId);

    @Select("select * from package where in_package_id=#{inPackageId} and name = #{name}")
    public Package selectPackageByPackageIdAndName(@Param("inPackageId")int inPackageId, @Param("name") String name);
}
