package me.bigmonkey.mvc.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
// preHandle는 컨트롤러 호출 전에 호출 (더 정확히는 핸들러 어댑터 호출 전에 호출)
// postHandle은 컨트롤러 호출 후에 호출 (더 정확히는 핸들러 어댑터 호출 후에 호출)
// afterCompletion은 뷰가 렌더링 된 이후에 호출

// preHandle은 컨트롤러 호출 전에 호출 컨트롤러에서 예외 발생 시 postHandle은 호출되지 않는다
// afterCompletion은 항상 호출된다 이 경우 예외를 파라미터로 받아서 어떤 예외가 발생했는지 로그로 출력할 수 있다
// 정상 호출 흐름 상황에서는 ex 파라미터가 null 이다
public class LogInterceptor implements HandlerInterceptor {

    public static final String LOG_ID = "logId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString();
        // 서블릿 필터의 경우 지역 변수로 해결이 가능하지만 인터셉터는 호출 시점이 완전히 분리되어 있다
        // 인터셉터는 싱글톤으로 사용되기 때문에 멤버 변수로 사용하면 안되고 request에 담아 두어서 해결!
        request.setAttribute(LOG_ID, uuid);

        //@RequestMapping: HandlerMethod
        //정적 리소스: ResourceHttpRequestHandler
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;//호출할 컨트롤러 메서드의 모든 정보가 포함되어 있다.
        }

        log.info("REQUEST [{}][{}][{}]", uuid, requestURI, handler);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle [{}]", modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        String logId = (String) request.getAttribute(LOG_ID);
        log.info("RESPONSE [{}][{}][{}]", logId, requestURI, handler);
        if (ex != null) {
            log.error("afterCompletion error!!", ex);
        }

    }
}
