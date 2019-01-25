package com.deliver.util;

import com.baidubce.Protocol;
import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.BosClientConfiguration;
import com.baidubce.services.bos.model.*;
import com.deliver.service.BOSFileService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * Created by pdl on 2018/12/24.
 */
public class BosUtil {
    private static final String STS_ENDPOINT = "gz.bcebos.com";
    private static final String ACCESS_KEY_ID = "07ab72df85ac4e8088e67109b73845fc";// 用户的Access Key ID
    private static final String SECRET_ACCESS_KEY = "b3bbc76058f541a0b3858da1e884f14d";// 用户的Secret Access Key

    public static BosClient createBosClient(){
        BosClientConfiguration config = new BosClientConfiguration();
        config.setCredentials(new DefaultBceCredentials(ACCESS_KEY_ID, SECRET_ACCESS_KEY));
        config.setEndpoint(STS_ENDPOINT);
        config.setProtocol(Protocol.HTTPS); // 如果不指明, 则使用http
        BosClient client = new BosClient(config);
        return client;
    }

    public static void createBucket (BosClient client, String bucketName) {
        // 新建一个Bucket
        client.createBucket(bucketName);
    }

    public static void listBuckets (BosClient client) {
        // 获取用户的Bucket列表
        List<BucketSummary> buckets = client.listBuckets().getBuckets();

        // 遍历Bucket
        for (BucketSummary bucket : buckets) {
            System.out.println(bucket.getName());
        }
    }

    public static void deleteBucket (BosClient client, String bucketName) {
        // 删除Bucket
        client.deleteBucket(bucketName);
    }

    public static void setBucketPrivate (BosClient client, String bucketName) {
        client.setBucketAcl(bucketName, CannedAccessControlList.Private);
    }

    public static void PutObject(BosClient client, String bucketName, String objectKey, String path){
        // 获取指定文件
        File file = new File(path);

        // 以文件形式上传Object
        PutObjectResponse putObjectFromFileResponse = client.putObject(bucketName, objectKey, file);

        // 以二进制串上传Object
        //PutObjectResponse putObjectResponseFromByte = client.putObject(bucketName, objectKey, byte1);
        // 以字符串上传Object
        //PutObjectResponse putObjectResponseFromString = client.putObject(bucketName, objectKey, string1);

        // 打印ETag
        System.out.println(putObjectFromFileResponse.getETag());
    }

    public static void getObject(HttpServletResponse response, BosClient client, String bucketName, String objectKey)
            throws IOException {

        // 获取Object，返回结果为BosObject对象
        BosObject object = client.getObject(bucketName, objectKey);

        // 获取ObjectMeta
        ObjectMetadata meta = object.getObjectMetadata();

        // 获取Object的输入流
        InputStream objectContent = object.getObjectContent();

        // 处理Object
        response.reset();
        response.setContentType("application/force-download");// 设置强制下载不打开
        response.addHeader("Content-Disposition",
                "attachment;fileName=" + objectKey);// 设置文件名
        // 循环取出流中的数据
        /*byte[] b = new byte[objectContent.available()+1];
        int len=0;
        try {
            while ((len = objectContent.read(b)) > 0)
                response.getOutputStream().write(b, 0, len);
            objectContent.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        byte[] buffer = new byte[1024];
        //FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(objectContent);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
            System.out.println("success");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (objectContent != null) {
                try {
                    objectContent.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        // 关闭流
        //objectContent.close();

        //System.out.println(new String(buffer,0,len));
    }

    public static void main(String[] args) throws IOException {

        BosClient client = createBosClient();
        //createBucket(client,"deliverbos10");
        listBuckets(client);
        ListObjectsResponse listing = client.listObjects("deliverbos10");

// 遍历所有Object
        for (BosObjectSummary objectSummary : listing.getContents()) {
            System.out.println("ObjectKey: " + objectSummary.getKey());
        }
        //PutObject(client,"deliverbos10","21.png","D:\\images\\1.png");
        //HttpServletResponse response ;
        //getObject(response,client,"deliverbos10","1.png");

        BOSFileService bosFileService= new BOSFileService();

        String url  = bosFileService.getFileURL(client,"21.png");
        System.out.println("url: " + url);


    }
}
