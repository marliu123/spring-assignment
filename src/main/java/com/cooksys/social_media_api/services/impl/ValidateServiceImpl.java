package com.cooksys.social_media_api.services.impl;

import com.cooksys.social_media_api.repositories.HashtagRepository;
import com.cooksys.social_media_api.services.ValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidateServiceImpl implements ValidateService {

    private HashtagRepository hashtagRepository;

    @Override
    public boolean hashtagExists() {
        return false;
    }

    @Override
    public boolean usernameExists() {
        return false;
    }

    @Override
    public boolean usernameAvailable() {
        return false;
    }
}
