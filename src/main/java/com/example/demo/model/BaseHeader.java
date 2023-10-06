//package com.example.demo.model;
//
//
//import com.google.gson.JsonObject;
//
//public class BaseHeader {
//    private final String user;
//    private final String account;
//    private final String channel;
//    private final String language;
//    private final String group;
//
//    public BaseHeader(String account, String channel, String language, String group) {
//        this.account = account;
//        this.channel = channel;
//        this.language = language;
//        this.group = group;
//    }
//
//    public BaseHeader(String user, String account, String channel, String language, String group) {
//        this.account = account;
//        this.channel = channel;
//        this.language = language;
//        this.user = user;
//        this.group = group;
//    }
//
//
//    public JsonObject toJsonObject() {
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("ACCOUNT", this.account);
//        jsonObject.addProperty("CHANNEL", this.channel);
//        jsonObject.addProperty("GROUP", this.group);
//        jsonObject.addProperty("LAN", this.language);
//        return jsonObject;
////        return new JsonObject().put("ACCOUNT", this.account).put("CHANNEL", this.channel)
////                .put("GROUP", this.group).put("LAN", this.language);
//    }
//
//    public String getUser() {
//        return user;
//    }
//
//
//    public String getAccount() {
//        return account;
//    }
//
//
//    public String getChannel() {
//        return channel;
//    }
//
//
//    public String getLanguage() {
//        return language;
//    }
//
//
//}
