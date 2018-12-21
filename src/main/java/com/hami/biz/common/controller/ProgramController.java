package com.hami.biz.common.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hami.biz.common.model.CommonResponseBody;
import com.hami.biz.common.model.GridResponseBody;
import com.hami.biz.common.service.MenuService;
import com.hami.biz.login.model.User;
import com.hami.biz.system.utils.SecurityUtils;
import com.hami.sys.util.StringUtils;

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
    public ModelAndView pgm(@PathVariable String pgmId, HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView();
        Boolean errYn = true;

        if(pgmId != null && !"null".equals(pgmId)){
            //Program Info
            User user = SecurityUtils.getUser();
            Map<String, Object> programInfo = new HashMap<String, Object>();
            programInfo = (Map<String, Object>) menuService.getProgram01(StringUtils.newMap("C_CD", user.getCcd(), "PGM_ID", pgmId, "USER_ID", user.getUsername())).get("ds_result");
            
            if(!StringUtils.isEmpty(programInfo)){
                String url = (String) programInfo.get("PGM_URL");
                
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                mav.addObject("programId", "pid_"+timestamp.getTime());
                
                if(StringUtils.nvl(request.getParameter("menuId"),null) != null){
                    mav.addObject("menuId", request.getParameter("menuId"));
                }
                
                mav.setViewName(url);
                errYn = false;
            } 
        } 
        
        if(errYn){
            mav.setViewName("/com/exception/404_pgm.html");
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
    
    @RequestMapping(value = "/gridCfg", method = RequestMethod.GET)
    public ResponseEntity<GridResponseBody>  gridCfg(@RequestBody Map<String, Object> paramMap, HttpServletRequest request) throws Exception {
        GridResponseBody result = new GridResponseBody();
        if(!paramMap.isEmpty()){
            result.setCode(HttpStatus.OK);
            result.setMsg("");
            Map<String, Object> gridCfg = new HashMap<String, Object>();
            
            
            result.setResult(gridCfg);
        } else {
            result.setCode(HttpStatus.BAD_REQUEST);
            result.setMsg("Parameter needed");
        }
        
        return new ResponseEntity<GridResponseBody>(result, result.getCode());
    }

}
