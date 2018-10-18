package com.lp.reptilianvideo.service.impl;


import com.lp.reptilianvideo.dao.ReptilianRecordDao;

import com.lp.reptilianvideo.entity.ReptilianRecordEntity;
import com.lp.reptilianvideo.service.ExtractPageUrlService;
import com.lp.reptilianvideo.service.ReptilianVideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class ReptilianVideoServiceImpl implements ReptilianVideoService
{
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private BlockingQueue<String> urlBlockingQueue = new LinkedBlockingQueue<>(10);

    @Autowired
    private ReptilianRecordDao reptilianRecordDao;

    @Autowired
    private ExtractPageUrlService extractPageUrlService;

    private ExecutorService executorService;

    @Override
    public String reptilianVideo(String url)
    {

        while(url != null)
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

            try
            {
                urlBlockingQueue.put(url);
            }
            catch(Exception e)
            {
                logger.error("add queue error!", e);
            }

            url = extractPageUrlService.analysisNextIndexUrl(url);
        }

        return null;
    }

    @PostConstruct
    public void init()
    {
        executorService = Executors.newFixedThreadPool(3);

        for(int i = 0; i < 3; i++)
        {
            executorService.submit(new AnalysisUrlTask());
        }

    }

    @PreDestroy
    public void destroy()
    {
        if(executorService != null)
        {
            executorService.shutdown();
        }
    }


    private class AnalysisUrlTask implements Runnable
    {
        private String url;

        @Override
        public void run()
        {
            while(true)
            {
                try
                {
                    url = urlBlockingQueue.take();

                    extractPageUrlService.analysisVideoUrl(url);
                }
                catch(InterruptedException e)
                {
                    logger.error("analysis task interrupted  error!", e);
                }
                catch(Exception e)
                {
                    logger.error("analysis task exception error!", e);
                }
            }
        }
    }

}
