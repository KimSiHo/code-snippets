package me.bigmonkey.validation.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.bigmonkey.validation.dao.ItemRepository;
import me.bigmonkey.validation.dto.Item;
import me.bigmonkey.validation.validator.ItemValidator;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/validation")
@Controller
public class ValidationV2Controller {
    /*v1에서는 map에 우리가 직접 메시지를 담아줬다면 v2는 스프링이 제공하는 오류 처리 방법을 알아보자. 핵심은 BindingResult이다!*/
    /*bindingResult는 스프링이 제공하는 검증 오류를 보관하는 객체이다, 검증 오류가 발생하면 여기에 보관하면 된다
      bindingResult가 있으면 @ModelAttribute 에 데이터 바인딩 시 오류가 발생해도 컨트롤러가 호출된다*/
    /*bindingResult가 없으면 400오류가 발생하면서 컨트롤러가 호출되지 않고 오류 페이지로 이동
        있으면, 오류 정보(fieldError)를 BindingReuslt에 담아서 컨트롤러를 정상 호출*/
    /*타입이 달라서 아예 바인딩할 수 없는 에러인 경우에도 컨트롤러가 호출이 된다!!*/

    private final ItemRepository itemRepository;
    private final ItemValidator itemValidator;

    @GetMapping("/v2/items")
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v2/items";
    }

    @GetMapping("/v2/items/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/item";
    }

    @GetMapping("/v2/items/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v2/addForm";
    }

    //@PostMapping("/v2/items/add")
    //필드의 순서 중요 item을 검증하는 거니까 item 다음에 BindingResult 와야 한다
    //하지만 v1은 실패했던 값이 유지가 안된다
    public String addItemV1(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        /* 오브젝트 네임은 model에 담겨있는 이름을 넣으면 된다
         * bindingResult은 자동으로 model에 들어간다 */

        //검증 로직
        if (!StringUtils.hasText(item.getItemName())) {
            bindingResult.addError(new FieldError("item", "itemName", "상품 이름은 필수 입니다."));
        }
        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            bindingResult.addError(new FieldError("item", "price", "가격은 1,000 ~ 1,000,000 까지 허용합니다."));
        }
        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
            bindingResult.addError(new FieldError("item", "quantity", "수량은 최대 9,999 까지 허용합니다."));
        }

        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.addError(new ObjectError("item", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice));
            }
        }

        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors={} ", bindingResult);
            return "templates/validation/v2/addForm";
        }

        //성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    //@PostMapping("/v2/items/add")
    public String addItemV2(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        //bidingResult가 타입이 맞지 않는 값도 저장하고 있는 것은 컨트롤러가 호출되기 전에 스프링에서 request.getParameter 같은 것으로
        //타입이 맞지 않는 값을 알수 있고 해당 값으로 FieldError를 생성해서 bindingResult에 넣어주는 것이다

        //검증 로직
        if (!StringUtils.hasText(item.getItemName())) {
            //v2은 실패했던 값을 유지하기 위해 reject value를 같이 넣어준다, 그리고 bidingFailure은 바인딩이 실패했는지 여부를 설정하는 것인데
            //바인딩 자체는 성공했으므로 false이다 그리고 메시지 코드와 아규먼트
            bindingResult.addError(new FieldError("item", "itemName", item.getItemName(), false, null, null, "상품 이름은 필수 입니다."));
        }
        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            bindingResult.addError(new FieldError("item", "price", item.getPrice(), false, null, null, "가격은 1,000 ~ 1,000,000 까지 허용합니다."));
        }
        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
            bindingResult.addError(new FieldError("item", "quantity", item.getQuantity(), false, null, null, "수량은 최대 9,999 까지 허용합니다."));
        }

        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                //오브젝트 에러는 넘어오는 값이 없으므로 저장할 값도 없다
                bindingResult.addError(new ObjectError("item", null, null, "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice));
            }
        }

        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors={} ", bindingResult);
            return "templates/validation/v2/addForm";
        }

        //성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    //@PostMapping("/v2/items/add")
    public String addItemV3(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        //코드는 배열 형태로 넘겨주어서 첫번째 꺼 없으면 두 번째꺼 가는 식으로 할 수 있다
        //검증 로직
        if (!StringUtils.hasText(item.getItemName())) {
            bindingResult.addError(new FieldError("item", "itemName", item.getItemName(), false, new String[]{"required.item.itemName"}, null, null));
        }
        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            bindingResult.addError(
                new FieldError("item", "price", item.getPrice(), false, new String[]{"range.item.price"}, new Object[]{1000, 1000000}, null));
        }
        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
            bindingResult
                .addError(new FieldError("item", "quantity", item.getQuantity(), false, new String[]{"max.item.quantity"}, new Object[]{9999}, null));
        }

        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.addError(new ObjectError("item", new String[]{"totalPriceMin"}, new Object[]{10000, resultPrice}, null));
            }
        }

        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors={} ", bindingResult);
            return "templates/validation/v2/addForm";
        }

        //성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    //@PostMapping("/v2/items/add")
    public String addItemV4(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        //타입 오류가 발생한 경우 타입 오류만 먼저 보여주고 싶은 경우에 주로 이렇게 한다다
        /*if(bindingResult.hasErrors()) {
             log.info("errors={}", bindingResult);
             return "validation/v2/addForm";
        }*/

        //기존 fieldError 와 ObjectError 는 다루기 너무 어렵다 아규먼트 수가 많아서
        //bidingResult는 ModelAttribute 바로 다음에 위치하므로 item 을 이미 알고 있다
        log.info("objectName={}", bindingResult.getObjectName());
        log.info("target={}", bindingResult.getTarget());

        // 이거와 아래는 같은 코드이다, 간단한 유효성 체크는 ValidationUtils를 쓰면 좋다, Empty 공백 같은 단순한 기능만 제공
        ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult, "itemName", "required");

        /*if (!StringUtils.hasText(item.getItemName())) {
            //bindingResult.addError(new FieldError("item", "itemName", item.getItemName(), false, new String[]{"required.item.itemName"}, null, null));
            bindingResult.rejectValue("itemName", "required");
        }*/

        //에러 메시지 코드는 상세한 게 있으면 상세한 것을 쓰고 아니면 기본 것을 쓴다 이렇게 code에 들어가는 문자열 배열을 messageCodesResovler를 통해서 만든다
        //{"required.item.itemName", "required"} 이렇게 상세한 것이 있으면 상세한 것을 쓰고 아니면 범용적인 것을 쓰는 것이다
        //이렇게 생성된 메시지 코드를 기반으로 순서대로 MessageSource에서 메세지를 찾는다

        //rejectValue가 fieldError를 안에서 생성해준다
        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            bindingResult.rejectValue("price", "range", new Object[]{1000, 10000000}, null);
        }
        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
            bindingResult.rejectValue("quantity", "max", new Object[]{9999}, null);
        }

        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors={} ", bindingResult);
            return "validation/v2/addForm";
        }

        //성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }
    // 정리
    // rejectValue 호출 > MessageCodesResovler를 사용해서 검증 오류 코드로 메시지 코드들을 생성
    // new FieldError를 생성하면서 메시지 코드들을 보관(문자열 배열을 통해 메시지 코드들을 전달) (구체적인 것 부터 범용적인 것으로)
    // th:errors에서 메시지 코드들로 메시지를 messageSource를 통해서 순서대로 찾고 노출

    // 타입이 맞지 않으면 스프링이 typeMismatch.item.price > typeMismatch.price > typeMismatch.java.lang.Ineteger > typeMistach
    // 총 4가지의 메시지 코드들을 생성해서 BindingResult에 넣어주고 컨트롤러가 호출이 된다
    // 해당 코드들을 정의하지 않으면 디폴트 메시지를 보여주는데 지저분하고 복잡한 메시지가 나온다

    //@PostMapping("/v2/items/add")
    public String addItemV5(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        itemValidator.validate(item, bindingResult);

        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors={} ", bindingResult);
            return "validation/v2/addForm";
        }

        //성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    //스프링이 Validator 인터페이스를 별도로 제공하는 이유는 체계적으로 검증 기능을 도입하기 위해서다
    //Validator 인터페이스를 사용해서 검증기를 만들면 스프링의 추가적인 도움을 받을 수 있다
    //webDataBinder는 파라미터 바인딩 해주고, 검증기 가지고 검증도 해주고 스프링 MVC 내부에서 사용하는 기능이다, 이것을 밖으로 꺼내서 안에다가
    //validator를 넣어주어야 된다. 그래야지 webDataBinder가 validator를 적용한다

    // 컨트롤러가 호출될 때마다 항상 호출된다, 말 그래도 web data 바인딩도하고 검증도 하는 등 MVC 내부에서 동작하는 개념이다
    // dataBinder는 요청이 올 때마다 항상 새로 만들어진다
    // 따라서 어떤 요청으로 흐름이 가더라도 검증기를 적용할 수 있다
    // 이렇게 하면 해당 컨트롤러에만 적용이 된다
    @InitBinder
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(itemValidator);
    }

    // 글로벌 설정을 하려면 WebMvcConfigurer에서 Validator를 빈 등록하면 된다
    // 기존 컨트롤러의 @InitBinder를 제거해도 글로벌 설정으로 정상 동작하는 것을 확인 가능. 하지만 글로벌 설정은 제거해두자.
    // 글로벌 설정을 하면 BeanValidator가 자동 등록되지 않는다

    // 검증시 @Validated와 @Valid 둘다 사용 가능
    // @Valid를 사용하려면 starter-validation이 필요. @Validated는 스프링 전용 검증 애노테이션이고, @Valid는 자바 표준 검증 애노테이션이다
    // groups 기능은 @Validated에만 있다
    @PostMapping("/v2/items/add")
    public String addItemV6(@Validated @ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors={} ", bindingResult);
            return "validation/v2/addForm";
        }

        //성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    @GetMapping("/v2/items/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/editForm";
    }

    @PostMapping("/v2/items/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/validation/v2/items/{itemId}";
    }
}
