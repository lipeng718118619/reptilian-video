package com.lp.reptilianvideo.dao;

import com.lp.reptilianvideo.entity.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReptilianVideoDao extends JpaRepository<VideoEntity,Integer>
{
   List<VideoEntity> queryByUrl(String url);
}
