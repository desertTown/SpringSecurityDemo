package com.evan.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration  // 注解这是一个配置类。
@EnableWebSecurity  // 注解开启Spring Security的功能。
@EnableGlobalMethodSecurity(prePostEnabled = true)  // PreAuthorize开启方法级别安全控制
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        /*
         * 配置为从内存中进行加载认证信息.
         * 这里配置了两个用户 admin和user
         */
//        auth.inMemoryAuthentication()
//                .passwordEncoder(new BCryptPasswordEncoder())
//                .withUser("admin")
//                .password(new BCryptPasswordEncoder().encode("123456"))
//                .roles("admin");
//        auth.inMemoryAuthentication()
//                .passwordEncoder(new BCryptPasswordEncoder())
//                .withUser("user")
//                .password(new BCryptPasswordEncoder().encode("123456"))
//                .roles("normal");
//    }

    // 注入PsswordEncoder,
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}