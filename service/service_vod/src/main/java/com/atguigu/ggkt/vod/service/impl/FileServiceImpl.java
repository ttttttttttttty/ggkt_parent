package com.atguigu.ggkt.vod.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.ggkt.vod.service.FileService;
import com.atguigu.ggkt.vod.utils.ConstantPropertiesUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * ClassName: FileServiceImpl
 * Package: com.atguigu.ggkt.vod.service.impl
 * Description:
 *
 * @Author:天宇
 * @Create：2023/7/21-16:19
 * @Version: v1.0
 */
@Service
public class FileServiceImpl implements FileService {

    @Override
    public String upload(MultipartFile file) {
        String url = null;
        // 初始化，生成COS客户端
        COSClient cos = this.init();
        String bucketName = "ggkt-atguigu-1319501190";
        try {
            url = this.up(cos, file, bucketName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return url;
    }

    public COSClient init() {
        // 1 初始化用户身份信息（secretId, secretKey）。
        String secretId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String secretKey = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);

        // 2 设置 bucket 的地域
        // clientConfig 中包含了设置 region, https(默认 http),超时, 代理等 set 方法
        Region region = new Region(ConstantPropertiesUtil.END_POINT);
        ClientConfig clientConfig = new ClientConfig(region);
        // 这里建议设置使用 https 协议
        // 从 5.6.54 版本开始，默认使用了 https
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 3 生成 cos 客户端
        COSClient cosClient = new COSClient(cred, clientConfig);
        return cosClient;
    }

    // 上传对象(适用于20M以下图片类小文件上传，最大支持上传不超过5GB文件)
    public String up(COSClient cosClient, MultipartFile file, String bucketName) throws IOException {
        // Endpoint以北京为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtil.END_POINT;
        // 指定要上传的文件
        InputStream inputStream = file.getInputStream();
        // 指定文件将要存放的存储桶
        // 指定文件上传到 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
        String key = UUID
                .randomUUID()
                .toString()
                .replaceAll("-", "")
                + file.getOriginalFilename(); // 2023/07/22
        // 对上传文件分组，根据当前时间
        String dateUrl = new DateTime()
                .toString("yyyy/MM/dd");
        key = dateUrl + "/" + key;
        ObjectMetadata objectMetadata = new ObjectMetadata();
        PutObjectRequest putObjectRequest = new PutObjectRequest(
                bucketName,
                key,
                inputStream,
                objectMetadata);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        // json输出返回结果
        System.out.println(JSON.toJSON(putObjectResult));
        String url = "https://" + bucketName + "." + "cos" + "." + endpoint + ".myqcloud.com" + "/" + key;
        return url;
    }
}
