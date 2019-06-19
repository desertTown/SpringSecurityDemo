#### 初体验
Spring Security 5.x 之后， 如果想关闭security校验，#security.basic.enabled=false 是无效的， 使用
@SpringBootApplication(exclude= SecurityAutoConfiguration.class)

http://127.0.0.1:8080/hello

#### 2.基于内存的认证信息

在Spring Security 5.x 之后， 如果添加密码加密方式， 将会报如下错误
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
http://127.0.0.1:8080/hello


#### 3. 基于内存的角色授权

``` java
 @EnableGlobalMethodSecurity注解：
 
 （1）prePostEnabled :决定Spring Security的前注解是否可用 [@PreAuthorize,@PostAuthorize,..]
 
 （2）secureEnabled : 决定是否Spring Security的保障注解 [@Secured] 是否可用。
 
 （3）jsr250Enabled ：决定 JSR-250 annotations 注解[@RolesAllowed..] 是否可用。
```
 
只有添加了@EnableGlobalMethodSecurity(prePostEnabled=true)之后，@PreAuthorize("hasAnyRole('admin')")才能生效。

测试： 

        （1）启动应用程序，访问如下地址：
        
        http://127.0.0.1:8080/hello/helloUser
        
        跳转到登录页面，输入账号user/123456，成功登录之后，会看到返回信息：Hello,user
        
        然后在输入另外一个地址：
        
        http://127.0.0.1:8080/hello/helloAdmin
        
        这时候会看到403的报错：


