package com.deliver.dao;

import com.deliver.entity.HumanInfo;
import com.deliver.entity.HumanType;
import com.deliver.entity.MacInfo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pdl on 2018/9/13.
 */
@CacheConfig(cacheNames = "macInfo")
public interface MacInfoDao extends JpaRepository<MacInfo, Integer> {
    //@Cacheable()  //查询缓存
    @Cacheable(value="macs")
    MacInfo findByMacID(int id);

//    @Cacheable()  //查询缓存
    @Cacheable(value="macs")
    MacInfo findByMacNameAndDeleteFlag(String macName,int deleteFlag);

    //@Cacheable()  //查询缓存
    @Cacheable(value="macs")
    List<MacInfo> findBySchoolIDAndDeleteFlag(int id, int deleteflag) ;

    //@Cacheable()  //查询缓存
    @Cacheable(value="macs")
    List<MacInfo> findByDeleteFlag(int deleteflag) ;

    /**
     * 新增或修改时
     */
    @Override
    @CacheEvict(value="macs", allEntries=true)
    public MacInfo save(MacInfo macInfo);

    @Transactional   //事务管理
    @Modifying
    int deleteByMacID(int id);

    @Transactional   //事务管理
    @Modifying
    int deleteByschoolID(int schoolID);
}

