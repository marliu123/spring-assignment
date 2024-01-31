package com.cooksys.social_media_api.repositories;

import com.cooksys.social_media_api.embeddables.Credentials;
import com.cooksys.social_media_api.entities.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//	List<User> findAllByDeletedFalse();
//
//	User findByUsername(String username);
//	
//	User findByCredentials(Credentials credentials);
}
