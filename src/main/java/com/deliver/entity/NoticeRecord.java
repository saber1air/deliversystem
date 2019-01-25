package com.deliver.entity;

import com.deliver.model.BaseObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by pdl on 2018/9/13.
 */

@Entity
@Table(name="tc_notice_record")
public class NoticeRecord extends BaseObject implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer nRecordID;

    @Column(length = 1000,nullable=true)
    private String noticeContent;

    @Column(length = 255,nullable=true)
    private String accessoryPath;

    @Column(nullable=true)
    private Integer schoolID;

    @Column(nullable=true)
    private Integer humanID;

    @Column(nullable=true)
    private Date createTime;
    @Column(nullable=true)
    private Date updateTime;
    @Column(columnDefinition="int default 0",nullable=true)
    private Integer deleteFlag=0;

    @Column(columnDefinition="int default 0",nullable=true)
    private Integer noticeType=0;
    @Column(nullable=true)
    private String remarks;

    public Integer getnRecordID() {
        return nRecordID;
    }

    public void setnRecordID(Integer nRecordID) {
        this.nRecordID = nRecordID;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public String getAccessoryPath() {
        return accessoryPath;
    }

    public void setAccessoryPath(String accessoryPath) {
        this.accessoryPath = accessoryPath;
    }

    public Integer getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(Integer schoolID) {
        this.schoolID = schoolID;
    }

    public Integer getHumanID() {
        return humanID;
    }

    public void setHumanID(Integer humanID) {
        this.humanID = humanID;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Integer getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(Integer noticeType) {
        this.noticeType = noticeType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "NoticeRecord{" +
                "nRecordID=" + nRecordID +
                ", noticeContent='" + noticeContent + '\'' +
                ", accessoryPath='" + accessoryPath + '\'' +
                ", schoolID=" + schoolID +
                ", humanID=" + humanID +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", deleteFlag=" + deleteFlag +
                ", noticeType=" + noticeType +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
