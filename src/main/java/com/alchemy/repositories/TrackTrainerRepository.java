package com.alchemy.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alchemy.entities.LearningTrackEntity;
import com.alchemy.entities.TrackTrainer;
import com.alchemy.entities.TrainersMasterEntity;
import com.alchemy.iListDto.IListTrackTrainer;

@Repository
public interface TrackTrainerRepository extends JpaRepository<TrackTrainer, Long> {

	public List<TrackTrainer> findByLearningTrackId(Long trackId);

	@Query(value = "select tt.id as Id,tm.name as TrainerName,tm.id as TrainerId,tm.description as Description, tl.id as TrackId,\r\n"
			+ "tl.learning_track_name as TrackName,tl.objective as Objective ,tl.curriculum as Curriculum,tl.start_date as StartDate,tl.end_date as EndDate from track_trainer tt \r\n"
			+ "join trainer_master tm on tm.id=tt.trainer_id\r\n"
			+ "join learning_track tl on tl.id=tt.track_id and tl.is_active=true and tm.is_active=true order by tt.id desc", nativeQuery = true)
	Page<IListTrackTrainer> findByOrderByIdDesc(Pageable pageable, Class<IListTrackTrainer> class1);

	@Query(value = "select tt.id as Id from track_trainer tt where tt.trainer_id=:id\r\n" + "", nativeQuery = true)
	List<Long> findByTrainerIds(@Param("id") Long id);

	@Transactional
	@Modifying
	void deleteByLearningTrackId(Long trackEntity);

	TrackTrainer findByLearningTrackAndTrainersMaster(LearningTrackEntity trackEntity, TrainersMasterEntity database);
}
