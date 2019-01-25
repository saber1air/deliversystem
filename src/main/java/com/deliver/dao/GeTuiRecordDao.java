package com.deliver.dao;

import com.deliver.entity.AccessRecord;
import com.deliver.entity.GeTueRecord;
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
@CacheConfig(cacheNames = "geTueRecord")
public interface GeTuiRecordDao extends JpaRepository<GeTueRecord, Integer> {
    //@Cacheable()  //查询缓存
    @Cacheable(value="getuis")
    GeTueRecord findByGetuiID(int id);

    /**
     * 新增或修改时
     */
    @Override
    @CacheEvict(value="getuis", allEntries=true)
    GeTueRecord save(GeTueRecord accessRecord);

    @Transactional   //事务管理
    @Modifying
    int deleteByGetuiID(int id);

}

