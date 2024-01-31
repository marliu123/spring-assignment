package com.cooksys.social_media_api.controllers;

import com.cooksys.social_media_api.services.ValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/validate")
public class ValidateController {

    private ValidateService validateService;

    @GetMapping("/tag/exists/{label}")
    public boolean hashtagExists(@PathVariable String label){
        return validateService.hashtagExists(label);
    }

    @GetMapping("/username/exists/{username}")
    public boolean usernameExists(@PathVariable String username){
        return validateService.usernameExists(username);
    }

    @GetMapping("/username/available/{username}")
    public boolean usernameAvailable(@PathVariable String username){
        return validateService.usernameAvailable(username);
    }

}
