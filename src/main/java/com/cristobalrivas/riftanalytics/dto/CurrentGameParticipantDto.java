package com.cristobalrivas.riftanalytics.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentGameParticipantDto {

    private String puuid;
    private String summonerId;
    private Long championId;
    private Long teamId;
    private Long spell1Id;
    private Long spell2Id;
}
