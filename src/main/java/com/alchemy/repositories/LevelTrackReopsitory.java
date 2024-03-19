package com.alchemy.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alchemy.entities.LearningTrackEntity;
import com.alchemy.entities.LevelEntity;
import com.alchemy.entities.TrackLevelEntity;

@Repository
public interface LevelTrackReopsitory extends JpaRepository<TrackLevelEntity, Long> {

	@Transactional
	@Modifying
	void deleteByLearningTrackId(LearningTrackEntity entity);

	List<TrackLevelEntity> findByLearningTrackId(LearningTrackEntity learningTrackEntity);

	List<TrackLevelEntity> findByLearningTrackIdAndLevelId(LearningTrackEntity trackEntity, LevelEntity levelEntity);
	
	@Query(value = "SELECT level_id as id  FROM track_level WHERE track_id = :trackId and is_active = true", nativeQuery = true)
	List<Long> findLevelIdByTrackId(@Param("trackId") Long trackId);


}
