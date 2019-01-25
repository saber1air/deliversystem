package com.deliver.dao;

import com.deliver.entity.GradeInfo;
import com.deliver.entity.HumanInfo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by pdl on 2018/9/13.
 */
@CacheConfig(cacheNames = "humanInfo")
public interface HumanInfoDao extends JpaRepository<HumanInfo, Integer> {
    //@Cacheable()  //查询缓存
    //HumanInfo findByHumanID(int id);

    @Cacheable(value="humans")
    HumanInfo findByHumanIDAndDeleteFlag(int id,int deleteFlag);

    @Cacheable(value="humans")
    List<HumanInfo> findBySchoolIDAndUpdateTimeAfterAndCheckFlag(int id, Date date, int checkFlag);

    @Cacheable(value="humans")
    List<HumanInfo> findBySchoolIDAndDeleteFlagAndCheckFlag(int id, int deleteFlag,int checkFlag);
    @Cacheable(value="humans")
    List<HumanInfo> findBySchoolIDAndCheckFlag(int id, int checkFlag);


    //@Cacheable()  //查询缓存
    @Cacheable(value="humans")
    List<HumanInfo> findByHumanNameAndDeleteFlag(String humanName,int deleteFlag);

    @Cacheable(value="humans")
    List<HumanInfo> findByHumanIDAndDeleteFlagAndCheckFlag(int humanID,int deleteFlag,int checkFlag);

    //@Cacheable()  //查询缓存
    @Cacheable(value="humans")
    List<HumanInfo> findByTelAndDeleteFlag(String tel,int deleteFlag);

    //@Cacheable()  //查询缓存
    @Cacheable(value="humans")
    HumanInfo findByHumanNameAndTelAndDeleteFlag(String humanName,String tel,int deleteFlag);

    //HumanInfo findByHumanNameAndTelAndDeleteFlag(String humanName,String tel,int deleteFlag);

    @Cacheable(value="humans")
    HumanInfo findByHumanNameAndSchoolIDAndGradeIDAndClassIDAndDeleteFlag(String humanName,int schoolID,int gradeID,int classID,int deleteFlag);
    @Cacheable(value="humans")
    List<HumanInfo> findByTelAndPasswordAndDeleteFlag(String tel,String password,int deleteFlag);
    @Cacheable(value="humans")
    List<HumanInfo> findByClassIDAndCheckFlagAndHumanTypeIn(int classID,int checkFlag,String humanType);
    @Cacheable(value="humans")
    List<HumanInfo> findBySchoolIDAndCheckFlagAndHumanType(int schoolID,int checkFlag,int humanType);
    @Cacheable(value="humans")
    List<HumanInfo> findBySchoolIDAndCheckFlagAndHumanTypeAndDeleteFlag(int schoolID,int checkFlag,int humanType,int deleteFlag);
    @Cacheable(value="humans")
    List<HumanInfo> findByCheckFlagAndHumanTypeAndDeleteFlag(int checkFlag,int humanType,int deleteFlag);
    @Cacheable(value="humans")
    List<HumanInfo> findByCheckFlagAndHumanType(int checkFlag,int humanType);

    /**
     * 新增或修改时
     */
    @Override
    @CacheEvict(value="humans", allEntries=true)
    HumanInfo save(HumanInfo humanInfo);

    @Transactional   //事务管理
    @Modifying
    int deleteByHumanID(int id);

}

