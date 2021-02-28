package com.windsnowli.workserver.services;

import com.windsnowli.workserver.services.impl.OssFileServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestServiceOss {
    private OssFileServiceImpl ossFileService;

    @Autowired
    public void setOssFileService(OssFileServiceImpl ossFileService) {
        this.ossFileService = ossFileService;
    }

    @Test
    public void testGetFileList() {
        System.out.println(ossFileService.getTaskFileListJson(41));
    }
}
