package com.example.demo;

import com.example.demo.model.ObjectRedis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Constant {
    public static List<String> deviceKeyList = new ArrayList<>();

    public static String deviceKey = "fw3offlalrxnI4ikBbKMIc:APA91bHGXz71eeKMHsCG64CWeOUYbho6XzcyO-u9Uk5nzNBTVOVosRuoB2RjszN96v3skEOjy_VwbAfUF68G573FZ3MXgGxJQXE4C4QjAQ9_I-iQjNzOg6bPuS7rqYhtryKoaRZFsBiq";

    public static HashMap<String, String> userToken = new HashMap<>();

    public static BlockingQueue<ObjectRedis> messageFromRedis = new LinkedBlockingQueue<>();
}
