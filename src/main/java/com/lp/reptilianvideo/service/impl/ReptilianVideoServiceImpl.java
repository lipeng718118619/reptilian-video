package com.lp.reptilianvideo.service.impl;


import com.lp.reptilianvideo.dao.ReptilianRecordDao;

import com.lp.reptilianvideo.entity.ReptilianRecordEntity;
import com.lp.reptilianvideo.service.ExtractPageUrlService;
import com.lp.reptilianvideo.service.ReptilianVideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReptilianVideoServiceImpl implements ReptilianVideoService
{
    private final Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private ReptilianRecordDao reptilianRecordDao;

    @Autowired
    private ExtractPageUrlService extractPageUrlService;



    @Override
    public String reptilianVideo(String url)
    {

        while (url!= null)
        {

            if(reptilianRecordDao.existsByRootUrl(url))
            {
                url=extractPageUrlService.analysisNextIndexUrl(url);
                continue;
            }
            else
            {
                ReptilianRecordEntity entity = new ReptilianRecordEntity();
                entity.setRootUrl(url);
                reptilianRecordDao.save(entity);
            }

            extractPageUrlService.analysisVideoUrl(url);

            url=extractPageUrlService.analysisNextIndexUrl(url);
        }

        return null;
    }

}
