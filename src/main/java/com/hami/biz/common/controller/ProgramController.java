package com.hami.biz.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hami.biz.common.service.MenuService;
import com.hami.biz.system.utils.SecurityUtils;
import com.hami.sys.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * <li>Program Name : ProgramController
 * <li>Description  :
 * <li>History      : 2017. 7. 31.
 * </pre>
 *
 * @author HHG
 */
@Controller
public class ProgramController {

    @Autowired
    MenuService menuService;
    
    @RequestMapping(value = "/pgm/{pageId}", method = RequestMethod.GET)
    public ModelAndView example(@PathVariable String pageId) {
        ModelAndView mav = new ModelAndView();
        
        //Program Info
        String ccd = SecurityUtils.getUser().getCcd();
        Map<String, Object> programInfo = new HashMap<String, Object>();
        programInfo = (Map<String, Object>) menuService.getProgram01(StringUtils.newMap("C_CD", ccd, "USER_ID", pageId)).get("ds_result");
        
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        mav.addObject("programId", "pid_"+timestamp.getTime());

        mav.setViewName(page+".html");
        return mav;

    }

}
