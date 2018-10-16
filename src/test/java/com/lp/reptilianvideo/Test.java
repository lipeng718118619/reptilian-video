package com.lp.reptilianvideo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


public class Test
{


    public static void main(String[] args) throws IOException
    {
        Document document = Jsoup.connect("https://www.xiaoyia1.xyz/ixx/toupaizipai/58928/play-0-0.html").get();

        Element element = document.selectFirst("div#a1");
        Elements elements = element.getElementsByTag("script");

        String realUrl = elements.get(0).childNode(0).toString().split("\\$")[3];

    }
}
