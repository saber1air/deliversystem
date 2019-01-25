package com.deliver.controller;

import com.deliver.entity.HumanMedia;
import com.deliver.service.HumanMediaService;
import com.deliver.util.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by pdl on 2018/12/4.
 */
@Controller
@RequestMapping("/media")
public class MediaController {

    @Autowired
    private HumanMediaService humanMediaService;

    /**
     * 图片删除接口
     */
    @RequestMapping(value="/delmedia")
    @ResponseBody
    public ResultInfo delMedia(@RequestBody Map<String,Object> jsonMap ) throws Exception {
        Integer mediaID = (Integer) jsonMap.get("mediaID");
        ResultInfo resultInfo = new ResultInfo(false);
        if(mediaID!=null && mediaID!=-1){
            HumanMedia humanMedia = humanMediaService.findByMediaID(mediaID);
            if(humanMedia.getDeleteFlag()==0 && humanMedia.getShowFlag()==1){
                resultInfo.setCode(400);
                resultInfo.setMessage("删除失败！不能删除头像，请在手机端进行头像修改！");
                resultInfo.setSuccess(false);
                return resultInfo;
            }
            humanMediaService.delMedia(humanMedia);
            resultInfo.setCode(200);
            resultInfo.setMessage("删除成功！");
            resultInfo.setSuccess(true);
            return resultInfo;
        }else{
            resultInfo.setCode(400);
            resultInfo.setMessage("删除失败！图片不存在！");
            resultInfo.setSuccess(false);
            return resultInfo;
        }

    }

    /**
     * 图片删除接口
     */
    @RequestMapping(value="/findmedia")
    @ResponseBody
    public ResultInfo findMedia(@RequestBody Map<String,Object> jsonMap ) throws Exception {
        Integer humanID = (Integer) jsonMap.get("humanID");
        ResultInfo resultInfo = new ResultInfo(false);
        if(humanID!=null && humanID!=-1){
            List<HumanMedia> humanMediaList = humanMediaService.findByHumanIDAndDeleteFlagAndCheckFlagAndShowFlag(humanID,0,1,0);
            resultInfo.addData("humanMediaList",humanMediaList);
            resultInfo.setCode(200);
            resultInfo.setMessage("查询成功！");
            resultInfo.setSuccess(true);
            return resultInfo;
        }else{
            resultInfo.setCode(400);
            resultInfo.setMessage("查询失败！人员不存在！");
            resultInfo.setSuccess(false);
            return resultInfo;
        }
    }
}
