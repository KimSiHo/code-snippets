package me.bigmonkey.mvc.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ServletErrorController {
    // 서블릿 컨테이너는 예외가 발생해서 서블릿 컨테이너까지 올라가는 경우와 response.sendError 함수 호출을 통한 2가지 방법으로 예외 처리를 지원
    // was - 필터 - 서블릿 - 인터셉터 - 컨트롤러 (예외 발생) : 타고 올라가서 톰캣 같은 WAS 까지 예외가 전달

    // sendError 메서드를 사용한다고 하더라도 당장 예외가 발생하는 것은 아니지만, 서블릿 커네이너에게 오류가 발생했다는 점을 전달할 수 있다
    // 이 메서드를 사용하면 HTTP 상태 코드와 오류 메시지도 추가할 수 있다다

    @GetMapping("/error/error-ex")
    public void errorEX() {
        throw new RuntimeException();
    }

    @GetMapping("/error/error-404")
    public void error404(HttpServletResponse response) throws IOException {
        response.sendError(404, "404 오류!");
    }

    // sendError를 호출하면 response 내부에는 오류가 발생했다는 상태를 저장해둔다, 그리고 서블릿 컨테이너는 고객에게 응답 전에 response에
    // sendError가 호출되었는지 확인, 호출되었다면 설정한 오류 코드에 맞추어 기본 오류 페이지를 보여준다
    @GetMapping("/error/error-500")
    public void error500(HttpServletResponse response) throws IOException {
        response.sendError(500);
    }

    // 서블릿 컨테이너는 정리하면 예외가 발생하면 500, sendError를 호출하면 그에 맞는 코드를 설정해서 WAS 기본 오류 페이지 호출
}
