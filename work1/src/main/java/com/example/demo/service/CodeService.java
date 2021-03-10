package com.example.demo.service;

import com.example.demo.util.Code;
import org.springframework.stereotype.Service;

@Service
public interface CodeService {
    public void saveCode(Code code);

    public Code selectCodeByUserId(int userId);

    public void deleteCodeByUserId(int userId);
}
