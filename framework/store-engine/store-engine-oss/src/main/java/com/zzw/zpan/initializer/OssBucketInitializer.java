package com.zzw.zpan.initializer;

import com.aliyun.oss.OSSClient;
import com.zzw.zpan.config.OssStorageEngineConfig;
import com.zzw.zpan.exception.RPanFrameworkException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


/**
 * OSS桶初始化器
 */
@Component
@Slf4j
public class OssBucketInitializer implements CommandLineRunner {

    @Autowired
    private OssStorageEngineConfig config;

    @Autowired
    private OSSClient client;

    @Override
    public void run(String... args) throws Exception {
        boolean bucketExist = client.doesBucketExist(config.getBucketName());

        if (!bucketExist && config.getAutoCreateBucket()) {
            client.createBucket(config.getBucketName());
        }

        if (!bucketExist && !config.getAutoCreateBucket()) {
            throw new RPanFrameworkException("the bucket " + config.getBucketName() + " is not available");
        }

        log.info("the bucket " + config.getBucketName() + " have been created!");
    }

}
