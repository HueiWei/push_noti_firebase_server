package com.example.demo.service;

import com.example.demo.Constant;
import com.example.demo.model.ObjectRedis;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;

//@Service
public class QueueDataReader  implements Runnable{

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " đang hoạt động");
        while(true) {
            ObjectRedis objectRedis = null;
            try {
                objectRedis = Constant.messageFromRedis.take();
            } catch (InterruptedException e) {
                System.out.println("Bị ngắt trong khi đợi");
                e.printStackTrace();
            }
            if(objectRedis != null) {
                if (objectRedis.topic == null) {
                    // send public
                    String token = Constant.userToken.get(objectRedis.user);
                    if (token != null) {
                        System.out.println("Token: " + token);
                        Notification notification = com.google.firebase.messaging.Notification.builder()
                                .setTitle(objectRedis.title)
                                .setBody(objectRedis.body)
                                .build();
                        com.google.firebase.messaging.Message messageToFirebase = com.google.firebase.messaging.Message.builder()
                                .setNotification(notification)
                                .setToken(token) // The FCM registration token of the recipient
                                .putData("id", objectRedis.id)
                                .build();
                        try {
                            FirebaseMessaging.getInstance().send((com.google.firebase.messaging.Message) messageToFirebase);
//                                        redisTemplate.opsForList().rightPop("backup");
                            System.out.println("Send public thành công bởi: " + Thread.currentThread().getName());
                        } catch (FirebaseMessagingException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        System.out.println("Không có thông tin token");
                    }
                } else {
                    // send to topic
                    Notification notification = com.google.firebase.messaging.Notification.builder()
                            .setTitle(objectRedis.title)
                            .setBody(objectRedis.body)
                            .build();
                    com.google.firebase.messaging.Message messageToFirebase = com.google.firebase.messaging.Message.builder()
                            .setNotification(notification)
                            .setTopic(objectRedis.topic)
                            .putData("id", objectRedis.id)
                            .build();
                    try {
                        FirebaseMessaging.getInstance().send((com.google.firebase.messaging.Message) messageToFirebase);
                        System.out.println("Send to topic thành công bởi: " + Thread.currentThread().getName());
//                                    redisTemplate.opsForList().rightPop("backup");
                    } catch (FirebaseMessagingException e) {
                        throw new RuntimeException("Send to topic không thành công");
                    }
                }
            }
        }
    }
}
