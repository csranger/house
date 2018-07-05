package com.csranger.house.autoconfig;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)  // SpringRunner是SpringJUnit4ClassRunner的别名
@SpringBootTest
public class HttpClientAutoConfigurationTest {

    @Autowired
    private HttpClient httpClient;

    @Test
    public void httpClient() throws Exception {
        // 打印出百度首页内容
        System.out.println(EntityUtils.toString( httpClient.execute(new HttpGet("http://www.baidu.com")).getEntity()));
    }
}