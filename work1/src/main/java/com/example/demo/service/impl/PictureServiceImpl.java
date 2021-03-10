package com.example.demo.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.mapper.PictureMapper;
import com.example.demo.service.PictureService;
import com.example.demo.tool.DeleteFile;
import com.example.demo.util.Picture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PictureServiceImpl implements PictureService {
    @Autowired
    PictureMapper pictureMapper;

    @Override
    public void savePicture(Picture picture) {
        pictureMapper.savePicture(picture);
    }

    @Override
    public void deletePictureById(int id) {
        pictureMapper.deletePictureById(id);
    }

    @Override
    public Picture selectPictureByPlace(String place) {
        return pictureMapper.selectPictureByPlace(place);
    }

    @Override
    public List<Picture> selectPictureByUserId(int userId) {
        return pictureMapper.selectPictureByUserId(userId);
    }

    @Override
    public List<Picture> selectHeaderPictureByUserId(int userId) {
        return pictureMapper.selectHeaderPictureByUserId(userId);
    }

    @Override
    public JSONArray selectPictureByPackageId(int inPackageId) {
        JSONArray pictureList = new JSONArray();
        List<Picture> pictures = pictureMapper.selectPictureByPackageId(inPackageId);
        for(Picture picture : pictures ){
            JSONObject json = new JSONObject();
            json.put("examine",picture.getExamine()==1?"已过审":"审核中");
            json.put("filename",picture.getName());
            json.put("filedate",picture.getTime().toString());
            json.put("place",picture.getPlace());
            pictureList.add(json);
        }
        return pictureList;
    }

    @Override
    public Picture selectHeaderPictureByUserIdAndName(int inPackageId, String name) {
        return pictureMapper.selectHeaderPictureByUserIdAndName(inPackageId,name);
    }

    @Override
    public void deletePicture(String place) {
        DeleteFile.deleteFile(place);
        pictureMapper.deletePictureByPlace(place);
    }

    @Override
    public void updatePictureExamine(String place) {
        pictureMapper.updatePictureExamine(place);
    }
}
