package com.example.demo.service.impl;

import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import com.example.demo.util.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public void saveUser(User user) {
        userMapper.saveUser(user);
    }

    @Override
    public User selectUserByEmail(String email) {
        return userMapper.selectByEmail(email);
    }

    @Override
    public void UpdateUserCapacityByUserId(float capacity,int id) {
        userMapper.updateUserCapacityByUserId(capacity,id);
    }

    @Override
    public void UpdataUserPasswordByEmail(String password,String email) {
        userMapper.updatePasswordByEmail(email,password);
    }
}
