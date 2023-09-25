package com.example.demo.controller;

import com.example.demo.Constant;
import com.example.demo.model.SubscribeTopicRequest;
import com.example.demo.service.FCMInitializer;
import com.google.firebase.messaging.*;
import com.google.gson.JsonObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.PushNotificationRequest;
import com.example.demo.model.PushNotificationResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

//import com.example.demo.service.PushNotificationService;
@RestController
public class PushNotificationController {

        @Autowired
        FCMInitializer fcmInitializer;
//    private PushNotificationService pushNotificationService;
//
//    public PushNotificationController(PushNotificationService pushNotificationService) {
//        this.pushNotificationService = pushNotificationService;
//    }

    @PostMapping("/notification/subscribeTopic")
    public ResponseEntity subscribeTopic(@RequestBody SubscribeTopicRequest subscribeTopicRequest) throws FirebaseMessagingException {
        System.out.println("Subscribe Topic");
        List<String> registrationTokens = Arrays.asList(
                subscribeTopicRequest.deviceToken
        );
        TopicManagementResponse response = FirebaseMessaging.getInstance().subscribeToTopic(
                registrationTokens, subscribeTopicRequest.topic);
        System.out.println(response.getSuccessCount() + " tokens were subscribed successfully");
        return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Successful registration."), HttpStatus.OK);
    }

    @PostMapping("/notification/send")
    public ResponseEntity sendTokenNotification(@RequestBody PushNotificationRequest pushNotificationRequest) {
        try {
            JsonObject jsonData = new JsonObject();
            jsonData.addProperty("key1", "value1");
            jsonData.addProperty("key2", "value2");
            Notification notification = Notification.builder()
                    .setTitle(pushNotificationRequest.getTitle())
                    .setBody(pushNotificationRequest.getMessage())
                    .build();
            Message message = Message.builder()
                    .setNotification(notification)
                    .setToken(pushNotificationRequest.getToken()) // The FCM registration token of the recipient
                    .putData("data", jsonData.toString())
                    .putData("priority", "high")
                    .build();

            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Successfully sent message: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    }
    @PostMapping("/notification/sendToTopic")
    public ResponseEntity sendMessageToTopic(@RequestBody PushNotificationRequest pushNotificationRequest) {
        System.out.println("sendMessageToTopic");
        try {
            Notification notification = Notification.builder()
                    .setTitle(pushNotificationRequest.getTitle())
                    .setBody(pushNotificationRequest.getMessage())
                    .build();
            Message message = Message.builder()
                    .setNotification(notification)
                    .setTopic(pushNotificationRequest.getTopic())
                    .build();

            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Send Message To Topic thành công");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Send Message To Topic thành công"), HttpStatus.OK);
    }

}