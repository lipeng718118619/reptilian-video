package com.lp.reptilianvideo;

import com.lp.reptilianvideo.service.DownLoadVideoService;
import com.lp.reptilianvideo.service.ExtractPageUrlService;
import com.lp.reptilianvideo.service.ReptilianVideoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReptilianVideoApplicationTests {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ReptilianVideoService reptilianVideoService;

	@Autowired
	private ExtractPageUrlService extractPageUrlService;


	@Autowired
	private DownLoadVideoService downLoadVideoService;

	@Test
	public void contextLoads() {
	}


	@Test
	public void test() throws IOException
	{
		Resource resource = restTemplate
		.getForObject("https://www.xiaoyia1.xyz/ixx/toupaizipai/index.html",Resource.class);

		InputStream inputStream =resource.getInputStream();

		BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream("D://EnumState.mp4"));


		//读取数据
		//一次性取多少字节
		byte[] bytes = new byte[1024];
		//接受读取的内容(n就代表的相关数据，只不过是数字的形式)
		int n = -1;
		//循环取出数据
		while ((n = inputStream.read(bytes,0,bytes.length)) != -1) {
			//写入相关文件
			outputStream.write(bytes, 0, n);
		}

		outputStream.flush();
		inputStream.close();
		outputStream.close();
	}

	@Test
	public void tes2t() throws IOException
	{
		extractPageUrlService.analysisVideoUrl("https://www.xiaoyia1.xyz/ixx/toupaizipai/index.html");
	}

	@Test
	public void testt()
	{
		reptilianVideoService.reptilianVideo("https://www.xiaoyia1.xyz/ixx/toupaizipai/index.html");
	}


	@Test
	public void test2()
	{
		downLoadVideoService.downLoadFile("http://py.xhsyun.xyz/001/archives3651/index.m3u8","/Users/honddy/Documents/test.mp4");
	}



}
