package com.evan.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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

    // 复写登录页面的路径
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests() // 定义哪些URL需要被保护、哪些不需要被保护
                .antMatchers("/login").permitAll()// 设置所有人都可以访问登录页面
//                .anyRequest().authenticated()  // 任何请求,登录后可以访问
                .antMatchers("/","/index").permitAll()  // 允许/index为白名单
                .antMatchers("/test/**","/test1/**").permitAll()   // /test/下的所有文件都为白名单：
                .antMatchers("/res/**/*.{js,html}").permitAll()   //  把/res/的所有.js,html设置为白名单：
                .anyRequest().access("@authService.canAccess(request,authentication)")
                .and()
                .formLogin().loginPage("/login")
                // 设置session并发为1
                .and().sessionManagement().maximumSessions(1)
        ;

        http.addFilterBefore(new BeforeLoginFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(new AfterLoginFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAt(new AtLoginFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    // 注入PsswordEncoder,
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
