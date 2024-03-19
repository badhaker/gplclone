package com.alchemy.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.alchemy.entities.NudgedTracks;

import io.lettuce.core.dynamic.annotation.Param;

public interface NudgedTracksRepository extends JpaRepository<NudgedTracks, Long> {

	@Transactional
	@Modifying
	@Query("delete from NudgedTracks where track_id=:id")
	void deletedeleteByLearningTrackId(@Param("id") Long id);

}
