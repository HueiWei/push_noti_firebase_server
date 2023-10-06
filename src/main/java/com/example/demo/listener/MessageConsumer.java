package com.example.demo.listener;

import com.example.demo.Constant;
import com.example.demo.model.ObjectRedis;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Notification;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class MessageConsumer implements MessageListener {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String receivedMessage = message.toString();

        String jsonMessage = null;
        try {
            jsonMessage = new String(message.getBody(), "UTF-8");
            System.out.println("jsonMessage: " + jsonMessage);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Lắng nghe message từ Redis: " + receivedMessage);

        Gson gson = new Gson();

        if(jsonMessage != null) {
            // Có thể xử lý dữ liệu trước khi đưa vào queue


            // Đưa dữ liệu vào Queue
            try {
                Constant.messageFromRedis.put(gson.fromJson(jsonMessage, ObjectRedis.class));
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

//        if (jsonMessage != null) {
//            ObjectRedis objectRedis = gson.fromJson(jsonMessage, ObjectRedis.class);
//            if (objectRedis.topic == null) {
//                // send public
//                String token = Constant.userToken.get(objectRedis.user);
//                if (token != null) {
//                    System.out.println("Token: " + token);
//                    Notification notification = com.google.firebase.messaging.Notification.builder()
//                            .setTitle(objectRedis.title)
//                            .setBody(objectRedis.body)
//                            .build();
//                    com.google.firebase.messaging.Message messageToFirebase = com.google.firebase.messaging.Message.builder()
//                            .setNotification(notification)
//                            .setToken(token) // The FCM registration token of the recipient
//                            .build();
//                    try {
//                        FirebaseMessaging.getInstance().send((com.google.firebase.messaging.Message) messageToFirebase);
//                        redisTemplate.opsForList().rightPop("backup");
//                        System.out.println("Send public thành công");
//                    } catch (FirebaseMessagingException e) {
//                        throw new RuntimeException(e);
//                    }
//                } else {
//                    System.out.println("Không có thông tin token");
//                }
//            } else {
//                // send to topic
//                Notification notification = com.google.firebase.messaging.Notification.builder()
//                        .setTitle(objectRedis.title)
//                        .setBody(objectRedis.body)
//                        .build();
//                com.google.firebase.messaging.Message messageToFirebase = com.google.firebase.messaging.Message.builder()
//                        .setNotification(notification)
//                        .setTopic(objectRedis.topic)
//                        .build();
//                try {
//                    FirebaseMessaging.getInstance().send((com.google.firebase.messaging.Message) messageToFirebase);
//                    System.out.println("Send to topic thành công");
//                    redisTemplate.opsForList().rightPop("backup");
//                } catch (FirebaseMessagingException e) {
//                    throw new RuntimeException("Send to topic không thành công");
//                }
//            }
//        }
    }
}
