package com.sanglt.aggregator.controllers;

import com.sanglt.aggregator.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/chat")
    public ResponseEntity<?> chat() {
        userService.chat();
        return ResponseEntity.ok().build();
    }

}
