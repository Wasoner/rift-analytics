package com.cristobalrivas.matchservice.controller;

import com.cristobalrivas.matchservice.dto.CurrentGameInfoDto;
import com.cristobalrivas.matchservice.service.MatchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/matches")
public class LiveMatchController {

    private final MatchService matchService;

    public LiveMatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("/live/{region}/{gameName}/{tagLine}")
    public ResponseEntity<CurrentGameInfoDto> getLiveMatch(
            @PathVariable String region,
            @PathVariable String gameName,
            @PathVariable String tagLine) {
        return matchService.findLiveMatch(region, gameName, tagLine)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }
}
