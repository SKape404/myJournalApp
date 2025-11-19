package com.prabhav.myJournalApp.service;

import com.prabhav.myJournalApp.api.response.WeatherResponse;
import com.prabhav.myJournalApp.cache.AppCache;
import com.prabhav.myJournalApp.constants.PlaceHolders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getWeather(String city) {
        String finalAPI = appCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace(PlaceHolders.CITY, city).replace(PlaceHolders.API_KEY, apiKey);

//        External POST request to RequestEntity for Frontend
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.set("key", "value");
//
//        User user = User.builder().userName("Priyanshi").password("Priyanshi").build();
//        HttpEntity<User> httpEntity = new HttpEntity<>(user, httpHeaders);
//
//        ResponseEntity<WeatherResponse> postResponse = restTemplate.exchange(finalAPI, HttpMethod.POST, httpEntity, WeatherResponse.class);
//

        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
        return response.getBody();
    }
}
