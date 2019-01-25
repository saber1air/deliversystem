package com.deliver.dao;

import com.deliver.entity.ClassInfo;
import com.deliver.entity.GradeInfo;
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
@CacheConfig(cacheNames = "gradeInfo")
public interface GradeInfoDao extends JpaRepository<GradeInfo, Integer> {
    //@Cacheable()  //查询缓存
    @Cacheable(value="grades")
    GradeInfo findByGradeIDAndDeleteFlag(int id,int deleteFlag);
    @Cacheable(value="grades")
    List<GradeInfo> findBySchoolIDAndDeleteFlag(int schoolID, int delele);
    @Cacheable(value="grades")
    List<GradeInfo> findBySchoolID(int schoolID);

    @Cacheable(value="grades")
    List<GradeInfo> findBySchoolIDAndGradeNumAndDeleteFlag(int schoolID,int gradeNum, int delete);

    @Cacheable(value="grades")
    List<GradeInfo> findBySchoolIDAndUpdateTimeAfter(int schoolID, Date date);


    /**
     * 新增或修改时
     */
    @Override
    @CacheEvict(value="grades", allEntries=true)
    GradeInfo save(GradeInfo gradeInfo);

    @Transactional   //事务管理
    @Modifying
    int deleteByGradeID(int id);

}

