package com.deliver.dao;

import com.deliver.entity.HumanRegisterRecord;
import com.deliver.entity.HumanType;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by pdl on 2018/9/13.
 */
@CacheConfig(cacheNames = "humanType")
public interface HumanTypeDao extends JpaRepository<HumanType, Integer> {
    @Cacheable(value="types")
    HumanType findById(int id);

    @Cacheable(value="types")
    List<HumanType> findByDeleteFlag(int delete);

    /**
     * 新增或修改时
     */
    @Override
    @CacheEvict(value="types", allEntries=true)
    HumanType save(HumanType humanType);

    @Transactional   //事务管理
    @Modifying
    int deleteById(int id);

}

