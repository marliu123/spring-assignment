package com.cooksys.social_media_api.services;

public interface ValidateService {
    boolean hashtagExists();

    boolean usernameExists();

    boolean usernameAvailable();
}
