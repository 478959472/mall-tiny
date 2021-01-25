package com.montnets.creation.platform.modules.test.controller;

import com.montnets.creation.platform.common.api.CommonResult;
import com.montnets.creation.platform.modules.test.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author montnets
 */
@RestController
@Api(tags = "LaihuaTestController")
@RequestMapping("/laihua")
public class LaihuaTestController {

    @Autowired
    private TestService testService;
    @Resource
    HttpServletRequest request;

    @ApiOperation("来画登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult login() {
        return CommonResult.success(testService.login(null));
    }

    @ApiOperation("查询我的作品列表")
    @RequestMapping(value = "/my-videos", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult listMyVideo() {
        String token = "EGG_SESS=nk0ZLUGdokkxAa5KBQBAEIdeA43UEluXnoo9w28REThCtpzy3LbVncaSD7Nk32LB";//request.getHeader("laihuaToken");
        return CommonResult.success(testService.listMyVideo(token));
    }

    @ApiOperation("跳转到来画平台")
    @RequestMapping(value = "/my-laihua", method = RequestMethod.GET)
    public void myLaihua(HttpServletResponse response) {
        response.addHeader("cookie", "EGG_SESS=nk0ZLUGdokkxAa5KBQBAEIdeA43UEluXnoo9w28REThCtpzy3LbVncaSD7Nk32LB");
        try {
            response.sendRedirect("https://common.laihua.com/v1/oauth2/userInfo");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
