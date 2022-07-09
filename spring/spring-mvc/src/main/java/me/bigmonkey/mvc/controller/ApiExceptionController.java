package me.bigmonkey.mvc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.bigmonkey.mvc.common.apiException.UserException;
import me.bigmonkey.mvc.common.apiException.BadRequestException;

@Slf4j
@RestController
public class ApiExceptionController {

    // api 예외 처리도 스프링 부트가 제공하는 BasicErrorController 를 사용할 수 있다

    // basicErrorController를 확장하면 JSON 메시지도 변경할 수 있다 (protected 메소드를 오버라이드하면), 그런데 API 오류는 @ExceptionHandler가 제공하는 기능을 사용하는 것이 더 좋다
    // 스프링 부트가 제공하는 BasicErrorController는 HTML 페이지를 제공하는 경우에는 매우 편리, 4xx 5xx등등 모두 잘 처리
    // 그런데 API 오류 처리는 다른 차원의 이야기, API 마다 응답이 달라지므로 매우 세밀하고 복잡하다
    // 따라서 basicErrorController는 HTML 화면을 처리할 때 사용하고 API 오류 처리는 ExceptionHandler를 사용하자

    // 스프링 MVC는 컨트롤러 (핸들러) 밖으로 예외가 던져진 경우 예외를 해결하고, 동작을 새로 정의할 수 있는 방법을 제공
    // 컨트롤러 밖으로 던져진 예외를 해결하고, 동작 방식을 변경하고 싶으면 HandlerExceptionResolver를 사용하면 된다 줄여서 ExceptionResolver라 한다
    // exceptionResolver에서 modelAndView를 반환해서 정상 응답 흐름으로 바꿀 수 있다, 하지만 예외를 해결해도 postHandle은 호출 X

    // 모든 예외가 발생하면 500응답 코드로 오지만 exceptionReoslver를 등록해서 IllegalArgument인 경우 400 상태코드 응답이 온다
    // 하지만 이렇게 ExceptionReoslver를 통해서 상태 코드를 변경해도 WAS까지 에러가 간 다음, WAS에서 등록된 에러 페이지를 호출하고 해당 호출이
    // BasicErrorController까지 온 다음 거기서 JSON 헤더 값에 따른 JSON 응답을 보여주는 것이다
    @GetMapping("/api/members/{id}")
    public MemberDto getMember(@PathVariable("id") String id) {

        if (id.equals("ex")) {
            throw new RuntimeException("잘못된 사용자");
        }
        if (id.equals("bad")) {
            throw new IllegalArgumentException("잘못된 입력 값");
        }
        if (id.equals("user-ex")) {
            throw new UserException("사용자 오류");
        }

        return new MemberDto(id, "hello " + id);
    }

    // 스프링 부트가 기본으로 제공하는 ExceptionResolver는 다음과 같다
    // HandlerExceptionResolverComposite에 다음 순서로 등록
    // ExceptionHandlerExceptionResolver, ResponseStatusExceptionResolver, DefaultHandlerExceptionResolver (우선 순위가 가장 낮다)
    // ExceptionHandlerExceptionResolver는 @ExceptionHandler 을 처리, API 예외 처리는 대부분 이 기능으로 해결
    // ResponseStatusExceptionResolver는 HTTP 상태 코드를 지정해준다
    // DefaultHandlerExceptionResolver는 스프링 내부 기본 예외를 처리
    @GetMapping("/api/response-status-ex1")
    public String responseStatusEx1() {
        throw new BadRequestException();
    }

    // 내가 변경할 수 없는 라이브러리 예외일때
    @GetMapping("/api/response-status-ex2")
    public String responseStatusEx2() {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "error.bad", new IllegalArgumentException());
    }

    // DefaultHandlerExceptionResolver는 스프링 내부에서 발생하는 스프링 예외를 해결
    // 대표적으로 파라미터 바인딩 시점에 타입이 맞지 않으면 내부에서 TypeMismathcException이 발생하는데, 이 경우 예외가 발생했기 때문에 그냥 두면
    // 서블릿 컨테이너까지 오류가 올라가고, 결과적으로 500 오류가 발생. 그런데 파라미터 바인딩은 대부분 클라이언트가 HTTP 요청 정보를 잘못 호출해서 발생하는 문제
    // HTTP에서는 이런 경우 HTTP 상태 코드 400을 사용하도록 되어 있다. DefaultHandlerExceptionResolver 는 이것을 500오류가 아니라 400오류로 변경
    // sendError를 호출했기에 WAS에서 다시 오류 페이지를 내부 요청
    @GetMapping("/api/default-handler-ex")
    public String defaultException(@RequestParam Integer data) {
        return "ok";
    }

    // 지금까지 HTTP 상태 코드를 변경하고, 스프링 내부 예외의 상태코드를 변경하는 기능도 알아보았다
    // 그런데 HandlerExceptionResolver를 직접 사용하기는 복잡하다, API 오류 응답의 경우 response 에 직접 데이터를 넣어야 해서 매우 불편하고 번거롭다
    // modelAndView를 반환해야 하는 것도 API 에는 잘 맞지 않는다, 스프링은 이 문제를 해결하기 위해 @ExceptionHandler라는 예외 처리 기능을 제공

    @Data
    @AllArgsConstructor
    static class MemberDto {

        private String memberId;
        private String name;
    }
}
