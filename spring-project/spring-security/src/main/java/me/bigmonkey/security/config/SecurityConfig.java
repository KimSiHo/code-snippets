package me.bigmonkey.security.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
//@Configuration
//@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .anyRequest().authenticated()
        .and()
            .formLogin()
            .loginPage("/login.html")
            .defaultSuccessUrl("/home")
            .failureUrl("/login.html?error=true")
            .usernameParameter("username")
            .passwordParameter("password")
            .loginProcessingUrl("/login");
            //.successHandler(loginSuccessHandler())
            //.failureHandler(loginFailureHandler());

        http.logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login")
            .deleteCookies("JSESSIONID", "remember-me")
            .addLogoutHandler(logoutHandler())
            .logoutSuccessHandler(logoutSuccessHandler());

        http.rememberMe()
            .rememberMeParameter("remember")
            .tokenValiditySeconds(3600)
            .alwaysRemember(true) // 기본 값 false, 리멤버 기능을 체크 버튼 같은 것을 통해 활성화 하지 않아도 항상 실행하는지를 설정
            .userDetailsService(userDetailsService); // 기능 처리 중에, 시스템에 있는 사용자 계정을 조회하는 처리과정이 있는데 그 때, 필요

        http.sessionManagement()
            .maximumSessions(1)
            .maxSessionsPreventsLogin(true) // false : 기존 세션 만료(default)
            .and()
            .invalidSessionUrl("/invalid");

        http.sessionManagement()
            .sessionFixation().changeSessionId(); // 기본 값

        // 설정 시 구체적인 경로가 먼저 오고 그것 보다 큰 범위의 경로가 뒤에 오도록 해야 함
        http
            .antMatcher("/shop/**")
            .authorizeRequests()
                .antMatchers("/shop/login", "/shop/users/**").permitAll()
                .antMatchers("/shop/myplage").hasRole("USER")
                .antMatchers("/shop/admin/pay").access("hasRole('ADMIN')")
                .antMatchers("/shop/admin/**").access("hasRole('ADMIN') or hasRole('SYS ')")
                .anyRequest().authenticated();

    }

    // 인 메모리 방식으로 사용자 추가 가능
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password("{noop}1111").roles("USER");
    }

    // 스프링 시큐리티가 기본으로 제공하는 로그아웃 핸들러에서 세션을 무효화시키고, 인증 토큰을 삭제하는 등의 처리를 하는데
    // 그 처리 외에 추가적인 처리를 하고 싶을 때.
    // 기본적으로 로그아웃 처리는 post 처리이다
    private LogoutSuccessHandler logoutSuccessHandler() {
        return new LogoutSuccessHandler() {
            @Override
            public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                response.sendRedirect("/login");
            }
        };
    }

    private LogoutHandler logoutHandler() {
        return new LogoutHandler() {
            @Override
            public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
                final HttpSession session = request.getSession();
                session.invalidate();
            }
        };
    }

    private AuthenticationFailureHandler loginFailureHandler() {

        return null;
    }

    private AuthenticationSuccessHandler loginSuccessHandler() {

        return null;
    }
}
