package com.alchemy.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alchemy.entities.LearningTrackEntity;
import com.alchemy.entities.TrackDepartmentEntity;

@Repository
public interface TrackDepartmentRepository extends JpaRepository<TrackDepartmentEntity, Long> {

	@Query(value = "select td.id  from track_department td where td.department_id=:deptId", nativeQuery = true)
	List<Long> findByIds(@Param("deptId") Long deptId);

	@Transactional
	@Modifying
	void deleteByLearningTrackEntityId(Long id);

	List<TrackDepartmentEntity> findByDepartmentEntityId(Long deptId);

	List<TrackDepartmentEntity> findByDepartmentEntityIdAndLearningTrackEntityId(Long id, Long id2);

	List<TrackDepartmentEntity> findByDepartmentEntityId(LearningTrackEntity learningTrackEntity);

//	List<TrackDepartmentEntity> findByTrackIdIn(List<Long> trackId);

//	List<Long> findDepartmentIdByTrackId(Long id);

	@Query(value = "SELECT td.department_id FROM track_department td WHERE td.track_id = :trackId", nativeQuery = true)
	List<Long> findDepartmentIdByTrackId(@Param("trackId") Long trackId);

}
