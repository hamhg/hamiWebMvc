package com.hami.biz.sample.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.hami.biz.sample.model.AjaxResponseBody;
import com.hami.biz.sample.model.User;

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

    private List<User> users;
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    // @ResponseBody, not necessary, since class is annotated with @RestController
    // @RequestBody - Convert the json data into object (SearchCriteria) mapped by field name.
    // @JsonView(Views.Public.class) - Optional, limited the json data display to client.
    @JsonView(JsonView.class)
    @RequestMapping(value = "/search/api/getSearchResult")
    public AjaxResponseBody getSearchResultViaAjax(@RequestBody Map<String, Object> search) {

        AjaxResponseBody result = new AjaxResponseBody();
        log.debug("username ===="+search.get("username"));

        if (isValidSearchCriteria(search)) {
            List<User> users = findByUserNameOrEmail((String) search.get("username"), (String) search.get("email"));

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

        //CommonResponseBody will be converted into json format and send back to client.
        return result;

    }

    private boolean isValidSearchCriteria(Map<String, Object> search) {

        boolean valid = true;

        if (search == null) {
            valid = false;
        }

        if ((StringUtils.isEmpty(search.get("username"))) && (StringUtils.isEmpty(search.get("email")))) {
            valid = false;
        }

        return valid;
    }

    // Init some users for testing
    @PostConstruct
    private void iniDataForTesting() {
        users = new ArrayList<User>();

        User user1 = new User("hami", "pass123", "hami@yahoo.com", "012-1234567", "address 123");
        User user2 = new User("ham", "pass456", "yflow@yahoo.com", "016-7654321", "address 456");
        User user3 = new User("kim", "pass543", "kim@yahoo.com", "012-115551", "address 25");
        User user4 = new User("lee", "pass234", "lee@yahoo.com", "012-1134251", "address 752");
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);

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
