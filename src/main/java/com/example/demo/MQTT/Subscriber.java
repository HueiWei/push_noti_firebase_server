package com.example.demo.MQTT;

import org.eclipse.paho.client.mqttv3.*;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
public class Subscriber {
    private static final String CONNECTION_URL = "tcp://rtmdata.algoplatform.vn:1883";
    private static final String USERNAME = "rtmuser06";
    private static final String PASSWORD = "hHgZ0GYg";
    public void init() throws MqttException {
        MqttClient client = null;
        try {
            String publiserId = UUID.randomUUID().toString();
            client = new MqttClient(CONNECTION_URL, publiserId);
            MqttConnectOptions connectOptions = setUpConnectionOptions(USERNAME, PASSWORD);

            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable throwable) {
                    System.out.println("ConnectionLost: " + throwable.getMessage());
                }

                @Override
                public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                    System.out.println("Topic: " + s);
                    System.out.println("Message: " + new String (mqttMessage.getPayload(), StandardCharsets.UTF_8));
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                    System.out.println("DeliveryComplete: " + iMqttDeliveryToken.isComplete());
                }
            });
            client.connect(connectOptions);

            client.subscribe("socket/*/vn30f2311", 1);
        } catch (Exception e) {
            if(client!= null) {
                client.disconnect();
            }
        }

    }
    private static MqttConnectOptions setUpConnectionOptions(String username, String password) {
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setUserName(username);
        connOpts.setPassword(password.toCharArray());
        connOpts.setCleanSession(true);
        return connOpts;
    }

}
