package com.deliver.dao;

import com.deliver.entity.HumanInfo;
import com.deliver.entity.HumanMedia;
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
@CacheConfig(cacheNames = "humanMedia")
public interface HumanMediaDao extends JpaRepository<HumanMedia, Integer> {
    //@Cacheable()  //查询缓存
    @Cacheable(value="medias")
    HumanMedia findByMediaIDAndDeleteFlag(int id,int deleteFlag);

    @Cacheable(value="medias")
    HumanMedia findByHumanIDAndUpdateTimeAfterAndDeleteFlag(int id, Date date, int deleteFlag);
    @Cacheable(value="medias")
    List<HumanMedia> findBySchoolIDAndUpdateTimeAfterAndCheckFlag(int id, Date date,int checkFlag);
    @Cacheable(value="medias")
    List<HumanMedia> findBySchoolID(int id);
    @Cacheable(value="medias")
    List<HumanMedia> findBySchoolIDAndCheckFlag(int id ,int checkFlag);
    @Cacheable(value="medias")
    List<HumanMedia> findByHumanIDAndDeleteFlag(int id ,int deleteFlag);
    @Cacheable(value="medias")
    List<HumanMedia> findByHumanIDAndDeleteFlagOrderByUpdateTimeDesc(int id ,int deleteFlag);


    @Cacheable(value="medias")
    List<HumanMedia> findByHumanIDAndDeleteFlagAndCheckFlag(int id ,int deleteFlag,int checkFlag);
    @Cacheable(value="medias")
    List<HumanMedia> findByHumanIDAndDeleteFlagAndCheckFlagAndShowFlagOrderByCreateTimeDesc(int id ,int deleteFlag,int checkFlag,int showFlag);
    @Cacheable(value="medias")
    List<HumanMedia> findByHumanIDAndDeleteFlagAndCheckFlagAndShowFlag(int id ,int deleteFlag,int checkFlag,int showFlag);

    /**
     * 新增或修改时
     */
    @Override
    @CacheEvict(value="medias", allEntries=true)
    HumanMedia save(HumanMedia humanMedia);

    @Transactional   //事务管理
    @Modifying
    int deleteByMediaID(int id);

}

