package me.bigmonkey.converter.common.converter;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import me.bigmonkey.converter.common.formatter.MyNumberFormatter;

@Configuration
public class ConverterWebConfig implements WebMvcConfigurer {

    // MyNumberFormatter 와 String 과 Integer 컨버터는 둘 다 똑같은 숫자 <> 문자이다
    // 컨버터가 우선순위를 가지므로 포매터가 작동하려면 컨버터를 주석처리하자!
    @Override
    public void addFormatters(FormatterRegistry registry) {
        //주석처리 우선순위
        //registry.addConverter(new StringToIntegerConverter());
        //registry.addConverter(new IntegerToStringConverter());
        registry.addConverter(new StringToIpPortConverter());
        registry.addConverter(new IpPortToStringConverter());

        //추가
        registry.addFormatter(new MyNumberFormatter());
    }
}
