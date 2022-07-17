package me.bigmonkey.validation.controller;

import lombok.extern.slf4j.Slf4j;
import me.bigmonkey.validation.dto.form.ItemSaveForm;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/validation/api/items")
public class ValidationItemApiController {

    // bean validation 은 HttpMessageConverter (@RequestBody) 에도 적용할 수 있다
    // (@ModelAttribute는 HTTP 요청 파라미터 (URL 쿼리 스트링, POST Form)를 다룰 때 사용, @RequestBody는 HTTP body의 데이터를 객체로 변환할 때 사용)
    // JSON 의 경우 매핑이 잘못되는 등의 이유로 객체 변환이 실패하면 컨트롤러까지 요청이 전달되지 않고 사용자한테 그대로 예외가 전달된다

    // API의 경우 3가지 경우를 나누어 생각해야 한다
    // 성공 요청, 실패 요청 (JSON  객체를 생성하는 것 자체가 실패), 검증 오류 요청 (JSON을 객체로 생성하는 것은 성공했고, 검증에서 실패함)
    @PostMapping("/add")
    public Object addItem(@RequestBody @Validated ItemSaveForm form, BindingResult bindingResult) {

        log.info("API 컨트롤러 호출");

        if (bindingResult.hasErrors()) {
            log.info("검증 오류 발생 errors={}", bindingResult);
            // 실무에서는 필요한 필드들만 변환해서 반환해야 한다
            return bindingResult.getAllErrors();
        }

        log.info("성공 로직 실행");
        return form;
    }


    // @ModelAttribute vs @RequestBody
    // @ModelAttribute 는 필드 단위로 정교하게 바인딩이 적용. 특정 필드가 바인딩 되지 않아도 나머지 필드는 정상 바인딩 되고, Validator를 사용한 검증도 적용 가능
    // @RequestBody는 HttpMessageConverter 단계에서 JSON 데이터를 객체로 변경하지 못하면 이후 단계 자체가 진행않고 예외가 발생.
    // 컨트롤러도 호출되지 않고 validator도 적용할 수 없다

    // HttpMessageConverter는 @ModelAttribute와 다르게 각각의 필드 단위로 적용되는 것이 아니라 전체 객체 단위로 적용된다
}
