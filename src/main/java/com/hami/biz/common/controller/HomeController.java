package com.hami.biz.common.controller;

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
public class HomeController {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DataSource dataSource;

    // index.html
    @RequestMapping("/index")
    public String index() {
        return "index.html";
    }

    // Login form with error
    @RequestMapping("/login-error.html")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login.html";
    }

    @RequestMapping(value = { "/", "/welcome**" }, method = RequestMethod.GET)
    public ModelAndView defaultPage() {

        ModelAndView model = new ModelAndView();

        log.debug(" * isAuthenticated() : "+isAuthenticated());
        log.debug(" * isRememberMeAuthenticated() : "+isRememberMeAuthenticated());

        if(isAuthenticated() || isRememberMeAuthenticated()){
            model.setViewName("index.html");
        } else {
            model.setViewName("login.html");
        }
        return model;

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

        if (isRememberMeAuthenticated()) {
            //send login for update
            setRememberMeTargetUrlToSession(request);
            model.addObject("loginUpdate", true);
            model.setViewName("/login.html");

        } else {
            model.setViewName("update");
        }

        return model;

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
            String targetUrl = getRememberMeTargetUrlFromSession(request);
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

        if(isAuthenticated() || isRememberMeAuthenticated()){
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
            log.debug("isAuthenticated()==="+isAuthenticated());
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

    /**
     * If the login in from remember me cookie, refer
     * org.springframework.security.authentication.AuthenticationTrustResolverImpl
     */
    private boolean isRememberMeAuthenticated() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return false;
        }
        return RememberMeAuthenticationToken.class.isAssignableFrom(auth.getClass());
    }
    /**
     * If the login in from remember me cookie, refer
     * org.springframework.security.authentication.AuthenticationTrustResolverImpl
     */
    private boolean isAuthenticated() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || "anonymousUser".equals(auth.getName())) {
            return false;
        } else {
            return auth.isAuthenticated();
        }

    }

    /**
     * save targetURL in session
     */
    private void setRememberMeTargetUrlToSession(HttpServletRequest request){
        String targetUrl;
        HttpSession session = request.getSession(false);
        if(session!=null){
            targetUrl = session.getAttribute("targetUrl")==null?"":session.getAttribute("targetUrl").toString();
            session.setAttribute("targetUrl", targetUrl);
        }
    }

    /**
     * get targetURL from session
     */
    private String getRememberMeTargetUrlFromSession(HttpServletRequest request){
        String targetUrl = "";
        HttpSession session = request.getSession(false);
        if(session!=null){
            targetUrl = session.getAttribute("targetUrl")==null?"":session.getAttribute("targetUrl").toString();
        }
        return targetUrl;
    }
}
