package com.deliver.controller;

import com.baidubce.services.bos.BosClient;
import com.deliver.service.BOSFileService;
import com.deliver.util.BosUtil;
import com.deliver.util.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by pdl on 2018/12/27.
 */
@Controller
@RequestMapping("/bos")
public class BosController {

    @Autowired
    private BOSFileService bosFileService;

    @Value("${baidu.bucketName}")
    public String bucketName;
    /**
     * *
     */
    @RequestMapping(value = "/getimgpath")
    @ResponseBody
    public ResultInfo getImgPath(HttpServletRequest request) throws Exception {
        BosUtil bosUtil = new BosUtil();
        BosClient client = bosUtil.createBosClient();
        System.out.println("bucketName: " + bucketName);
        String url  = bosFileService.getFileURL(client,"21.png");
        System.out.println("url: " + url);
        ResultInfo resultInfo = new ResultInfo(true);
        return resultInfo;
    }
}
