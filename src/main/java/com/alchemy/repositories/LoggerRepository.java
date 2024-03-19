package com.alchemy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alchemy.entities.LoggerEntity;

@Repository
public interface LoggerRepository extends JpaRepository<LoggerEntity, Long> {

	@Query(value = "select * from auth_logger al where al.user_id=:userId", nativeQuery = true)
	LoggerEntity getLog(@Param("userId") Long userId);

}
