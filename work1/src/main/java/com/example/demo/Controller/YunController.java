package com.example.demo.Controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.service.PackageService;
import com.example.demo.service.PictureService;
import com.example.demo.service.UserService;
import com.example.demo.tool.CreateRand;
import com.example.demo.tool.TestUser;
import com.example.demo.util.Package;
import com.example.demo.util.Picture;
import com.example.demo.util.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;


@Controller
public class YunController {
    @Autowired
    private PackageService packageService;
    @Autowired
    private PictureService pictureService;
    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value="GetCapacity",method = RequestMethod.POST)
    public JSONObject logout(int packageId,HttpServletRequest request){
        JSONObject json = new JSONObject();
        //HttpSession session=request.getSession();
        //String email = (String)session.getAttribute("email");
        JSONArray pictureArray = pictureService.selectPictureByPackageId(packageId);
        JSONArray packageArray = packageService.selectPackageByPackageId(packageId);
        json.put("pictures",pictureArray);
        json.put("packages",packageArray);
        json.put("length",pictureArray.size()+packageArray.size());
        return json;
    }

    @RequestMapping(value = "putPic",method = RequestMethod.POST)
    public String fileUpload(@RequestParam("fileName") MultipartFile file, @RequestParam("packageId")int inPackageId,HttpServletRequest request){
        HttpSession session=request.getSession();
        //获取登录用户
        User userLogin;
        float fileSize = (float) file.getSize()/1024/1024;
        String email = (String)session.getAttribute("email");
        if(file.isEmpty()){
            return "404";
        }
        //System.out.println(11111);
        if(email!=null){
            userLogin = userService.selectUserByEmail(email);
            userService.UpdateUserCapacityByUserId(userLogin.getCapacity()+fileSize,userLogin.getId());
            if(userLogin.getCapacity()+fileSize>100){
                return "404";
            }
        }else{
            return "404";
        }
        String fileName = file.getOriginalFilename();
        //判断是否已经存在文件
        if(pictureService.selectHeaderPictureByUserIdAndName(inPackageId,fileName)!=null){
            return "404";
        }
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        if(!suffixName.equals(".gif")&&!suffixName.equals(".png")&&!suffixName.equals(".jpg")&&!suffixName.equals(".jpgf")){
            return "404";
        }
        int size = (int) file.getSize();
        //System.out.println(fileName + "-->" + size);
        String path = "/root/pic" ;
        //String path1 = "C:/PICTURE" ;
        String path1 = "/root/pic" ;
        String text1 = CreateRand.getGUID();
        File dest = new File(path1 + "/" + userLogin.getEmail()+text1 +suffixName);
        //System.out.println(1111);
        //System.out.println(1111);
        if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest); //保存文件
            pictureService.savePicture(new Picture(fileName,path + "/" + userLogin.getEmail()+ text1 +suffixName,userLogin.getId(),new java.sql.Date(new java.util.Date().getTime()),inPackageId,0));
            return "redirect:/yunHtml";
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "404";
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "404";
        }
    }
    @ResponseBody
    @RequestMapping(value="createPackage",method = RequestMethod.POST)
    public JSONObject testPackage(String name,int packageId,HttpServletRequest request){
        HttpSession session=request.getSession();
        JSONObject json = new JSONObject();
        User userLogin;
        String email = (String)session.getAttribute("email");
        if(email!=null){
            userLogin = userService.selectUserByEmail(email);
        }else{
            json.put("result",false);
            return json;
        }
        if(packageService.testPackageByPackageIdAndName(packageId,name)){
            packageService.savePackage(new Package(name,packageId,userLogin.getId()));
            json.put("result",true);
        }else{
            json.put("result",false);
        }

        return json;
    }

    @ResponseBody
    @RequestMapping(value="selectInPackage",method = RequestMethod.POST)
    public JSONObject selectInPackage(int packageId,HttpServletRequest request){
        HttpSession session=request.getSession();
        JSONObject json = new JSONObject();
        User userLogin;
        String email = (String)session.getAttribute("email");
        if(email!=null){
            userLogin = userService.selectUserByEmail(email);
        }else{
            json.put("result",false);
            return json;
        }
        Package pack=packageService.selectInPackageByPackageId(packageId);
        if(pack!=null){
            json.put("id",pack.getInPackageId());
            json.put("result",true);
        }else{
            json.put("result",false);
        }

        return json;
    }

    @ResponseBody
    @RequestMapping(value="deletePicture",method = RequestMethod.POST)
    public JSONObject deletePicture(String place,HttpServletRequest request){
        HttpSession session=request.getSession();
        JSONObject json = new JSONObject();
        User userLogin;
        String email = (String)session.getAttribute("email");
        if(email!=null){
            userLogin = userService.selectUserByEmail(email);
        }else{
            json.put("result",false);
            return json;
        }

        Picture picture = pictureService.selectPictureByPlace(place);
        if(picture.getUserId()!=userLogin.getId()&&userLogin.getRole()!=1){
            json.put("result",false);
            return json;
        }else{
            pictureService.deletePicture(place);
            json.put("result",true);
        }
        return json;
    }

    @ResponseBody
    @RequestMapping(value="deletePackage",method = RequestMethod.POST)
    public JSONObject deletePackage(int packageId,HttpServletRequest request){
        HttpSession session=request.getSession();
        JSONObject json = new JSONObject();
        User userLogin;
        String email = (String)session.getAttribute("email");
        if(email!=null){
            userLogin = userService.selectUserByEmail(email);
        }else{
            json.put("result",false);
            return json;
        }
        //packageService.deletePackage(packageId);
        Package package1 = packageService.selectPackageById(packageId);
        if(package1.getUserId()!=userLogin.getId()&&userLogin.getRole()!=1){
            json.put("result",false);
            return json;
        }else {
            packageService.deletePackage(packageId);
            json.put("result",true);
        }

        return json;
    }


    @ResponseBody
    @RequestMapping(value="turnBefore",method = RequestMethod.POST)
    public JSONObject turnBefore(int packageId,HttpServletRequest request){
        //System.out.println("ww"+packageId);
        HttpSession session=request.getSession();
        JSONObject json = new JSONObject();
        User userLogin;
        String email = (String)session.getAttribute("email");
        if(email!=null){
            userLogin = userService.selectUserByEmail(email);
        }else{
            json.put("result",false);
            return json;
        }
        //packageService.deletePackage(packageId);
        int id = packageService.selectInPackageIdByPackageId(packageId);
        System.out.println(id);
        if(packageService.selectPackageById(id).getUserId()!=userLogin.getId()&&userLogin.getRole()!=1){
            json.put("result",false);
            return json;
        }else {
            json.put("packageId",id);
            json.put("result",true);
            //System.out.println(packageService.selectInPackageIdByPackageId(id));
            if(packageService.selectInPackageIdByPackageId(id)==0){
                //System.out.println("gogogo");
                json.put("show",false);
            }else{
                json.put("show",true);
            }
        }

        return json;
    }

    @ResponseBody
    @RequestMapping(value="GetPackageId",method = RequestMethod.POST)
    public JSONObject getPackageId(String email,HttpServletRequest request){
        //System.out.println("ww"+packageId);
        HttpSession session=request.getSession();
        JSONObject json = new JSONObject();
        User userLogin;
        String email1 = (String)session.getAttribute("email");
        if(userService.selectUserByEmail(email1).getRole()==1){
            userLogin = userService.selectUserByEmail(email);
        }else{
            json.put("result",false);
            return json;
        }
        json.put("packageId",packageService.selectHeadPackageByUserId(userLogin.getId()).getId());

        return json;
    }

    @ResponseBody
    @RequestMapping(value="examine",method = RequestMethod.POST)
    public JSONObject examine(String place,HttpServletRequest request){
        //System.out.println("ww"+packageId);
        HttpSession session=request.getSession();
        JSONObject json = new JSONObject();
        User userLogin;
        String email1 = (String)session.getAttribute("email");
        if(userService.selectUserByEmail(email1).getRole()==1){
            pictureService.updatePictureExamine(place);
            json.put("result",true);

        }else{
            json.put("result",false);

        }

        return json;
    }

}
