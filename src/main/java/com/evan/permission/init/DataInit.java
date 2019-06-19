package com.evan.permission.init;

import com.evan.permission.bean.Role;
import com.evan.permission.bean.UserInfo;
import com.evan.permission.repository.RoleRepository;
import com.evan.permission.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataInit {
    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public void dataInit() {

        List<Role> roles = new ArrayList<>();
        Role adminRole= new Role("admin","管理员");
        Role normalRole = new Role("normal","普通用户");
        roleRepository.save(adminRole);
        roleRepository.save(normalRole);

        roles.add(adminRole);
        roles.add(normalRole);

        UserInfo admin = new UserInfo();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("123"));
        admin.setRoles(roles);
        userInfoRepository.save(admin);


        roles = new ArrayList<>();
        roles.add(normalRole);

        UserInfo user = new UserInfo();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("123"));
        user.setRoles(roles);
        userInfoRepository.save(user);
    }
}
