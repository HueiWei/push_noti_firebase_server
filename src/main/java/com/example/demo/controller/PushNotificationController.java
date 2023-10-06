package com.example.demo.controller;

import com.example.demo.Constant;
import com.example.demo.model.ObjectRedis;
import com.example.demo.model.SubscribeTopicRequest;
import com.example.demo.service.FCMInitializer;
import com.example.demo.service.RedisDataService;
import com.google.api.client.json.Json;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.messaging.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
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
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.PushNotificationRequest;
import com.example.demo.model.PushNotificationResponse;
import org.springframework.web.client.RestTemplate;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

//import com.example.demo.service.PushNotificationService;
@RestController
public class PushNotificationController {

    @Autowired
    RedisDataService redisDataService;

    @Autowired
    FCMInitializer fcmInitializer;

    @Autowired
    private StringRedisTemplate redisTemplate;


    //    private PushNotificationService pushNotificationService;
//
//    public PushNotificationController(PushNotificationService pushNotificationService) {
//        this.pushNotificationService = pushNotificationService;
//    }
    @CrossOrigin
    @PostMapping("/notification/subscribeTopic")
    public ResponseEntity subscribeTopic(@RequestBody PushNotificationRequest PushNotificationRequest) throws FirebaseMessagingException {
        System.out.println("Subscribe Topic");
        List<String> registrationTokens = Arrays.asList(
                PushNotificationRequest.getToken()
        );
//        List<String> registrationTokens = PushNotificationRequest.tokenList;
        TopicManagementResponse response = FirebaseMessaging.getInstance().subscribeToTopic(
                registrationTokens, PushNotificationRequest.getTopic());
        System.out.println(response.getSuccessCount() + " tokens were subscribed successfully");
        return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Successful registration."), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/notification/send")
    public ResponseEntity sendTokenNotification(@RequestBody PushNotificationRequest pushNotificationRequest) {
        try {
            Constant.userToken.put(pushNotificationRequest.getUser(), pushNotificationRequest.getToken());
            System.out.println("Lưu trữ token thành công");

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

    @CrossOrigin(origins = "*")
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

    @CrossOrigin(origins = "*")
    @PostMapping("/sendByProtocol")
    public ResponseEntity<PushNotificationResponse> createDeviceGroup(@RequestBody PushNotificationRequest pushNotificationRequest) {
        try {
            // Đường dẫn API FCM
            String fcmApiUrl = "https://fcm.googleapis.com/v1/projects/push-noti-84d64/messages:send";

            // Tạo một đối tượng RestTemplate
            RestTemplate restTemplate = new RestTemplate();

            // Tạo tiêu đề Authorization
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            System.out.println("Token: " + getAccessToken());
            headers.set("Authorization", "Bearer " + getAccessToken());
//            headers.set("project_id", "173910941950");

            Gson gson = new Gson();
            JsonObject jsonObject = new JsonObject();
//            jsonObject.addProperty("operation", "create");
//            jsonObject.addProperty("notification_key_name", "appUser");
//            JsonArray deviceKeyJsonArray = new JsonArray();
//            deviceKeyJsonArray.add("fw3offlalrxnI4ikBbKMIc:APA91bHGXz71eeKMHsCG64CWeOUYbho6XzcyO-u9Uk5nzNBTVOVosRuoB2RjszN96v3skEOjy_VwbAfUF68G573FZ3MXgGxJQXE4C4QjAQ9_I-iQjNzOg6bPuS7rqYhtryKoaRZFsBiq");
//            deviceKeyJsonArray.add("ff2PKQA9lSkSZ0VBR_Vz2d:APA91bEBRqmtTL1Tz0RgD70kXkDf2Iy5cgdZfxCBPS1KqCdAnZg7imcQVBapUqOHfDm-NpmbsryosuXLPfZIlYOzQIx20KzZqCmjbauXfZnJswFp8NvYyaZF42HLgt5dHTateU7EgflP");
//            jsonObject.add("registration_ids", deviceKeyJsonArray);
            JsonObject message = new JsonObject();
            JsonObject messageObject = new JsonObject();

            JsonObject notificationObject = new JsonObject();
            notificationObject.addProperty("body", "This is an FCM notification message!");
            notificationObject.addProperty("title", "Khong Biet");

//            eT8fYOlWRIhq-qBDKLX4-v:APA91bGCRz-Cw8V5ihkaJ2M0AgjHOdcxP1dor5-E05NKcqAr2Ja77qZVt7Yh6EDXdhCt9EqZyTI0sqlb-BcOusl9XmRlGo8AIuXgx90-FtZBVjXbP-Yt7wkfd5worF3JaqOmQC4AB1Hk
            messageObject.addProperty("token", pushNotificationRequest.getToken());
            messageObject.add("notification", notificationObject);

            message.add("message", messageObject);


            // Tạo một đối tượng HttpEntity chứa dữ liệu và tiêu đề
            org.springframework.http.HttpEntity<String> request = new org.springframework.http.HttpEntity<>(message.toString(), headers);

            // Gửi yêu cầu POST
            ResponseEntity<String> response = restTemplate.postForEntity(fcmApiUrl, request, String.class);
            System.out.println("response: " + response);
            return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Create Device Group"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.BAD_REQUEST.value(), "Error"), HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @GetMapping("/sendMessageToMultiToken")
    public ResponseEntity sendMessageToMultiToken() {
        try {

            JsonObject jsonData = new JsonObject();
            jsonData.addProperty("key1", "value1");
            jsonData.addProperty("key2", "value2");
            Notification notification = Notification.builder()
                    .setTitle("hieu nguyen")
                    .setBody("hieu nguyen 123")
                    .build();

            List<String> registrationTokens = Arrays.asList(
                    "fw3offlalrxnI4ikBbKMIc:APA91bHGXz71eeKMHsCG64CWeOUYbho6XzcyO-u9Uk5nzNBTVOVosRuoB2RjszN96v3skEOjy_VwbAfUF68G573FZ3MXgGxJQXE4C4QjAQ9_I-iQjNzOg6bPuS7rqYhtryKoaRZFsBiqYOUR_REGISTRATION_TOKEN_1",
                    "ff2PKQA9lSkSZ0VBR_Vz2d:APA91bEBRqmtTL1Tz0RgD70kXkDf2Iy5cgdZfxCBPS1KqCdAnZg7imcQVBapUqOHfDm-NpmbsryosuXLPfZIlYOzQIx20KzZqCmjbauXfZnJswFp8NvYyaZF42HLgt5dHTateU7EgflP"
            );
            MulticastMessage message = MulticastMessage.builder()
                    .setNotification(notification)
                    .addAllTokens(registrationTokens)
                    .build();

            BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/getFullNotification")
    public ResponseEntity<ObjectRedis> getFullNotification(@RequestBody PushNotificationRequest pushNotificationRequest) {
        ObjectRedis objectRedis = null;
        String id = pushNotificationRequest.getId();
        String title = pushNotificationRequest.getTitle();
        String message = pushNotificationRequest.getMessage();

        objectRedis = redisDataService.getFullInfoNotification(id, title, message);

        return new ResponseEntity<>(objectRedis, HttpStatus.OK);
    }


    public static String getAccessToken() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new FileInputStream("./src/main/java/com/example/demo/serviceAccountKey.json"))
                .createScoped(Arrays.asList("https://www.googleapis.com/auth/firebase.messaging"));
        googleCredentials.refresh();
        return googleCredentials.getAccessToken().getTokenValue();
    }
}