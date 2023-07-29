package com.atguigu.ggkt.vod.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * ClassName: FileService
 * Package: com.atguigu.ggkt.vod.service
 * Description:
 *
 * @Author:天宇
 * @Create：2023/7/21-16:18
 * @Version: v1.0
 */
public interface FileService {

    // 文件上传
    String upload(MultipartFile file);
}
