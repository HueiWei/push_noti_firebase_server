package com.example.demo.model;

import java.util.List;

public class PushNotificationRequest {

	private String id;
	private String user;
    private String title;
    private String message;
    private String topic;
    private String token;

	public List<String> tokenList;
    
    
  public PushNotificationRequest() {
		super();
	}
  
  
	public PushNotificationRequest(String id, String user, String title, String message, String topic, String token) {
		super();
		this.id = id;
		this.user = user;
		this.title = title;
		this.message = message;
		this.topic = topic;
		this.token = token;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}  
    
    
}