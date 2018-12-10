package com.hami.biz.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hami.biz.common.service.MenuService;
import com.hami.biz.system.utils.SecurityUtils;
import com.hami.sys.exception.BizException;
import com.hami.sys.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
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
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/pgm/{pgmId}", method = RequestMethod.GET)
    public ModelAndView pgm(@PathVariable String pgmId) throws Exception {
        ModelAndView mav = new ModelAndView();
        
        //Program Info
        String ccd = SecurityUtils.getUser().getCcd();
        String userId = SecurityUtils.getUser().getUsername();
        Map<String, Object> programInfo = new HashMap<String, Object>();
        programInfo = ((List<Map>) menuService.getProgram01(StringUtils.newMap("C_CD", ccd, "PGM_ID", pgmId, "USER_ID", userId)).get("ds_result")).get(0);
        if(!StringUtils.isEmpty(programInfo)){
            String url = (String) programInfo.get("PGM_URL");
            
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            mav.addObject("programId", "pid_"+timestamp.getTime());
            
            mav.setViewName(url);
        } else {
            mav.addObject("error", "No file");
        }
        
        return mav;
    }
    
    @RequestMapping(value = "/treeGridInit", method = RequestMethod.GET)
    public ModelAndView treeGridInit() throws Exception {
        ModelAndView mav = new ModelAndView();
        
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        mav.addObject("programId", "pid_"+timestamp.getTime());
        mav.setViewName("/com/inc/treeGridInit.html");
        
        return mav;
    }

}
