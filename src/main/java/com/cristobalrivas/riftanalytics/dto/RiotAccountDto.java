package com.cristobalrivas.riftanalytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RiotAccountDto {

    private String puuid;
    private String gameName;
    private String tagLine;
}
