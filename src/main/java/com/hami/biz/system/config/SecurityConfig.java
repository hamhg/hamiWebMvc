package com.hami.biz.system.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.hami.biz.system.login.service.CustomUserDetailsManager;
import com.hami.biz.system.login.service.UserAuthService;

import javax.sql.DataSource;

/**
 * <pre>
 * <li>Program Name : SecurityConfig
 * <li>Description  :
 * <li>History      : 2017. 7. 28.
 * </pre>
 *
 * @author HHG
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    private static final String[] UNSECURED_RESOURCE_LIST = new String[] {
            "/resources/**","/libs/**","/css/**","/js/**","/img/**","/images/**","/fonts/**"
    };

    private static final String[] UNAUTHORIZED_RESOURCE_LIST = new String[] {
            "/login.html","/403.html"
    };

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserAuthService();
    };
    
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
    	CustomUserDetailsManager userDetailsService = new CustomUserDetailsManager();
        userDetailsService.setDataSource(dataSource);

        //@formatter:off
        auth
            .userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
        //@formatter:on
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //@formatter:off
        web
            .ignoring()
                .antMatchers(UNSECURED_RESOURCE_LIST);
        //@formatter:on
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //@formatter:off
        http
            .authorizeRequests()
                //.antMatchers("/**").access("hasRole('USER')")
                .antMatchers("/admin/**").access("hasRole('ADMIN')")
                //.antMatchers("/wc/**").access("hasRole('ROLE_DBA')")
            .and()
                .authorizeRequests()
                    .antMatchers(UNAUTHORIZED_RESOURCE_LIST)
                        .permitAll()
            .and()
                .authorizeRequests()
                    .anyRequest()
                        .authenticated()
            .and()
                .formLogin()
                    .successHandler(savedRequestAwareAuthenticationSuccessHandler())
                    .loginPage("/login")
                    .failureUrl("/login-error.html")
                    .loginProcessingUrl("/auth/login_check")
                    .successForwardUrl("/index")
                    .usernameParameter("userid")
                    .passwordParameter("password")
                    .permitAll()
            .and()
                .logout()
                    .logoutUrl( "/logout" )
                    .logoutSuccessUrl( "/index" )
                    .invalidateHttpSession(true)
                    //.deleteCookies(cookieNamesToClear)
            .and()
                .sessionManagement()
                    .sessionFixation()
                        .migrateSession()
            .and()
                .rememberMe()
                    .tokenRepository(persistentTokenRepository())
                    .tokenValiditySeconds(60 * 60 * 24 * 3) // 3 days
            .and()
                .csrf()
            .and()
                .exceptionHandling()
                    .accessDeniedPage( "/403" );
        //@formatter:on
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        SessionRegistry sessionRegistry = new SessionRegistryImpl();
        return sessionRegistry;
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);
        return db;
    }

    @Bean
    public SavedRequestAwareAuthenticationSuccessHandler savedRequestAwareAuthenticationSuccessHandler() {
        SavedRequestAwareAuthenticationSuccessHandler auth = new SavedRequestAwareAuthenticationSuccessHandler();
        auth.setTargetUrlParameter("targetUrl");
        return auth;
    }
    
}
