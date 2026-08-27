package com.cristobalrivas.riftanalytics.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentGameInfoDto {

    private Long gameId;
    private String gameMode;
    private Long gameLength;
    private Long gameStartTime;
    private List<CurrentGameParticipantDto> participants;
}
