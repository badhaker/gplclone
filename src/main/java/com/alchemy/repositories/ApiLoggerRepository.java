package com.alchemy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.alchemy.entities.ApiLoggerEntity;

@Repository
public interface ApiLoggerRepository extends JpaRepository<ApiLoggerEntity, Long> {

}
