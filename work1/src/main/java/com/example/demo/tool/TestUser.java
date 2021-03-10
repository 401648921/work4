package com.example.demo.tool;

import com.example.demo.util.User;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestUser {
    public static boolean testEmail(String email){

        boolean flag = false;
        try {
            String check = "[a-zA-Z0-9]{1,11}@[a-zA-Z0-9]{1,5}.[a-zA-Z0-9]{1,3}";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }


    public static boolean testUserPassword(String password){
        boolean flag;
        if(password.length()!=0){
            flag = true;
        }else{
            flag= false;
        }
        return flag;
    }

    public static boolean testUser(User user){
        return testUserPassword(user.getPassword())&&testEmail(user.getEmail());
    }

}
