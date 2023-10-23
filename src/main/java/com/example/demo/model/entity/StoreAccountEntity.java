package com.example.demo.model.entity;

import java.util.Date;

public class StoreAccountEntity implements EntityDB{
    private Integer ROW_NUM;
    private String PK_STORE_ACCOUNT;
    private String C_STORE_CODE;
    private String C_DEPOSIT_CODE;
    private String C_ACCOUNT_TYPE;
    private String C_ACCOUNT_CODE;
    private String C_ACCOUNT_FULL;
    private String C_ACCOUNT_NAME;
    private Date C_OPEN_DATE;
    private Date C_CLOSE_DATE;
    private String C_STATUS;
    private String C_CONTENT;
    private String C_CREATOR_CODE;
    private Date C_CREATE_TIME;

    private Integer C_TOTAL_RECORD;

    public Integer getROW_NUM() {
        return ROW_NUM;
    }

    public void setRowNum(Integer ROW_NUM) {
        this.ROW_NUM = ROW_NUM;
    }

    public String getPK_STORE_ACCOUNT() {
        return PK_STORE_ACCOUNT;
    }

    public void setPK_STORE_ACCOUNT(String PK_STORE_ACCOUNT) {
        this.PK_STORE_ACCOUNT = PK_STORE_ACCOUNT;
    }

    public String getC_STORE_CODE() {
        return C_STORE_CODE;
    }

    public void setC_STORE_CODE(String c_STORE_CODE) {
        C_STORE_CODE = c_STORE_CODE;
    }

    public String getC_DEPOSIT_CODE() {
        return C_DEPOSIT_CODE;
    }

    public void setC_DEPOSIT_CODE(String c_DEPOSIT_CODE) {
        C_DEPOSIT_CODE = c_DEPOSIT_CODE;
    }

    public String getC_ACCOUNT_TYPE() {
        return C_ACCOUNT_TYPE;
    }

    public void setC_ACCOUNT_TYPE(String c_ACCOUNT_TYPE) {
        C_ACCOUNT_TYPE = c_ACCOUNT_TYPE;
    }

    public String getC_ACCOUNT_CODE() {
        return C_ACCOUNT_CODE;
    }

    public void setC_ACCOUNT_CODE(String c_ACCOUNT_CODE) {
        C_ACCOUNT_CODE = c_ACCOUNT_CODE;
    }

    public String getC_ACCOUNT_FULL() {
        return C_ACCOUNT_FULL;
    }

    public void setC_ACCOUNT_FULL(String c_ACCOUNT_FULL) {
        C_ACCOUNT_FULL = c_ACCOUNT_FULL;
    }

    public String getC_ACCOUNT_NAME() {
        return C_ACCOUNT_NAME;
    }

    public void setC_ACCOUNT_NAME(String c_ACCOUNT_NAME) {
        C_ACCOUNT_NAME = c_ACCOUNT_NAME;
    }

    public Date getC_OPEN_DATE() {
        return C_OPEN_DATE;
    }

    public void setC_OPEN_DATE(Date c_OPEN_DATE) {
        C_OPEN_DATE = c_OPEN_DATE;
    }

    public Date getC_CLOSE_DATE() {
        return C_CLOSE_DATE;
    }

    public void setC_CLOSE_DATE(Date c_CLOSE_DATE) {
        C_CLOSE_DATE = c_CLOSE_DATE;
    }

    public String getC_STATUS() {
        return C_STATUS;
    }

    public void setC_STATUS(String c_STATUS) {
        C_STATUS = c_STATUS;
    }

    public String getC_CONTENT() {
        return C_CONTENT;
    }

    public void setC_CONTENT(String c_CONTENT) {
        C_CONTENT = c_CONTENT;
    }

    public String getC_CREATOR_CODE() {
        return C_CREATOR_CODE;
    }

    public void setC_CREATOR_CODE(String c_CREATOR_CODE) {
        C_CREATOR_CODE = c_CREATOR_CODE;
    }

    public Date getC_CREATE_TIME() {
        return C_CREATE_TIME;
    }

    public void setC_CREATE_TIME(Date c_CREATE_TIME) {
        C_CREATE_TIME = c_CREATE_TIME;
    }

    public Integer getC_TOTAL_RECORD() {
        return C_TOTAL_RECORD;
    }

    public void setC_TOTAL_RECORD(Integer c_TOTAL_RECORD) {
        C_TOTAL_RECORD = c_TOTAL_RECORD;
    }
}
