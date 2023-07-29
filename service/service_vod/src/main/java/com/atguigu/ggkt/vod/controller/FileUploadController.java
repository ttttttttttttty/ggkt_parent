package com.atguigu.ggkt.vod.controller;

import com.atguigu.ggkt.result.Result;
import com.atguigu.ggkt.vod.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * ClassName: FileUploadController
 * Package: com.atguigu.ggkt.vod.controller
 * Description: 上传头像到腾讯云对象存储COS
 *
 * @Author:天宇
 * @Create：2023/7/21-16:17
 * @Version: v1.0
 */
@Api(tags = "文件上传接口")
@RestController
@RequestMapping("/admin/vod/file")
@CrossOrigin  // 跨域
public class FileUploadController {

    private FileService fileService;

    @Autowired
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    @ApiOperation("文件上传")
    @PostMapping("/upload")
    public Result uploadFile(MultipartFile file) {
        String url = fileService.upload(file);
        if (StringUtils.isEmpty(url)) {
            return Result.fail().message("上传文件失败");
        } else {
            return Result.ok(url).message("上传文件成功");
        }
    }
}
