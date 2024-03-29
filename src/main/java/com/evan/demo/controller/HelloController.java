package com.evan.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping
    public String getWelcomeMsg() {
        return "Hello,Spring Security";
    }

    @GetMapping("/helloAdmin")
//    @PreAuthorize("hasAnyRole('admin')")
    public String helloAdmin() {
        return "Hello,admin";
    }

    @GetMapping("/helloUser")
//    @PreAuthorize("hasAnyRole('admin','normal')")
//    @PreAuthorize("hasRole('normal') AND hasRole('admin')")  如果我们要求用户必须同时拥有normal和admin的话.就这么写
    public String helloUser() {
        return "Hello,user";
    }
}
