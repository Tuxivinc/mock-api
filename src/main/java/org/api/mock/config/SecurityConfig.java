package org.api.mock.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * The Security config.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // TODO : Pour l'instant désactivé, mais pourra nous servir par la suite

    /*@Override
    public void configure(WebSecurity web) throws Exception {
        // Ignore any request that starts with /resources or /webjars
        web.ignoring()
                .antMatchers("/resources*//**")
     .antMatchers("/webjars*/
    /**
     * ");
     * }
     */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
/*        // for app access
        http.authorizeRequests()
                .antMatchers("/configuration").permitAll()
                .antMatchers("/user").permitAll()
                .anyRequest().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/auth_error")
                .and()
                .formLogin().loginPage("/login").failureUrl("/login?error").permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/").invalidateHttpSession(true);
        // for management access with basic auth
        http.httpBasic()
                .and()
                .authorizeRequests()
                //.antMatchers("/management*//**//**").permitAll();
         */

        http.authorizeRequests().antMatchers("*//**").permitAll();
    }

    /*@Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .passwordEncoder(new BCryptPasswordEncoder());
    }*/
}