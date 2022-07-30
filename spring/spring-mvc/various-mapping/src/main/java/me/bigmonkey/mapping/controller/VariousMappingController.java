package me.bigmonkey.mapping.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import me.bigmonkey.mvc.dto.RbDto;
import me.bigmonkey.mvc.dto.RpDto;

@Controller
public class VariousMappingController {

    @GetMapping("/requestParam/test1")
    @ResponseBody
    public String rpTest1(@RequestParam String id) {
        return "ID: " + id;
    }

    @GetMapping("/requestParam/test2")
    @ResponseBody
    public String rpTest2(@RequestParam(name = "test") String id) {
        return "ID: " + id;
    }

    // 받을 수도 있고 안 받을 수도 있음
    @GetMapping("/requestParam/test3")
    @ResponseBody
    public String rpTest3(@RequestParam(name = "test") Optional<String> id) {
        return "ID: " + id.orElseGet(() -> "not provided");
    }

    // 여러 키 값의 파라미터를 map으로 바인딩
    @GetMapping("/requestParam/test4")
    @ResponseBody
    public String rpTest4(@RequestParam Map<String, String> allParams) {
        return "parameters are " + allParams.entrySet();
    }

    // 값은 키 값의 파라미터를 list로 바인딩
    @GetMapping("/requestParam/test5")
    @ResponseBody
    public String rpTest5(@RequestParam List<String> id) {
        return "IDs are " + id;
    }

    // 기본 생성자 필요 매핑 방법
    @GetMapping("/requestBody/test1")
    @ResponseBody
    public String rbTest1(@RequestBody RbDto rbDto) {
        return "body is " + rbDto.toString();
    }

    // model 에 값 추가하는 방법
    @GetMapping("/modelAttribute/test1")
    @ModelAttribute
    public String maTest1(Model model) {
        model.addAttribute("categories", List.of("sutdy", "seminar"));
        return "test1";
    }

    // 컨트롤러 매핑되기 전에 먼저 실행되서 모델에 값 추가
    @ModelAttribute
    public List<String> maTest2(Model model) {
        return List.of("study", "seminar", "hobby");
    }

    // form-data로 넘어온 값  자동 추가
    @GetMapping("/modelAttribute/test3")
    @ModelAttribute
    public String maTest3(Model model, RpDto rpDto) {
        model.addAttribute("categories", List.of("sutdy", "seminar"));
        return "test1";
    }

    // model에 자동으로 매핑되는 key값 이름 변경
    @GetMapping("/modelAttribute/test4")
    @ModelAttribute
    public String maTest4(Model model, @ModelAttribute("test") RpDto rpDto) {
        model.addAttribute("categories", List.of("sutdy", "seminar"));
        return "test1";
    }

    // 중간에 값 변경함으로써 변경된 값 모델에 바인딩
    @GetMapping("/modelAttribute/test5")
    @ModelAttribute
    public String maTest5(Model model, @ModelAttribute("test") RpDto rpDto) {
        model.addAttribute("categories", List.of("sutdy", "seminar"));
        rpDto.setName("33333");
        return "test1";
    }

    @GetMapping("/healthcheck")
    @ResponseBody
    public Object testtest(@RequestParam(name = "format") String format, HttpServletResponse response) {
        Status ok = new Status("OK", null);

        return ok;
    }

    @GetMapping("/testhhest")
    @ResponseBody
    public Object testtest2(HttpServletResponse response) {
        Status ok = new Status("OK", LocalDateTime.now());

        return ok;
    }

    @JsonInclude(Include.NON_NULL)
    public static class Status {

        String status;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "Asia/Seoul")
        LocalDateTime currentTime;

        public Status(String status, LocalDateTime currentTime) {
            this.status = status;
            this.currentTime = currentTime;
        }

        public String getStatus() {
            return status;
        }

        public LocalDateTime getCurrentTime() throws IOException {
            return currentTime;
        }
    }
}
