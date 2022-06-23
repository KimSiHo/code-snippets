package me.bigmonkey.repository;

import java.util.List;
import java.util.Optional;

import me.bigmonkey.domain.Item;

public interface ItemRepository {

    Item save(Item item);

    void update(Long itemId, ItemUpdateDto updateParam);

    Optional<Item> findById(Long id);

    List<Item> findAll(ItemSearchCond cond);
}
