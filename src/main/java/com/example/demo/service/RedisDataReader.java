//package com.example.demo.service;
//
//import com.example.demo.Constant;
//import com.example.demo.model.ObjectRedis;
//import com.example.demo.model.PushNotificationRequest;
//import com.google.firebase.messaging.FirebaseMessaging;
//import com.google.firebase.messaging.FirebaseMessagingException;
//import com.google.firebase.messaging.Message;
//import com.google.firebase.messaging.Notification;
//import com.google.gson.Gson;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
//
//@Service
//public class RedisDataReader {
//    @Autowired
//    private StringRedisTemplate redisTemplate;
//
//    @PostConstruct
//    public void readDataFromRedis() {
//        Gson gson = new Gson();
//        Thread readingThread = new Thread(() -> {
//            while (true) {
//                String value = redisTemplate.opsForList().rightPop("info");
//
//                if(value != null) {
//                    System.out.println("Data from Redis: " + value);
//                    ObjectRedis objectRedis
//                            = gson.fromJson(value, ObjectRedis.class);
//                    if(objectRedis.topic != null) {
//                        // send public
//                        try {
//                            String token = Constant.userToken.get(objectRedis.user);
//                            if(token != null) {
//                                System.out.println("Token: " + token);
//                                Notification notification = Notification.builder()
//                                        .setTitle(objectRedis.title)
//                                        .setBody(objectRedis.body)
//                                        .build();
//                                Message message = Message.builder()
//                                        .setNotification(notification)
//                                        .setToken(token) // The FCM registration token of the recipient
//                                        .build();
//                                FirebaseMessaging.getInstance().send(message);
//                                System.out.println("Send public thành công");
//                            } else {
//                                System.out.println("Không có thông tin token");
//                            }
//                        } catch (FirebaseMessagingException e) {
//                            throw new RuntimeException(e);
//                        }
//                    } else {
//                        // send to topic
//                        Notification notification = Notification.builder()
//                                .setTitle(objectRedis.title)
//                                .setBody(objectRedis.body)
//                                .build();
//                        Message message = Message.builder()
//                                .setNotification(notification)
//                                .setTopic(objectRedis.topic)
//                                .build();
//                        try {
//                            FirebaseMessaging.getInstance().send(message);
//                        } catch (FirebaseMessagingException e) {
//                            throw new RuntimeException(e);
//                        }
//                        System.out.println("Send to topic thành công");
//                    }
//                } else {
//                    System.out.println("No data found in Redis");
//                }
//
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        readingThread.start();
//    }
//    private String generateRandomData() {
//        return "RandomData: " + Math.floor(Math.random() * 10) + 1;
//    }
//}
