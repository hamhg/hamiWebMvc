package com.hami.biz.sample.model;

import lombok.Data;

/**
 * <pre>
 * <li>Program Name : SearchCriteria
 * <li>Description  :
 * <li>History      : 2017. 7. 29.
 * </pre>
 *
 * @author HHG
 */
public @Data class SearchCriteria {

    String username;
    String email;

    @Override
    public String toString() {
        return "SearchCriteria [username=" + username + ", email=" + email + "]";
    }

}
