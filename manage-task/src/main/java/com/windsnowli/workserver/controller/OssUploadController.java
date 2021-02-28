package com.windsnowli.workserver.controller;


import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.windsnowli.workserver.annotation.PowerLevel;
import com.windsnowli.workserver.config.AliyunOssConfig;
import com.windsnowli.workserver.entity.Msg;
import com.windsnowli.workserver.entity.Student;
import com.windsnowli.workserver.entity.Task;
import com.windsnowli.workserver.services.impl.OssFileServiceImpl;
import com.windsnowli.workserver.services.impl.StudentServiceImpl;
import com.windsnowli.workserver.services.impl.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author windSnowLi
 */
@RestController
@RequestMapping(value = "/file")
public class OssUploadController {
    private AliyunOssConfig aliyunOssConfig;
    private TaskServiceImpl taskService;
    private StudentServiceImpl studentService;
    private OssFileServiceImpl ossFileService;

    @Autowired
    public void setAliyunOssConfig(AliyunOssConfig aliyunOssConfig) {
        this.aliyunOssConfig = aliyunOssConfig;
    }

    @Autowired
    public void setTaskService(TaskServiceImpl taskService) {
        this.taskService = taskService;
    }

    @Autowired
    public void setStudentService(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }

    @Autowired
    public void setOssFileService(OssFileServiceImpl ossFileService) {
        this.ossFileService = ossFileService;
    }


    @RequestMapping(value = "callback")
    @ResponseBody
    public String uploadCallback(@RequestParam Map<String, Object> map) {
        String fileName = ((String) map.get("filename"));
        fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
        String[] names = fileName.split("_");
        try {
            taskService.completeTask(Integer.parseInt(names[0]), Integer.parseInt(names[1]));
            return Msg.getSuccessMsg();
        } catch (Exception e) {
            e.printStackTrace();
            return Msg.getFailMsg();
        }
    }

    /**
     * Post请求
     */
    @PostMapping(value = "upload")
    public String uploadPost(@RequestBody String ossCallbackBody, HttpServletRequest request) {
        boolean ret = ossFileService.verifyOssCallbackRequest(request, ossCallbackBody);
        if (ret) {
            return Msg.getSuccessMsg();
        } else {
            return Msg.getFailMsg();
        }
    }

    /**
     * 上传文件获取密钥请求
     * @param request 请求对象
     * @return 上传密钥
     */
    @GetMapping(value = "upload")
    public String uploadGet(HttpServletRequest request) {
        OSS client = new OSSClientBuilder().build(aliyunOssConfig.getEndpoint(), aliyunOssConfig.getAccessId(), aliyunOssConfig.getAccessKey());
        try {
            long expireTime = 30;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            Task task = taskService.findTask(Integer.parseInt(request.getParameter("taskId")));
            Student student = studentService.findStudentBaseData(Integer.parseInt(request.getParameter("userId")));
            if (null == task || null == student) {
                return "";
            }

            String saveDir = "task/";
            switch (task.getTaskPower()) {
                case CLAZZ:
                    saveDir += "clazz/" + student.getStudentClazzId();
                    break;
                case ACADEMY:
                    saveDir += "academy/" + student.getStudentAcademyId();
                    break;
                case MAJOR:
                    saveDir += "major/" + student.getStudentMajorId();
                    break;
                default:
                    return "";
            }
            saveDir = saveDir + "/" + task.getTaskId() + "/";
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, aliyunOssConfig.getDir() + saveDir);

            String postPolicy = client.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes(StandardCharsets.UTF_8);
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = client.calculatePostSignature(postPolicy);

            Map<String, String> respMap = new LinkedHashMap<>();
            respMap.put("accessid", aliyunOssConfig.getAccessId());
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            respMap.put("dir", aliyunOssConfig.getDir() + saveDir);
            respMap.put("host", aliyunOssConfig.getHost());
            respMap.put("expire", String.valueOf(expireEndTime / 1000));

            JSONObject jasonCallback = new JSONObject();
            jasonCallback.put("callbackUrl", aliyunOssConfig.getCallbackUrl());
            jasonCallback.put("callbackBody",
                    "filename=${object}&size=${size}&mimeType=${mimeType}&height=${imageInfo.height}&width=${imageInfo.width}");
            jasonCallback.put("callbackBodyType", "application/x-www-form-urlencoded");
            String base64CallbackBody = BinaryUtil.toBase64String(jasonCallback.toString().getBytes());
            respMap.put("callback", base64CallbackBody);

            JSONObject ja1 = (JSONObject) JSONObject.toJSON(respMap);

            String callbackFunName = request.getParameter("callback");
            if (callbackFunName == null || "".equalsIgnoreCase(callbackFunName)) {
                return ja1.toString();
            } else {
                return callbackFunName + "( " + ja1.toString() + " )";
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return Msg.getFailMsg();
    }


    /**
     * 获取任务文件信息列表
     *
     * @param taskJson task信息，格式：{"taskId":"int"}
     * @return 文件信息列表Msg信息
     */
    @PowerLevel(value = 1)
    @PostMapping(value = "getTaskFileListJson", produces = "application/json;charset=UTF-8")
    public String getTaskFileListJson(@RequestBody JSONObject taskJson) {
        return ossFileService.getTaskFileListJson(taskJson.getInteger("taskId"));
    }


    /**
     * 获取文件的签名url
     *
     * @param fileJson file信息，格式：{"fileName":"int"}
     * @return 签名url
     */
    @PostMapping(value = "getSignedUrlJson", produces = "application/json;charset=UTF-8")
    public String getSignedUrlJson(@RequestBody JSONObject fileJson) {
        return ossFileService.getSignedUrlJson(fileJson.getString("filePosition"));
    }

    /**
     * 删除文件
     *
     * @param fileJson 文件信息，格式{"userId":"int","fileName":"String","token":"String"}
     * @return 删除结果Msg信息
     */
    @PowerLevel(value = 1)
    @PostMapping(value = "deleteFileUrlJson", produces = "application/json;charset=UTF-8")
    public String deleteFileUrlJson(@RequestBody JSONObject fileJson) {
        return ossFileService.deleteFile(fileJson);
    }
}
