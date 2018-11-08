package com.hami.biz.system.config;

import javax.sql.DataSource;

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.hami.biz.login.service.CustomJdbcTokenRepositoryImpl;
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

    @Autowired
    DataSource dataSource;

    private static final String[] UNSECURED_RESOURCE_LIST = new String[] {
            "/resources/**","/libs/**","/css/**","/js/**","/img/**","/images/**","/fonts/**"
    };

    private static final String[] UNAUTHORIZED_RESOURCE_LIST = new String[] {
            "/login.html","/403.html"
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
                .addFilterBefore(customUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
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
        CustomJdbcTokenRepositoryImpl db = new CustomJdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);
        return db;
    }

    @Bean
    public CustomUsernamePasswordAuthenticationFilter customUsernamePasswordAuthenticationFilter() throws Exception {
        CustomUsernamePasswordAuthenticationFilter customUsernamePasswordAuthenticationFilter = new CustomUsernamePasswordAuthenticationFilter();
        customUsernamePasswordAuthenticationFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/auth/login_check", "POST"));
        customUsernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        return customUsernamePasswordAuthenticationFilter;
    }

    @Bean
    public SavedRequestAwareAuthenticationSuccessHandler savedRequestAwareAuthenticationSuccessHandler() {
        SavedRequestAwareAuthenticationSuccessHandler auth = new SavedRequestAwareAuthenticationSuccessHandler();
        auth.setTargetUrlParameter("targetUrl");
        return auth;
    }
    
}
