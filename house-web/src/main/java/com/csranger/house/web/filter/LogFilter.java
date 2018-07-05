package com.csranger.house.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;

public class LogFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(LogFilter.class);

    // 启动器启动执行
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    // 请求拦截时候执行
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        logger.info("Request--coming");      // 请求已经到来
        chain.doFilter(request, response);
    }

    // 容器销毁时执行
    @Override
    public void destroy() {

    }
}
