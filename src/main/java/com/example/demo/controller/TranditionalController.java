//package com.example.demo.controller;
//
//import com.example.demo.model.PushNotificationResponse;
//import com.example.demo.repository.TranditionalRepository;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.sql.SQLException;
//
//@RestController
//public class TranditionalController {
//
//    @Autowired
//    TranditionalRepository tranditionalRepository;
//
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
//
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
//}
