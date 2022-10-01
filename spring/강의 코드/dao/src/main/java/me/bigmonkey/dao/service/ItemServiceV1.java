package me.bigmonkey.dao.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.bigmonkey.dao.domain.Item;
import me.bigmonkey.dao.repository.ItemRepository;
import me.bigmonkey.dao.repository.ItemSearchCond;
import me.bigmonkey.dao.repository.ItemUpdateDto;

@Service
@RequiredArgsConstructor
public class ItemServiceV1 implements ItemService {

    private final ItemRepository itemRepository;

    @Override
    public Item save(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        itemRepository.update(itemId, updateParam);
    }

    @Override
    public Optional<Item> findById(Long id) {
        return itemRepository.findById(id);
    }

    @Override
    public List<Item> findItems(ItemSearchCond cond) {
        return itemRepository.findAll(cond);
    }
}
