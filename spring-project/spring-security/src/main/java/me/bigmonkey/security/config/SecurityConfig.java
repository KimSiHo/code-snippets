package me.bigmonkey.security.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

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
