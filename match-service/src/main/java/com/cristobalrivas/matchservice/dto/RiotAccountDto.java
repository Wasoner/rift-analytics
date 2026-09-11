package com.cristobalrivas.matchservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RiotAccountDto(String puuid, String gameName, String tagLine) {
}
