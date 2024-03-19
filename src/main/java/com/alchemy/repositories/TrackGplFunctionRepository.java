package com.alchemy.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alchemy.entities.TrackGplFunctionEntity;

@Repository
public interface TrackGplFunctionRepository extends JpaRepository<TrackGplFunctionEntity, Long> {

	@Transactional
	@Modifying
	void deleteByLearningTrackEntityId(Long id);

	@Query(value = "SELECT function_id FROM track_function WHERE track_id = :trackId and is_active = true", nativeQuery = true)
	List<Long> findDepartmentIdByTrackId(@Param("trackId") Long trackId);

	List<TrackGplFunctionEntity> findByGplFunctionEntityIdAndLearningTrackEntityId(Long id, Long id2);

	List<TrackGplFunctionEntity> findByGplFunctionEntityId(Long functionIds);

}
