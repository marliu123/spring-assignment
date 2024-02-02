package com.cooksys.social_media_api.repositories;

import com.cooksys.social_media_api.embeddables.Credentials;
import com.cooksys.social_media_api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	List<User> findAllByDeletedFalse();
  
	User findByCredentials(Credentials credential);
	
	User findByCredentialsUsername(String username);


}
