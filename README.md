#### 初体验
Spring 5.x 之后， 如果想关闭security校验，#security.basic.enabled=false 是无效的， 使用
@SpringBootApplication(exclude= SecurityAutoConfiguration.class)




