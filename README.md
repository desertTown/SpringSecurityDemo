总参考链接 https://412887952-qq-com.iteye.com/blog/2441544

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


#### 4. 基于内存数据库的身份认证和角色授权

编码思路
``` 
这里我们使用的是Spring Data JPA进行操作数据库，所以需要添加相关的依赖；
其次就是需要定义一个保存用户基本的实体类；再者需要定义相应的服务获取用户的信息
；最后重写UserDetailsService的loadUserByUsername方法从数据库中获取用户信息
，传给Spring Security进行处理。
```
测试：

    （1）测试账号：user/123
    
           启动应用访问地址：
    
    http://127.0.0.1:8080/hello/helloUser
    
    自动跳转到登录页面，输入账号user/123，可以看到页面：    
    
    紧接着访问地址：
    
    http://127.0.0.1:8080/hello/helloAdmin  访问被拒绝：

#### 5. 基于MySQL数据库的身份认证和角色授权 
验证方式和上例一样

#### 6. 自定义登录页面和构建主页
复写登录页面的路径

```
        @Override
    protected void configure(HttpSecurity http) throws Exception {  
        http.authorizeRequests() // 定义哪些URL需要被保护、哪些不需要被保护
            .antMatchers("/login").permitAll()// 设置所有人都可以访问登录页面
            .anyRequest().authenticated()  // 任何请求,登录后可以访问
            .and()
            .formLogin().loginPage("/login")
            ;

    }
```  

测试：  http://127.0.0.1:8080/login    