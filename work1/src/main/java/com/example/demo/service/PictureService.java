package com.example.demo.service;

import com.alibaba.fastjson.JSONArray;
import com.example.demo.util.Picture;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PictureService {
    public void savePicture(Picture picture);

    public void deletePictureById(int id);

    public Picture selectPictureByPlace(String place);

    public List<Picture> selectPictureByUserId(int userId);

    public List<Picture> selectHeaderPictureByUserId(int userId);

    public JSONArray selectPictureByPackageId(int inPackageId);

    public void deletePicture(String place);

    public Picture selectHeaderPictureByUserIdAndName(int inPackageId, String name);

    public void updatePictureExamine(String place);
}
