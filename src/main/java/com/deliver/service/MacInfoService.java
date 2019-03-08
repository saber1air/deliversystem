package com.deliver.service;

import com.deliver.dao.AccessRecordDao;
import com.deliver.dao.MacInfoDao;
import com.deliver.entity.AccessRecord;
import com.deliver.entity.MacInfo;
import com.deliver.util.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * Created by pdl on 2018/9/13.
 */

@Service
public class MacInfoService {
    @Autowired
    private MacInfoDao macInfoDao;

    public MacInfo findByMacID(int id) {
        return macInfoDao.findByMacID(id);
    }

    public MacInfo findByMacNameAndDeleteFlag(String macName,int deleteFlag) {
        return macInfoDao.findByMacNameAndDeleteFlag(macName,deleteFlag);
    }


    public ResultInfo findBySchoolID(int id) {

        List<MacInfo> macinfolist = macInfoDao.findBySchoolIDAndDeleteFlag(id, 0);

        ResultInfo resultInfo = new ResultInfo(true);
        resultInfo.setCode(200);
        resultInfo.setMessage("查找成功");
        resultInfo.setSuccess(true);
        resultInfo.addData("macinfo",macinfolist);
        return resultInfo;
    }

    public List<MacInfo> findlistBySchoolID(int id) {
        List<MacInfo> macinfolist = macInfoDao.findBySchoolIDAndDeleteFlag(id, 0);
        return macinfolist;
    }

    public ResultInfo findallMacDevice() {

        List<MacInfo> macinfolist = macInfoDao.findByDeleteFlag(0);

        ResultInfo resultInfo = new ResultInfo(true);
        resultInfo.setMessage("200");
        resultInfo.setSuccess(true);
        resultInfo.addData("macinfolist",macinfolist);
        return resultInfo;
    }


    public MacInfo save(MacInfo macInfo) {
        return macInfoDao.save(macInfo);
    }

    public int delete(int id) {
        return macInfoDao.deleteByMacID(id);
    }

    public boolean deleteBySchool(int id) {
        List<MacInfo> macInfoList = macInfoDao.findBySchoolIDAndDeleteFlag(id,0);
        if(macInfoList!=null && macInfoList.size()>0){
            for(MacInfo macInfo:macInfoList){
                macInfo.setDeleteFlag(1);
                macInfo.setUpdateTime(new Date());
                macInfoDao.save(macInfo);
            }
        }
        return true;
    }
}
