package com.example.demo;

import com.example.demo.controller.BSCController;
import com.example.demo.service.DataPublisher;
import com.example.demo.service.FCMInitializer;
import com.example.demo.service.QueueDataReader;
import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

//import java.net.URI;

@SpringBootApplication
public class SpringBootPushNotificationsApplication {

	public static void main(String[] args) throws URISyntaxException, IOException {
		SpringApplication.run(SpringBootPushNotificationsApplication.class, args);

		// Khởi tạo firebase
//		ApplicationContext context = SpringApplication.run(SpringBootPushNotificationsApplication.class, args);
//		FCMInitializer fcmInitializer = context.getBean(FCMInitializer.class);
//		fcmInitializer.initialize();


		// Ghi dữ liệu vào Redis
//		StringRedisTemplate stringRedisTemplate = context.getBean(StringRedisTemplate.class);
//		new Thread(new DataPublisher(stringRedisTemplate)).start();

		// Đọc dữ liệu từ Queue để gửi qua firebase
//		for(int i = 0; i< 2; i++) {
//			Thread thread = new Thread(new QueueDataReader());
//			thread.start();
//		}
//		BSCController bscController = context.getBean(BSCController.class);
//		System.out.println(bscController);
//		bscController.login();
//		System.out.println("Xong");
//		RestTemplate restTemplate = context.getBean(RestTemplate.class);
//
//		String url = "https://trading.bsc.com.vn/sso/login";
//
//		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
//		formData.add("username", "002C095103");
//		formData.add("password", "Abc123456@");
//		formData.add("returnTo", "/oauth/authorize?client_id=TFVUTH2WJE&response_type=code&redirect_uri=https%3A%2F%2Ftrading.bsc.com.vn%2Fvalidate_code&scope=account-read%20account-write%20order-read%20order-write%20balance-read%20balance-write&state=HdgC26dBzDRqVusjRWbX4o2PC6p2f0&ui_locales=vi&loginWithAnother=false&webtrade=true&language=vi&name=NGUY%E1%BB%84N+CH%C3%8D+TH%C3%80NH&username=002C095103&theme=dark");
//
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(httpHeaders);
//
//		RequestEntity<Object> requestEntity = new RequestEntity<>(entity, HttpMethod.POST, new URI(url));
//
//		ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
//		System.out.println(responseEntity.getStatusCode());
//		System.out.println(responseEntity.getBody());
//		System.out.println(responseEntity.getHeaders());


    }

}
