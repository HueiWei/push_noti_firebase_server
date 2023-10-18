package com.example.demo.controller;

import com.example.demo.model.PushNotificationRequest;
import com.example.demo.model.PushNotificationResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.TopicManagementResponse;
import com.google.gson.JsonObject;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController()
public class BSCController {
    @Autowired
    RestTemplate restTemplate;

    public ResponseEntity<String> callExternalApi() {
        String apiUrl = "https://trading.bsc.com.vn/sso/login"; // Thay đổi thành API bạn đang gọi
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl);

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(builder.toUriString(), String.class);
            System.out.println("response: " + response);
            // Xử lý response ở đây
            return response;
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.MOVED_PERMANENTLY || ex.getStatusCode() == HttpStatus.FOUND) {
                // Nếu nhận được mã trạng thái chuyển hướng, bạn có thể truy cập header "Location" để biết URL mới
                String redirectUrl = ex.getResponseHeaders().getLocation().toString();
                // Xử lý chuyển hướng tại đây, có thể thực hiện gọi lại API với URL mới
                // Hoặc xử lý các thông tin khác liên quan đến chuyển hướng
                System.out.println("redirectUrl: " + redirectUrl);
                return ResponseEntity.status(HttpStatus.OK).body("Redirected to: " + redirectUrl);
            } else {
                // Xử lý các lỗi khác
                return ResponseEntity.status(ex.getStatusCode()).body(ex.getResponseBodyAsString());
            }
        }
    }

    @RequestMapping(value = "/validate_code")
    public void validate_code() {
        try {
            String apiUrl = "https://trading.bsc.com.vn/validate_code?code=wdU2oaZMAWSKs1vc&state=QiWu5XT8IZvKhAK1bnCQccFquR0wUi";
            URL url = new URL(apiUrl);

            // Tạo kết nối HttpURLConnection
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
//            connection.setRequestProperty("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
//            connection.setRequestProperty("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
            connection.setRequestProperty("Host", "trading.bsc.com.vn");
//            connection.setRequestProperty("Origin", "https://trading.bsc.com.vn");
            connection.setRequestProperty("Referer", "https://trading.bsc.com.vn/sso/login?returnTo=%2Foauth%2Fauthorize%3Fclient_" +
                    "id%3DTFVUTH2WJE%26response_type%3Dcode%26redirect_uri%3Dhttps%253A%252F%252Ftrading.bsc.com.vn%252Fvalidate_code%26s" +
                    "cope%3Daccount-read%2520account-write%2520order-read%2520order-write%2520balance-read%2520balance-write%26state%3DYOot0" +
                    "xgFIS1sOIs0ORLYm0pvi08bpd%26ui_locales%3Dvi%26loginWithAnother%3Dfalse%26webtrade%3Dtrue%26language%3Dvi%26name%3DNGUY%25E1" +
                    "%25BB%2584N%2BCH%25C3%258D%2BTH%25C3%2580NH%26username%3D002C095103%26theme%3Ddark");

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

            // Đọc phản hồi từ API
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println("API Response: " + response.toString());
            }

            System.out.println("Vao day roi");
            // Đóng kết nối
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "authorize")
    public RedirectView authorize() {
        try {
            String apiUrl = "https://trading.bsc.com.vn/sso/oauth/authorize?client_id=TFVUTH2WJE&response_type=code&redirect_uri=https%3A%2F%2Ftrading.bsc.com.vn%2Fvalidate_code&scope=account-read%20account-write%20order-read%20order-write%20balance-read%20balance-write&state=QiWu5XT8IZvKhAK1bnCQccFquR0wUi&ui_locales=vi&loginWithAnother=false&webtrade=true&language=vi&name=&username=&theme=dark";
            URL url = new URL(apiUrl);

            // Tạo kết nối HttpURLConnection
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
//            connection.setRequestProperty("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
//            connection.setRequestProperty("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
            connection.setRequestProperty("Host", "trading.bsc.com.vn");
//            connection.setRequestProperty("Origin", "https://trading.bsc.com.vn");
            connection.setRequestProperty("Referer", "https://trading.bsc.com.vn/sso/login?returnTo=%2Foauth%2Fauthorize%3Fclient_" +
                    "id%3DTFVUTH2WJE%26response_type%3Dcode%26redirect_uri%3Dhttps%253A%252F%252Ftrading.bsc.com.vn%252Fvalidate_code%26s" +
                    "cope%3Daccount-read%2520account-write%2520order-read%2520order-write%2520balance-read%2520balance-write%26state%3DYOot0" +
                    "xgFIS1sOIs0ORLYm0pvi08bpd%26ui_locales%3Dvi%26loginWithAnother%3Dfalse%26webtrade%3Dtrue%26language%3Dvi%26name%3DNGUY%25E1" +
                    "%25BB%2584N%2BCH%25C3%258D%2BTH%25C3%2580NH%26username%3D002C095103%26theme%3Ddark");

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
//            String formData = "username=002C095103" +
//                    "&password=Abc123456@" +
//                    "&returnTo=/oauth/authorize?client_id=TFVUTH2WJE&response_type=code&redirect_uri=https%3A%2F%2Ftrading.bsc.com.vn%2Fvalidate_code&scope=account-read%20account-write%20order-read%20order-write%20balance-read%20balance-write&state=YOot0xgFIS1sOIs0ORLYm0pvi08bpd&ui_locales=vi&loginWithAnother=false&webtrade=true&language=vi&name=NGUY%E1%BB%84N+CH%C3%8D+TH%C3%80NH&username=002C095103&theme=dark";
//
//////             Ghi dữ liệu form data vào luồng đầu ra của kết nối
//            try (OutputStream os = connection.getOutputStream()) {
//                byte[] input = formData.getBytes("utf-8");
//                os.write(input, 0, input.length);
//            }
//
//            FileOutputStream outputStream = null;
//            outputStream = new FileOutputStream("C:\\Users\\HPG9\\Documents\\ServerFirebase\\push_noti_firebase_server\\src\\main\\java\\com\\example\\demo\\test.txt");
//            int c;
//            while ((c = connection.getInputStream().read()) != -1) {
//                outputStream.write(c);
//            }
//            int responseCode = connection.getResponseCode();
//            System.out.println("Location: " + connection.getHeaderField("Location"));
//            System.out.println("responseCode: " + responseCode);
//            if(responseCode == HttpURLConnection.HTTP_MOVED_TEMP || responseCode == HttpURLConnection.HTTP_MOVED_PERM) {
//                String newURL = connection.getHeaderField("Location");
//                System.out.println("newURL: " + newURL);
//            }

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
        return new RedirectView("/validate_code");
    }

    public String login() throws IOException {
        try {
            String apiUrl = "https://trading.bsc.com.vn/sso/login";
//            String apiUrl = "https://api.publicapis.org/entries";
            URL url = new URL(apiUrl);


            // Tạo kết nối HttpURLConnection
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Host", "trading.bsc.com.vn");
            connection.setRequestProperty("Origin", "https://trading.bsc.com.vn");
            connection.setRequestProperty("Referer", "https://trading.bsc.com.vn/sso/login?returnTo=%2Foauth%2Fauthorize%3Fclient_id%3DTFVUTH2WJE%26response_type%3Dcode%26redirect_uri%3Dhttps%253A%252F%252Ftrading.bsc.com.vn%252Fvalidate_code%26scope%3Daccount-read%2520account-write%2520order-read%2520order-write%2520balance-read%2520balance-write%26state%3DmrcezXnKBTHTt56MEKI80p6GOAJEpJ%26ui_locales%3Dvi%26loginWithAnother%3Dfalse%26webtrade%3Dtrue%26language%3Dvi%26name%3D%26username%3D%26theme%3Ddark");
//            connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");

            connection.setInstanceFollowRedirects(false);
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
                    "&returnTo=/oauth/authorize?client_id=TFVUTH2WJE&response_type=code&redirect_uri=https%3A%2F%2Ftrading.bsc.com.vn%2Fvalidate_code&scope=account-read%20account-write%20order-read%20order-write%20balance-read%20balance-write&state=mrcezXnKBTHTt56MEKI80p6GOAJEpJ&ui_locales=vi&loginWithAnother=false&webtrade=true&language=vi&name=&username=&theme=dark";

//             Ghi dữ liệu form data vào luồng đầu ra của kết nối
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = formData.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            String sOut = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            sOut = response.toString();
            System.out.println(sOut);

            // Đọc phản hồi từ API
//            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
//                StringBuilder response = new StringBuilder();
//                String responseLine = null;
//                while ((responseLine = br.readLine()) != null) {
//                    response.append(responseLine.trim());
//                }
//                System.out.println("API Response: " + response.toString());
//            }

            System.out.println("Vao day roi2");
            // Đóng kết nối
            connection.disconnect();
        } catch (IOException | KeyManagementException e) {
            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        } catch (KeyManagementException e) {
//            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return "";
    }

    public String authorize2() throws IOException {
        try {
            String apiUrl = "https://trading.bsc.com.vn/sso/oauth/authorize?client_id=TFVUTH2WJE&response_type=code&redirect_uri=https%3A%2F%2Ftrading.bsc.com.vn%2Fvalidate_code&scope=account-read%20account-write%20order-read%20order-write%20balance-read%20balance-write&state=BLHVi9wxVSWforgKr2wRaJHOoRUaHu&ui_locales=vi&loginWithAnother=false&webtrade=true&language=vi&name=&username=&theme=dark";
            URL url = new URL(apiUrl);

            // Tạo kết nối HttpURLConnection
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
//            connection.setRequestProperty("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);

            connection.setInstanceFollowRedirects(false);
//            SSLContext sc = SSLContext.getInstance("SSL");
//
//            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
//                @Override
//                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                    return new java.security.cert.X509Certificate[]{};
//                }
//
//                @Override
//                public void checkClientTrusted(X509Certificate[] chain,
//                                               String authType) throws CertificateException {
//                }
//
//                @Override
//                public void checkServerTrusted(X509Certificate[] chain,
//                                               String authType) throws CertificateException {
//                }
//            }};
//
//            sc.init(null, trustAllCerts, new java.security.SecureRandom());
//
//            connection.setSSLSocketFactory(sc.getSocketFactory());

            FileOutputStream outputStream = null;
            outputStream = new FileOutputStream("C:\\Users\\HPG9\\Documents\\ServerFirebase\\push_noti_firebase_server\\src\\main\\java\\com\\example\\demo\\test.txt");
            int c;
            while ((c = connection.getInputStream().read()) != -1) {
                outputStream.write(c);
            }
            System.out.println(connection.getHeaderFields());
            int responseCode = connection.getResponseCode();
            System.out.println("Location: " + connection.getHeaderField("Location"));
            System.out.println("responseCode: " + responseCode);
//            if(responseCode == HttpURLConnection.HTTP_MOVED_TEMP || responseCode == HttpURLConnection.HTTP_MOVED_PERM) {
//                String newURL = connection.getHeaderField("Location");
//                System.out.println("newURL: " + newURL);
//            }

            // Đọc phản hồi từ API
//            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
//                StringBuilder response = new StringBuilder();
//                String responseLine = null;
//                while ((responseLine = br.readLine()) != null) {
//                    response.append(responseLine.trim());
//                }
//                System.out.println("API Response: " + response.toString());
//            }

            System.out.println("Vao day roi2");
            // Đóng kết nối
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        } catch (KeyManagementException e) {
//            throw new RuntimeException(e);
        }
        return "";
    }
    public String validate2() throws IOException {
        try {
            String apiUrl = "https://trading.bsc.com.vn/validate_code?code=akSdrR7Y7g9SmgBE&state=BLHVi9wxVSWforgKr2wRaJHOoRUaHu";
            URL url = new URL(apiUrl);

            // Tạo kết nối HttpURLConnection
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
//            connection.setRequestProperty("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);

            connection.setInstanceFollowRedirects(false);
//            SSLContext sc = SSLContext.getInstance("SSL");
//
//            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
//                @Override
//                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                    return new java.security.cert.X509Certificate[]{};
//                }
//
//                @Override
//                public void checkClientTrusted(X509Certificate[] chain,
//                                               String authType) throws CertificateException {
//                }
//
//                @Override
//                public void checkServerTrusted(X509Certificate[] chain,
//                                               String authType) throws CertificateException {
//                }
//            }};
//
//            sc.init(null, trustAllCerts, new java.security.SecureRandom());
//
//            connection.setSSLSocketFactory(sc.getSocketFactory());

            FileOutputStream outputStream = null;
            outputStream = new FileOutputStream("C:\\Users\\HPG9\\Documents\\ServerFirebase\\push_noti_firebase_server\\src\\main\\java\\com\\example\\demo\\test.txt");
            int c;
            while ((c = connection.getInputStream().read()) != -1) {
                outputStream.write(c);
            }
            System.out.println(connection.getHeaderFields());
            int responseCode = connection.getResponseCode();
            System.out.println("Location: " + connection.getHeaderField("Location"));
            System.out.println("responseCode: " + responseCode);
//            if(responseCode == HttpURLConnection.HTTP_MOVED_TEMP || responseCode == HttpURLConnection.HTTP_MOVED_PERM) {
//                String newURL = connection.getHeaderField("Location");
//                System.out.println("newURL: " + newURL);
//            }

            // Đọc phản hồi từ API
//            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
//                StringBuilder response = new StringBuilder();
//                String responseLine = null;
//                while ((responseLine = br.readLine()) != null) {
//                    response.append(responseLine.trim());
//                }
//                System.out.println("API Response: " + response.toString());
//            }

            System.out.println("Vao day roi2");
            // Đóng kết nối
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        } catch (KeyManagementException e) {
//            throw new RuntimeException(e);
        }
        return "";
    }
}
