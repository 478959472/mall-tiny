package com.montnets.creation.platform.modules.test.controller;

import com.montnets.creation.platform.common.api.CommonResult;
import com.montnets.creation.platform.modules.test.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author montnets
 */
@RestController
@Api(tags = "LaihuaTestController")
@RequestMapping("/laihua")
public class LaihuaTestController {

    @Autowired
    private TestService testService;

    @ApiOperation("来画登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult login() {
        return CommonResult.success(testService.login(null));
    }
}
