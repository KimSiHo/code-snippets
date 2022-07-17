package me.bigmonkey.cas.controller;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.extern.slf4j.Slf4j;
import me.bigmonkey.cas.common.session.SessionConst;

// modelAttribute가 붙은 메소드는 컨트롤러 매핑되기 전에 먼저 실행되고, 해당 어노테이션과 같은 이름을 가진
// sessionAttributes 어노테이션이 있으면 modelAtt 어노테이션이 반환한 값이 해당 컨트롤러 session 에 저장된다
// s와 s없는 것이 다르다!
// s 붙어있는 것은 현재 컨트롤러에서만 동작하는 것이다 s가 붙어있는 SessionStatus를 통해 세션에서 값을 삭제할 수 있다
// modelAttr에 SessionAttrs와 같은 값이 있으면 세션에서 찾고 그다음에 사용자 요청으로 들어온 값을 매핑한다
@Slf4j
@SessionAttributes("sessionAttrTest")
@Controller
public class CookieSessionController {

    @ModelAttribute("sessionAttrTest")
    public String sessionAttrTest() {
        return "sessionAttrTest";
    }

    @GetMapping("/cookie/add")
    public String cookieAdd(HttpServletResponse response) {
        // value로 문자열만 가능
        // 만료날짜 지정하지 않으면 세션 쿠키로 브라우저 종료시 자동 삭제
        response.addCookie(new Cookie("test", "cookietest"));
        return "cookie/home";
    }

    @GetMapping("/cookie/exists")
    @ResponseBody
    public String cookieExists(@CookieValue(name = "test", required = false) String cookie) {
        return cookie;
    }

    @GetMapping("/cookie/delete")
    @ResponseBody
    public String cookieDel(HttpServletResponse response) {
        expiredCookie(response, "test");
        return "see homePage";
    }

    private void expiredCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    @GetMapping("/session/create")
    @ResponseBody
    public String sessionCreate(HttpServletRequest request) {
        //세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성, default true
        HttpSession session = request.getSession(true);

        /*세션이 있으면 기존 세션 반환, 없으면 null 반환*/
        /*HttpSession session = request.getSession(false);*/

        session.setAttribute(SessionConst.SESSION_KEY, "session test");
        return "see homePage";
    }

    @GetMapping("/session/delete")
    @ResponseBody
    public String sessionDel(HttpServletRequest request) {
        //세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성
        HttpSession session = request.getSession(false);
        /*session.setMaxInactiveInterval(1800);*/ // 1800초

        // 세션은 사용자가 로그아웃을 직접 호출해서 invalidate가 호출되는 경우에 삭제, 그런데 대부분의 사용자는 그냥 브라우저 종료를 하고
        // http가 비연결성이므로 서버 입장에서는 웹 브라우저를 종료한 것인지 아닌지를 인식할 수 없다
        // 특정 세션 단위로 세션 타임아웃을 설정하거나 application 프로퍼티 파일에서 전역으로 설정 가능
        // 사용자가 접근하면 만료 시간 초기화! 다시 30분으로 늘어난 다는 말이다!
        if (session != null) {
            session.invalidate();
        }

        return "see homePage";
    }

    @GetMapping("/session/exists")
    @ResponseBody
    public String sessionExists(HttpServletRequest request) {
        //세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성
        HttpSession session = request.getSession();

        session.setAttribute("test2", "session test2");
        return "see homePage";
    }

    @GetMapping("/session/annotation")
    @ResponseBody
    // 해당 어노테이션은 세션을 생성하지 않는다
    // 세션을 가져와서 세션에서 SESSION_KEY 라는 값으로 설정한 value 를 찾는 로직을 자동으로 해주는 것이다
    public String sessionAnnotation(@SessionAttribute(name = SessionConst.SESSION_KEY, required = false) String val) {
        System.out.println("val = " + val);

        return "see homePage";
    }

    @GetMapping("/session/info")
    @ResponseBody
    public String sessionInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "세션이 없다";
        }

        session.getAttributeNames().asIterator()
            .forEachRemaining(name -> log.info(name, session.getAttribute(name)));

        //JSESSIONID의 값이다
        log.info(session.getId());
        //세션의 유효시간
        log.info("{}", session.getMaxInactiveInterval());
        // 세션 생성일시
        log.info("{}", new Date(session.getCreationTime()));
        // 세션과 연결된 사용자가 최근에 서버에 접근한 시간, 클라이언트에서 서버로 sessionid를 요청한 경웅 갱신
        log.info("{}", new Date(session.getLastAccessedTime()));
        log.info("{}", session.isNew());

        return "see homePage";
    }
}
