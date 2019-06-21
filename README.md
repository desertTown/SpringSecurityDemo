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


#### 7.  登出和403处理 -- 小技巧
3.1 修改.html页面不生效

       修改了比如index.html页面之后，立即访问不能看到最新的页面，需要重新启动才能看到
       最新的更改，这是因为模板引擎有缓存，需要先将缓存关闭掉，
       在application.properties中添加如下配置：
       spring.thymeleaf.cache=false
3.2 修改Java需要重启才能生效

       修改了Java代码之后，每次都是需要重新启动在部署的，这个开发效率很低，
       需要使用devtools进行热部署，只需要添加以下的依赖：
       ```
        <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-devtools</artifactId>
              <optional>true</optional>
        </dependency>       
       ``` 
       
页面修改之后(JAVA Code也一样)，  按 Ctrl+F9 重新编译，   再刷新页面就可以了


#### 8. 动态加载角色


#### 9. 原理1
Filter

       如果要对Web资源进行保护，最好的办法莫过于Filter，要想对方法调用进行保护，最好的办法莫过于AOP。Spring Security对Web资源的保护，就是靠Filter实现的：
#### 10. 自定义Filter

         怎么在Spring Security中的Filter指定位置加入自定义的Filter呐？Spring Security的HttpSecurity为此提供了三个常用方法来配置：
         
         （1）addFilterBefore(Filter filter, Class<? extends Filter>beforeFilter)
         
         在 beforeFilter 之前添加 filter
         
         （2）addFilterAfter(Filter filter, Class<? extends Filter>afterFilter)
         
         在 afterFilter 之后添加 filter
         
         （3）addFilterAt(Filter filter, Class<? extends Filter> atFilter)
         
         在 atFilter 相同位置添加 filter， 此 filter 不覆盖 filter     
1.5 测试

       启动测试，访问地址：

http://127.0.0.1:8080/

       控制台的输出如下：

        this is a filter beforeUsernamePasswordAuthenticationFilter.
        
        this is a filter atUsernamePasswordAuthenticationFilter.
        
        this is a filter after UsernamePasswordAuthenticationFilter.

所以Filter的执行情况：

BeforeLoginFilter->AtLoginFilter->UsernamePasswordAuthenticationFilter->AfterLoginFilter            

#### 11.页面白名单和获取登录信息 

把/res/的所有.js,html设置为白名单：
```
http.authorizeRequests() // 定义哪些URL需要被保护、哪些不需要被保护
            .antMatchers("/res/**/*.{js,html}").permitAll()

```

获取登录信息
```
Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
```

http://127.0.0.1:8080/index_info     


#### 12. 基于URL动态权限：准备工作

启动程序， 在控制台会见到这些日志

> {/hello/helloAdmin=[ROLE_admin], /hello/helloUser=[ROLE_admin, ROLE_normal]}

#### 13. 基于URL动态权限：扩展access()的SpEL表达式

测试：  

http://localhost:8080/hello/helloAdmin     admin/123
http://localhost:8080/hello/helloUser     user/123