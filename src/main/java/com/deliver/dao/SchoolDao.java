package com.deliver.dao;

import com.deliver.entity.RegionInfo;
import com.deliver.entity.School;
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
@CacheConfig(cacheNames = "school")
public interface SchoolDao extends JpaRepository<School, Integer> {
    //@Cacheable()  //查询缓存
    @Cacheable(value="schools")
    School findBySchoolIDAndDeleteFlag(int id,int deleteFlag);

    @Cacheable(value="schools")
    School findBySchoolID(int id);

    @Cacheable(value="schools")
    List<School> findByDeleteFlag(int deleteFlag);

    @Cacheable(value="schools")
    List<School> findBySchoolNameAndDeleteFlag(String schoolName,int deleteFlag);

    @Cacheable(value="schools")
    School findBySchoolIDAndUpdateTimeAfter(int id, Date date);


    /**
     * 新增或修改时
     */
    @Override
    //@CachePut(value="schools",key="'schoolid'")//往缓存中新增
    @CacheEvict(value="schools", allEntries=true)
    School save(School school);

    @Transactional   //事务管理
    @Modifying
    int deleteBySchoolID(int id);

}

