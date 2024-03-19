package com.alchemy.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alchemy.entities.OtpEntity;

@Repository
public interface OTPRepository extends JpaRepository<OtpEntity, Long> {

	OtpEntity findByEmailIgnoreCase(String email);

	OtpEntity findByOtp(String otp);

	OtpEntity findByEmail(String email);

//	@Transactional
//	@Modifying
//    @Query(value = "DELETE FROM otp_logger u WHERE u.email=:email", nativeQuery = true)
	void deleteAllByEmail(String email);

}