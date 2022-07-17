package me.bigmonkey.mvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

// webServerCustomizer에 등록한 URL 과 매핑하기 위한 컨트롤러
@Slf4j
@Controller
public class ErrorPageController {

    //RequestDispatcher 상수로 정의되어 있음
    public static final String ERROR_EXCEPTION = "javax.servlet.error.exception";
    public static final String ERROR_EXCEPTION_TYPE = "javax.servlet.error.exception_type";
    public static final String ERROR_MESSAGE = "javax.servlet.error.message";
    public static final String ERROR_REQUEST_URI = "javax.servlet.error.request_uri";
    public static final String ERROR_SERVLET_NAME = "javax.servlet.error.servlet_name";
    public static final String ERROR_STATUS_CODE = "javax.servlet.error.status_code";

    // WebServerCustomizer (내가 구현한 클래스)를 등록해서 서블릿 오류 페이지를 재정의하면 오류가 발생했을 때 해당 클래스에 지정한 요청이 다시 필터 - 서블릿
    // 을 거쳐 처음부터 요청이 들어온다
    // 예를 들어서 RuntimeException 예외가 WAS까지 전달되면, WAS는 오류 페이지 정보를 확인, 확인해보니 RuntimeException 의 오류 페이지로 /error-page/500
    // 이 지정되어 있다. WAS는 오류 페이지를 출력하기 위해 /error-page/500을 다시 요청한다
    // WAS(/error-page/500) 다시 요청 > 필터 > 서블릿 > 인터셉터 > 컨트롤러(error-page/500) > view
    // 중요한 점은 웹 브라우저(클라이언트)는 서버 내부에서 이런 일이 일어나는지 전혀 모른다, 오직 서버 내부에서 오류 페이지를 찾기 위해 추가적인 호출
    @RequestMapping("/error-page/404")
    public String errorPage404(HttpServletRequest request, HttpServletResponse response) {
        log.info("errorPage 404");
        printErrorInfo(request);
        return "error-page/404";
    }

    // WAS는 오류 페이지를 단순히 다시 요청한 하는 것이 아니라, 오류 정보를 request의 attribute에 추가해서 넘겨준다
    @RequestMapping("/error-page/500")
    public String errorPage500(HttpServletRequest request, HttpServletResponse response) {
        log.info("errorPage 500");
        printErrorInfo(request);
        return "error-page/500";
    }

    // 오류가 발생하면 오류 페이지를 출력하기 위해 WAS 내부에서 다시 한번 호출 발생, 이 때 필터, 서블릿, 인터셉터 모두 다시 호출
    // 그런데 로그인 인증 체크 같은 경우를 생각해보면, 이미 한번 필터나, 인터셉터에서 로그인 체크를 완료했다 따라서 서버 내부에서 오류 페이지를 호출한다고
    // 해서 해당 필터나 인터셉트가 한번 더 호출되는 것은 매우 비효율적
    // 결국 클라이언트로부터 발생한 정상 요청인지, 아니면 오류 페이지를 출력하기 위한 내부 요청인지 구분할 수 있어야 한다
    // 서블릿은 이런 문제를 해결하기 위해 DispatcherType 이라는 추가 정보를 제공
    // 고객이 처음 요청하면 dispatcherType이 REQUEST 이다, 내부 오류 호출이면 ERROR 이다

    // 지금까지 예외 처리 페이지를 만들기 위해서 다음과 같은 복잡한 과정을 거침
    // > webServerCustomizer를 만들고
    // > 예외 종류에 따라서 ErrorPage를 추가하고
    // > 예외 처리용 컨트롤러를 만듬
    // 스프링 부트는 이런 과정을 모두 기본으로 제공 > (ErrorPage를 자동으로 등록, 이때 /error 라는 경로로 기본 오류 페이지를 설정)
    // 상태 코드와 예외를 설정하지 않으면 기본 오류 페이지로 사용, 서블릿 밖으로 예외가 발생하거나, response.sendError가 호출되면 모든 오류는 /error를
    // 호출하게 된다
    // BasicErrorController라는 스프링 컨트롤러를 자동으로 등록 > ErrorPage에서 등록한 /error를 매핑해서 처리하는 컨트롤러 (ErrorMvcAutoConfiguration)

    // basiceErrorController는 기본적인 로직이 모두 개발되어 있음, 개발자는 오류 페이지 화면만 BasicErrorController가 제공하는 룰과 우선순위에 따라 등록하면 된다
    // basicErrorController는 몇몇 정보를 model에 담아서 뷰에 전달. 뷰 템플릿은 이 값을 활용해서 출력할 수 있다

    // accept-header에 따라서 이것이 우선순위를 가지고, API 오류 응답인 경우에는 이것을 사용한다
    // responseEntity의 2번째 매개변수를 통해 http 상태 코드를 설정할 수 있다다
   @RequestMapping(value = "/error-page/500", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> errorPage500Api(
            HttpServletRequest request, HttpServletResponse response) {

        log.info("API errorPage 500");

        Map<String, Object> result = new HashMap<>();
        Exception ex = (Exception) request.getAttribute(ERROR_EXCEPTION);
        result.put("status", request.getAttribute(ERROR_STATUS_CODE));
        result.put("message", ex.getMessage());

        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        return new ResponseEntity<>(result, HttpStatus.valueOf(statusCode));
    }

    private void printErrorInfo(HttpServletRequest request) {
        log.info("ERROR_EXCEPTION: {}", request.getAttribute(ERROR_EXCEPTION));
        log.info("ERROR_EXCEPTION_TYPE: {}", request.getAttribute(ERROR_EXCEPTION_TYPE));
        log.info("ERROR_MESSAGE: {}", request.getAttribute(ERROR_MESSAGE));
        log.info("ERROR_REQUEST_URI: {}", request.getAttribute(ERROR_REQUEST_URI));
        log.info("ERROR_SERVLET_NAME: {}", request.getAttribute(ERROR_SERVLET_NAME));
        log.info("ERROR_STATUS_CODE: {}", request.getAttribute(ERROR_STATUS_CODE));
        log.info("dispatchType={}", request.getDispatcherType());
    }
}
