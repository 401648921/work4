package com.example.demo.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.service.PackageService;
import com.example.demo.service.UserService;
import com.example.demo.tool.TestUser;
import com.example.demo.util.Package;
import com.example.demo.util.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.text.DecimalFormat;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private PackageService packageService;

    //登录界面
    @RequestMapping(value = "login")
    public String loginController(Model model){
        return "sign";
    }

    @ResponseBody
    @RequestMapping(value="loginUser",method = RequestMethod.POST)
    public JSONObject logout(String email, String password ){
        User user = new User(email,password,0,0);
        JSONObject json = new JSONObject();
        if(userService.selectUserByEmail(email)!=null|| !TestUser.testUser(user)){
            json.put("result",false);
        }else{
            //存入数据库
            userService.saveUser(user);
            packageService.savePackage(new Package("root",0,userService.selectUserByEmail(user.getEmail()).getId()));
            System.out.println(user.getId());
            json.put("result",true);
        }
        System.out.println(json);
        return json;

    }

    @RequestMapping(value="signUser",method = RequestMethod.POST)
    public String sign(String email, String password,HttpServletRequest request, Model model){
        User user = userService.selectUserByEmail(email);
        HttpSession session=request.getSession();
        if(user==null||!user.getPassword().equals(password)){
            return "404";
        }else{
            session.setAttribute("email",user.getEmail());
            session.setAttribute("password",user.getPassword());
            return "redirect:yunHtml";
        }

    }
    @ResponseBody
    @RequestMapping(value="signBefore",method = RequestMethod.POST)
    public JSONObject signBefore(String email, String password){
        User user = userService.selectUserByEmail(email);
        JSONObject json = new JSONObject();
        if(user==null||!user.getPassword().equals(password)){
            json.put("result",false);
        }else{
            json.put("result",true);
        }
        return json;

    }

    @RequestMapping(value="yunHtml",method = RequestMethod.GET)
    public String yunHTML(Model model, HttpServletRequest request){
        HttpSession session=request.getSession();
        String email = (String)session.getAttribute("email");
        User user = userService.selectUserByEmail(email);
        model.addAttribute("role",user.getRole()==1);
        model.addAttribute("capacity",(float)((int)(user.getCapacity()*100))/100);
        model.addAttribute("packageId",packageService.selectHeadPackageByUserId(user.getId()).getId());
        if(email!=null){
            model.addAttribute("email", email.substring(0,email.indexOf('@')));
        }else{
            return "404";
        }

        return "yun";

    }
    //注销
    @RequestMapping(value="logout")
    public String logout(HttpSession session, SessionStatus sessionStatus ){
        session.invalidate();
        sessionStatus.setComplete();
        return "redirect:login";
    }

}
