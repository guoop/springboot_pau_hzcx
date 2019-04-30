package com.soft.ware;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

/**
 * 存储桶测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TencentRepository {
    // 1 初始化用户身份信息(secretId, secretKey)
    static COSCredentials cred = new BasicCOSCredentials("AKIDNYXzVu9IExQnPXsBiEXG1abZ3xjPrGer", "Rk3B32rqg3TkpfP1RfqgNwLmznFWlonB");
    // 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
    // clientConfig中包含了设置region, https(默认http), 超时, 代理等set方法, 使用可参见源码或者接口文档FAQ中说明
    static ClientConfig clientConfig = new ClientConfig(new Region("ap-chengdu"));
    // 3 生成cos客户端
    static COSClient cosClient = new COSClient(cred, clientConfig);
    // bucket的命名规则为{name}-{appid} ，此处填写的存储桶名称必须为此格式
    static String bucketName = "paulo-excel-1251363375";
    // 指定要上传到 COS 上对象键
    // 对象键（Key）是对象在存储桶中的唯一标识。例如，在对象的访问域名 `bucket1-1250000000.cos.ap-chengdu.myqcloud.com/mydemo.jpg` 中，对象键为 mydemo.jpg, 详情参考 [对象键](https://cloud.tencent.com/document/product/436/13324)
    static  String key = "mydemo.jpg";

    /**
     * 初始化CosClient相关配置， appid、accessKey、secretKey、region
     * @return
     */
    @Test
    public  void download() {
        // 设置要下载到的本地路径
        File downFile = new File("E:\\\\WORK\\\\demo.txt");
        // 设置要下载的文件所在的 对象桶的名称 和对象键
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
        ObjectMetadata downObjectMeta = cosClient.getObject(getObjectRequest, downFile);
        // 1 初始化用户身份信息(secretId, secretKey)
        // 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig中包含了设置region, https(默认http), 超时, 代理等set方法, 使用可参见源码或者接口文档FAQ中说明
        // 3 生成cos客户端
        // bucket的命名规则为{name}-{appid} ，此处填写的存储桶名称必须为此格式
        //String bucketName = BUCKETNAME;
    }


    @Test
    public  void upload(){
        File localFile = new File("E:\\\\\\\\WORK\\\\\\\\demo.txt");
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        String etag = putObjectResult.getETag();  // 获取文件的 etag
    }
    @Test
    public  void del(){
        // 指定要删除的 bucket 和对象键
        cosClient.deleteObject(bucketName, key);
    }


}
