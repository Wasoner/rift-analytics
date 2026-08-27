package com.cristobalrivas.riftanalytics.controller;

import com.cristobalrivas.riftanalytics.dto.RiotAccountDto;
import com.cristobalrivas.riftanalytics.service.RiotService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/players")
@CrossOrigin(origins = "*")
public class PlayerController {

    private final RiotService riotService;

    public PlayerController(RiotService riotService) {
        this.riotService = riotService;
    }

    @GetMapping("/{gameName}/{tagLine}")
    public RiotAccountDto getPlayer(
            @PathVariable String gameName,
            @PathVariable String tagLine) {
        return riotService.getAccountByRiotId(gameName, tagLine);
    }
}
