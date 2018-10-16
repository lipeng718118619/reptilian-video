package com.lp.reptilianvideo.dao;

import com.lp.reptilianvideo.entity.ReptilianRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReptilianRecordDao extends JpaRepository<ReptilianRecordEntity,Integer>
{
    boolean existsByRootUrl(String rootUrl);
}
