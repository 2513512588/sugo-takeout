package com.sugo.smart_city.api.controller.common;

import com.sugo.smart_city.common.util.Result;
import com.sugo.smart_city.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Api(tags = "公用文件上传接口")
@RestController
@RequestMapping("/commons/upload")
public class UploadController {

    @Resource
    @Qualifier("aliOssServiceImpl")
    private OssService ossService;

    @ApiOperation(value = "上传文件")
    @PostMapping("")
    public Result upload(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        String upload = ossService.upload(multipartFile.getInputStream(), "image/png");
        return Result.ok().data("url", upload);
    }

}
