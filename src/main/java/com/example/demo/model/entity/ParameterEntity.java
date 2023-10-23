package com.example.demo.model.entity;

public class ParameterEntity implements EntityDB{
    private String  PK_LIST_SYSTEM_PARAMETER;
    private String  C_SYSTEM_PARAMETER_CODE;
    private String  C_SYSTEM_PARAMETER_NAME;
    private String  C_SYSTEM_PARAMETER_VALUE;
    private Integer  C_ORDER;
    private Integer  C_STATUS;
    private String  C_NOTE;

    private Integer C_TOTAL_RECORD;

    public String getPk_LIST_SYSTEM_PARAMETER() {
        return PK_LIST_SYSTEM_PARAMETER;
    }

    public void setPk_LIST_SYSTEM_PARAMETER(String pk_LIST_SYSTEM_PARAMETER) {
        this.PK_LIST_SYSTEM_PARAMETER = pk_LIST_SYSTEM_PARAMETER;
    }

    public String getC_SYSTEM_PARAMETER_CODE() {
        return C_SYSTEM_PARAMETER_CODE;
    }

    public void setC_SYSTEM_PARAMETER_CODE(String c_SYSTEM_PARAMETER_CODE) {
        this.C_SYSTEM_PARAMETER_CODE = c_SYSTEM_PARAMETER_CODE;
    }

    public String getC_SYSTEM_PARAMETER_NAME() {
        return C_SYSTEM_PARAMETER_NAME;
    }

    public void setC_SYSTEM_PARAMETER_NAME(String c_SYSTEM_PARAMETER_NAME) {
        this.C_SYSTEM_PARAMETER_NAME = c_SYSTEM_PARAMETER_NAME;
    }

    public String getC_SYSTEM_PARAMETER_VALUE() {
        return C_SYSTEM_PARAMETER_VALUE;
    }

    public void setC_SYSTEM_PARAMETER_VALUE(String c_SYSTEM_PARAMETER_VALUE) {
        this.C_SYSTEM_PARAMETER_VALUE = c_SYSTEM_PARAMETER_VALUE;
    }

    public Integer getC_ORDER() {
        return C_ORDER;
    }

    public void setC_ORDER(Integer c_ORDER) {
        this.C_ORDER = c_ORDER;
    }

    public Integer getC_STATUS() {
        return C_STATUS;
    }

    public void setC_STATUS(Integer c_STATUS) {
        this.C_STATUS = c_STATUS;
    }

    public String getC_NOTE() {
        return C_NOTE;
    }

    public void setC_NOTE(String c_NOTE) {
        this.C_NOTE = c_NOTE;
    }

    public Integer getC_TOTAL_RECORD() {
        return C_TOTAL_RECORD;
    }

    public void setC_TOTAL_RECORD(Integer c_TOTAL_RECORD) {
        C_TOTAL_RECORD = c_TOTAL_RECORD;
    }
}
