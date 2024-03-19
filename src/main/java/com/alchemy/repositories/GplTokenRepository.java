package com.alchemy.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alchemy.entities.GplToken;

@Repository
public interface GplTokenRepository extends JpaRepository<GplToken, Long> {

	GplToken findGplTokenByEmail(String email);

	@Transactional
	void deleteByEmail(String emailId);
}
