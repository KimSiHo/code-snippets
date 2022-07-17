package me.bigmonkey.validation;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.Test;

import me.bigmonkey.validation.dto.ItemBean;

public class BeanValidationTest {

    // 스프링과 통합하면 우리가 직접 이런 코드를 작성하지는 않는다
    @Test
    void beanValidation() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        ItemBean item = new ItemBean();
        item.setItemName(" ");
        item.setPrice(0);
        item.setQuantity(10000);

        Set<ConstraintViolation<ItemBean>> violations = validator.validate(item);
        // 결과가 비어 있으면 검증 오류가 없는 것이다
        for (ConstraintViolation<ItemBean> violation : violations) {
            System.out.println("violation = " + violation);
            System.out.println("violation.getMessage() = " + violation.getMessage());
        }
    }
}
