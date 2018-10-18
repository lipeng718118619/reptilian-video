package com.lp.reptilianvideo.service.impl;

import com.lp.reptilianvideo.dao.ReptilianVideoDao;
import com.lp.reptilianvideo.entity.EnumState;
import com.lp.reptilianvideo.entity.VideoEntity;
import com.lp.reptilianvideo.service.DownLoadVideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

@Service
public class DownLoadVideoServiceImpl implements DownLoadVideoService
{

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final HttpHeaders httpHeaders = new HttpHeaders();

    private HttpEntity httpEntity;

    @Autowired
    private ReptilianVideoDao reptilianVideoDao;

    @Autowired
    private RestTemplate restTemplate;

    {
        httpHeaders.set("user-agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Mobile Safari/537.36");
        httpEntity = new HttpEntity(httpHeaders);
    }


    @Override
    public void downLoadFile(String url, String diskFilePath)
    {
        try
        {
           List<String> fileStreamUrls = getFileStreamList(url);
           downLoadFiles(fileStreamUrls, diskFilePath);
        }
        catch(Exception e)
        {
            logger.error(e.getMessage(), e);
            VideoEntity entity = reptilianVideoDao.queryByUrl(url.replaceFirst("http","https"));
            entity.setState(EnumState.FAILED.toString());
            reptilianVideoDao.saveAndFlush(entity);
        }
    }

    /**
     * @param fileStreamUrls
     * @param diskFilePath
     */
    private void downLoadFiles(List<String> fileStreamUrls, String diskFilePath) throws Exception
    {
        if(CollectionUtils.isEmpty(fileStreamUrls))
        {
            return;
        }

        File file = new File(diskFilePath);

        if(file.exists())
        {
            file.delete();
        }

        try(BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file)))
        {

            int index = 0;
            for(String url : fileStreamUrls)
            {
                if(index % 10 ==0)
                {
                    logger.info("download url: {} , index: {} , size: {}", url, index++, fileStreamUrls.size());
                }
                writeSteam2File(url, outputStream);
            }

        }
        catch(Exception e)
        {
            throw e;
        }

    }

    /**
     * 文件流写入 文件
     *
     * @param fileStreamUrl
     * @param outputStream
     * @throws IOException
     */
    private void writeSteam2File(String fileStreamUrl, BufferedOutputStream outputStream) throws Exception
    {
        ResponseEntity<Resource> responseEntity = restTemplate.exchange(fileStreamUrl, HttpMethod.GET, httpEntity, Resource.class);

        if(!responseEntity.getStatusCode().is2xxSuccessful())
        {
            throw new Exception("call download url : " + fileStreamUrl + "error!");

        }

        try(InputStream inputStream = responseEntity.getBody().getInputStream())
        {
            //读取数据
            //一次性取多少字节
            byte[] bytes = new byte[1024];
            //接受读取的内容(n就代表的相关数据，只不过是数字的形式)
            int n = -1;
            //循环取出数据
            while((n = inputStream.read(bytes, 0, bytes.length)) != -1)
            {
                //写入相关文件
                outputStream.write(bytes, 0, n);
            }

            outputStream.flush();

        }
        catch(Exception e)
        {
            throw new Exception("download url: " + fileStreamUrl + "error!", e);
        }
    }


    /**
     * 根据url 获取单个文件的下载列表
     *
     * @param url
     * @return
     */
    private List<String> getFileStreamList(String url) throws Exception
    {

        ResponseEntity<String> resourceResponseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

        if(!resourceResponseEntity.getStatusCode().is2xxSuccessful())
        {
            throw new Exception("get down load list error,url : " + url);
        }

        String streamList = resourceResponseEntity.getBody();

        String[] steams = streamList.split("\n");

        List<String> result = new LinkedList<>();

        if(steams == null)
        {
            return result;
        }

        for(String steam : steams)
        {
            if(steam.startsWith("https:"))
            {
                result.add(steam.replaceFirst("https", "http"));
            }
        }

        return result;
    }
}
