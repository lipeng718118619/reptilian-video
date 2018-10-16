package com.lp.reptilianvideo.service.impl;

import com.lp.reptilianvideo.config.RabbitMQConfig;
import com.lp.reptilianvideo.dao.ReptilianRecordDao;
import com.lp.reptilianvideo.dao.ReptilianVideoDao;
import com.lp.reptilianvideo.entity.EnumState;
import com.lp.reptilianvideo.entity.ReptilianRecordEntity;
import com.lp.reptilianvideo.entity.VideoEntity;
import com.lp.reptilianvideo.service.DownLoadVideoService;
import com.lp.reptilianvideo.service.ExtractPageUrlService;
import com.lp.reptilianvideo.service.ReptilianVideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class ReptilianVideoServiceImpl implements ReptilianVideoService
{
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ReptilianVideoDao reptilianVideoDao;

    @Autowired
    private ReptilianRecordDao reptilianRecordDao;

    @Autowired
    private ExtractPageUrlService extractPageUrlService;

    @Autowired
    private DownLoadVideoService downLoadVideoService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitMQConfig mqConfig;

    private final int threadPoolSize = 5;

    private ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);

    @Override
    public String reptilianVideo(String url)
    {

        if(reptilianRecordDao.existsByRootUrl(url))
        {

        }
        else
        {
            ReptilianRecordEntity entity = new ReptilianRecordEntity();
            entity.setRootUrl(url);
            reptilianRecordDao.save(entity);
        }

        List<VideoEntity> videoEntities = extractPageUrlService.analysisVideoUrl(url);
        if(CollectionUtils.isEmpty(videoEntities))
        {
            return null;
        }
        reptilianVideoDao.saveAll(videoEntities);

        for(VideoEntity videoEntity : videoEntities )
        {
            rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC,videoEntity);
        }

        return null;
    }

    @PostConstruct
    public void init()
    {
        for(int i=0;i<threadPoolSize;i++)
        {
            executorService.submit(new DownLoadTask());
        }
    }

    @PreDestroy
    public void destroy()
    {
        executorService.shutdown();
    }

    private class DownLoadTask implements Runnable
    {
        private VideoEntity videoEntity;

        @Override
        public void run()
        {

        }
    }
}
