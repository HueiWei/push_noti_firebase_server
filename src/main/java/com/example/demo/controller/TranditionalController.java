package com.example.demo.controller;

import com.example.demo.model.PushNotificationResponse;
import com.example.demo.repository.TranditionalRepository;
import com.example.demo.model.TranditionalObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLException;

@RestController
public class TranditionalController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    TranditionalRepository tranditionalRepository;

//    @CrossOrigin("*")
//    @GetMapping("/update_user")
//    public ResponseEntity update_user() throws SQLException {
//        String user = "quynhnx";
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("CUSTOMER_ID", "06B6AB9064434ADBE063817BAA6716FB");
//        jsonObject.addProperty("MARKETING_ID", "1119");
//
//        tranditionalRepository.update_user_by_marketing(user, jsonObject);
//        return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Successful registration."), HttpStatus.OK);
//    }

//    @CrossOrigin("*")
//    @GetMapping("/get_log_update_user")
//    public ResponseEntity get_log_update_user() throws SQLException {
//        String user = "quynhnx";
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("CUSTOMER_ID", "06B6AB9064434ADBE063817BAA6716FB");
//        jsonObject.addProperty("MARKETING_ID", "1119");
//
//        tranditionalRepository.get_log_update_user(user, jsonObject);
//        return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Successful registration."), HttpStatus.OK);
//    }
//    @CrossOrigin("*")
//    @GetMapping("/get_log_update_user")
//    public ResponseEntity login() throws SQLException {
//
//
//        tranditionalRepository.get_log_update_user(user, jsonObject);
//        return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Successful registration."), HttpStatus.OK);
//    }
    @CrossOrigin("*")
    @PostMapping("/traditional-service")
    public ResponseEntity tranditional_service(@RequestBody String jsonString) throws SQLException {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

        String user = jsonObject.get("user").getAsString();
        String session = jsonObject.get("session").getAsString();
        String cmd = jsonObject.get("cmd").getAsString();
        String group = jsonObject.get("group").getAsString();
        JsonObject data = jsonObject.get("data").getAsJsonObject();

        System.out.println(user);
        System.out.println(session);
        System.out.println(cmd);
        System.out.println(group);
        System.out.println(data);

        tranditionalRepository.tranditional_service(user, data, session, cmd, group);
        return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Successful registration."), HttpStatus.OK);
    }
    @GetMapping("/hello")
    public String hello() {
        System.out.println("hello");
        return "Hello";
    }
}
