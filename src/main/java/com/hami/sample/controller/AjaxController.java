package com.hami.web.sample.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.hami.web.sample.views.JsonViews;
import com.hami.web.model.AjaxResponseBody;
import com.hami.web.model.SearchCriteria;
import com.hami.web.sample.dao.User;

/**
 * <pre>
 * <li>Program Name : AjaxController
 * <li>Description  :
 * <li>History      : 2017. 7. 29.
 * </pre>
 *
 * @author HHG
 */
@RestController
public class AjaxController {

    List<User> users;
    Logger log = LoggerFactory.getLogger(getClass().getName());

    // @ResponseBody, not necessary, since class is annotated with @RestController
    // @RequestBody - Convert the json data into object (SearchCriteria) mapped by field name.
    // @JsonView(Views.Public.class) - Optional, limited the json data display to client.
    @JsonView(JsonViews.Public.class)
    @RequestMapping(value = "/search/api/getSearchResult")
    public AjaxResponseBody getSearchResultViaAjax(@RequestBody SearchCriteria search) {

        AjaxResponseBody result = new AjaxResponseBody();

        log.debug("username ===="+search.getUsername());

        if (isValidSearchCriteria(search)) {
            List<User> users = findByUserNameOrEmail(search.getUsername(), search.getEmail());

            if (users.size() > 0) {
                result.setCode("200");
                result.setMsg("");
                result.setResult(users);
            } else {
                result.setCode("204");
                result.setMsg("No user!");
            }

        } else {
            result.setCode("400");
            result.setMsg("Search criteria is empty!");
        }

        //AjaxResponseBody will be converted into json format and send back to client.
        return result;

    }

    private boolean isValidSearchCriteria(SearchCriteria search) {

        boolean valid = true;

        if (search == null) {
            valid = false;
        }

        if ((StringUtils.isEmpty(search.getUsername())) && (StringUtils.isEmpty(search.getEmail()))) {
            valid = false;
        }

        return valid;
    }

    // Init some users for testing
    @PostConstruct
    private void iniDataForTesting() {
        users = new ArrayList<User>();

        User user1 = new User("hami", "pass123", "hami@yahoo.com", "012-1234567", "address 123");
        User user2 = new User("yflow", "pass456", "yflow@yahoo.com", "016-7654321", "address 456");
        User user3 = new User("laplap", "pass789", "hami@yahoo.com", "012-111111", "address 789");
        users.add(user1);
        users.add(user2);
        users.add(user3);

    }

    // Simulate the search function
    private List<User> findByUserNameOrEmail(String username, String email) {

        List<User> result = new ArrayList<User>();

        for (User user : users) {

            if ((!StringUtils.isEmpty(username)) && (!StringUtils.isEmpty(email))) {

                if (username.equals(user.getUsername()) && email.equals(user.getEmail())) {
                    result.add(user);
                    continue;
                } else {
                    continue;
                }

            }
            if (!StringUtils.isEmpty(username)) {
                if (username.equals(user.getUsername())) {
                    result.add(user);
                    continue;
                }
            }

            if (!StringUtils.isEmpty(email)) {
                if (email.equals(user.getEmail())) {
                    result.add(user);
                    continue;
                }
            }

        }

        return result;

    }
}
