package com.deliver.dao;

import com.deliver.entity.Advert;
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
@CacheConfig(cacheNames = "advert")
public interface AdvertDao extends JpaRepository<Advert, Integer> {
    //@Cacheable()  //查询缓存
    @Cacheable(value="adverts")
    List<Advert> findBySchoolIDAndDeleteFlag(int id, int deleteFlag);

    @Cacheable(value="adverts")
    Advert findByAdvertIDAndDeleteFlag(int id,int deleteFlag);

    @Cacheable(value="adverts")
    List<Advert> findByDeleteFlag(int deleteFlag);

    @Cacheable(value="adverts")
    List<Advert> findBySchoolIDAndUpdateTimeAfter(int id, Date date);

    /**
     * 新增或修改时
     */
    @Override
    @CacheEvict(value="adverts", allEntries=true)
    Advert save(Advert advert);

    @Transactional   //事务管理
    @Modifying
    int deleteByAdvertID(int id);

}

