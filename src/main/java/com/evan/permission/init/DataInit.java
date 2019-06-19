package com.evan.permission.init;

import com.evan.permission.bean.UserInfo;
import com.evan.permission.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class DataInit {
    @Autowired
    UserInfoRepository userInfoRepository;


    @PostConstruct
    public void dataInit() {

        UserInfo admin = new UserInfo();
        admin.setUsername("admin");
        admin.setPassword("123");
        admin.setRole(UserInfo.Role.admin);
        userInfoRepository.save(admin);


        UserInfo user = new UserInfo();
        user.setUsername("user");
        user.setPassword("123");
        user.setRole(UserInfo.Role.normal);
        userInfoRepository.save(user);
    }
}
