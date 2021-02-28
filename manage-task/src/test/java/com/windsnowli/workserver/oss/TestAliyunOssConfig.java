package com.windsnowli.workserver.oss;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.utils.DateUtil;
import com.aliyun.oss.model.*;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.windsnowli.workserver.config.AliyunOssConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class TestAliyunOssConfig {
    private AliyunOssConfig aliyunOssConfig;

    @Autowired
    public void setAliyunOss(AliyunOssConfig aliyunOssConfig) {
        this.aliyunOssConfig = aliyunOssConfig;
    }


    @Test
    public void testGetUrl() throws ParseException {
        String objectName = "user-dir-prefix/4_2_global.jpg";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(aliyunOssConfig.getEndpoint(), aliyunOssConfig.getAccessId(), aliyunOssConfig.getAccessKey());

        Date expiration = DateUtil.parseRfc822Date("Wed, 18 Mar 2022 14:20:00 GMT");
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(aliyunOssConfig.getBucket(), objectName, HttpMethod.GET);
        // 设置过期时间。
        request.setExpiration(expiration);
        // 通过HTTP GET请求生成签名URL。
        URL signedUrl = ossClient.generatePresignedUrl(request);
        System.out.println("signed url for getObject: " + signedUrl);

//        // 使用签名URL发送请求。
//        Map<String, String> customHeaders = new HashMap<String, String>();
//        // 添加GetObject请求头。
//        customHeaders.put("Range", "bytes=100-1000");
//        OSSObject object = ossClient.getObject(signedUrl, customHeaders);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    @Test
    public void testListObject() {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = aliyunOssConfig.getEndpoint();
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
        String accessKeyId = aliyunOssConfig.getAccessId();
        String accessKeySecret = aliyunOssConfig.getAccessKey();
        String bucketName = aliyunOssConfig.getBucket();
        String keyPrefix = "user-dir-prefix/ ";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 设置最大个数。
        final int maxKeys = 200;
        String nextContinuationToken = null;
        ListObjectsV2Result result = null;

        // 分页列举指定前缀的文件。
        do {
            ListObjectsV2Request listObjectsV2Request = new ListObjectsV2Request(bucketName).withMaxKeys(maxKeys);
            listObjectsV2Request.setPrefix(keyPrefix);
            listObjectsV2Request.setContinuationToken(nextContinuationToken);
            result = ossClient.listObjectsV2(listObjectsV2Request);

            List<OSSObjectSummary> sums = result.getObjectSummaries();
            for (OSSObjectSummary s : sums) {
                System.out.println("\t" + s.getKey() + " : " + (s.getSize() / 1024) + "KB");
            }

            nextContinuationToken = result.getNextContinuationToken();

        } while (result.isTruncated());

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    // 获取某个存储空间下指定目录（文件夹）下的文件大小。
    private static long calculateFolderLength(OSS ossClient, String bucketName, String folder) {
        long size = 0L;
        ListObjectsV2Result result = null;
        do {
            // MaxKey默认值为100，最大值为1000。
            ListObjectsV2Request request = new ListObjectsV2Request(bucketName).withPrefix(folder).withMaxKeys(1000);
            if (result != null) {
                request.setContinuationToken(result.getNextContinuationToken());
            }
            result = ossClient.listObjectsV2(request);
            List<OSSObjectSummary> sums = result.getObjectSummaries();
            for (OSSObjectSummary s : sums) {
                size += s.getSize();
            }
        } while (result.isTruncated());
        return size;
    }

    public void Calculate() {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
        String accessKeyId = "<yourAccessKeyId>";
        String accessKeySecret = "<yourAccessKeySecret>";
        String bucketName = "<yourBucketName>";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 指定前缀。若您希望遍历主目录文件夹，将该值置空。
        final String keyPrefix = "<YourPrefix>";
        ListObjectsV2Result result = null;
        do {
            // 默认情况下，每次列举100个文件或目录。
            ListObjectsV2Request request = new ListObjectsV2Request(bucketName).withDelimiter("/").withPrefix(keyPrefix);
            if (result != null) {
                request.setContinuationToken(result.getNextContinuationToken());
            }
            result = ossClient.listObjectsV2(request);
            List<String> folders = result.getCommonPrefixes();
            for (String folder : folders) {
                System.out.println(folder + " : " + (calculateFolderLength(ossClient, bucketName, folder) / 1024) + "KB");
            }
            List<OSSObjectSummary> sums = result.getObjectSummaries();
            for (OSSObjectSummary s : sums) {
                System.out.println(s.getKey() + " : " + (s.getSize() / 1024) + "KB");
            }
        } while (result.isTruncated());
        ossClient.shutdown();
    }
}
