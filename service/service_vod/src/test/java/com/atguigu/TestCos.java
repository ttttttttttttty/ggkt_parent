package com.atguigu;

import com.alibaba.fastjson.JSON;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import org.joda.time.DateTime;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * ClassName: TestCos
 * Package: com.atguigu
 * Description:
 *
 * @Author:天宇
 * @Create：2023/7/20-7:51
 * @Version: v1.0
 */
public class TestCos {
    public static void main(String[] args) {
//        String localFilePath = "C:\\Users\\tianyu\\Downloads\\default.gif";
//        TestCos testCos = new TestCos();
//        COSClient cosClient = testCos.init();
//        testCos.up(cosClient, localFilePath, "ggkt-atguigu-1319501190");
//        testCos.shutdown(cosClient);
        String dateUrl = new DateTime()
                .toString("yyyy/MM/dd");
        System.out.println(dateUrl);
    }

    // 初始化客户端
    public COSClient init() {
        // 1 初始化用户身份信息（secretId, secretKey）。
        // SECRETID 和 SECRETKEY 请登录访问管理控制台 https://console.cloud.tencent.com/cam/capi 进行查看和管理
        String secretId = "AKIDjIpIYdSNL8h48JUyN5pQmuocN3V1IPQI";//用户的 SecretId，建议使用子账号密钥，授权遵循最小权限指引，降低使用风险。子账号密钥获取可参见 https://cloud.tencent.com/document/product/598/37140
        String secretKey = "8CHF24PcfUSL5cz73oNjDc6cAkLo6C5m";//用户的 SecretKey，建议使用子账号密钥，授权遵循最小权限指引，降低使用风险。子账号密钥获取可参见 https://cloud.tencent.com/document/product/598/37140
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置 bucket 的地域, COS 地域的简称请参见 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region("ap-beijing");
        ClientConfig clientConfig = new ClientConfig(region);
        // 这里建议设置使用 https 协议
        // 从 5.6.54 版本开始，默认使用了 https
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);
        return cosClient;
    }

    // 创建存储桶
    public void add(COSClient cosClient) {
        String bucket = "examplebucket-1250000000"; //存储桶名称，格式：BucketName-APPID
        CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucket);
        // 设置 bucket 的权限为 Private(私有读写)、其他可选有 PublicRead（公有读私有写）、PublicReadWrite（公有读写）
        createBucketRequest.setCannedAcl(CannedAccessControlList.Private);
        try {
            Bucket bucketResult = cosClient.createBucket(createBucketRequest);
        } catch (CosServiceException serverException) {
            serverException.printStackTrace();
        } catch (CosClientException clientException) {
            clientException.printStackTrace();
        }

    }

    // 查询存储桶列表
    public void selectBuckets(COSClient cosClient) {
        List<Bucket> buckets = cosClient.listBuckets();
        for (Bucket bucketElement : buckets) {
            String bucketName = bucketElement.getName();
            String bucketLocation = bucketElement.getLocation();
        }

    }

    // 上传对象(适用于20M以下图片类小文件上传，最大支持上传不超过5GB文件)
    public void up(COSClient cosClient, String localFilePath, String bucketName) {
        // 指定要上传的文件
        File localFile = new File(localFilePath);
        // 指定文件将要存放的存储桶
        // String bucketName = "examplebucket-1250000000";
        // 指定文件上传到 COS 上的路径，即对象键。例如对象键为 folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
        String key = "/2023/07/20/01.gif";
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        // json输出返回结果
        System.out.println(JSON.toJSON(putObjectResult));
    }

    // 查询对象列表
    public void select(COSClient cosClient) {
        // Bucket 的命名格式为 BucketName-APPID ，此处填写的存储桶名称必须为此格式
        String bucketName = "examplebucket-1250000000";
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
        // 设置 bucket 名称
        listObjectsRequest.setBucketName(bucketName);
        // prefix 表示列出的 object 的 key 以 prefix 开始
        listObjectsRequest.setPrefix("images/");
        // deliter 表示分隔符, 设置为/表示列出当前目录下的 object, 设置为空表示列出所有的 object
        listObjectsRequest.setDelimiter("/");
        // 设置最大遍历出多少个对象, 一次 listobject 最大支持1000
        listObjectsRequest.setMaxKeys(1000);
        ObjectListing objectListing = null;
        do {
            try {
                objectListing = cosClient.listObjects(listObjectsRequest);
            } catch (CosServiceException e) {
                e.printStackTrace();
                return;
            } catch (CosClientException e) {
                e.printStackTrace();
                return;
            }
            // common prefix 表示被 delimiter 截断的路径, 如 delimter 设置为/, common prefix 则表示所有子目录的路径
            List<String> commonPrefixs = objectListing.getCommonPrefixes();


            // object summary 表示所有列出的 object 列表
            List<COSObjectSummary> cosObjectSummaries = objectListing.getObjectSummaries();
            for (COSObjectSummary cosObjectSummary : cosObjectSummaries) {
                // 文件的路径 key
                String key = cosObjectSummary.getKey();
                // 文件的 etag
                String etag = cosObjectSummary.getETag();
                // 文件的长度
                long fileSize = cosObjectSummary.getSize();
                // 文件的存储类型
                String storageClasses = cosObjectSummary.getStorageClass();
            }
            String nextMarker = objectListing.getNextMarker();
            listObjectsRequest.setMarker(nextMarker);
        } while (objectListing.isTruncated());

    }

    // 下载对象
    public void down1(COSClient cosClient) {
        // Bucket 的命名格式为 BucketName-APPID ，此处填写的存储桶名称必须为此格式
        String bucketName = "examplebucket-1250000000";
        // 指定文件在 COS 上的路径，即对象键。例如对象键为 folder/picture.jpg，则表示下载的文件 picture.jpg 在 folder 路径下
        String key = "exampleobject";
        // 方法1 获取下载输入流
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
        COSObject cosObject = cosClient.getObject(getObjectRequest);
        COSObjectInputStream cosObjectInput = cosObject.getObjectContent();
        // 下载对象的 CRC64
        String crc64Ecma = cosObject.getObjectMetadata().getCrc64Ecma();
        // 关闭输入流
        try {
            cosObjectInput.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 方法2 下载文件到本地的路径，例如 D 盘的某个目录
//        String outputFilePath = "exampleobject";
//        File downFile = new File(outputFilePath);
//        getObjectRequest = new GetObjectRequest(bucketName, key);
//        ObjectMetadata downObjectMeta = cosClient.getObject(getObjectRequest, downFile);

    }

    // 删除对象
    public void delete(COSClient cosClient) {
        // Bucket 的命名格式为 BucketName-APPID ，此处填写的存储桶名称必须为此格式
        String bucketName = "examplebucket-1250000000";
        // 指定被删除的文件在 COS 上的路径，即对象键。例如对象键为 folder/picture.jpg，则表示删除位于 folder 路径下的文件 picture.jpg
        String key = "exampleobject";
        cosClient.deleteObject(bucketName, key);

    }

    // 关闭客户端
    public void shutdown(COSClient cosClient) {
        cosClient.shutdown();
    }
}