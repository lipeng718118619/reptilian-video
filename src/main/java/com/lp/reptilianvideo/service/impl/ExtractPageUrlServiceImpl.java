package com.lp.reptilianvideo.service.impl;

import com.lp.reptilianvideo.entity.EnumState;
import com.lp.reptilianvideo.entity.VideoEntity;
import com.lp.reptilianvideo.service.ExtractPageUrlService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * 解析 下载url
 */
@Service
public class ExtractPageUrlServiceImpl implements ExtractPageUrlService
{
    private static final String URI="https://www.xiaoyia1.xyz";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public List<VideoEntity> analysisVideoUrl(String indexUrl)
    {
        List<VideoEntity> videoEntities = new LinkedList<>();
        try
        {
            Document document = Jsoup.connect(indexUrl).get();
            Elements videoElements= document.select("li#video-小姨在线");

            int index=0;
            for(Element videoElement : videoElements)
            {
                Element hrefElement = videoElement.selectFirst("a[href]");
                String hrefUrl = hrefElement.attr("href");
                String title = hrefElement.attr("title");

                if(hrefUrl.startsWith("https://vvv.viplaotie.xyz"))
                {
                    continue;
                }

                Element imgElement = videoElement.selectFirst("img[src]");
                String imgUrl = imgElement.attr("src");

                logger.info("video title {}, href {}, imgUrl {}",title,hrefUrl,imgUrl);

                Thread.sleep(3000);

                String url = getRealDownLoadUrl(hrefUrl);

                Thread.sleep(2000);

                VideoEntity videoEntity = new VideoEntity(title);

                videoEntity.setRootUrl(indexUrl);
                videoEntity.setIndexUrl(hrefUrl);
                videoEntity.setPicUrl(imgUrl);
                videoEntity.setUrl(url);
                videoEntity.setIndex(index++);
                videoEntity.setState(EnumState.INIT.toString());
                videoEntities.add(videoEntity);
            }

        }
        catch(Exception e)
        {
            logger.error("analysis video url error!",e);
        }

        return videoEntities;
    }

    /**
     * 获取真实下载url
     * @param secondaryUrl
     * @return
     */
    private String getRealDownLoadUrl(String secondaryUrl)
    {
        try
        {
            Document document = Jsoup.connect(URI+secondaryUrl).get();

            Element element = document.selectFirst("div#a1");
            Elements elements = element.getElementsByTag("script");

            String realUrl = elements.get(0).childNode(0).toString().split("\\$")[3];

            logger.info("secondaryUrl: {} -> realUrl: {}",secondaryUrl,realUrl);

            return realUrl;
        }
        catch(IOException e)
        {
            logger.error("analysis real download url error! secondaryUrl: {}",secondaryUrl,secondaryUrl);

        }

        return null;
    }

}
