package com.hami.biz.sample.model;

import java.util.Date;

import lombok.Data;

/**
 * <pre>
 * <li>Program Name : Customer
 * <li>Description  :
 * <li>History      : 2017. 8. 12.
 * </pre>
 *
 * @author HHG
 */
@Data
public class Customer {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private Date dateOfBirth;

    public Customer(long id, String firstName, String lastName, String email, String mobile) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobile = mobile;
        this.dateOfBirth = new Date();
    }

}
