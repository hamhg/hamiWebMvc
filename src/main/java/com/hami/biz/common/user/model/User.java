package com.hami.biz.common.user.model;

import lombok.Data;

/**
 * <pre>
 * <li>Program Name : User
 * <li>Description  :
 * <li>History      : 2018. 1. 8.
 * </pre>
 *
 * @author HHG
 */
public @Data class User {
    private String ccd;
    private String userid;
    private String password;
    private String authority;
}
