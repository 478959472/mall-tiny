package com.montnets.creation.platform.modules.test.service;

import com.montnets.creation.platform.modules.test.dto.LoginDto;
import com.montnets.creation.platform.modules.test.vo.LoginVo;
import com.montnets.creation.platform.modules.test.vo.VideoVO;

import java.util.List;

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

    /**
     * 获取我的来画视频
     * @param token
     * @return
     */
    List<VideoVO> listMyVideo(String token);

}
