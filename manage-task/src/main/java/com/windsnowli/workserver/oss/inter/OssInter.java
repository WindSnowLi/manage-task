package com.windsnowli.workserver.oss.inter;

import com.aliyun.oss.model.OSSObjectSummary;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OssInter {
    /**
     * 删除文件
     *
     * @param objectName 文件完整位置
     * @return
     */
    boolean deleteFile(String objectName);

    /**
     * 获取文件签名连接
     *
     * @param objectName 文件路径
     * @return 签名url
     */
    String getSignedUrl(String objectName);

    /**
     * 获取指定前缀文件列表
     *
     * @param fileHead 文件前缀
     * @return 文件对象列表
     */
    List<OSSObjectSummary> getFileList(String fileHead);


    /**
     * 获取public key
     *
     * @param url 请求url
     * @return 密钥结果
     */
    String executeGet(String url);

    /**
     * 验证上传回调的Request
     *
     * @param request         上传回调请求
     * @param ossCallbackBody 上传回调请求参数
     * @return 验证结果
     * @throws NumberFormatException 请求参数异常
     */
    boolean verifyOssCallbackRequest(HttpServletRequest request, String ossCallbackBody);
}
