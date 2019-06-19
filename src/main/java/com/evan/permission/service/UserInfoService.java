package com.evan.permission.service;


import com.evan.permission.bean.UserInfo;

public interface UserInfoService {

    UserInfo findByUsername(String username);
}
