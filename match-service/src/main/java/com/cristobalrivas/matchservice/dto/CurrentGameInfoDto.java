package com.cristobalrivas.matchservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CurrentGameInfoDto(
        Long gameId,
        String gameMode,
        Long gameLength,
        Long gameStartTime,
        List<CurrentGameParticipantDto> participants) {
}
