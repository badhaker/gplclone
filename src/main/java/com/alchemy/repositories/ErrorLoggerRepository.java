package com.alchemy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.alchemy.entities.ErrorLoggerEntity;

@Repository
public interface ErrorLoggerRepository extends JpaRepository<ErrorLoggerEntity, Long> {

	@Query(value = "select count(*)\r\n" + "from error_logger e\r\n" + "where e.message = 'redisConnectionFail' \r\n"
			+ "      and e.created_at >= current_timestamp - interval '30 minute'", nativeQuery = true)
	long getErrorCount();

}
