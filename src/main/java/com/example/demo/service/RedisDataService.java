package com.example.demo.service;

import com.example.demo.model.ObjectRedis;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RedisDataService {
    @Autowired
    StringRedisTemplate redisTemplate;
    public ObjectRedis getFullInfoNotification(String id, String title, String message) {
        Gson gson = new Gson();
//        System.out.println("Full Info: " + redisTemplate.opsForHash().get("messageInfo", id));
         return gson.fromJson(JsonParser.parseString(redisTemplate.opsForHash().get("messageInfo", id).toString()), ObjectRedis.class);
    }
}
