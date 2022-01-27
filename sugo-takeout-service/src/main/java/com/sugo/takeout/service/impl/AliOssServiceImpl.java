package com.sugo.takeout.service.impl;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.sugo.takeout.common.config.oss.AliOssProperties;
import com.sugo.takeout.service.OssService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;

/**
 * @author HHY
 */
@Service
public class AliOssServiceImpl implements OssService {

    private AliOssProperties properties;

    @Autowired
    public void setProperties(AliOssProperties properties) {
        this.properties = properties;
    }

    @Override
    public String upload(@NonNull InputStream inputStream, @NonNull String contentType)  {
        OSS ossClient = new OSSClientBuilder().build(properties.getEndPoint(),properties.getKeyId(),properties.getKeySecret());
        String filename = new DateTime().toString("yyyy/MM/dd").concat("/") + UUID.randomUUID().toString().replace("-","");
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(contentType);
        ossClient.putObject(properties.getBucketName(),filename, inputStream,objectMetadata);
        ossClient.shutdown();
        return "https://".concat(properties.getBucketName()).concat(".").concat(properties.getEndPoint()).concat("/").concat(filename);
    }

}
