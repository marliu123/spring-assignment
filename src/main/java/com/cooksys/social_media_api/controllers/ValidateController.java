package com.cooksys.social_media_api.controllers;

import com.cooksys.social_media_api.services.ValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/validate")
public class ValidateController {

    private ValidateService validateService;

    @GetMapping("/tag/exists/{label}")
    public boolean hashtagExists(){
        return validateService.hashtagExists();
    }

    @GetMapping("/username/exists/{username}")
    public boolean usernameExists(){
        return validateService.usernameExists();
    }

    @GetMapping("/username/available/{username}")
    public boolean usernameAvailable(){
        return validateService.usernameAvailable();
    }

}
