package com.montnets.creation.platform.common.utils;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 */
@Data
public class ApiLog {

    private static final Logger LOG = LoggerFactory.getLogger(ApiLog.class);
    /**
     * 请求url
     */
    private String requestUrl;

    /**
     * 请求报文
     */
    private String request;


    /**
     * 响应时间
     */
    private String responseTime;

    /**
     * 请求开始时间
     */
    private LocalDateTime startTime;



    /**
     *
     * @param requestUrl 请求url
     * @param request 请求报文
     */
    public static ApiLog buildApiLogStart(String requestUrl, String request) {
        LocalDateTime localDateTime = LocalDateTime.now();
        ApiLog apiLog = new ApiLog();
        apiLog.setRequestUrl(requestUrl);
        apiLog.setRequest(request);
        apiLog.setStartTime(localDateTime);
        StringBuilder log = new StringBuilder();
        log.append("\nUrl:").append(requestUrl).append("\n");
        log.append("开始时间：" + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS").format(localDateTime));
        log.append("\n");
        LOG.info(log.toString());
        return apiLog;
    }



    /**
     * 请求成功日志
     *
     * @param res 响应报文
     * @return
     */
    public void successLog(String res) {
        LocalDateTime localDateTime = LocalDateTime.now();
        StringBuilder log = new StringBuilder();
        log.append("\nUrl:").append(requestUrl).append("\n");
        log.append("Request:").append(request).append("\n");
        log.append("Response:").append(res.length() <= 1000 ? res : res.substring(0, 1000)+"...").append("\n");
        Long responseTime = Duration.between(startTime, localDateTime).toMillis();
        log.append("Time:").append(responseTime).append("\n");
        log.append("Status:").append("success").append("\n");
        log.append("结束时间：" + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS").format(localDateTime)).append("\n");
        LOG.info(log.toString());
    }

    /**
     * 请求失败日志
     *
     * @param message 错误信息
     * @return
     */
    public void failLog(String message) {
        LocalDateTime localDateTime = LocalDateTime.now();
        StringBuilder log = new StringBuilder();
        log.append("\nUrl:").append(requestUrl).append("\n");
        log.append("Request:").append(request).append("\n");
        log.append("Message:").append(message).append("\n");
        Long responseTime = Duration.between(startTime, localDateTime).toMillis();
        log.append("Time:").append(responseTime).append("\n");
        log.append("Status:").append("fail").append("\n");
        log.append("结束时间：" + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS").format(localDateTime)).append("\n");
        LOG.error(log.toString());
    }

}
