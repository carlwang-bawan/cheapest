package com.bawan.cheapest.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangzhen on 2016/11/11.
 */
public class ExternalResponse {
    Long elapsedTime ;
    Integer errorCode;
    String errorDesc;
    public static final String SUCCESS_CODE_DESC="成功";

    public ExternalResponse(){
        elapsedTime = System.currentTimeMillis();
    }

    public void  markSuccess(){
        this.setErrorCode(0);
        this.setErrorDesc(SUCCESS_CODE_DESC);
        this.setElapsedTime(System.currentTimeMillis() - elapsedTime);
    }

    public void markFailed(Integer errorCode,String errorDesc){
        this.setErrorCode(errorCode);
        this.setErrorDesc(errorDesc);
        this.setElapsedTime(System.currentTimeMillis() - elapsedTime);
    }

    private Map<String, Object> body = new HashMap<String, Object>();

    public Long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(Long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    public Map<String, Object> getBody() {
        return body;
    }

    public void setBody(Map<String, Object> body) {
        this.body = body;
    }
}
