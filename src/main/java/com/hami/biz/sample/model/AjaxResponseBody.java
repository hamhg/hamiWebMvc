package com.hami.biz.sample.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.util.List;

/**
 * <pre>
 * <li>Program Name : CommonResponseBody
 * <li>Description  :
 * <li>History      : 2017. 7. 29.
 * </pre>
 *
 * @author HHG
 */
public @Data class AjaxResponseBody {

    @JsonView(JsonView.class)
    String msg;
    @JsonView(JsonView.class)
    String code;
    @JsonView(JsonView.class)
    List<User> result;

    @Override
    public String toString() {
        return "AjaxResponseResult [msg=" + msg + ", code=" + code + ", result=" + result + "]";
    }

}
