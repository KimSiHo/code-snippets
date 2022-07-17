package me.bigmonkey.mvc.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ResourceLoaderController {

    private final ResourceLoader resourceLoader;

    // 기본적인 리소스 사용 방법
    @ResponseBody
    @GetMapping("/resource/test1")
    public String resource1() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:messages.properties");
        System.out.println(resource.exists());
        System.out.println(resource.getDescription());
        System.out.println(Files.readString(Path.of(resource.getURI())));

        return "see log";
    }

    // 접두사 명시하지 않을 경우 기본 리소스 타입
    // servletContextResource 스프링 부트 디스패처 서블릿에 콘텍스트 패스가 명시되어 있지 않으므로 찾을 수 없다
    @ResponseBody
    @GetMapping("/resource/test2")
    public String resource2() {
        Resource resource = resourceLoader.getResource("mvc-0.0.1-SNAPSHOT.jar");
        System.out.println(resource.getClass());
        System.out.println(resource.exists());

        return "see log";
    }
}
