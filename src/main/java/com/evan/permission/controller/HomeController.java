package com.evan.permission.controller;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @GetMapping({"","/","/index"})
    public String index() {
        return "/index";
    }


    @GetMapping("index_info")
    public String index(Model model) {
        //  获取登录信息的方式
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //未登录的情况下，返回的是一个字符串：anonymousUser
        //登录的情况下，返回的是在CustomUserDetailService的loadUserByUsername的方法中返回的User对象。
        if("anonymousUser".equals(principal)) {
            model.addAttribute("name","anonymous");
        }else {
            User user = (User)principal;
            model.addAttribute("name",user.getUsername());
        }
        return "/index";
    }
}
