
## 项目过程与个人笔记
### 整合freemarker
1. pom文件里的 freemarker 起步依赖
2. application.properties 配置 freemarker 相关配置

### freemarker 结构化布局
1. 抽取 header, footer, nav, js, 分页
2. 页面中引用 header, footer
3. 编写页面中自定义部分

### spring boot 起步依赖 (starter)
1. 理解一些常见的注解，例子：
    - @ConditionalOnClass({SqlSessionFactory.class, SqlSessionFactoryBean.class}) 
    注解在某类上: 只有在SqlSessionFactory和SqlSessionFactoryBean存在的情况下才会加载这个类对应的Bean
    - @ConditionalOnBean(DataSource.class)注解在某类上:在DataSource这个Bean存在的情况下才会加载这个类对应的Bean
    - @EnableConfigurationProperties(MybatisProperties.class)注解在某类上:
    MybatisProperties作为一个Bean引入进来
    - @AutoConfigurationAfter(DataSourceAutoConfiguration.class):注解在某类上:
    DataSourceAutoConfiguration作为Bena配置之后才会进行这个类对应的Bean的配置
    - @ConditionalOnMissingBean注解在某方法上：只有这个方法返回类型对应的Bean不存在的情况下才会加载当前方法定义的Bean

2. @SpringBootApplication
    - 包含了 @EnableAutoConfiguration 此注解会在容器启动时将@Configuration的配置类加载到spring容器里成为Bean

3. @ConfigurationProperties(prefix="spring.druid")注解到方法上
    - 将外部application.properties与方法生产类对应的Bean绑定，application.properties文件里的以spring.druid开头
    的键值对的值传递给Bean; MybatisProperties类上也有一个@ConfigurationProperties(prefix="mybatis")，所以可以将
    mybatis.config-location=classpath:/mybatis/mybatis-config.xml传递个MybatisProperties类中的属性
    
4. 自定义 HttpClient 起步依赖
    - pom依赖 + HttpClientAutoConfiguration + HttpClientProperties + HttpClientAutoConfigurationTest

### Maven 多 module 实现
1. 多Module不是分布式，最终还是集成在一个jar包。好处：使得代码层次清晰，不同人负责不同部分，增加协同开发效率。
2. house-biz:负责业务访问层和业务实现；house-common:负责定义数据模型和公共代码；house-web:负责定义启动类，controller，模版引擎
3. 将主项目上的pom文件修改为artifactId>house-parent</artifactId><version>1.0.0-SNAPSHOT</version><packaging>pom</packaging>，
然后在主项目文件上新建Module-Maven，Module-name为house-biz，Parent Project为hosue-parent；另外两个类似;然后将相应的文件资源以及pom
依赖放入合适的Module里
4. house-parent 里的pom依赖也会成为house-biz，house-common，house-web的依赖
5. 注意启动类HouseApplication放在house-web中的com.csranger.house包下，而不是com.csranger.house.web 包下

### 错误页面
1. 错误页面路径配置 server.error.path(默认 error)
2. 异常处理器进行日志输出


