package me.bigmonkey.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import me.bigmonkey.mvc.common.argumentResolver.Login;

@Controller
// WAS - 필터 - 서블릿 (디스패처 서블릿) - 컨트롤러 순으로 요청이 들어가는 것이다
// 필터와 인터셉터는 HttpRequest를 제공해주므로 웹과 관련된 공통 처리를 할 때는 AOP보다 더 효율적이다
@RequestMapping("/filterAndInterceptor")
public class FilterAndInterceptorController {

    @GetMapping("/filter")
    @ResponseBody
    public String hello() {
        return "hello";
    }

    // 스프링 인터셉터도 서블릿 필터와 같이 웹과 관련된 공통 관심 사항을 효과적으로 해결할 수 있는 기술
    // 서블릿 필터가 서블릿이 제공하는 기술이라면 스프링 인터셉터는 스프링 MVC가 제공하는 기술
    // 둘다 웹과 과녈ㄴ된 공통 관심 사항을 처리하지만, 적용되는 순서와 범위 그리고 사용 방법이 다르다
    // HTTP 요청 - WAS - 필터 - 서블릿 -스프링 인터셉터 - 컨트롤러
    // 스프링 인터셉터는 디스패처 서블릿과 컨트롤러 사이에서 컨트롤러 호출 직전에 호출
    // 스프링 인터셉터는 스프링 MVC가 제공하는 기능이기 떄문에 디스패처 서블릿 이우헤 등작, 스프링 MVC의 시작점이 디스패처 서블릿이라고 생각해보면 이해가 될 것
    // 스프링 인터셉터에도 URL 패턴을 적용할 수 있는데, 서블릿 URL 패턴과는 다르고 매우 정밀하게 설정 가능
    // 인터셉터에서 적절하지 않은 요청이라고 판단하면 거기에서 끝을 낼 수도 있다
    // 꼭 필터를 사용해야 하는 특별한 경우가 아니면 인터셉터를 이용하는 것이 훨씬 편리!
    @GetMapping("/interceptor")
    @ResponseBody
    public String hello2() {
        return "hello";
    }


    @GetMapping("/argument/resolver")
    @ResponseBody
    public String hello3(@Login String loginMember) {
        return loginMember;
    }

}
