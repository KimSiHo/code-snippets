package me.bigmonkey.springcomponents.cache;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class BoardController {

  private final BoardService service;
  
  
  @GetMapping()
  public List<Board> boards(String size) {
    List<Board> boards = service.getBoards(size);
    return boards;
  }
  
  @GetMapping("count")
  public int count() {
    return BoardService.getDbCount();
  }

}