package com.csranger.house.web.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

// 成为spring bean 的config
@Configuration
public class FilterBeanConfig {

    /**
     * 创建Filter Bean需要三步
     * 1。构造filter
     * 2。配置拦截urlPattern
     * 3。利用FilterRegistrationBean进行包装
     * @return
     */
    @Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new LogFilter());
        List<String> urlList = new ArrayList<>();
        urlList.add("*");
        filterRegistrationBean.setUrlPatterns(urlList);
        return filterRegistrationBean;
    }
}
