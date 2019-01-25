package com.deliver.dao;

import com.deliver.entity.ManagerType;
import com.deliver.entity.ParenStudentRel;
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
@CacheConfig(cacheNames = "parenStudentRel")
public interface PareStudentRelDao extends JpaRepository<ParenStudentRel, Integer> {
    //@Cacheable()  //查询缓存
    @Cacheable(value="rels")
    ParenStudentRel findByRelid(int id);

    @Cacheable(value="rels")
    List<ParenStudentRel> findByHumanIDAndUpdateTimeAfterAndDeleteFlag(int id, Date date, int deleteflag);
    @Cacheable(value="rels")
    List<ParenStudentRel> findBySchoolIDAndUpdateTimeAfterAndCheckFlag(int id, Date date,int checkFlag);
    @Cacheable(value="rels")
    List<ParenStudentRel> findBySchoolIDAndDeleteFlag(int id, int deleteflag);
    @Cacheable(value="rels")
    List<ParenStudentRel> findBySchoolIDAndCheckFlag(int id,int checkFlag);
    @Cacheable(value="rels")
    List<ParenStudentRel> findByHumanIDAndCheckFlagAndDeleteFlag(int id,int checkFlag,int deleteflag);
    @Cacheable(value="rels")
    List<ParenStudentRel> findByHumanIDAndDeleteFlag(int humanID,int deleteflag);
    @Cacheable(value="rels")
    List<ParenStudentRel> findByHomeIDAndDeleteFlag(int homeID,int deleteflag);

    /**
     * 新增或修改时
     */
    @Override
    @CacheEvict(value="rels", allEntries=true)
    ParenStudentRel save(ParenStudentRel parenStudentRel);

    @Transactional   //事务管理
    @Modifying
    int deleteByRelid(int id);

    //@Cacheable()  //查询缓存
    @Cacheable(value="rels")
    List<ParenStudentRel> findByHomeIDAndHumanID(int homeID, int humanID);
    @Cacheable(value="rels")
    List<ParenStudentRel> findByHomeIDAndHumanIDAndSchoolID(int homeID, int humanID,int schoolid);
    @Cacheable(value="rels")
    List<ParenStudentRel> findByHomeIDAndHumanIDAndDeleteFlag(int homeID, int humanID,int deleteFlag);

}

