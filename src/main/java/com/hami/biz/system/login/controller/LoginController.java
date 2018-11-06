package com.hami.biz.system.login.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hami.biz.system.utils.SecurityUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 * <pre>
 * <li>Program Name : CommonController
 * <li>Description  :
 * <li>History      : 2017. 7. 31.
 * </pre>
 *
 * @author HHG
 */
@Controller
public class LoginController {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DataSource dataSource;

    // Login form with error
    @RequestMapping("/login-error.html")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login.html";
    }

    /**
     * both "normal login" and "login for update" shared this form.
     *
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout, HttpServletRequest request) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("errorError", true);

            //login form for update, if login error, get the targetUrl from session again.
            String targetUrl = SecurityUtils.getRememberMeTargetUrlFromSession(request);
            log.debug("targetUrl====="+targetUrl);
            if(StringUtils.hasText(targetUrl)){
                model.addObject("targetUrl", targetUrl);
                model.addObject("loginUpdate", true);
            }

        }

        if (logout != null) {
            log.debug("logout");
            model.addObject("msg", "You've been logged out successfully.");
        }

        if(SecurityUtils.isAuthenticated() || SecurityUtils.isRememberMeAuthenticated()){
            model.setViewName("index.html");
        } else {
            model.setViewName("login.html");
        }

        return model;

    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            persistentTokenRepository().removeUserTokens(auth.getName());
            new SecurityContextLogoutHandler().logout(request, response, auth);
            SecurityContextHolder.getContext().setAuthentication(null);
            log.debug("isAuthenticated()==="+SecurityUtils.isAuthenticated());
        }
        model.addAttribute("logout", true);
        return new ModelAndView("redirect:/login?logout=true", model);
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);
        return db;
    }
}
