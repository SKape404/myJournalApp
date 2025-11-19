package com.prabhav.myJournalApp.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AirQuality {

    private String co;

    private String no2;

    private String o3;

    private String so2;

    private String pm2_5;

    private String pm10;

    @JsonProperty("us-epa-index")
    private String usEpaIndex;

    @JsonProperty("gb-defra-index")
    private String gbDefraIndex;
}