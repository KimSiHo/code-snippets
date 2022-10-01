package me.bigmonkey.dao.repository;

import java.util.List;
import java.util.Optional;

import me.bigmonkey.dao.domain.Item;

public interface ItemRepository {

    Item save(Item item);

    void update(Long itemId, ItemUpdateDto updateParam);

    Optional<Item> findById(Long id);

    List<Item> findAll(ItemSearchCond cond);
}
