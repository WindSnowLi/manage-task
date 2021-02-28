package com.windsnowli.workserver.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author windSnowLi
 */
@Getter
@Setter
@ToString
@Component
@ConfigurationProperties(prefix = "aliyunoss")
public class AliyunOssConfig {
    /**
     * 请填写您的AccessKeyId。
     */
    private String accessId;
    /**
     * 请填写您的AccessKeySecret。
     */
    private String accessKey;
    /**
     * 请填写您的 endpoint。
     */
    private String endpoint;
    /**
     * 请填写您的 bucketname 。
     */
    private String bucket;
    /**
     * host的格式为 bucketname.endpoint
     */
    private String host;
    /**
     * callbackUrl为 上传回调服务器的URL，请将下面的IP和Port配置为您自己的真实信息。
     */
    private String callbackUrl;
    /**
     * 用户上传文件时指定的前缀
     */
    private String dir;
}
