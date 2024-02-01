package com.cooksys.social_media_api.services.impl;

import com.cooksys.social_media_api.entities.Hashtag;
import com.cooksys.social_media_api.entities.User;
import com.cooksys.social_media_api.repositories.HashtagRepository;
import com.cooksys.social_media_api.repositories.UserRepository;
import com.cooksys.social_media_api.services.ValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ValidateServiceImpl implements ValidateService {

    private final HashtagRepository hashtagRepository;
    private final UserRepository userRepository;

    @Override
    public boolean hashtagExists(String label) {

        List<Hashtag> allHashtags = hashtagRepository.findAll();
        for (Hashtag h : allHashtags) {
            if (h.getLabel() == label) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean usernameExists(String username) {
        List<User> allUsersWithDeleted = userRepository.findAll();
        for (User u : allUsersWithDeleted) {
            if (u.getCredentials().getUsername() == username) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean usernameAvailable(String username) {
        return !usernameExists(username);
    }
}
