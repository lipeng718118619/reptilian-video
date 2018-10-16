package com.lp.reptilianvideo.dao;

import com.lp.reptilianvideo.entity.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReptilianVideoDao extends JpaRepository<VideoEntity,Integer>
{
   VideoEntity queryByUrl(String url);
}
