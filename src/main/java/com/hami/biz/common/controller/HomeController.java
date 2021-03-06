package com.hami.biz.common.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hami.biz.common.service.CommonService;
import com.hami.biz.common.service.MenuService;
import com.hami.biz.login.controller.LoginController;
import com.hami.biz.login.model.User;
import com.hami.biz.system.utils.SecurityUtils;
import com.hami.sys.util.StringUtils;

/**
 * <pre>
 * <li>Program Name : HomeController
 * <li>Description  :
 * <li>History      : 2017. 7. 31.
 * </pre>
 *
 * @author HHG
 */
@Controller
public class HomeController {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    DataSource dataSource;

    @Autowired(required=true)
    private HttpServletRequest request;
    
    @Autowired
    LoginController loginController;
    
    @Autowired
    CommonService commonService;
    
    @Autowired
    MenuService menuService;

    @RequestMapping(value = { "/" }, method = RequestMethod.GET)
    public ModelAndView defaultPage() throws Exception {

        ModelAndView model = new ModelAndView();

        log.debug(" * isAuthenticated() : "+SecurityUtils.isAuthenticated());
        log.debug(" * isRememberMeAuthenticated() : "+SecurityUtils.isRememberMeAuthenticated());

        if(SecurityUtils.isAuthenticated() || SecurityUtils.isRememberMeAuthenticated()){
            
            User user = SecurityUtils.getUser();
            String ccd = user.getCcd();
            String user_id = user.getUsername();
            
            //TopMenu
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("TOP_MENU", menuService.getTopMenuList01(StringUtils.newMap("C_CD", ccd, "USER_ID", user_id)).get("ds_result"));

            //LeftMenu
            data.put("LEFT_MENU", mapper.writeValueAsString(menuService.getLeftMenuList01(StringUtils.newMap("C_CD", ccd, "USER_ID", user_id)).get("ds_result")));
            model.addAllObjects(data);
            
            model.setViewName("index.html");
        } else {
            //회사코드
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("COM_CD", commonService.getCommonCodeByCd(StringUtils.newMap("GRP_CD", "COM_CD")).get("ds_result"));
            model.addAllObjects(data);

            model.setViewName("login.html");
        }
        return model;
    }
    
    @RequestMapping("/favicon.ico")
    public String favicon() {
        return "forward:/img/favicon.ico";
    }

    @RequestMapping(value = "/admin**", method = RequestMethod.GET)
    public ModelAndView adminPage() {

        ModelAndView model = new ModelAndView();
        model.addObject("title", "Spring Security Remember Me");
        model.addObject("message", "This page is for ADMIN only!");
        model.setViewName("admin.jsp");
        return model;

    }

    /**
     * This update page is for user login with password only.
     * If user is logjn via remember me cookie, send login to ask for password again.
     * To avoid stolen remember me cookie to update anything
     */
    @RequestMapping(value = "/admin/update**", method = RequestMethod.GET)
    public ModelAndView updatePage(HttpServletRequest request) {

        ModelAndView model = new ModelAndView();

        if (SecurityUtils.isRememberMeAuthenticated()) {
            //send login for update
            SecurityUtils.setRememberMeTargetUrlToSession(request);
            model.addObject("loginUpdate", true);
            model.setViewName("/login.html");

        } else {
            model.setViewName("update");
        }

        return model;

    }
}
