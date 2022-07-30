package me.bigmonkey.validation.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.bigmonkey.validation.dao.ItemBeanRepository;
import me.bigmonkey.validation.dao.ItemRepository;
import me.bigmonkey.validation.dto.Item;
import me.bigmonkey.validation.dto.ItemBean;
import me.bigmonkey.validation.dto.SaveCheck;
import me.bigmonkey.validation.dto.UpdateCheck;
import me.bigmonkey.validation.dto.form.ItemSaveForm;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/bean")
@Controller
public class BeanValidationController {

    // 검증 기능을 매번 코드로 작성하는 것은 상당히 번거롭다
    // 특히 특정 필드에 대한 검증 로직은 대부분 빈 값인지 아닌지, 특정 크기를 넘는지 아닌지와 같이 매우 일반적인 로직이다
    // 이런 검증 로직을 모든 프로젝트에 적용할 수 있게 공통화하고, 표준화 한 것이 바로 Bean Validation 이다
    // Bean Validation을 잘 활용하면, 애노테이션 하나로 검증 로직을 매우 편리하게 적용 가능

    // Bean Validation은 기술 표준이다. 쉽게 이야기해서 검증 애노테이션과 여러 인터페이스의 모음
    // Bean Validation을 구현한 기술중에 일반적으로 사용하는 구현체는 하이버네이트 Validator이다
    // 실제 라이브러리에서 인터페이스가 자카르타로 시작하는 패키지이고 구현체가 하이버네이트 validator 이다

    private final ItemBeanRepository itemRepository;

    @GetMapping("/v2/items")
    public String items(Model model) {
        List<ItemBean> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v3/items";
    }

    @GetMapping("/v2/items/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        ItemBean item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v3/item";
    }

    @GetMapping("/v2/items/add")
    public String addForm(Model model) {
        model.addAttribute("item", new ItemBean());
        return "validation/v3/addForm";
    }

    // 스프링 부트가 start-validation 라이브러리를 넣으면 자동으로 Bean Validator를 인지하고 스프링에 통합
    // 스프링 부트는 자동으로 글로버 Validator로 등록
    // LocalValidatorFactoryBean을 글로벌 Validator로 등록. 이 Validator는 @NotNull 같은 애노테이션을 보고 검증을 수행
    // 이렇게 글로벌 Validator가 적용되어 있기에 @Valid, @Validated만 적용하면 됨
    // 검증 오류가 발생하면 FieldError, ObjectError를 생성해서 BindingResult에 담아준다

    // 글로벌 validator 설정을 하면 LocalValidatorFactoryBean이 등록이 안된다

    // 검증 순서
    // @ModealAttribute 각각의 필드에 타입 변환 시도. 성공하면 다음으로, 실패하면 typeMismatch로 FieldError 추가.
    // 그 다음에, Validator 적용

    // 바인딩에 성공한 필드만 Bean Validation 적용
    // BeanValidator는 바인딩에 실패한 필드는 BeanValidation을 적용하지 않는다. 생각해보면 타입 변환에 성공해서 바인딩에 성공한 필드여야 BeanValidation
    // 적용이 의미 있다

    // 예를 들어 price 필드에 문자 'A' 입력. -> 'A'를 숫자 타입 변환 시도 실패 -> typeMismatch FiedlError 추가 -> price 필드는 BeanValidation 적용 X

    // bean validator를 통해서 bindingResult에 등록되는 error code들은 오류 코드가 애노테이션 이름으로 등록된다
    // @NotBlank -> NotBlank.item.itemName, NotBlank.itemName, NotBlank.java.lang.String, NotBlank
    // bean validation 메시지 찾는 순서 : 생성된 메시지 코드 순서대로 messageSource에서 메시지 찾기 -> 애노테이션의 message 속성 사용 -> 라이브러리가 제공하는 기본 값 사용

    // Bean Validation에서 특정 필드(FieldError)가 아닌 오브젝트 관련 오류 (ObjectError)는 @ScriptAssert()를 사용하면 된다
    // 하지만 실제 사용해보면 제약이 많고 복잡. 그리고 실무에서는 검증 기능이 해당 객체의 범위를 넘어서는 경우들도 종종 등장하는데, 그런 경우 대응이 어렵다
    // 메시지 코드도 ScriptAssert.item, ScriptAssert로 생성이 된다
    // 따라서 오브젝트 오류(글로벌 오류)의 경우 해당 기능을 사용하기 보다는 직접 자바 코드로 작성하는 것을 권장

    //@PostMapping("/v2/items/add")
    public String addItemV1(@Validated @ModelAttribute ItemBean item, BindingResult bindingResult, RedirectAttributes redirectAttributes,
        Model model) {
        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors={} ", bindingResult);
            return "validation/v3/addForm";
        }

        //성공 로직
        ItemBean savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v3/items/{itemId}";
    }

    // groups 기능을 사용해서 각각 다르게 검증할 수 있는데 전박적으로 복잡도가 올라간다
    // 실무에서는 주로 등록용 폼 객체와 수정용 폼 객체를 분리해서 사용하는 방법을 사용!
    // 실무에서는 회원 등록시 회원과 관련된 데이터만 전달받는 것이 아니라, 약관 정보도 추가로 받는 등 수 많은 부가 데이터가 넘어온다
    // 따라서 복잡한 폼의 데이터를 컨트롤러까지 전달할 별도의 객체를 만들고 이것을 통해 이후 컨트롤러에서 필요한 데이터를 사용해서 도메인 객체를 생성
    // 따라서 이렇게 폼 데이터 전달을 위한 별도의 객체를 사용하고, 등록, 수정용 폼 객체를 나누면 등록, 수정이 완전히 분리되기에 groups를 적용할 일은 드물다
    //@PostMapping("/v2/items/add")
    public String addItemV2(@Validated(SaveCheck.class) @ModelAttribute ItemBean item, BindingResult bindingResult,
        RedirectAttributes redirectAttributes, Model model) {
        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors={} ", bindingResult);
            return "validation/v3/addForm";
        }

        //성공 로직
        ItemBean savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v3/items/{itemId}";
    }

    @PostMapping("/v2/items/add")
    public String addItemV3(@Validated @ModelAttribute("item") ItemSaveForm form, BindingResult bindingResult,
        RedirectAttributes redirectAttributes) {

        //특정 필드가 아닌 복합 룰 검증
        if (form.getPrice() != null && form.getQuantity() != null) {
            int resultPrice = form.getPrice() * form.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors={} ", bindingResult);
            return "validation/v4/addForm";
        }

        //성공 로직
        ItemBean item = new ItemBean();
        item.setItemName(form.getItemName());
        item.setPrice(form.getPrice());
        item.setQuantity(form.getQuantity());

        ItemBean savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v4/items/{itemId}";
    }

    @GetMapping("/v2/items/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        ItemBean item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v3/editForm";
    }

    //@PostMapping("/v2/items/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @Validated @ModelAttribute ItemBean item, BindingResult bindingResult) {
        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        //검증에 실패하면 다시 수정 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors={} ", bindingResult);
            return "validation/v3/editForm";
        }

        itemRepository.update(itemId, item);
        return "redirect:/validation/v3/items/{itemId}";
    }

    @PostMapping("/v2/items/{itemId}/edit")
    public String editV2(@PathVariable Long itemId, @Validated(UpdateCheck.class) @ModelAttribute ItemBean item, BindingResult bindingResult) {
        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        //검증에 실패하면 다시 수정 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors={} ", bindingResult);
            return "validation/v3/editForm";
        }

        itemRepository.update(itemId, item);
        return "redirect:/validation/v3/items/{itemId}";
    }
}