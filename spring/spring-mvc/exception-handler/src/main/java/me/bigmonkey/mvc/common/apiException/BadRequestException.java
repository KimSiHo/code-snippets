package me.bigmonkey.mvc.common.apiException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// responseStatusExceptionResolver가 처리하는 어노테이션
// 특별한 로직이 있는 것이 아니고 해당 어노테이션 정보를 찾아서 exceptionResolver에서 response.sendError를 호출하면 빈 modelAndView를 전달하는 것이다!
// sendError(400)를 호출했기에 WAS에서 다시 오류 페이지(/error)를 내부 요청한다
// @ResponseStatus는 개발자가 직접 변경할 수 없는 예외에는 적용할 수 없다, 추가로 어노테이션으로 인해 동적으로 변경하기 어렵다
// 이럴때 ResponseStatusException을 사용하면 된다
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "error.bad")
public class BadRequestException extends RuntimeException {

}
