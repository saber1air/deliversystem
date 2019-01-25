package com.deliver.service;

import com.baidubce.services.bos.BosClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;

/**
 * Created by pdl on 2018/12/27.
 */
@Service
public class BOSFileService {

    @Value("${baidu.bucketName}")
    public String bucketName;

    public String getFileURL(BosClient client,String fileKey){
        int duration = 7 * 24 * 3600;
        //指定用户需要获取的Object所在的Bucket名称、该Object名称、时间戳、URL的有效时长
        URL url = client.generatePresignedUrl(bucketName, fileKey, duration);
        String s = url.toString();
        return s;
    }
}
