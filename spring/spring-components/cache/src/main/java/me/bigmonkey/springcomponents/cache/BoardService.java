package me.bigmonkey.springcomponents.cache;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository repository;

    @Cacheable(key = "#size", value = "getBoards")
    public List<Board> getBoards(String size) {
        return repository.createBySize(size);
    }

    public static int getDbCount() {
        return BoardRepository.getDbCount();
    }
}
