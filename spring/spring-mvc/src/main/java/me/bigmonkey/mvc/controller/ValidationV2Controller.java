package me.bigmonkey.mvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.bigmonkey.mvc.dao.validation.ItemRepository;
import me.bigmonkey.mvc.dto.validation.Item;

//컨트롤러의 중요한 역할 중 하나는 HTTP 요청이 정상인지 검증하는 것!
@Slf4j
@RequiredArgsConstructor
@Controller
public class ValidationV2Controller {
    /*v1에서는 map에 우리가 직접 메시지를 담아줬다면 v2는 스프링이 제공하는 오류 처리 방법을 알아보자. 핵심은 BindingResult이다!*/
    /*bindingResult는 스프링이 제공하는 검증 오류를 보관하는 객체이다, 검증 오류가 발생하면 여기에 보관하면 된다
      bindingResult가 있으면 @ModelAttribute 에 데이터 바인딩 시 오류가 발생해도 컨트롤러가 호출된다*/
    /*bindingResult가 없으면 400오류가 발생하면서 컨트롤러가 호출되지 않고 오류 페이지로 이동
        있으면, 오류 정보(fieldError)를 BindingReuslt에 담아서 컨트롤러를 정상 호출*/
    /*타입이 달라서 아예 바인딩할 수 없는 에러인 경우에도 컨트롤러가 호출이 된다!!*/

    private final ItemRepository itemRepository;

    @GetMapping("validation/v2/items")
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v2/items";
    }

    @GetMapping("validation/v2/items/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/item";
    }

    @GetMapping("validation/v2/items/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v2/addForm";
    }

    //@PostMapping("validation/v2/items/add")
    //필드의 순서 중요 item을 검증하는 거니까 item 다음에 BindingResult 와야 한다
    // 하지만 v1은 실패했던 값이 유지가 안된다
    public String addItemV1(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        /*오브젝트 네임은 model에 담겨있는 이름을 넣으면 된다
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
            return "validation/v2/addForm";
        }


        //성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

   // @PostMapping("validation/v2/items/add")
    public String addItemV2(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        // bidingResult가 타입이 맞지 않는 값도 저장하고 있는 것은 컨트롤러가 호출되기 전에 스프링에서 request.getParameter 같은 것으로
        // 타입이 맞지 않는 값을 알수 있고 해당 값으로 FieldError를 생성해서 bindingResult에 넣어주는 것이다

        //검증 로직
        if (!StringUtils.hasText(item.getItemName())) {
            // v2은 실패했던 값을 유지하기 위해 reject value를 같이 넣어준다, 그리고 bidingFailure은 바인딩이 실패했는지 여부를 설정하는 것인데
            // 바인딩 자체는 성공했으므로 false이다 그리고 메시지 코드와 아규먼트
            bindingResult.addError(new FieldError("item", "itemName", item.getItemName(), false, null, null, "상품 이름은 필수 입니다."));
        }
        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            bindingResult.addError(new FieldError("item", "price", item.getPrice(), false, null, null, "가격은 1,000 ~ 1,000,000 까지 허용합니다."));
        }
        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
            bindingResult.addError(new FieldError("item", "quantity", item.getQuantity(), false, null ,null, "수량은 최대 9,999 까지 허용합니다."));
        }

        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                // 오브젝트 에러는 넘어오는 값이 없으므로 저장할 값도 없다
                bindingResult.addError(new ObjectError("item",null ,null, "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice));
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

    //@PostMapping("validation/v2/items/add")
    public String addItemV3(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        // 코드는 배열 형태로 넘겨주어서 첫번째 꺼 없으면 두 번째꺼 가는 식으로 할 수 있다
        //검증 로직
        if (!StringUtils.hasText(item.getItemName())) {
            bindingResult.addError(new FieldError("item", "itemName", item.getItemName(), false, new String[]{"required.item.itemName"}, null, null));
        }
        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            bindingResult.addError(new FieldError("item", "price", item.getPrice(), false, new String[]{"range.item.price"}, new Object[]{1000, 1000000}, null));
        }
        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
            bindingResult.addError(new FieldError("item", "quantity", item.getQuantity(), false, new String[]{"max.item.quantity"} ,new Object[]{9999}, null));
        }

        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.addError(new ObjectError("item",new String[]{"totalPriceMin"} ,new Object[]{10000, resultPrice}, null));
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

    @PostMapping("validation/v2/items/add")
    public String addItemV4(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        // 타입 오류가 발생한 경우 타입 오류만 먼저 보여주고 싶은 경우에 주로 이렇게 한다다
       /*if(bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "validation/v2/addForm";
        }*/

        //기존 fieldError와 ObjectError는 다루기 너무 어렵다 아규먼트 수가 많아서,
        //bidingResult는 ModelAttribute 바로 다음에 오므로 item 을 이미 알고 있다
        log.info("objectName={}", bindingResult.getObjectName());
        log.info("target={}", bindingResult.getTarget());

        // 이거와 아래는 같은 코드이다, 간단한 유효성 체크는 ValidationUtils를 쓰면 좋다, Empty 공백 같은 단순한 기능만 제공
        ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult, "itemName", "required");

        /*if (!StringUtils.hasText(item.getItemName())) {
            //bindingResult.addError(new FieldError("item", "itemName", item.getItemName(), false, new String[]{"required.item.itemName"}, null, null));
            // 에러 메시지 코드는 상세한 게 있으면 상세한 것을 쓰고 아니면 기본 것을 쓴다 이렇게 code에 들어가는 문자열 배열을 messageCodesResovler를 통해서 만든다
            // {"required.item.itemName", "required"} 이렇게 상세한 것이 있으면 상세한 것을 쓰고 아니면 범용적인 것을 쓰는 것이다
            // 이렇게 생성된 메시지 코드를 기반으로 순서대로 MessageSource에서 메세지를 찾는다
            bindingResult.rejectValue("itemName", "required");
        }*/
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
    // new FieldError를 생성하면서 메시지 코드들을 보관(문자열 배열을 통해 메시지 코드들을 전달)
    // th:errors에서 메시지 코드들로 메시지를 messageSource를 통해서 순서대로 찾고 노출

    // 타입이 맞지 않으면 스프링이 typeMismatch.item.price > typeMismatch.price > typeMismatch.java.lang.Ineteger > typeMistach
    // 총 4가지의 메시지 코드들을 생성해서 BindingResult에 넣어주고 컨트롤러가 호출이 된다

    @GetMapping("validation/v2/items/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/editForm";
    }

    @PostMapping("validation/v2/items/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/validation/v2/items/{itemId}";
    }
}
