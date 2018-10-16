package com.lp.reptilianvideo.service;

import com.lp.reptilianvideo.entity.VideoEntity;

import java.util.List;

public interface ExtractPageUrlService
{
    List<VideoEntity> analysisVideoUrl(String indexUrl);
}
