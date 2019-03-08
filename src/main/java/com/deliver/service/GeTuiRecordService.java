package com.deliver.service;

import com.deliver.dao.AccessRecordDao;
import com.deliver.dao.GeTuiRecordDao;
import com.deliver.entity.AccessRecord;
import com.deliver.entity.GeTueRecord;
import com.deliver.util.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by pdl on 2018/9/13.
 */

@Service
public class GeTuiRecordService {
    @Autowired
    private GeTuiRecordDao geTuiRecordDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public GeTueRecord findByAccessID(int id) {
        return geTuiRecordDao.findByGetuiID(id);
    }

    public GeTueRecord save(GeTueRecord geTueRecord) {
        return geTuiRecordDao.save(geTueRecord);
    }

    public int delete(int id) {
        return geTuiRecordDao.deleteByGetuiID(id);
    }

    public List<Map<String,Object>> geTuiRecordQuery(int humanid,String beginTime,String endTime){
        String sql = "select * from tc_getui_record c where c.parentid= "+humanid;

        //int start = (pageCurrent-1)*pageSize;
        if(beginTime!=null && beginTime!="" && !beginTime.equals("")){
            sql+=" and c.getui_time>=DATE_FORMAT('"+beginTime+"','%Y-%m-%d') ";
        }
        if(endTime!=null && endTime!="" && !endTime.equals("")){
            sql+=" and c.getui_time<=DATE_FORMAT('"+endTime+"','%Y-%m-%d') ";
        }
        sql += "  ORDER BY getui_time DESC  limit 80";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String,Object>> geTuiRecordQueryByTime(int humanid,String beginTime,String endTime){
        String sql = "select * from tc_getui_record c where c.parentid= "+humanid;

        //int start = (pageCurrent-1)*pageSize;
        if(beginTime!=null && beginTime!="" && !beginTime.equals("")){
            sql+=" and c.getui_time>=DATE_FORMAT('"+beginTime+"','%Y-%m-%d') ";
        }
        if(endTime!=null && endTime!="" && !endTime.equals("")){
            sql+=" and c.getui_time<=DATE_FORMAT('"+endTime+"','%Y-%m-%d') ";
        }
        sql += "  ORDER BY getui_time DESC";
        return jdbcTemplate.queryForList(sql);
    }

    public long geTuiRecordCount(int humanid,String beginTime,String endTime){
        String sql = "select count(*) as num from tc_getui_record c where c.parentid= "+humanid;

        if(beginTime!=null && beginTime!="" && !beginTime.equals("")){
            sql+=" and c.getui_time>=DATE_FORMAT('"+beginTime+"','%Y-%m-%d') ";
        }
        if(endTime!=null && endTime!="" && !endTime.equals("")){
            sql+=" and c.getui_time<=DATE_FORMAT('"+endTime+"','%Y-%m-%d') ";
        }
        sql += "  ORDER BY getui_time DESC ";
        return (Long) jdbcTemplate.queryForList(sql).get(0).get("num");
    }

    public ResultInfo getTuiRecordQueryByMonth(int humanid,String beginTime,String endTime){
        ResultInfo resultInfo = new ResultInfo(false);
        String sql = "select DATE_FORMAT(t.getui_time,'%Y-%m-%d') as getuiTime " +
                " from tc_getui_record c where c.parentid= "+humanid;

        //int start = (pageCurrent-1)*pageSize;
        if(beginTime!=null && beginTime!="" && !beginTime.equals("")){
            sql+=" and c.getui_time>=DATE_FORMAT('"+beginTime+"','%Y-%m-%d %h:%i:%s') ";
        }
        if(endTime!=null && endTime!="" && !endTime.equals("")){
            sql+=" and c.getui_time<=DATE_FORMAT('"+endTime+"','%Y-%m-%d %h:%i:%s') ";
        }
        sql += " GROUP BY DATE_FORMAT(t.getui_time,'%Y-%m-%d')";
        List<Map<String, Object>> timeList = jdbcTemplate.queryForList(sql);
        resultInfo.addData("timeList",timeList);
        resultInfo.setSuccess(true);
        resultInfo.setCode(200);
        resultInfo.setMessage("查询成功！");
        return resultInfo;
    }

    public ResultInfo geTuiRecordQueryByDay(int humanid, String dayTime){
        ResultInfo resultInfo = new ResultInfo(false);
        String ssql = "select * from tc_getui_record t where t.parentid="+humanid;
        if (dayTime != null && !dayTime.equals("")) {
            ssql += " and date_format(getui_time,'%Y-%m-%d')= date_format('"+dayTime+"','%Y-%m-%d') ";
        }

        ssql += " ORDER BY a.getui_time DESC";
        List<Map<String, Object>> getuirecordlist = jdbcTemplate.queryForList(ssql);
        resultInfo.addData("getuirecordlist",getuirecordlist);
        resultInfo.setSuccess(true);
        resultInfo.setCode(200);
        resultInfo.setMessage("查询成功！");
        return resultInfo;

    }
}
