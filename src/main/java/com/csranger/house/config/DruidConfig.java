package com.csranger.house.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.google.common.collect.Lists;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DruidConfig {

    @ConfigurationProperties(prefix="spring.druid")// 将外部application.properties与DruidDataSource绑定，在spring.druid开头的配置文件写入到DruidDataSource的属性中
    @Bean(initMethod = "init", destroyMethod = "close")   // 何时启用与结束
    public DruidDataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setProxyFilters(Lists.newArrayList(statFilter()));    // 将慢日志的功能加到连接池里

        return dataSource;
    }

    // 将慢日志打印出来
    @Bean
    public Filter statFilter() {
        StatFilter statFilter = new StatFilter();
        statFilter.setSlowSqlMillis(1);      // 慢日志的时间，根据这个时间判断是否属于慢sql，设为5000(5s）
        statFilter.setLogSlowSql(true);         // 是否打印出慢日志
        statFilter.setMergeSql(true);           // 是否将日志合并起来
        return statFilter;
    }


    // 监控：druid为监控而生
    // 分析mysql及每个sql执行的时间，时间的分布
    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        return new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
    }



}
