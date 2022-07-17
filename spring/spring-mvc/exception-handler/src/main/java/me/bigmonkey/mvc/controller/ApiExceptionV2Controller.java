package me.bigmonkey.mvc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.bigmonkey.mvc.common.apiException.UserException;
import me.bigmonkey.mvc.common.exHandler.ErrorResult;

@Slf4j
@RestController
public class ApiExceptionV2Controller {

    // exceptionHandlerResolver가 해당 메소드를 실행시킨다
    // 예외를 먹어서 정상 흐름으로 가기에 200 코드가 나오고 그것이 맞다. 하지만 상태 코드를 바꾸고 싶으면 ResponseStatus를 사용하면 된다
    // 서블릿 컨테이너까지 요청이 가지 않고 여기서 흐름이 끝나는 것이다!
   //@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandler(IllegalArgumentException e){
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("BAD", e.getMessage());
    }

    // 이렇게 할 수도 있다, ExceptionHandler 어노테이션 value는 생략해도 된다, 그러면 메서드 파라미터의 예외가 지정
   @ExceptionHandler
    public ResponseEntity<ErrorResult> userExHandler(UserException e) {
        log.error("[exceptionHandler] ex", e);
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    // 해당 컨트롤러에만 적용이 되는 exceptionHandler이다
    // ExceptionHandler에는 마치 스프링 컨트롤러의 파라미터 응답처럼 다양한 파라미터와 응답을 지정 가능
    // 여기서 modelAndView를 리턴해서 페이지 렌더링 할수도 있지만 그렇게는 거의 안한다
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("EX", "내부 오류");
    }

    @GetMapping("/api2/members/{id}")
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

    @Data
    @AllArgsConstructor
    static class MemberDto {

        private String memberId;
        private String name;
    }
}
