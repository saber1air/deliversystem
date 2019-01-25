package com.deliver.dao;

import com.deliver.entity.Notice;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by pdl on 2018/12/18.
 */
@CacheConfig(cacheNames = "notice")
public interface NoticeDao extends JpaRepository<Notice, Integer> {
    //@Cacheable()  //查询缓存
    @Cacheable(value="noticess")
    List<Notice> findBySchoolIDAndDeleteFlag(int id, int deleteFlag);

    @Cacheable(value="noticess")
    List<Notice> findBySchoolIDAndDeleteFlagOrderByCreateTimeDesc(int schoolID,int deleteFlag);

    /*@Cacheable(value="noticess")
    List<Notice> findByNoticeTypeAndDeleteFlagOrderByCreateTimeDesc(int id, int deleteFlag);*/

    @Cacheable(value="noticess")
    List<Notice> findBySchoolIDAndNoticeTypeAndDeleteFlagOrderByCreateTimeDesc(int id,int noticeType, int deleteFlag);

    @Cacheable(value="noticess")
    Notice findByNoticeIDAndDeleteFlag(int id, int deleteFlag);

    @Cacheable(value="noticess")
    List<Notice> findByDeleteFlag(int deleteFlag);

    @Cacheable(value="noticess")
    List<Notice> findBySchoolIDAndUpdateTimeAfter(int id, Date date);

    /**
     * 新增或修改时
     */
    @Override
    @CacheEvict(value="noticess", allEntries=true)
    Notice save(Notice notice);

    @Transactional   //事务管理
    @Modifying
    int deleteByNoticeID(int id);

}

