package me.bigmonkey.dao.service;

import java.util.List;
import java.util.Optional;

import me.bigmonkey.dao.domain.Item;
import me.bigmonkey.dao.repository.ItemSearchCond;
import me.bigmonkey.dao.repository.ItemUpdateDto;

public interface ItemService {

    Item save(Item item);

    void update(Long itemId, ItemUpdateDto updateParam);

    Optional<Item> findById(Long id);

    List<Item> findItems(ItemSearchCond itemSearch);
}
