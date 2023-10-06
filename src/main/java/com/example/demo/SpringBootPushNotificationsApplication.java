package com.example.demo;

import com.example.demo.controller.BSCController;
import com.example.demo.service.DataPublisher;
import com.example.demo.service.FCMInitializer;
import com.example.demo.service.QueueDataReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootApplication
public class SpringBootPushNotificationsApplication {


	public static void main(String[] args) {
//		SpringApplication.run(SpringBootPushNotificationsApplication.class, args);

		// Khởi tạo firebase
		ApplicationContext context = SpringApplication.run(SpringBootPushNotificationsApplication.class, args);
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
		BSCController bscController = context.getBean(BSCController.class);
		System.out.println(bscController);
		String a = bscController.login();
		System.out.println("a: " + a);
	}

}
