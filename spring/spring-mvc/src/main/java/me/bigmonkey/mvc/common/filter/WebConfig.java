package me.bigmonkey.mvc.common.filter;

import javax.servlet.DispatcherType;
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

    // dispatcherType은 기본값이 request이다, 즉 클라이언트의 요청이 있는 경우에만 필터가 적용된다
    // 특별히 오류 페이지 경로도 필터를 적용할 것이 아니면, 기본 값을 그대로 사용하면 된다
    //@Bean
    /*public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ERROR);
        return filterRegistrationBean;
    }*/
}
