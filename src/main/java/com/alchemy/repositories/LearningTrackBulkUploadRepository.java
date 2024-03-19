package com.alchemy.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.alchemy.entities.LearningTrackBulkUploadTemporaryEntity;

@Repository
public interface LearningTrackBulkUploadRepository extends JpaRepository<LearningTrackBulkUploadTemporaryEntity, Long> {

	List<LearningTrackBulkUploadTemporaryEntity> findByBulkId(Long bulkId);

	@Query(value = "select LTB.bulk_upload_id  from learning_track_bulk_upload_temporary_entity as LTB where bulk_upload_id=?", nativeQuery = true)
	List<Long> findByBulk(Long bulkId);

}
