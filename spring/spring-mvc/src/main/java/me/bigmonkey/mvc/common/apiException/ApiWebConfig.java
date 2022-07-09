package me.bigmonkey.mvc.common.apiException;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import me.bigmonkey.mvc.common.apiException.resolver.MyHandlerExceptionResolver;
import me.bigmonkey.mvc.common.apiException.resolver.UserHandlerExceptionResolver;

@Configuration
public class ApiWebConfig implements WebMvcConfigurer {

    // 스프링이 기본으로 등록하는 ExceptionResolver를 유지한 채 exceptionResolver 를 추가
    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        resolvers.add(new MyHandlerExceptionResolver());
        resolvers.add(new UserHandlerExceptionResolver());
    }
}
