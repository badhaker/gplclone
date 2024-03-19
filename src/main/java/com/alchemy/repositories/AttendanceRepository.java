package com.alchemy.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alchemy.entities.AttendanceEntity;
import com.alchemy.entities.LearningTrackEntity;
import com.alchemy.entities.SubTrackEntity;
import com.alchemy.entities.UserEntity;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceEntity, Long>{

	List<AttendanceEntity> findByBulkId(Long bulkUploadId);


}
