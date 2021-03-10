package com.example.demo.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.mapper.PackageMapper;
import com.example.demo.mapper.PictureMapper;
import com.example.demo.service.PackageService;
import com.example.demo.tool.DeleteFile;
import com.example.demo.util.Package;
import com.example.demo.util.Picture;
import com.sun.xml.bind.v2.runtime.reflect.Lister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageServiceImpl implements PackageService {
    @Autowired
    PictureMapper pictureMapper;
    @Autowired
    PackageMapper packageMapper;

    @Override
    public void savePackage(Package package1) {
        packageMapper.savePackage(package1);
    }

    @Override
    public void deletePackageById(int id) {
        Package package1 = packageMapper.selectPackageById(id);
        pictureMapper.deletePictureByInPackageId(package1.getInPackageId());
        packageMapper.deletePackageByInPackageId(package1.getInPackageId());
        packageMapper.deletePackageById(id);
    }

    @Override
    public Package selectPackageById(int id) {
        return packageMapper.selectPackageById(id);
    }

    @Override
    public List<Package> selectPackageByUserId(int userId) {
        return packageMapper.selectPackageByUserId(userId);
    }

    @Override
    public List<Package> selectHeaderPackageByUserId(int userId) {
        return packageMapper.selectHeaderPackageByUserId(userId);
    }

    @Override
    public JSONArray selectPackageByPackageId(int inPackageId) {
        JSONArray packageList = new JSONArray();
        List<Package> packages = packageMapper.selectPackageByInPackageId(inPackageId);
        for(Package package1 : packages ){
            JSONObject json = new JSONObject();
            json.put("filename",package1.getName());
            json.put("packageId",package1.getId());
            packageList.add(json);
        }
        return packageList;
    }

    @Override
    public Package selectHeadPackageByUserId(int userId) {
        return packageMapper.selectHeadPackageByUserId(userId);
    }

    @Override
    public boolean testPackageByPackageIdAndName(int inPackageId, String name) {
        return packageMapper.selectPackageByPackageIdAndName(inPackageId,name)==null;
    }

    @Override
    public void deletePackage(int inPackageId) {
        List<Picture> pictures = pictureMapper.selectPictureByPackageId(inPackageId);
        List<Package> packages = packageMapper.selectPackageByPackageId(inPackageId);
        for(Picture picture : pictures){
            DeleteFile.deleteFile(picture.getPlace());
        }
        pictureMapper.deletePictureByInPackageId(inPackageId);
        for(Package package1 : packages){
            //System.out.println(package1.getId());
            deletePackage(package1.getId());
        }
        packageMapper.deletePackageById(inPackageId);
    }

    @Override
    public Package selectInPackageByPackageId(int inPackageId) {
        return packageMapper.selectPackageById(inPackageId);
    }

    @Override
    public int selectInPackageIdByPackageId(int PackageId) {
        return packageMapper.selectInPackageByPackageId(PackageId);
    }
}
