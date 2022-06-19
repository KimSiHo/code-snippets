package me.bigmonkey.mvc.common.filter;

import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    // 스프링 부트를 사용할 때는 스프링 부트가 WAS를 내장한 상태로 기동하므로 이렇게 빈으로 등록하면 필터가 등록이 된다
    //@ServletComponentScan과 @WebFilter로 필터 등록이 가능하지만 순서 조절이 안된다 따라서 FilterRegistrationBean을 사용하자!
    @Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/filterAndInterceptor/*");

        return filterRegistrationBean;
    }

    //@Bean
    public FilterRegistrationBean loginCheckFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginCheckFilter());
        filterRegistrationBean.setOrder(2);
        // 여기서 필터링을 할 수도 있지만, 필터에 등록한 화이트 리스트를 제외하고는 항상 로그인 체크를 하게 하고
        // 설정을 수정하지 않기 위해 모든 요청이 매핑되게 설정, 화이트 리스트 체크를 필터 안에서 하기에 상관없다!
        filterRegistrationBean.addUrlPatterns("/filterAndInterceptor/*");

        return filterRegistrationBean;
    }
}
