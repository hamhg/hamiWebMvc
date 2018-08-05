package com.hami.web.model;

import java.util.List;
import lombok.Data;

import com.fasterxml.jackson.annotation.JsonView;
import com.hami.web.sample.dao.User;
import com.hami.web.sample.views.JsonViews;

/**
 * <pre>
 * <li>Program Name : AjaxResponseBody
 * <li>Description  :
 * <li>History      : 2017. 7. 29.
 * </pre>
 *
 * @author HHG
 */
public @Data class AjaxResponseBody {

    @JsonView(JsonViews.Public.class)
    String msg;
    @JsonView(JsonViews.Public.class)
    String code;
    @JsonView(JsonViews.Public.class)
    List<User> result;

    @Override
    public String toString() {
        return "AjaxResponseResult [msg=" + msg + ", code=" + code + ", result=" + result + "]";
    }

}
