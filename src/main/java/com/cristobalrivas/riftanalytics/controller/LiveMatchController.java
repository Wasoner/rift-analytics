package com.cristobalrivas.riftanalytics.controller;

import com.cristobalrivas.riftanalytics.dto.CurrentGameInfoDto;
import com.cristobalrivas.riftanalytics.dto.RiotAccountDto;
import com.cristobalrivas.riftanalytics.service.RiotService;
import com.cristobalrivas.riftanalytics.service.SpectatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/matches")
@CrossOrigin(origins = "*")
public class LiveMatchController {

    private final RiotService riotService;
    private final SpectatorService spectatorService;

    public LiveMatchController(RiotService riotService, SpectatorService spectatorService) {
        this.riotService = riotService;
        this.spectatorService = spectatorService;
    }

    @GetMapping("/live/{region}/{gameName}/{tagLine}")
    public ResponseEntity<CurrentGameInfoDto> getLiveMatch(
            @PathVariable String region,
            @PathVariable String gameName,
            @PathVariable String tagLine) {
        RiotAccountDto account = riotService.getAccountByRiotId(gameName, tagLine);

        return spectatorService.findActiveGame(region, account.getPuuid())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }
}
