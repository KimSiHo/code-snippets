package me.bigmonkey.mvc.common.apiException.resolver;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        log.info("call resolver", ex);

        // modelAndView를 넘기면 리턴 리턴 되서 정상 흐름으로 호출, 그리고 WAS에 도착하면 sendError 호출로 인해 WAS에서 에러가 발생한 것을 인지
        // 예외를 여기서 먹은 것이다
        // IOException은 해당 sendError 메소드가 체크 예외를 던져서 그냥 감싸서 처리하면 된다
        // 아래 CASE는 상태 코드만 변경하는 것이고 이후 WAS는 서블릿 오류 페이지를 찾아서 내부 호출한다, 예를 들어 스프링 부트가 기본으로 설정한 /error가 호출됨
        try {
            if (ex instanceof IllegalArgumentException) {
                log.info("IllegalArgumentException resolver to 400");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
                return new ModelAndView();
            }
        } catch (IOException e) {
            log.error("resolver ex", e);
        }

        // null 이면 다음 ExceptionResolver를 찾고 해결이 안되면 예외가 그대로 전달된다
        return null;
    }
}
