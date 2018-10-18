package com.lp.reptilianvideo.config;

import com.alibaba.fastjson.JSONObject;
import com.lp.reptilianvideo.dao.ReptilianVideoDao;
import com.lp.reptilianvideo.entity.EnumState;
import com.lp.reptilianvideo.entity.VideoEntity;
import com.lp.reptilianvideo.service.DownLoadVideoService;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageListenerAdapter implements ChannelAwareMessageListener
{
    @Autowired
    private DownLoadVideoService downLoadVideoService;

    @Autowired
    private ReptilianVideoDao reptilianVideoDao;


    private final String diskPath="J:\\data\\";


    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onMessage(Message message, Channel channel)
    {
        VideoEntity videoEntity= null;
        try
        {

            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

            String data = new String(message.getBody(),"UTF-8");

            videoEntity = JSONObject.parseObject(data,VideoEntity.class);
            if(videoEntity == null)
            {
                return;
            }

            videoEntity.setState(EnumState.DOWNLOAD.toString());
            reptilianVideoDao.saveAndFlush(videoEntity);

            downLoadVideoService.downLoadFile(videoEntity.getUrl().replace("https","http"),diskPath+videoEntity.getTitle()+".mp4");
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
            try
            {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            }

        }
    }
}
