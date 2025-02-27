package com.zzw.zpan.initializer;


import com.zzw.zpan.config.LocalStorageEngineConfig;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * 初始化上传文件根目录和文件分片存储根目录的初始化器
 */
@Component
@Slf4j
public class UploadFolderAndChunksFolderInitializer implements CommandLineRunner {

    @Resource
    private LocalStorageEngineConfig config;

    @Override
    public void run(String... args) throws Exception {
        FileUtils.forceMkdir(new File(config.getRootFilePath()));
        log.info("the root file path has been created!");
        FileUtils.forceMkdir(new File(config.getRootFileChunkPath()));
        log.info("the root file chunk path has been created!");
    }

}
