package com.example.demo.tool;

import java.io.File;

public class DeleteFile{
    public static boolean deleteFile(String place) {
        place = place.substring(place.lastIndexOf("/"));
        place="file:/root/pic/"+place;
        File file = new File(place);
        System.out.println(place);
        if(!file.exists()){
            //System.out.println(1);
            return false;
        }else{
            //System.out.println(2);
            return file.delete();
        }
    }

    public static void main(String[] args) {
        System.out.println(deleteFile("C:/PICTURE/444@qq.com07LdF1k1O2p506Qk.png"));
    }
}
