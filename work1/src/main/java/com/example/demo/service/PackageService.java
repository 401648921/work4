package com.example.demo.service;

import com.alibaba.fastjson.JSONArray;
import com.example.demo.util.Package;
import com.example.demo.util.Picture;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PackageService {
    public void savePackage(Package package1);

    public void deletePackageById(int id);

    public Package selectPackageById(int id);

    public Package selectHeadPackageByUserId(int userId);


    public List<Package> selectPackageByUserId(int userId);

    public List<Package> selectHeaderPackageByUserId(int userId);

    public JSONArray selectPackageByPackageId(int userId);

    public boolean testPackageByPackageIdAndName(int inPackageId, String name);

    public void deletePackage(int inPackageId);

    public Package selectInPackageByPackageId(int inPackageId);

    public int selectInPackageIdByPackageId(int PackageId);
}
