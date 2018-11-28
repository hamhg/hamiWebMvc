package com.hami.biz.system.config;

import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.hami.biz.login.service.CustomJdbcTokenRepositoryImpl;
import com.hami.biz.login.service.CustomPersistentTokenBasedRememberMeServices;
import com.hami.biz.login.service.CustomSavedRequestAwareAuthenticationSuccessHandler;
import com.hami.biz.login.service.CustomUserDetailsManager;
import com.hami.biz.login.service.CustomUsernamePasswordAuthenticationFilter;

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
    public static final int TOKEN_VALIDITY_SECONDS = 60 * 60 * 24 * 3; // 3 days
    private static final String REMEMBER_ME_KEY = UUID.randomUUID().toString();
    private static final String AUTH_PATH = "/auth/login";

    private static final String[] UNSECURED_RESOURCE_LIST = new String[] {  
            "/resources/**","/libs/**","/css/**","/js/**","/img/**","/images/**","/fonts/**","/favicon.ico"
    };

    private static final String[] UNAUTHORIZED_RESOURCE_LIST = new String[] {
            "/","/login.html","/403.html"
    };

    public CustomUserDetailsManager userDetailsService;
    
    @Autowired
    DataSource dataSource; 

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        CustomUserDetailsManager userDetailsService = new CustomUserDetailsManager();
        this.userDetailsService = userDetailsService;
        userDetailsService.setDataSource(dataSource);

        //@formatter:off
        auth
            .userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder())
            .and()
                .authenticationProvider(new RememberMeAuthenticationProvider(REMEMBER_ME_KEY));
        
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
                .addFilterBefore(customUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                    .loginPage("/login")
                    .failureUrl("/login-error.html")
                    .loginProcessingUrl(AUTH_PATH)
                    //.successForwardUrl("/index")
                    .successHandler(customLoginSuccessHandler())
                    .defaultSuccessUrl("/")
                    .usernameParameter("userid")
                    .passwordParameter("password")
                    .permitAll()
            .and()
                .logout()
                    .logoutUrl( "/logout" )
                    .logoutSuccessUrl( "/" )
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
            .and()
                .sessionManagement()
                    .sessionFixation()
                        .migrateSession()
            .and()
                .rememberMe()
                    .tokenRepository(customPersistentTokenRepository())
                    .tokenValiditySeconds(TOKEN_VALIDITY_SECONDS) 
                    .rememberMeServices(customPersistentTokenBasedRememberMeServices())
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
    public PersistentTokenRepository customPersistentTokenRepository() {
        CustomJdbcTokenRepositoryImpl repository = new CustomJdbcTokenRepositoryImpl();
        repository.setDataSource(dataSource);
        return repository;
    }

    @Bean
    public CustomPersistentTokenBasedRememberMeServices customPersistentTokenBasedRememberMeServices() {
        CustomPersistentTokenBasedRememberMeServices service = new CustomPersistentTokenBasedRememberMeServices(REMEMBER_ME_KEY, userDetailsService, customPersistentTokenRepository());
        service.setCookieName("remember-me");
        service.setTokenValiditySeconds(TOKEN_VALIDITY_SECONDS);
        return service;
    }
    
    @Bean
    public CustomUsernamePasswordAuthenticationFilter customUsernamePasswordAuthenticationFilter() throws Exception {
        CustomUsernamePasswordAuthenticationFilter filter = new CustomUsernamePasswordAuthenticationFilter();
        filter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(AUTH_PATH, "POST"));
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setRememberMeServices(customPersistentTokenBasedRememberMeServices());
        return filter;
    }

    @Bean
    public SavedRequestAwareAuthenticationSuccessHandler customLoginSuccessHandler() {
        return new CustomSavedRequestAwareAuthenticationSuccessHandler("/");
    }
    
}
