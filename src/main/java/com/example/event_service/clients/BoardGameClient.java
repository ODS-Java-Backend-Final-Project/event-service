package com.example.event_service.clients;

import com.example.event_service.DTO.BoardGameDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "board-game-service")
public interface BoardGameClient {
    @GetMapping("/api/board-games/{id}")
    BoardGameDTO getBoardGameById(@PathVariable Long id);

}
