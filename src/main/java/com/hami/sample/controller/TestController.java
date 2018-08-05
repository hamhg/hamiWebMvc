package com.hami.web.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * <li>Program Name : TestController
 * <li>Description  :
 * <li>History      : 2017. 7. 31.
 * </pre>
 *
 * @author HHG
 */
@Controller
public class TestController {

    @RequestMapping(value = { "/dbTest" }, method = RequestMethod.GET)
    public ModelAndView dbTest() {

        ModelAndView model = new ModelAndView();
        model.setViewName("test/dbTest.jsp");
        return model;

    }

    @RequestMapping(value = "/ajaxTest", method = RequestMethod.GET)
    public String ajaxTest(ModelMap model) {

        return "test/ajax.html";

    }

    @RequestMapping(value = "/restTest", method = RequestMethod.GET)
    public String restTest(ModelMap model) {

        return "test/rest.html";

    }

}
