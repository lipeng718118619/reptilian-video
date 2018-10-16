package com.lp.reptilianvideo.controller;

import com.lp.reptilianvideo.service.ReptilianVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReptilianVideoController
{
    @Autowired
    private ReptilianVideoService reptilianVideoService;

    @GetMapping(value = "/api/vi/video")
    public String start()
    {
        new Thread(new Task()).start();

        return "{\"result\":\"success\"}";
    }

    private class Task implements Runnable
    {

        @Override
        public void run()
        {
            reptilianVideoService.reptilianVideo("https://www.xiaoyia1.xyz/ixx/toupaizipai/index.html");
        }
    }

}
