#### 初体验
Spring 5.x 之后， 如果想关闭security校验，#security.basic.enabled=false 是无效的， 使用
@SpringBootApplication(exclude= SecurityAutoConfiguration.class)

#### 2.基于内存的认证信息

在Spring 5.x之后， 如果添加密码加密方式， 将会报如下错误
> java.lang.IllegalArgumentException: There is no PasswordEncoder mapped for the id "null"

具体做法：
``` java
configure(AuthenticationManagerBuilder auth) {
    auth.inMemoryAuthentication()
                    .passwordEncoder(new BCryptPasswordEncoder())  //指定加密方式
                    .withUser("user")
                    .password(new BCryptPasswordEncoder().encode("123456")) // 指定加密方式
                    .roles();
}

...

@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

```




