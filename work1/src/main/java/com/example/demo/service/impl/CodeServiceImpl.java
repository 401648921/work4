package com.example.demo.service.impl;

import com.example.demo.mapper.CodeMapper;
import com.example.demo.service.CodeService;
import com.example.demo.util.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CodeServiceImpl implements CodeService {
    @Autowired
    CodeMapper codeMapper;

    @Override
    public void saveCode(Code code) {
        codeMapper.deleteCodeByUserId(code.getUserId());
        codeMapper.saveCode(code);
    }

    @Override
    public Code selectCodeByUserId(int userId) {
        return codeMapper.selectCodeByUserId(userId);
    }

    @Override
    public void deleteCodeByUserId(int userId) {
        codeMapper.deleteCodeByUserId(userId);
    }
}
