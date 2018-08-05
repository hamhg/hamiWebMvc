package com.hami.sys.config;

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
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

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

    private static final String[] UNSECURED_RESOURCE_LIST = new String[] {
            "/resources/**", "/assets/**", "/css/**"
        };

    private static final String[] UNAUTHORIZED_RESOURCE_LIST = new String[] {
            "/test.html", "/", "/error*"
        };

    @Autowired
    DataSource dataSource;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        JdbcUserDetailsManager userDetailsService = new JdbcUserDetailsManager();
        userDetailsService.setDataSource(dataSource);

        //@formatter:off
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder())
            .and()
                .jdbcAuthentication()
                    .dataSource(dataSource)
                    .usersByUsernameQuery("select username, password, enabled from users where username=?")
                    .authoritiesByUsernameQuery("select username, role from user_roles where username=?");
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
        http.authorizeRequests()
            .antMatchers("/**").access("hasRole('ROLE_USER')")
            .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
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
                    .failureUrl("/login?error")
                    .loginProcessingUrl("/auth/login_check")
                    .successForwardUrl("/home")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .permitAll()
            .and()
                .logout()
                    .logoutUrl( "/j_spring_security_logout" )
                    .logoutSuccessUrl( "/" )
                    .invalidateHttpSession(true)
                    //.deleteCookies(cookieNamesToClear)
            .and()
                .rememberMe()
                    .tokenRepository(persistentTokenRepository())
                    .tokenValiditySeconds(60 * 60 * 24 * 10) // 10 days
            .and()
                .csrf()
            .and()
                .exceptionHandling()
                    .accessDeniedPage( "/403.html" );
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
