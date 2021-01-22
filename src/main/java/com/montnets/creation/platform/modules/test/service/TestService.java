package com.montnets.creation.platform.modules.test.service;

import com.montnets.creation.platform.modules.test.dto.LoginDto;
import com.montnets.creation.platform.modules.test.vo.LoginVo;

/**
 * @author montnets
 */
public interface TestService {


    /**
     * 来画登录
     * @param loginDto
     * @return
     */
    LoginVo login(LoginDto loginDto);

}
