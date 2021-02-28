package com.windsnowli.workserver.services.inter;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author windSnowLi
 */
@Service
public interface OssFileServiceInter {
    /**
     * 获取任务文件信息列表
     *
     * @param taskId task信息
     * @return 文件信息列表Msg信息
     */
    String getTaskFileListJson(int taskId);

    /**
     * 获取任务文件信息列表
     *
     * @param taskId task信息
     * @return 文件信息列表
     */
    List<JSONObject> getTaskFileList(int taskId);


    /**
     * 获取文件签名连接
     *
     * @param objectName 文件路径
     * @return 签名urlMsg信息
     */
    String getSignedUrlJson(String objectName);

    /**
     * 验证上传回调的Request
     *
     * @param request         上传回调请求
     * @param ossCallbackBody 上传回调请求参数
     * @return 验证结果
     * @throws NumberFormatException 请求参数异常
     */
    boolean verifyOssCallbackRequest(HttpServletRequest request, String ossCallbackBody);

    /**
     * 删除文件
     *
     * @param fileJson 文件信息，格式{"userId":"int","fileName":"String"}
     * @return 删除结果Msg信息
     */
    String deleteFile(JSONObject fileJson);
}