package com.example.demo.controller;

import com.example.demo.model.PushNotificationResponse;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.SQLException;

@Service
public class BSCController {
    @Autowired
    RestTemplate restTemplate;

    public String login() {
        try {
            String apiUrl = "https://trading.bsc.com.vn/sso/login";
            URL url = new URL(apiUrl);

            // Tạo kết nối HttpURLConnection
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
//            connection.setRequestProperty("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
            connection.setRequestProperty("Content-Type", MediaType.MULTIPART_FORM_DATA_VALUE);
            connection.setRequestProperty("Origin", "https://trading.bsc.com.vn");

            SSLContext sc = SSLContext.getInstance("SSL");

            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[]{};
                }

                @Override
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }
            }};

            sc.init(null, trustAllCerts, new java.security.SecureRandom());

            connection.setSSLSocketFactory(sc.getSocketFactory());

            // Tạo dữ liệu form data
            String formData = "username=002C095103" +
                    "&password=Abc123456@" +
                    "&returnTo=%2Foauth%2Fauthorize%3Fclient_id%3DTFVUTH2WJE%26response_type%3Dcode%26redirect_uri%3Dhttps%253A%252F%252Ftrading.bsc.com.vn%252Fvalidate_code%26scope%3Daccount-read%2520account-write%2520order-read%2520order-write%2520balance-read%2520balance-write%26state%3DEQDHOnMphOomFHmLPnGncZSKnfPZDs%26ui_locales%3Dvi%26loginWithAnother%3Dfalse%26webtrade%3Dtrue%26language%3Dvi%26name%3D%26username%3D%26theme%3Ddark";

            // Ghi dữ liệu form data vào luồng đầu ra của kết nối
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = formData.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Đọc phản hồi từ API
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println("API Response: " + response.toString());
            }

            System.out.println("Vao day roi2");
            // Đóng kết nối
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        }
        return "";
    }

//    public String login() {
//        String apiUrl = "https://trading.bsc.com.vn/sso/login";
//
//        // Tạo dữ liệu form data
//        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
//        formData.add("username", "002C095103");
//        formData.add("password", "Abc123456%40");
//        formData.add("returnTo", "%2Foauth%2Fauthorize%3Fclient_id%3DTFVUTH2WJE%26response_type%3Dcode%26redirect_uri%3Dhttps%253A%252F%252Ftrading.bsc.com.vn%252Fvalidate_code%26scope%3Daccount-read%2520account-write%2520order-read%2520order-write%2520balance-read%2520balance-write%26state%3DEQDHOnMphOomFHmLPnGncZSKnfPZDs%26ui_locales%3Dvi%26loginWithAnother%3Dfalse%26webtrade%3Dtrue%26language%3Dvi%26name%3D%26username%3D%26theme%3Ddark");
//
//        // Tạo header để chỉ định loại nội dung
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        // Tạo yêu cầu HTTP POST với dữ liệu form data và header
//        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(formData, headers);
//
//        // Gọi API và nhận kết quả
//        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);
//
//        System.out.println(response);
//
//        return "Thanh cong";
//    }
}
