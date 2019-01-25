package com.deliver.dao;

import com.deliver.entity.AccessRecord;
import com.deliver.entity.ClassInfo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.util.Date;

/**
 * Created by pdl on 2018/9/13.
 */
@CacheConfig(cacheNames = "classInfo")
public interface ClassInfoDao extends JpaRepository<ClassInfo, Integer> {
    //@Cacheable()  //查询缓存
    @Cacheable(value="classinfos")
    ClassInfo findByClassIDAndDeleteFlag(int id,int deleteFlag);
    @Cacheable(value="classinfos")
    List<ClassInfo> findBySchoolIDAndUpdateTimeAfter(int id, Date date);
    @Cacheable(value="classinfos")
    List<ClassInfo> findBySchoolID(int id);
    @Cacheable(value="classinfos")
    List<ClassInfo> findByGradeID(int gradeID);
    @Cacheable(value="classinfos")
    List<ClassInfo> findByGradeIDAndDeleteFlag(int gradeID ,int deleteFlag);
    @Cacheable(value="classinfos")
    List<ClassInfo> findBySchoolIDAndGradeIDAndClassNumAndDeleteFlag(int schoolID,int gradeID,int classNum,int deleteFlag);
    /**
     * 新增或修改时
     */
    @Override
    @CacheEvict(value="classinfos", allEntries=true)
    ClassInfo save(ClassInfo classInfo);

    @Transactional   //事务管理
    @Modifying
    int deleteByClassID(int id);

}

