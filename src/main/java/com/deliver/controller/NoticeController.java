package com.deliver.controller;


import com.deliver.entity.HumanInfo;
import com.deliver.entity.Notice;
import com.deliver.entity.NoticeRecord;
import com.deliver.service.HumanInfoService;
import com.deliver.service.NoticeService;
import com.deliver.util.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 数据同步
 * Created by pdl on 2018/9/25.
 */

@Controller
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @Autowired
    private HumanInfoService humanInfoService;

    @Value("${cbs.imagesPath}")
    private String mImagesPath;

    /**
     * 查询通知消息记录
     */
    @RequestMapping(value="/getnoticerecord")
    @ResponseBody
    public ResultInfo getNoticeRecord(@RequestBody Map<String,Object> jsonMap ) throws Exception {
        ResultInfo resultInfo = new ResultInfo(false);
        List<Notice> noticeRecordList = noticeService.getNoticeRecord((Integer) jsonMap.get("humanID"));

        resultInfo.addData("noticeRecordList",noticeRecordList);
        resultInfo.setCode(200);
        resultInfo.setMessage("查询成功！");
        resultInfo.setSuccess(true);
        return resultInfo;
    }

    /**
     * 查询通知消息记录
     */
    @RequestMapping(value="/findnoticerecord")
    @ResponseBody
    public ResultInfo findNoticeRecord(@RequestBody Map<String,Object> jsonMap ) throws Exception {
        ResultInfo resultInfo = new ResultInfo(false);
        Integer schoolID = (Integer) jsonMap.get("schoolID");
        Integer noticeType = (Integer) jsonMap.get("noticeType");
        String beginTime = (String) jsonMap.get("beginTime");
        String endTime = (String) jsonMap.get("endTime");
        List<Map<String,Object>> noticeRecordList = noticeService.findNoticeRecord(schoolID,noticeType,beginTime,endTime);

        resultInfo.addData("noticeRecordList",noticeRecordList);
        resultInfo.setCode(200);
        resultInfo.setMessage("查询成功！");
        resultInfo.setSuccess(true);
        return resultInfo;
    }

    /**
     * 删除通知消息记录
     */
    @RequestMapping(value="/delnotice")
    @ResponseBody
    public ResultInfo delNotice(@RequestBody Map<String,Object> jsonMap ) throws Exception {
        ResultInfo resultInfo = new ResultInfo(false);
        List<Integer> noticeIDs = (List) jsonMap.get("noticeID");
        if(noticeIDs!=null && noticeIDs.size()>0){
            for(Integer noticeID:noticeIDs){
                if(noticeID!=null && noticeID!=-1){
                    Notice notice = noticeService.findByNoticeIDAndDeleteFlag(noticeID,0);
                    if (notice!=null) {
                        notice.setDeleteFlag(1);
                        noticeService.update(notice);
                    }
                }

            }
        }

        resultInfo.setCode(200);
        resultInfo.setMessage("删除成功！");
        resultInfo.setSuccess(true);
        return resultInfo;
    }

    /**
     * 清除未读消息记录数
     */
    @RequestMapping(value="/delnotreadmsgnum")
    @ResponseBody
    public ResultInfo delNotReadMsgNum(@RequestBody Map<String,Object> jsonMap ) throws Exception {
        Integer getuiType = (Integer) jsonMap.get("getuiType");//getuiType=1为接送推送，2为通知推送
        Integer humanID = (Integer) jsonMap.get("humanID");
        HumanInfo humanInfo = humanInfoService.findByHumanID(humanID);
        if(getuiType==1){
            humanInfo.setDeliverNotReadNum(0);
            humanInfoService.editHuman(humanInfo);
        }else if(getuiType==2){
            humanInfo.setNoticeNotReadNum(0);
            humanInfoService.editHuman(humanInfo);
        }
        ResultInfo resultInfo = new ResultInfo(true);
        resultInfo.setCode(200);
        resultInfo.setMessage("成功！");
        return resultInfo;
    }

    /**
     * 查询未读消息记录数
     */
    @RequestMapping(value="/findnotreadmsgnum")
    @ResponseBody
    public ResultInfo findNotReadMsgNum(@RequestBody Map<String,Object> jsonMap ) throws Exception {
        Integer humanID = (Integer) jsonMap.get("humanID");
        HumanInfo humanInfo = humanInfoService.findByHumanID(humanID);
        ResultInfo resultInfo = new ResultInfo(true);
        resultInfo.addData("deliverNotReadNum",humanInfo.getDeliverNotReadNum());
        resultInfo.addData("noticeNotReadNum",humanInfo.getNoticeNotReadNum());
        resultInfo.setCode(200);
        resultInfo.setMessage("成功！");

        return resultInfo;
    }

}
