package com.example.demo.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import javax.annotation.PostConstruct;

import com.google.auth.oauth2.AccessToken;
import com.google.firebase.messaging.FirebaseMessaging;
import io.lettuce.core.ScriptOutputType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Service
public class FCMInitializer {

    public void initialize() {
        try {
            FileInputStream serviceAccount = new FileInputStream("./src/main/java/com/example/demo/serviceAccountKey.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (IOException e) {

        }
    }

//    public String getAccessToken() throws IOException {
//        String serviceAccountFile = "./src/main/java/com/example/demo/serviceAccountKey.json";
//
//        // Đọc tệp JSON của Service Account Key
//        InputStream serviceAccount = new FileInputStream(serviceAccountFile);
//
//        // Tạo đối tượng GoogleCredentials từ Service Account Key
//        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
//
//        // Xác thực và lấy Access Token
//        AccessToken accessToken = credentials.refreshAccessToken();
//        String oauth2AccessToken = accessToken.getTokenValue();
//
//        // In ra Access Token
//        System.out.println("OAuth 2.0 Access Token: " + oauth2AccessToken);
//        return oauth2AccessToken;
//    }

    //    public String getRegistrationToken() {
//        return FirebaseMessaging.getInstance().getToken().get();
//    }

}