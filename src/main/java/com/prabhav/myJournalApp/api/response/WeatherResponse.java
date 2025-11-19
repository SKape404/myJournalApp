package com.prabhav.myJournalApp.api.response;

import lombok.Data;

@Data
public class WeatherResponse {
    private Request request;
    private Location location;
    private Current current;


}





