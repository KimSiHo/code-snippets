package me.bigmonkey.fai.common.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    // 인터셉터는 필터와 달리 요청 type같은 것이 없고 오류 페이지 요청의 경우 제외시키고 싶으면 강력한 excludePathPatterns 기능을 사용해서 제외시키면 된다!
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
            .order(1)
            .addPathPatterns("/filterAndInterceptor/**")
            .excludePathPatterns("/css/**", "/*.ico", "/error");

        /*registry.addInterceptor(new LoginCheckInterceptor())
            .order(2)
            .addPathPatterns("/filterAndInterceptor/**")
            .excludePathPatterns("/css/**", "/*.ico", "/error", "/", "/login", "/logout");*/
    }
}
