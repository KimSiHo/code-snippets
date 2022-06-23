package me.bigmonkey.service;

import java.util.List;
import java.util.Optional;

import me.bigmonkey.domain.Item;
import me.bigmonkey.repository.ItemSearchCond;
import me.bigmonkey.repository.ItemUpdateDto;

public interface ItemService {

    Item save(Item item);

    void update(Long itemId, ItemUpdateDto updateParam);

    Optional<Item> findById(Long id);

    List<Item> findItems(ItemSearchCond itemSearch);
}
