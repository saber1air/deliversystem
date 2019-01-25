package com.deliver.dao;

import com.deliver.entity.Notice;
import com.deliver.entity.NoticeRecord;
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
@CacheConfig(cacheNames = "noticeRecord")
public interface NoticeRecordDao extends JpaRepository<NoticeRecord, Integer> {
    //@Cacheable()  //查询缓存
    @Cacheable(value="noticeRecords")
    List<NoticeRecord> findBySchoolIDAndDeleteFlag(int id, int deleteFlag);

    @Cacheable(value="noticeRecords")
    List<NoticeRecord> findByHumanIDAndDeleteFlagOrderByCreateTimeDesc(int id, int deleteFlag);

    @Cacheable(value="noticeRecords")
    NoticeRecord findByNRecordIDAndDeleteFlag(int id, int deleteFlag);

    @Cacheable(value="noticeRecords")
    List<NoticeRecord> findByDeleteFlag(int deleteFlag);

    @Cacheable(value="noticeRecords")
    List<NoticeRecord> findBySchoolIDAndUpdateTimeAfter(int id, Date date);

    /**
     * 新增或修改时
     */
    @Override
    @CacheEvict(value="noticeRecords", allEntries=true)
    NoticeRecord save(NoticeRecord noticeRecord);

    @Transactional   //事务管理
    @Modifying
    int deleteByNRecordID(int id);

}

