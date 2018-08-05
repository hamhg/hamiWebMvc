package com.hami.biz.common.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Map;

/**
 * <pre>
 * <li>Program Name : CommonResponseBody
 * <li>Description  :
 * <li>History      : 2017. 7. 29.
 * </pre>
 *
 * @author HHG
 */
public @Data class CommonResponseBody {

    @JsonView(JsonView.class)
    String msg;
    @JsonView(JsonView.class)
    HttpStatus code;
    @JsonView(JsonView.class)
    Map<String, Object> systemHeader;
    @JsonView(JsonView.class)
    Map<String, Object> transactionHeader;
    @JsonView(JsonView.class)
    Map<String, Object> messageHeader;
    @JsonView(JsonView.class)
    Map<String, Object> resultData;

    @Override
    public String toString() {
        return "CommonResult [msg=" + msg + ", code=" + code + ", result=" + resultData + "]";
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public HttpStatus getCode() {
        return code;
    }

    public void setCode(HttpStatus code) {
        this.code = code;
    }

    public Map<String, Object> getSystemHeader() {
        return systemHeader;
    }

    public void setSystemHeader(Map<String, Object> systemHeader) {
        this.systemHeader = systemHeader;
    }

    public Map<String, Object> getTransactionHeader() {
        return transactionHeader;
    }

    public void setTransactionHeader(Map<String, Object> transactionHeader) {
        this.transactionHeader = transactionHeader;
    }

    public Map<String, Object> getMessageHeader() {
        return messageHeader;
    }

    public void setMessageHeader(Map<String, Object> messageHeader) {
        this.messageHeader = messageHeader;
    }

    public Map<String, Object> getResultData() {
        return resultData;
    }

    public void setResultData(Map<String, Object> resultData) {
        this.resultData = resultData;
    }
}
