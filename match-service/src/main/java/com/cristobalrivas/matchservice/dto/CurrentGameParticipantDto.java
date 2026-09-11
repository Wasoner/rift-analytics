package com.cristobalrivas.matchservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CurrentGameParticipantDto(
        String puuid,
        String summonerId,
        Long championId,
        Long teamId,
        Long spell1Id,
        Long spell2Id) {
}
