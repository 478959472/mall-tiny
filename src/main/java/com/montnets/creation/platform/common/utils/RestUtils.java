package com.montnets.creation.platform.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.montnets.creation.platform.common.vo.Result;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;


/**
 * http 请求工具
 *
 * @author montnets
 */
public class RestUtils {
    private static final Logger LOG = LoggerFactory.getLogger(RestUtils.class);







    public static String post(String url, JSONObject json, HttpHeaders headers,RestTemplate restTemplate) {
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<JSONObject> entity = new HttpEntity<>(json, headers);
        ResponseEntity<String> res = null;
        try {
            res = restTemplate.postForEntity(url, entity, String.class);
        } catch (HttpClientErrorException e) {
            LOG.error("post 错误\n" + url + "\n" + json.toJSONString(), e);
            String errorRes = e.getResponseBodyAsString();
            LOG.info(errorRes);
            return errorRes;
        }
        if (res.getStatusCode().is2xxSuccessful()) {
            return res.getBody();
        }
        return "";
    }

    public  static String post(String url, JSONObject json ,RestTemplate restTemplate) {
        ResponseEntity<String> res = null;
        ApiLog apiLog = ApiLog.buildApiLogStart(url, json == null ? "" : json.toJSONString());
        try {
            res = restTemplate.postForEntity(url, json, String.class);
        } catch (HttpClientErrorException e) {
            LOG.error("post 错误\n" + url + "\n" + json, e);
            String errorRes = e.getResponseBodyAsString();
            LOG.info(errorRes);
            return errorRes;
        }
        if (res.getStatusCode().is2xxSuccessful()) {
            apiLog.successLog(res.getBody());
            return res.getBody();
        }
        return "";
        
    }
    


    public static JSONObject postForObject(String url, Object json,RestTemplate restTemplate) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<Object> entity = new HttpEntity<>(json, httpHeaders);
        ApiLog apiLog = ApiLog.buildApiLogStart(url, json == null ? "" : JSON.toJSONString(json));
        ResponseEntity<String> res;
        JSONObject jsonObject = new JSONObject();
        try {
            res = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        } catch (HttpClientErrorException e) {
            LOG.error("post 错误\n" + url + "\n" + json, e);
            String errorRes = e.getResponseBodyAsString();
            LOG.info(errorRes);
            jsonObject.put("errorRes", errorRes);
            return jsonObject;
        }
        if (res.getStatusCode().is2xxSuccessful()) {
            apiLog.successLog(res.getBody());
            return JSON.parseObject(res.getBody());
        }
        return jsonObject;
    }



    public  static String getForJson(String url , JSONObject json, String token ,RestTemplate restTemplate) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("emptoken",token);
        headers.set("Authorization",token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<JSONObject> entity = new HttpEntity<>(json , headers);
        ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return res.getBody();
    }

    public static String get(String url , JSONObject json ,RestTemplate restTemplate , HttpHeaders headers) {
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<JSONObject> entity = new HttpEntity<>(json , headers);
        ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return res.getBody();
    }

    public static JSONObject httpRequest(String url, JSONObject json, RestTemplate restTemplate, HttpMethod method, HttpHeaders httpHeaders) {
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<Object> entity = new HttpEntity<>(json, httpHeaders);
        ApiLog apiLog = ApiLog.buildApiLogStart(url, json == null ? "" : JSON.toJSONString(json));
        ResponseEntity<String> res;
        try {
            res = restTemplate.exchange(url, method, entity, String.class);
        } catch (HttpClientErrorException e) {
            LOG.error("post 错误\n" + url + "\n" + json, e);
            String errorRes = e.getResponseBodyAsString();
            LOG.info(errorRes);
            apiLog.failLog(errorRes);
            return null;
        }
        if (res.getStatusCode().is2xxSuccessful()) {
            String resBody = res.getBody();
            apiLog.successLog(Objects.requireNonNull(resBody));
            HttpHeaders headers = res.getHeaders();
            String set_cookie = headers.getFirst(HttpHeaders.SET_COOKIE);
            JSONObject resJson = JSON.parseObject(resBody);
            resJson.put("cookie",set_cookie);
            return resJson;
        }
        return null;
    }


}
