package me.bigmonkey.mvc.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.bigmonkey.mvc.dao.form.FormItemRepository;
import me.bigmonkey.mvc.dto.form.DeliveryCode;
import me.bigmonkey.mvc.dto.form.Item;
import me.bigmonkey.mvc.dto.form.ItemType;

@Slf4j
@RequiredArgsConstructor
@Controller
// 스프링과 통합해서 사용하는 타임리프 기능들과 여러 폼 요소 기능들
public class SpringIntegratedThymeleafFormController {

    private final FormItemRepository formItemRepository;

    @ModelAttribute("regions")
    public Map<String, String> regions() {
        // 해쉬맵을 쓰면 순서가 보장이 안되서 보여지는 순서가 일치하지 않을 수 있다
        Map<String, String> regions = new LinkedHashMap<>();
        regions.put("SEOUL", "서울");
        regions.put("BUSAN", "부산");
        regions.put("JEJU", "제주");
        return regions;
    }

    @ModelAttribute("itemTypes")
    public ItemType[] itemTypes() {
        return ItemType.values();
    }

    @ModelAttribute("deliveryCodes")
    public List<DeliveryCode> deliveryCodes() {
        List<DeliveryCode> deliveryCodes = new ArrayList<>();
        deliveryCodes.add(new DeliveryCode("FAST", "빠른 배송"));
        deliveryCodes.add(new DeliveryCode("NORMAL", "일반 배송"));
        deliveryCodes.add(new DeliveryCode("SLOW", "느린 배송"));
        return deliveryCodes;
    }

    @GetMapping("thymeleaf/list")
    public String items(Model model) {
        List<Item> items = formItemRepository.findAll();
        model.addAttribute("items", items);
        return "form/items";
    }

    @GetMapping("thymeleaf/detail/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = formItemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "form/item";
    }

    @GetMapping("thymeleaf/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "form/addForm";
    }

    @PostMapping("thymeleaf/add")
    public String addItem(@ModelAttribute Item item, RedirectAttributes redirectAttributes) {

        log.info("item.open={}", item.getOpen());
        log.info("item.regions={}", item.getRegions());
        log.info("item.itemType={}", item.getItemType());

        Item savedItem = formItemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/thymeleaf/detail/{itemId}";
    }

    @GetMapping("thymeleaf/edit/{itemId}")
    public String editForm(@PathVariable Long itemId, Model model) {

        Item item = formItemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "form/editForm";
    }


    @PostMapping("thymeleaf/edit/{itemId}")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        formItemRepository.update(itemId, item);
        return "redirect:/thymeleaf/detail/{itemId}";
    }
}
