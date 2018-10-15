package com.lp.reptilianvideo;

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
import org.springframework.util.MultiValueMap;
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
	private ExtractPageUrlService extractPageUrlService;

	@Test
	public void contextLoads() {
	}


	@Test
	public void test() throws IOException
	{
		Resource resource = restTemplate
		.getForObject("https://www.xiaoyia1.xyz/ixx/toupaizipai/index.html",Resource.class);

		InputStream inputStream =resource.getInputStream();

		BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream("D://test.mp4"));


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
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("user-agent","Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Mobile Safari/537.36");


		HttpEntity httpEntity = new HttpEntity(httpHeaders);

		ResponseEntity<String> uu= restTemplate.exchange("https://www.xiaoyia1.xyz/ixx/toupaizipai/index.html",
				HttpMethod.GET, httpEntity,String.class);

		int i=0;
	}

	@Test
	public void test3()
	{
		extractPageUrlService.searchMainIndex("https://www.xiaoyia1.xyz/ixx/toupaizipai/index.html");
	}



}
