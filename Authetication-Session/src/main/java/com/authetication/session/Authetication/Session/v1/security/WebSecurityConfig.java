package com.authetication.session.Authetication.Session.v1.security;

import com.authetication.session.Authetication.Session.v1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

                 http.csrf().disable().authorizeRequests()
                         .antMatchers(HttpMethod.POST, "/user").permitAll()
                         .antMatchers(HttpMethod.PUT, "/user/update-passoword").hasAnyRole("ADMIN")
                         .anyRequest().authenticated()
                         .and().formLogin()
                         .usernameParameter("login")
                         .passwordParameter("password")
                         .successHandler(sucessHander())
                         .failureUrl("/login?error")
                         .and()
                         .userDetailsService(userService)
                         .logout()
                         .logoutUrl("/logout")
                         .invalidateHttpSession(true)
                         .deleteCookies("JSESSIONID");
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception{
        web.ignoring().antMatchers("/materialize/**", "/style/**");
    }

    private AuthenticationSuccessHandler sucessHander() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(
                    HttpServletRequest request, HttpServletResponse response,
                                                Authentication authentication) {
                try {
                    response.getWriter().write("it work");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
    }
}
