package com.montnets.creation.platform.modules.test.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.montnets.creation.platform.common.exception.ApiException;
import com.montnets.creation.platform.common.utils.RestUtils;
import com.montnets.creation.platform.modules.test.dto.LoginDto;
import com.montnets.creation.platform.modules.test.service.TestService;
import com.montnets.creation.platform.modules.test.vo.LoginVo;
import com.montnets.creation.platform.modules.test.vo.VideoVO;
import com.montnets.creation.platform.modules.ums.service.impl.UmsAdminServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        JSONObject res = RestUtils.httpRequest(url, json, restTemplate, HttpMethod.POST,new HttpHeaders());
        if(res == null){
            throw new ApiException("登录失败");
        }
        LoginVo loginVo = new LoginVo();
        String cookie = res.getString("cookie");
        if(StringUtils.isNotEmpty(cookie)){
            String[] cookies = cookie.split(";");
            cookie = cookies[0];
            loginVo.setToken(cookie);
            return loginVo;
        }
        throw new ApiException("登录失败");
    }

    @Override
    public List<VideoVO> listMyVideo(String token) {
        String url = "https://common.laihua.com/webapi/user/video?sOfPage=24&fPage=24&keyword=&pIndex=1";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie",token);
        JSONObject res = RestUtils.httpRequest(url, new JSONObject(), restTemplate, HttpMethod.GET,httpHeaders);
        if(res != null){
            JSONArray data = res.getJSONArray("data");
            List<VideoVO> voList = JSON.parseArray(data.toJSONString(),VideoVO.class);
            voList = voList.stream().peek(videoVO -> {
                String baseUrl = "https://resources.laihua.com/";
                videoVO.setThumbnailUrl(baseUrl+videoVO.getThumbnailUrl());
                videoVO.setUrl(baseUrl+videoVO.getUrl());
            }).collect(Collectors.toList());
            return voList;
        }
        return new ArrayList<>();

    }
}
