package com.windsnowli.workserver.services.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.auth0.jwt.JWT;
import com.windsnowli.workserver.entity.Msg;
import com.windsnowli.workserver.entity.Task;
import com.windsnowli.workserver.entity.TaskRecord;
import com.windsnowli.workserver.entity.User;
import com.windsnowli.workserver.oss.impl.OssImpl;
import com.windsnowli.workserver.services.inter.OssFileServiceInter;
import com.windsnowli.workserver.services.inter.UserServiceInter;
import com.windsnowli.workserver.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author windSnowLi
 */
@Service("ossFileService")
public class OssFileServiceImpl implements OssFileServiceInter {
    private TaskServiceImpl taskService;
    private OssImpl oss;
    private UserServiceInter userServiceInter;

    @Autowired
    public void setTaskService(TaskServiceImpl taskService) {
        this.taskService = taskService;
    }

    @Autowired
    public void setOss(OssImpl oss) {
        this.oss = oss;
    }

    @Autowired
    public void setUserService(UserServiceInter userServiceInter) {
        this.userServiceInter = userServiceInter;
    }

    /**
     * 获取任务文件信息列表
     *
     * @param taskId task信息
     * @return 文件信息列表Msg信息
     */
    @Override
    public String getTaskFileListJson(int taskId) {
        return Msg.getSuccessMsg(this.getTaskFileList(taskId));
    }

    /**
     * 获取任务文件信息列表
     *
     * @param taskId task信息
     * @return 文件信息列表
     */
    @Override
    public List<JSONObject> getTaskFileList(int taskId) {
        //TODO
        Task task = taskService.findTask(taskId);
        String fileHead = "task/";
        switch (task.getTaskPower()) {
            case CLAZZ:
                fileHead += "clazz/";
                break;
            case ACADEMY:
                fileHead += "academy/";
                break;
            case MAJOR:
                fileHead += "major/";
                break;
            default:
                return null;
        }
        fileHead = fileHead + task.getTaskPowerId() + "/" + task.getTaskId() + "/" + task.getTaskId() + "_";

        List<OSSObjectSummary> fileList = oss.getFileList(fileHead);
        List<JSONObject> records = new ArrayList<>();
        for (OSSObjectSummary file : fileList) {
            String[] fileInfoStr = file.getKey().substring(file.getKey().lastIndexOf("/") + 1).split("_");
            TaskRecord tempTaskRecord = taskService.findTaskRecord(Integer.parseInt(fileInfoStr[0]), Integer.parseInt(fileInfoStr[1]));
            JSONObject taskRecordJson = (JSONObject) JSONObject.toJSON(tempTaskRecord);
            taskRecordJson.put("task", taskService.findTask(task.getTaskId()));
            taskRecordJson.put("fileName", file.getKey());
            taskRecordJson.put("fileSize", file.getSize());
            records.add(taskRecordJson);
        }
        return records;
    }

    /**
     * 获取文件签名连接
     *
     * @param objectName 文件路径
     * @return 签名urlMsg信息
     */
    @Override
    public String getSignedUrlJson(String objectName) {
        return Msg.getSuccessMsg(oss.getSignedUrl(objectName));
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
    public boolean verifyOssCallbackRequest(HttpServletRequest request, String ossCallbackBody) {
        return oss.verifyOssCallbackRequest(request, ossCallbackBody);
    }

    /**
     * 删除文件
     *
     * @param fileJson 文件信息，格式{"fileName":"String","token":"String"}
     * @return 删除结果Msg信息
     */
    @Override
    public String deleteFile(JSONObject fileJson) {

        if (null == fileJson.getString("token")) {
            return Msg.getFailMsg();
        }
        User user = null;
        try {
            user = userServiceInter.findIdUser(JwtUtils.getTokenUserId(fileJson.getString("token")));
        } catch (Exception e) {
            return Msg.getFailMsg();
        }
        if (user == null || (user.getUserPower() < 2)) {
            return Msg.getFailMsg();
        }

        String objectName = fileJson.getString("fileName");
        final String pathSign = ".";
        if (objectName.lastIndexOf(pathSign) == -1) {
            return Msg.getFailMsg();
        }
        return oss.deleteFile(objectName) ? Msg.getSuccessMsg() : Msg.getFailMsg();
    }
}
