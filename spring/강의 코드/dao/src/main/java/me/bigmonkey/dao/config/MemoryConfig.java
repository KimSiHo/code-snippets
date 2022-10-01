package me.bigmonkey.dao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import me.bigmonkey.dao.repository.ItemRepository;
import me.bigmonkey.dao.repository.memory.MemoryItemRepository;
import me.bigmonkey.dao.service.ItemService;
import me.bigmonkey.dao.service.ItemServiceV1;

@Configuration
public class MemoryConfig {

    @Bean
    public ItemService itemService() {
        return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new MemoryItemRepository();
    }
}
