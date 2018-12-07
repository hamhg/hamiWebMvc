package com.hami.biz.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;

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

    @RequestMapping(value = "/test/{page}", method = RequestMethod.GET)
    public ModelAndView test(@PathVariable String page) {
        ModelAndView mav = new ModelAndView();

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        mav.addObject("programId", "pid_"+timestamp.getTime());

        mav.setViewName("test/"+page+".html");
        return mav;

    }

    @RequestMapping(value = "/testJsp/{page}", method = RequestMethod.GET)
    public ModelAndView testJsp(@PathVariable String page) {
        ModelAndView mav = new ModelAndView();

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        mav.addObject("programId", "pid_"+timestamp.getTime());

        mav.setViewName("test/"+page+".jsp");
        return mav;

    }

    @RequestMapping(value = "/excelDown", method = RequestMethod.GET)
    public String excelDn() {

        return "test/excelDown.html";

    }

    @RequestMapping(value = { "/file/excelDown" }, method = RequestMethod.GET)
    public ModelAndView excelDown(HttpServletRequest req, HttpServletResponse res) {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("excelView");
        return mav;
    }
}
