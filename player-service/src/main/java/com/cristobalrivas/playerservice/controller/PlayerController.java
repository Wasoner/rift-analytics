package com.cristobalrivas.playerservice.controller;

import com.cristobalrivas.playerservice.dto.RiotAccountDto;
import com.cristobalrivas.playerservice.service.PlayerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/{gameName}/{tagLine}")
    public RiotAccountDto getPlayer(
            @PathVariable String gameName,
            @PathVariable String tagLine) {
        return playerService.getPlayer(gameName, tagLine);
    }
}
