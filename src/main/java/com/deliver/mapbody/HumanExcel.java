package com.deliver.mapbody;

/**
 * Created by pdl on 2018/11/19.
 */
public class HumanExcel {
    String humanName;
    int schoolID;
    int gradeNum;
    int classNum;
    String parentName;
    String tel;
    String rel;

    @Override
    public String toString() {
        return "HumanExcel{" +
                "humanName='" + humanName + '\'' +
                ", schoolID=" + schoolID +
                ", gradeNum=" + gradeNum +
                ", classNum=" + classNum +
                ", parentName='" + parentName + '\'' +
                ", tel='" + tel + '\'' +
                ", rel='" + rel + '\'' +
                '}';
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getHumanName() {
        return humanName;
    }

    public void setHumanName(String humanName) {
        this.humanName = humanName;
    }


    public int getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(int schoolID) {
        this.schoolID = schoolID;
    }

    public int getGradeNum() {
        return gradeNum;
    }

    public void setGradeNum(int gradeNum) {
        this.gradeNum = gradeNum;
    }

    public int getClassNum() {
        return classNum;
    }

    public void setClassNum(int classNum) {
        this.classNum = classNum;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

}
