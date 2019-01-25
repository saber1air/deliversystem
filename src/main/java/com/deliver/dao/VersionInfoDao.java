package com.deliver.dao;

import com.deliver.entity.VersionInfo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by pdl on 2018/9/13.
 */
@CacheConfig(cacheNames = "versionInfo")
public interface VersionInfoDao extends JpaRepository<VersionInfo, Integer> {
    //@Cacheable()  //查询缓存
    @Cacheable(value="versions")
    VersionInfo findByVersionID(int id);

    /**
     * 新增或修改时
     */
    @Override
    @CacheEvict(value="versions", allEntries=true)
    VersionInfo save(VersionInfo versionInfo);

    @Transactional   //事务管理
    @Modifying
    int deleteByVersionID(int id);
    @Cacheable(value="versions")
    List<VersionInfo> findByVersionNumAndAppID(String versionNum, String appID);

    @Cacheable(value="versions")
    List<VersionInfo> findByUpdateFlagAndDeleteFlag(int updateFlag, int deleteFlag);

    @Cacheable(value="versions")
    List<VersionInfo> findByUpdateFlagAndOsAndDeleteFlag(int updateFlag, String os, int deleteFlag);

    @Cacheable(value="versions")
    List<VersionInfo> findByVersionNumAndOs(String versionNum, String os);

}

