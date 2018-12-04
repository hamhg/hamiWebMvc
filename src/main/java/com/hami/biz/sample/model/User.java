package com.hami.biz.sample.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

/**
 * <pre>
 * <li>Program Name : User
 * <li>Description  :
 * <li>History      : 2017. 7. 29.
 * </pre>
 *
 * @author HHG
 */
@Data
public class User {

    @JsonView(JsonView.class)
    String username;
    String password;
    @JsonView(JsonView.class)
    String email;
    @JsonView(JsonView.class)
    String phone;
    String address;

    public User() {
    }

    public User(String username, String password, String email, String phone, String address) {
        super();
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    @Override
    public String toString() {
        return "User [username=" + username + ", password=" + password + ", email=" + email + ", phone=" + phone
                + ", address=" + address + "]";
    }
}
