package com.prabhav.myJournalApp.controller;

import com.prabhav.myJournalApp.api.response.WeatherResponse;
import com.prabhav.myJournalApp.entity.User;
import com.prabhav.myJournalApp.repository.UserRepository;
import com.prabhav.myJournalApp.service.UserService;
import com.prabhav.myJournalApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WeatherService weatherService;

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        User userInDb = userService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        userInDb.setUserName(user.getUserName());
        userInDb.setPassword(user.getPassword());
        userService.saveNewUser(userInDb);

        return new ResponseEntity<>(userInDb, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserById() {
        userRepository.deleteByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<?> greetings() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        WeatherResponse weatherResponse = weatherService.getWeather("Mumbai");
        String greeting = "";

        if (weatherResponse != null) {
            greeting = "Weather feels like " + weatherResponse.getCurrent().getFeelslike() + " degrees Celsius in your city";
        }

        return new ResponseEntity<>("Hi " + name + "! " + greeting, HttpStatus.OK);
    }
}
