package com.hami.biz.common.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;

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
public class GridResponseBody {

    @JsonView(JsonView.class)
    String msg;
    @JsonView(JsonView.class)
    HttpStatus code;
    @JsonView(JsonView.class)
    Map<String, Object> result;

    @Override
    public String toString() {
        return "GridResponseResult [msg=" + msg + ", code=" + code + ", result=" + result.toString() + "]";
    }

}
