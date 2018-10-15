package com.lp.reptilianvideo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExtractPageUrlService
{

    private HttpHeaders httpHeaders = new HttpHeaders();

    {
        httpHeaders.set("user-agent","Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Mobile Safari/537.36");
        httpEntity = new HttpEntity(httpHeaders);
    }


   private HttpEntity httpEntity;

    @Autowired
    private RestTemplate restTemplate;


    public String searchMainIndex(String mainUrl)
    {
        ResponseEntity<String> responseEntity = restTemplate.exchange(mainUrl, HttpMethod.GET,httpEntity,String.class);



        return null;
    }
}
