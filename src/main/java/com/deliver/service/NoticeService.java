package com.deliver.service;

import com.deliver.dao.AdvertDao;
import com.deliver.dao.HumanInfoDao;
import com.deliver.dao.NoticeDao;
import com.deliver.dao.NoticeRecordDao;
import com.deliver.entity.Advert;
import com.deliver.entity.HumanInfo;
import com.deliver.entity.Notice;
import com.deliver.entity.NoticeRecord;
import com.deliver.util.AppPushUtils;
import com.deliver.util.ResultInfo;
import com.gexin.rp.sdk.base.IPushResult;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by pdl on 2018/9/13.
 */

@Service
public class NoticeService {
    @Autowired
    private NoticeDao noticeDao;

    @Autowired
    private NoticeRecordDao noticeRecordDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private HumanInfoDao humanInfoDao;
    @Autowired
    private HumanInfoService humanInfoService;

    private static String appId = "jPX0kbnuCc8Og0gmSBnu3";
    private static String appKey = "Og6qT7rLNN9fixE8O7ppR4";
    private static String masterSecret = "x28EanpNuRA1fMjoDS1Tv9";

    public List<Notice> findBySchoolIDAndDeleteFlag(int id, int deleteFlag) {
        return noticeDao.findBySchoolIDAndDeleteFlag(id,deleteFlag);
    }

    public List<Notice> findBySchoolIDAndUpdateTimeAfter(int id,Date date){
        return noticeDao.findBySchoolIDAndUpdateTimeAfter(id, date);
    }


    public Notice save(Notice notice) {
        notice.setCreateTime(new Date());
        notice.setUpdateTime(new Date());
        return noticeDao.save(notice);
    }

    public Notice findByNoticeIDAndDeleteFlag(int id, int deleteFlag){
        return noticeDao.findByNoticeIDAndDeleteFlag(id,deleteFlag);
    }



    public Notice update(Notice notice) {
        notice.setUpdateTime(new Date());
        return noticeDao.save(notice);
    }

    public Notice delAdvert(Notice notice) {
        notice.setUpdateTime(new Date());
        notice.setDeleteFlag(1);
        return noticeDao.save(notice);
    }

    public int delete(int id) {
        return noticeDao.deleteByNoticeID(id);
    }

    public List<Notice> getNoticeRecord(int humanID){
        /*List<NoticeRecord> noticeRecordList = noticeRecordDao.findByHumanIDAndDeleteFlagOrderByCreateTimeDesc(humanID,0);
        if(noticeRecordList!=null && noticeRecordList.size()>10){
            for(int i=10;i<noticeRecordList.size();i++){
                noticeRecordList.remove(noticeRecordList.get(i));
            }
        }
        return noticeRecordList;*/
        List<Notice> noticeRecordList = new ArrayList<Notice>();
        HumanInfo humanInfo = humanInfoDao.findByHumanIDAndDeleteFlag(humanID,0);
        if(humanInfo.getHumanType()==1){
            String sql = "select DISTINCT b.schoolid from tc_parent_student_rel t,tc_human_info b where \n" +
                    "t.homeid in (select a.homeid from tc_parent_student_rel a where a.homeid="+humanID+" or a.humanid="+humanID+") \n" +
                    "and t.humanid=b.humanid and b.human_type=0";
            List<Map<String,Object>> studentList = jdbcTemplate.queryForList(sql);
            if(studentList!=null && studentList.size()>0){
                for(Map map:studentList){
                    if((Integer) map.get("schoolid")!=null){
                        if((Integer) map.get("schoolid")!=null){
                            List<Notice> noticeList = noticeDao.findBySchoolIDAndNoticeTypeAndDeleteFlagOrderByCreateTimeDesc((Integer) map.get("schoolid"),1,0);
                            if(noticeList!=null && noticeList.size()>0){
                                for(Notice notice:noticeList){
                                    noticeRecordList.add(notice);
                                }
                            }
                        }

                    }

                }
                if(studentList.size()>1){//
                    Collections.sort(noticeRecordList, new Comparator<Notice>() {
                        public int compare(Notice o1, Notice o2) {
                            Date name1 = o1.getCreateTime() ;//name1是从你list里面拿出来的一个
                            Date name2 = o2.getCreateTime() ; //name1是从你list里面拿出来的第二个name
                            long aa = name1.getTime();
                            long bb = name2.getTime();
                            if(aa>bb) return -1;
                            else if(aa<bb) return 1;
                            else return 0;
                        }
                    });
                }

            }
        }else if(humanInfo.getHumanType()==2 || humanInfo.getHumanType()==3){
            if(humanInfo.getSchoolID()!=null){
                noticeRecordList = noticeDao.
                        findBySchoolIDAndNoticeTypeAndDeleteFlagOrderByCreateTimeDesc(humanInfo.getSchoolID(),humanInfo.getHumanType(),0);
            }

        }

        if(noticeRecordList!=null && noticeRecordList.size()>10){
            for(int i=10;i<noticeRecordList.size();i++){
                noticeRecordList.remove(noticeRecordList.get(i));
            }
        }
        return noticeRecordList;
    }

    public ResultInfo noticeGeTui(Notice notice,int getuiType) {//系统通知推送

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
        ResultInfo resultInfo = new ResultInfo(false);
        AppPushUtils pushUtils = new AppPushUtils(appId, appKey, masterSecret);

        if (notice.getNoticeType()==1) {

            String sql ="select DISTINCT d.humanid,d.human_type,d.human_name,d.tel,d.clientid " +
                    "from tc_parent_student_rel c,tc_human_info d where \n" +
                    "(d.humanid=c.homeid or d.humanid=c.humanid) and d.human_type=1 and c.homeid in \n" +
                    "(select b.homeid from tc_human_info a,tc_parent_student_rel b " +
                    "where a.human_type=0 and a.schoolid="+notice.getSchoolID()+" and a.humanid=b.humanid) ";
            List<Map<String,Object>> parentList = jdbcTemplate.queryForList(sql);

            Map<String, String> msg = new HashMap<String, String>();
            Map<String, Object> transText = new HashMap<String, Object>();

            transText.put("content",notice.getNoticeContent());
            transText.put("type",getuiType);//getuiType=1为接送推送，2为通知推送，3为版本更新推送
            JSONObject json = new JSONObject(transText);

            msg.put("title", "系统通知消息！");
            msg.put("titleText", notice.getNoticeContent());
            msg.put("transText", json.toString());

            if (parentList != null && parentList.size() > 0) {
                for (Map map : parentList) {
                    System.out.println("正在发送消息...");
                    if(map.get("receive_notice_getui_flag")!=null && (Integer)map.get("receive_notice_getui_flag")==1){
                        if (map.get("clientid") != null) {
                            IPushResult ret = pushUtils.pushMsgToSingle(map.get("clientid").toString(), msg);
                        }
                    }

                    NoticeRecord noticeRecord = new NoticeRecord();
                    noticeRecord.setAccessoryPath(notice.getAccessoryPath());
                    noticeRecord.setCreateTime(new Date());
                    noticeRecord.setDeleteFlag(0);
                    noticeRecord.setHumanID((Integer) map.get("humanid"));
                    noticeRecord.setNoticeContent(notice.getNoticeContent());
                    noticeRecord.setNoticeType(notice.getNoticeType());
                    noticeRecord.setRemarks(notice.getRemarks());
                    noticeRecord.setSchoolID(notice.getSchoolID());
                    noticeRecord.setUpdateTime(new Date());
                    noticeRecordDao.save(noticeRecord);

                    HumanInfo humanInfo = humanInfoDao.findByHumanIDAndDeleteFlag((Integer)map.get("humanid"),0);
                    if(humanInfo!=null){
                        humanInfo.setNoticeNotReadNum(humanInfo.getNoticeNotReadNum()+1);
                        humanInfoService.editHuman(humanInfo);
                    }

                }
            }

        }else if(notice.getNoticeType()==2){
            List<HumanInfo> teacherlist = humanInfoDao.findBySchoolIDAndCheckFlagAndHumanTypeAndDeleteFlag(notice.getSchoolID(),1,2,0);

            Map<String, String> msg = new HashMap<String, String>();
            Map<String, Object> transText = new HashMap<String, Object>();

            transText.put("content",notice.getNoticeContent());
            transText.put("type",getuiType);//getuiType=1为接送推送，2为通知推送，3为版本更新推送
            JSONObject json = new JSONObject(transText);

            msg.put("title", "系统通知消息！");
            msg.put("titleText", notice.getNoticeContent());
            msg.put("transText", json.toString());

            if(teacherlist!=null && teacherlist.size()>0){
                for (HumanInfo teacher : teacherlist) {
                    if(teacher.getReceiveNoticeGetuiFlag()==1){
                        System.out.println("正在发送消息...");
                        if (teacher.getClientID() != null) {
                            IPushResult ret = pushUtils.pushMsgToSingle(teacher.getClientID(), msg);

                        }
                    }

                    NoticeRecord noticeRecord = new NoticeRecord();
                    noticeRecord.setAccessoryPath(notice.getAccessoryPath());
                    noticeRecord.setCreateTime(new Date());
                    noticeRecord.setDeleteFlag(0);
                    noticeRecord.setHumanID(teacher.getHumanID());
                    noticeRecord.setNoticeContent(notice.getNoticeContent());
                    noticeRecord.setNoticeType(notice.getNoticeType());
                    noticeRecord.setRemarks(notice.getRemarks());
                    noticeRecord.setSchoolID(notice.getSchoolID());
                    noticeRecord.setUpdateTime(new Date());
                    noticeRecordDao.save(noticeRecord);

                    HumanInfo humanInfo = humanInfoDao.findByHumanIDAndDeleteFlag(teacher.getHumanID(),0);
                    if(humanInfo!=null){
                        humanInfo.setNoticeNotReadNum(humanInfo.getNoticeNotReadNum()+1);
                        humanInfoService.editHuman(humanInfo);
                    }

                }
            }
        }else if(notice.getNoticeType()==3){
           List<HumanInfo> managerlist = humanInfoDao.findBySchoolIDAndCheckFlagAndHumanTypeAndDeleteFlag(notice.getSchoolID(),1,3,0);

            Map<String, String> msg = new HashMap<String, String>();
            Map<String, Object> transText = new HashMap<String, Object>();

            transText.put("content",notice.getNoticeContent());
            transText.put("type",getuiType);//getuiType=1为接送推送，2为通知推送，3为版本更新推送
            JSONObject json = new JSONObject(transText);

            msg.put("title", "系统通知消息！");
            msg.put("titleText", notice.getNoticeContent());
            msg.put("transText", json.toString());

            if(managerlist!=null && managerlist.size()>0){
                for (HumanInfo manager : managerlist) {
                    if(manager.getReceiveNoticeGetuiFlag()==1){
                        System.out.println("正在发送消息...");
                        if (manager.getClientID() != null) {
                            IPushResult ret = pushUtils.pushMsgToSingle(manager.getClientID(), msg);


                        }
                    }

                    NoticeRecord noticeRecord = new NoticeRecord();
                    noticeRecord.setAccessoryPath(notice.getAccessoryPath());
                    noticeRecord.setCreateTime(new Date());
                    noticeRecord.setDeleteFlag(0);
                    noticeRecord.setHumanID(manager.getHumanID());
                    noticeRecord.setNoticeContent(notice.getNoticeContent());
                    noticeRecord.setNoticeType(notice.getNoticeType());
                    noticeRecord.setRemarks(notice.getRemarks());
                    noticeRecord.setSchoolID(notice.getSchoolID());
                    noticeRecord.setUpdateTime(new Date());
                    noticeRecordDao.save(noticeRecord);

                    HumanInfo humanInfo = humanInfoDao.findByHumanIDAndDeleteFlag(manager.getHumanID(),0);
                    if(humanInfo!=null){
                        humanInfo.setNoticeNotReadNum(humanInfo.getNoticeNotReadNum()+1);
                        humanInfoService.editHuman(humanInfo);
                    }

                }
            }
        }
        resultInfo.setCode(200);
        resultInfo.setSuccess(true);
        resultInfo.setMessage("系统通知发送成功！");

        return resultInfo;
    }

    public List<Map<String,Object>> findNoticeRecord(Integer schoolID,Integer noticeType,String beginTime,String endTime){
        String sql = "select * from tc_notice t where t.delete_flag=0 ";
        if(schoolID!=null && schoolID!=-1){
            sql+=" and t.schoolid="+schoolID;
        }

        if(noticeType!=null && noticeType!=-1){
            sql+=" and t.notice_type="+noticeType;
        }

        if(beginTime!=null && !beginTime.equals("") && beginTime!=null){
            sql+="  and t.create_time>DATE_FORMAT('"+beginTime+"','%Y-%m-%d')";
        }

        if(endTime!=null && !endTime.equals("") && endTime!=null){
            sql += " and t.create_time<=DATE_SUB(DATE_FORMAT('" + endTime + "','%Y-%m-%d'),INTERVAL -1 DAY) ";
        }

        sql +=" ORDER BY t.create_time DESC ";

        List<Map<String,Object>> noticeList = jdbcTemplate.queryForList(sql);
        return noticeList;

    }


}
