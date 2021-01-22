package com.montnets.creation.platform.common.config;

import lombok.Data;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author lianghuageng
 */
@Configuration
@ConfigurationProperties(prefix = "http")
@Data
public class RestTemplateConfig {

    /**
     * 最大连接数
     */
    private Integer maxConnectionTotal;

    /**
     * 设置到某个路由的最大连接数
     */
    private Integer routeMaxCount;

    /**
     * 连接超时时间 (连接上服务器- tcp 握手成功)
     */
    private Integer connectionTimeout;

    /**
     * socketTimeout
     * 等待数据返回时间, 服务器返回 response 的时候, 单位 ms
     * 超过该时间抛出 readTimeout
     */
    private Integer readTimeout;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        RestTemplate restTemplate = builder.build();
        restTemplate.setRequestFactory(clientHttpRequestFactory());
        // 使用 utf-8 编码集的 conver 替换默认的 conver（默认的 string conver 的编码集为"ISO-8859-1"）
        return restTemplate;
    }


    @Bean
    public HttpClientConnectionManager poolingConnectionManager() {
        PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager();
        // 连接池最大连接数
        poolingConnectionManager.setMaxTotal(maxConnectionTotal);
        // 每个主机的并发
        poolingConnectionManager.setDefaultMaxPerRoute(routeMaxCount);
        return poolingConnectionManager;
    }

    @Bean
    public HttpClientBuilder httpClientBuilder() {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        //设置HTTP连接管理器
        httpClientBuilder.setConnectionManager(poolingConnectionManager());
        return httpClientBuilder;
    }

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setHttpClient(httpClientBuilder().build());
        // 连接超时，毫秒
        clientHttpRequestFactory.setConnectTimeout(connectionTimeout);
        // 读写超时，毫秒
        clientHttpRequestFactory.setReadTimeout(readTimeout);
        return clientHttpRequestFactory;
    }

}
