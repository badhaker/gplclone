package com.alchemy.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alchemy.entities.EnrollNowTemp;
import com.alchemy.entities.LearningTrackEntity;
import com.alchemy.entities.UserEntity;
import com.alchemy.entities.UserTrackEntity;

@Repository
public interface EnrollNowTempRepository extends JpaRepository<EnrollNowTemp, Long>{

	List<EnrollNowTemp> findByBulkId(Long bulkUploadId);
	
//	@Query(value = "SELECT * FROM enroll_now_temp WHERE track_name =:trackName AND employee_email =:employeeEmail", nativeQuery = true)
//	EnrollNowTemp findByTrackNameAndUserEntity(@Param("trackName") String name, @Param("employeeEmail") String email);

}
