package com.windsnowli.workserver.oss.impl;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.common.utils.DateUtil;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.ListObjectsV2Request;
import com.aliyun.oss.model.ListObjectsV2Result;
import com.aliyun.oss.model.OSSObjectSummary;
import com.windsnowli.workserver.config.AliyunOssConfig;
import com.windsnowli.workserver.oss.inter.OssInter;
import com.windsnowli.workserver.utils.CodeUtils;
import com.windsnowli.workserver.utils.DateUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author windSnowLi
 */
@Service("oss")
public class OssImpl implements OssInter {
    private AliyunOssConfig aliyunOssConfig;

    @Autowired
    public void setAliyunOssConfig(AliyunOssConfig aliyunOssConfig) {
        this.aliyunOssConfig = aliyunOssConfig;
    }

    /**
     * 删除文件
     *
     * @param objectName 文件完整位置
     * @return 删除结果Msg信息
     */
    @Override
    public boolean deleteFile(String objectName) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(aliyunOssConfig.getEndpoint(), aliyunOssConfig.getAccessId(), aliyunOssConfig.getAccessKey());
        // 删除文件。
        ossClient.deleteObject(aliyunOssConfig.getBucket(), objectName);
        // 关闭OSSClient。
        ossClient.shutdown();
        return true;
    }

    /**
     * 获取文件签名连接
     *
     * @param objectName 文件路径
     * @return 签名url
     */
    @Override
    public String getSignedUrl(String objectName) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(aliyunOssConfig.getEndpoint(), aliyunOssConfig.getAccessId(), aliyunOssConfig.getAccessKey());

        Date expiration = null;
        try {
            //有效时间一个小时
            expiration = DateUtil.parseRfc822Date(DateUtils.calculationDate(new Date(), 1));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(aliyunOssConfig.getBucket(), objectName, HttpMethod.GET);
        // 设置过期时间。
        request.setExpiration(expiration);
        // 通过HTTP GET请求生成签名URL。
        URL signedUrl = ossClient.generatePresignedUrl(request);
        ossClient.shutdown();
        return signedUrl.toString();
    }

    /**
     * 获取指定前缀文件列表
     *
     * @param fileHead 文件前缀
     * @return 文件对象列表
     */
    @Override
    public List<OSSObjectSummary> getFileList(String fileHead) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = aliyunOssConfig.getEndpoint();
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
        String accessKeyId = aliyunOssConfig.getAccessId();
        String accessKeySecret = aliyunOssConfig.getAccessKey();
        String bucketName = aliyunOssConfig.getBucket();
        fileHead = aliyunOssConfig.getDir() + fileHead;
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 设置最大个数。
        final int maxKeys = 200;
        String nextContinuationToken = null;
        ListObjectsV2Result result;
        List<OSSObjectSummary> sums = new ArrayList<>();
        // 分页列举指定前缀的文件。
        do {
            ListObjectsV2Request listObjectsV2Request = new ListObjectsV2Request(bucketName).withMaxKeys(maxKeys);
            listObjectsV2Request.setPrefix(fileHead);
            listObjectsV2Request.setContinuationToken(nextContinuationToken);
            result = ossClient.listObjectsV2(listObjectsV2Request);
            sums.addAll(result.getObjectSummaries());
            nextContinuationToken = result.getNextContinuationToken();
        } while (result.isTruncated());
        // 关闭OSSClient。
        ossClient.shutdown();
        return sums;
    }


    /**
     * 获取public key
     *
     * @param url 请求url
     * @return 密钥结果
     */
    @Override
    public String executeGet(String url) {
        BufferedReader in = null;

        String content = null;
        try {
            // 定义HttpClient
            HttpClient client = HttpClientBuilder.create().build();
            // 实例化HTTP方法
            HttpGet request = new HttpGet();
            request.setURI(new URI(url));
            HttpResponse response = client.execute(request);

            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder sb = new StringBuilder();
            String line;
            String nl = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line).append(nl);
            }
            in.close();
            content = sb.toString();
        } catch (Exception ignored) {
        } finally {
            if (in != null) {
                try {
                    // 最后要关闭BufferedReader
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return content;
    }


    /**
     * 验证上传回调的Request
     *
     * @param request         上传回调请求
     * @param ossCallbackBody 上传回调请求参数
     * @return 验证结果
     * @throws NumberFormatException 请求参数异常
     */
    @Override
    public boolean verifyOssCallbackRequest(HttpServletRequest request, String ossCallbackBody)
            throws NumberFormatException {
        String autorizationInput = request.getHeader("Authorization");
        String pubKeyInput = request.getHeader("x-oss-pub-key-url");
        byte[] authorization = BinaryUtil.fromBase64String(autorizationInput);
        byte[] pubKey = BinaryUtil.fromBase64String(pubKeyInput);
        String pubKeyAddr = new String(pubKey);
        if (!pubKeyAddr.startsWith("http://gosspublic.alicdn.com/")
                && !pubKeyAddr.startsWith("https://gosspublic.alicdn.com/")) {
            return false;
        }
        String retString = executeGet(pubKeyAddr);
        retString = retString.replace("-----BEGIN PUBLIC KEY-----", "");
        retString = retString.replace("-----END PUBLIC KEY-----", "");
        String queryString = request.getQueryString();
        String uri = request.getRequestURI();
        String authStr = java.net.URLDecoder.decode(uri, StandardCharsets.UTF_8);
        if (queryString != null && !"".equals(queryString)) {
            authStr += "?" + queryString;
        }
        authStr += "\n" + ossCallbackBody;
        return CodeUtils.rsaCheck(authStr, authorization, retString);
    }
}
