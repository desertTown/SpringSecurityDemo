package com.evan.permission.service.impl;


import com.evan.permission.bean.UserInfo;
import com.evan.permission.repository.UserInfoRepository;
import com.evan.permission.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserInfo findByUsername(String username) {
        return userInfoRepository.findByUsername(username);
    }
}
