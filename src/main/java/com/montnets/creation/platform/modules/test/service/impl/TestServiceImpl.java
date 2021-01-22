package com.montnets.creation.platform.modules.test.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.montnets.creation.platform.common.exception.ApiException;
import com.montnets.creation.platform.common.utils.RestUtils;
import com.montnets.creation.platform.modules.test.dto.LoginDto;
import com.montnets.creation.platform.modules.test.service.TestService;
import com.montnets.creation.platform.modules.test.vo.LoginVo;
import com.montnets.creation.platform.modules.ums.service.impl.UmsAdminServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * @author montnets
 */
@Service
public class TestServiceImpl implements TestService {
    private static final Logger log = LoggerFactory.getLogger(TestServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public LoginVo login(LoginDto loginDto) {
        String account = "mengwang";
        String psw = "mengwang";

        JSONObject json = new JSONObject();
        json.put("account",account);
        json.put("psw",psw);

        String url = "https://common.laihua.com/webapi/login";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<Object> entity = new HttpEntity<>(json, httpHeaders);
        ResponseEntity<String> res;
        try {
            res = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        } catch (HttpClientErrorException e) {
            log.error("post 错误\n" + url + "\n" + json, e);
            String errorRes = e.getResponseBodyAsString();
            log.info(errorRes);
            throw new ApiException("登录失败");
        }
        if (res.getStatusCode().is2xxSuccessful()) {
            HttpHeaders headers = res.getHeaders();
            String set_cookie = headers.getFirst(HttpHeaders.SET_COOKIE);
            if(StringUtils.isEmpty(set_cookie) || !set_cookie.contains("EGG_SESS=")){
                throw new ApiException("登录失败");
            }
            LoginVo loginVo = new LoginVo();
            loginVo.setToken(set_cookie);
            return loginVo;
        }
        throw new ApiException("登录失败");
    }
}
