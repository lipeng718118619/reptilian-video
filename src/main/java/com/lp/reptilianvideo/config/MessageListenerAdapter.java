package com.lp.reptilianvideo.config;

import com.lp.reptilianvideo.dao.ReptilianVideoDao;
import com.lp.reptilianvideo.entity.EnumState;
import com.lp.reptilianvideo.entity.VideoEntity;
import com.lp.reptilianvideo.service.DownLoadVideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageListenerAdapter implements MessageListener
{
    @Autowired
    private DownLoadVideoService downLoadVideoService;

    @Autowired
    private ReptilianVideoDao reptilianVideoDao;


    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onMessage(Message message)
    {
        VideoEntity videoEntity= new VideoEntity();
        try
        {
            downLoadVideoService.downLoadFile(videoEntity.getUrl(),null);
            videoEntity.setState(EnumState.SUCCESS.toString());
            reptilianVideoDao.saveAndFlush(videoEntity);
        }
        catch(Exception e)
        {
            if(videoEntity != null)
            {
                logger.error("url : {} download failed!",videoEntity.getUrl(),e);
                videoEntity.setState(EnumState.FAILED.toString());
                reptilianVideoDao.saveAndFlush(videoEntity);
            }
        }
    }
}
