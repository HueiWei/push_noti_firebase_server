package com.example.demo.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

//@Service
public class DataPublisher implements Runnable {
    private StringRedisTemplate redisTemplate;

    public DataPublisher(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    //    @PostConstruct
//    public void startPublishing() {
//        Thread publishingThread = new Thread(() -> {
//            while (true) {
////                String key = generateRandomData();
////                String value = generateRandomData();
//                Gson gson = new Gson();
//
//                JsonObject jsonObject = new JsonObject();
//                jsonObject.addProperty("user", "hieu");
//                jsonObject.addProperty("title", "title");
//                jsonObject.addProperty("body", "body");
//                jsonObject.addProperty("topic", "sport4");
//
//                //Xử lý trường hợp message cos thể bị mất
////                redisTemplate.opsForList().leftPush("backup", gson.toJson(jsonObject));
//
//                try {
////                    redisTemplate.opsForList().leftPush("info", gson.toJson(jsonObject));
//                    redisTemplate.convertAndSend("my-queue", gson.toJson(jsonObject));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                System.out.println("Set data in Redis: info " + ": " + gson.toJson(jsonObject));
//
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        publishingThread.start();
//    }
    private String generateRandomData() {
        return "RandomData: " + Math.floor(Math.random() * 10) + 1;
    }


    private int generateRandomNumber() {
        // Sử dụng ThreadLocalRandom để sinh số ngẫu nhiên từ 1 đến 100 (bao gồm cả 1 và 100)
        return ThreadLocalRandom.current().nextInt(1, 101);
    }


    @Override
    public void run() {
        while (true) {
//                String key = generateRandomData();
//                String value = generateRandomData();
            Gson gson = new Gson();
            int id = generateRandomNumber();

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", id);
            jsonObject.addProperty("user", "hieu");
            jsonObject.addProperty("title", "title");
            jsonObject.addProperty("body", "body");
            jsonObject.addProperty("topic", "sport4");
            jsonObject.addProperty("urlImage", "./src/serviceAcountKey");
            jsonObject.addProperty("html", "<p>This is some HTML content.</p>");

            // Lưu trữ full thông tin của message
//            Map<String, String> messageInfo = new HashMap<>();
//            for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
//                String key = entry.getKey();
//                JsonElement value = entry.getValue();
//
//                messageInfo.put(key, value.getAsString());
//            }
            if(redisTemplate.opsForHash().hasKey("messageInfo", String.valueOf(id))) {
                System.out.println("Đã tồn tại id: " + id + "ở trong messageInfo");
            } else {
                redisTemplate.opsForHash().putIfAbsent("messageInfo", String.valueOf(id), jsonObject.toString());
                System.out.println("Id: " + id + "đã được thêm vào messageInfo");
            }

            //Xử lý trường hợp message cos thể bị mất
//                redisTemplate.opsForList().leftPush("backup", gson.toJson(jsonObject));

            try {
//                    redisTemplate.opsForList().leftPush("info", gson.toJson(jsonObject));
                redisTemplate.convertAndSend("my-queue", gson.toJson(jsonObject));
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Set data in Redis: info " + ": " + gson.toJson(jsonObject));

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
