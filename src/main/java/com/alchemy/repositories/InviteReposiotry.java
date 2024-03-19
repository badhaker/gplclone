package com.alchemy.repositories;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alchemy.entities.InviteEntity;

public interface InviteReposiotry extends JpaRepository<InviteEntity, Long> {

	@Query(value = "select * from invite_entity where code=:id", nativeQuery = true)
	InviteEntity findbyUuid(@Param("id") String id);

	@Transactional
	void deleteByUserId(Long userId);

	@Query(value = "select * from invite_entity where code=:id", nativeQuery = true)
	InviteEntity findbyUuid(@Param("id") UUID uuid);

}
