package com.hami.biz.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Data 
public class CommonResponseBody {

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
    @JsonFormat(locale = "Ko", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    Map<String, Object> resultData;

    @Override
    public String toString() {
        return "CommonResult { msg=" + msg + ", code=" + code + ", result=" + resultData + " }";
    }
}
