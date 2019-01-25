package com.deliver.entity;

import com.deliver.model.BaseObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by pdl on 2018/9/13.
 */

@Entity
@Table(name="tc_human_media")
public class HumanMedia extends BaseObject implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer mediaID;

    @Column(nullable=true)
    private Integer humanID;

    @Column(length = 3000,nullable=true)
    private String mediaPath; //图片路径

    @Column(length = 3000,nullable=true)
    private String feature;
    @Column(nullable=true)
    private Date createTime;
    @Column(nullable=true)
    private Date updateTime;
    @Column(columnDefinition="int default 0",nullable=true)
    private Integer deleteFlag=0;
    @Column(nullable=true)
    private String remarks;

    @Column(columnDefinition="int default 0",nullable=true)
    private Integer checkFlag=0;

    @Column(nullable=true)
    private Integer schoolID;

    @Column(columnDefinition="int default 1",nullable=true)
    private Integer showFlag=0;//1为头像显示

    @Column(columnDefinition="int default 0",nullable=true)
    private Integer phoneFlag=0;//1为来自手机端或批量录入的图片，0为来自本地端录入的图片。

    private static final long serialVersionUID = -9057888691340520012L;

    public Integer getPhoneFlag() {
        return phoneFlag;
    }

    public void setPhoneFlag(Integer phoneFlag) {
        this.phoneFlag = phoneFlag;
    }

    public Integer getMediaID() {
        return mediaID;
    }

    public void setMediaID(Integer mediaID) {
        this.mediaID = mediaID;
    }

    public Integer getHumanID() {
        return humanID;
    }

    public void setHumanID(Integer humanID) {
        this.humanID = humanID;
    }

    public String getMediaPath() {
        return mediaPath;
    }

    public void setMediaPath(String mediaPath) {
        this.mediaPath = mediaPath;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(Integer schoolID) {
        this.schoolID = schoolID;
    }

    public Integer getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(Integer checkFlag) {
        this.checkFlag = checkFlag;
    }

    public Integer getShowFlag() {
        return showFlag;
    }

    public void setShowFlag(Integer showFlag) {
        this.showFlag = showFlag;
    }

    @Override
    public String toString() {
        return "HumanMedia{" +
                "mediaID=" + mediaID +
                ", humanID=" + humanID +
                ", mediaPath='" + mediaPath + '\'' +
                ", feature='" + feature + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", deleteFlag=" + deleteFlag +
                ", remarks='" + remarks + '\'' +
                ", checkFlag=" + checkFlag +
                ", schoolID=" + schoolID +
                ", showFlag=" + showFlag +
                ", phoneFlag=" + phoneFlag +
                '}';
    }
}
