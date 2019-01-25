package com.deliver.dao;

import com.deliver.entity.AtSchoolRecord;
import com.deliver.entity.DeliverRecord;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by pdl on 2018/9/13.
 */
@CacheConfig(cacheNames = "atSchoolRecord")
public interface AtSchoolRecordDao extends JpaRepository<AtSchoolRecord, Integer> {
    @Cacheable(value="atschools") //查询缓存
    AtSchoolRecord findById(int id);

    /**
     * 新增或修改时
     */
    @Override
    @CacheEvict(value="atschools", allEntries=true)
    AtSchoolRecord save(AtSchoolRecord atSchoolRecord);

    @Transactional   //事务管理
    @Modifying
    int deleteById(int id);

}

