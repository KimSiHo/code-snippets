package me.bigmonkey.mvc.validation;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;

public class MessageCodesResolverTest {

    MessageCodesResolver codesResolver = new DefaultMessageCodesResolver();

    @Test
    //객체 오류는 다음과 같이 2가지 오류 코드를 생성
    void messageCodesResolverObject() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item");
        // 디테일 한게 먼저고 범용적인 것이 나중이다
        // reject 메소드안에서 codesResovler를 통해서 FieldError를 생성할 때 해당 에러 코드들을 넣어준다
        assertThat(messageCodes).containsExactly("required.item", "required");
    }

    @Test
    //필드 오류는 다음과 같이 4가지 오류 코드를 생성
    void messageCodesResolverField() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item", "itemName", String.class);
        assertThat(messageCodes).containsExactly(
            "required.item.itemName",
            "required.itemName",
            "required.java.lang.String",
            "required"
        );
    }

}