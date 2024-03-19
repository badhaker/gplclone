package com.alchemy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alchemy.entities.ThirdPartyLoginCredentials;

@Repository
public interface ThirdPartyCredentialsRepo extends JpaRepository<ThirdPartyLoginCredentials, Long> {
	
 	ThirdPartyLoginCredentials findByApiName(String email);

}
