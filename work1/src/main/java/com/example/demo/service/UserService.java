package com.example.demo.service;

import com.example.demo.util.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public void saveUser(User user);

    public User selectUserByEmail(String email);

    public void UpdateUserCapacityByUserId(float capacity,int id);

    public void UpdataUserPasswordByEmail(String password,String email);
}
