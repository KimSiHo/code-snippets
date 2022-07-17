package me.bigmonkey.validation.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import me.bigmonkey.validation.dto.ItemBean;

@Repository
public class ItemBeanRepository {

    private static final Map<Long, ItemBean> store = new HashMap<>();
    private static long sequence = 0L;

    public ItemBean save(ItemBean item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public List<ItemBean> findAll() {
        return new ArrayList<>(store.values());
    }

    public void update(Long itemId, ItemBean updateParam) {
        ItemBean findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public ItemBean findById(Long id) {
        return store.get(id);
    }

    public void clearStore() {
        store.clear();
    }
}
