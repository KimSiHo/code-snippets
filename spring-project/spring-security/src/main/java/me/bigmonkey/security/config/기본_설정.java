package me.bigmonkey.security.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
//@Configuration
//@EnableWebSecurity
public class 기본_설정 extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 폼 로그인 인증 기능
        http.formLogin()
            .loginPage("/login.html")
            .defaultSuccessUrl("/home")
            .failureUrl("/login.html?error=true")
            .usernameParameter("username")
            .passwordParameter("password")
            .loginProcessingUrl("/login");
        //.successHandler(loginSuccessHandler())
        //.failureHandler(loginFailureHandler());

        // 로그아웃 기능
        http.logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login")
            .deleteCookies("JSESSIONID", "remember-me")
            .addLogoutHandler(logoutHandler())
            .logoutSuccessHandler(logoutSuccessHandler());

        // 리멤버 미 인증
        http.rememberMe()
            .rememberMeParameter("remember")
            .tokenValiditySeconds(3600)
            .alwaysRemember(true) // 기본 값 false, 리멤버 기능을 체크 버튼 같은 것을 통해 활성화 하지 않아도 항상 실행하는지를 설정
            .userDetailsService(userDetailsService); // 기능 처리 중에, 시스템에 있는 사용자 계정을 조회하는 처리과정이 있는데 그 때, 필요

        // 세션 관리 기능
        http.sessionManagement() // 세션 관리 기능이 작동함
            .maximumSessions(1) // 최대 허용 가능 세션 수, -1 : 무제한 로그인 세션 허용
            .maxSessionsPreventsLogin(true) // 동시 로그인 차단함, false : 기존 세션 만료(default)
            .and()
            .invalidSessionUrl("/invalid"); // 세션이 유효하지 않을 때 이동할 페이지

        http.sessionManagement()
            .sessionFixation().changeSessionId(); // 기본 값
        // changeSessionId는 session id 값만 바꾼다. 서블릿 3.1이상 기본 값.
        // mS는 세션 아이디와, 세션을 새로 생성. 3.1이하 기본 값.
        // cS와 mS는 기존 세션에서 사용하던 속성값을 그대로 사용할 수 있지만. nS는 초기화되어서 세션에 속성값들을 다시 설정해 주어야 한다.

        http.exceptionHandling()
            .authenticationEntryPoint(null)
            .accessDeniedHandler(null);
    }

    // permitAll과 비슷하지만 permitAll은 필터로 들어오지만 ignoring 함수는 필터의 밖에 있으므로 비용적인 측면에서 더 좋다
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());

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

    // RequestCacheAwareFilter가 세션에 저장되있던 savedRequest객체를 꺼내와서 null이 아니면, 다음 필터로 보내는 역할을 한다.
    private AuthenticationSuccessHandler loginSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
                throws IOException, ServletException {
                RequestCache requestCache = new HttpSessionRequestCache();
                SavedRequest savedRequest = requestCache.getRequest(request, response);
                String redirectUrl = savedRequest.getRedirectUrl();
                response.sendRedirect(redirectUrl);
                return;
            }
        };
    }
}
