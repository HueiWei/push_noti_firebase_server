package com.example.demo.model;

import com.example.demo.model.entity.StoreAccountEntity;

import java.util.List;

public class Response {
    private String code;
    private String message;
    private String txId;
    private List<StoreAccountEntity> data;

    public Response() {
    }

    public Response(String code, String message, String txId, List<StoreAccountEntity> data) {
        this.code = code;
        this.message = message;
        this.txId = txId;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public List<StoreAccountEntity> getData() {
        return data;
    }

    public void setData(List<StoreAccountEntity> data) {
        this.data = data;
    }
}
