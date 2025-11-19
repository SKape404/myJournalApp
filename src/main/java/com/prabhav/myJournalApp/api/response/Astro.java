package com.prabhav.myJournalApp.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Astro {

    private String sunrise;

    private String sunset;

    private String moonrise;

    private String moonset;

    @JsonProperty("moon_phase")
    private String moonPhase;

    @JsonProperty("moon_illumination")
    private int moonIllumination;
}