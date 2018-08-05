package com.hami.web.sample.controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * <pre>
 * <li>Program Name : RestTestController
 * <li>Description  :
 * <li>History      : 2017. 7. 31.
 * </pre>
 *
 * @author HHG
 */
@RestController
public class RestTestController {

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public HashMap<String, Object> test3() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("abcaa", "ddeeed");
        return map;
    }

}
