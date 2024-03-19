package com.alchemy.repositories;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alchemy.entities.LearningTrackEntity;
import com.alchemy.entities.SponsorMaster;
import com.alchemy.entities.TrackSponsor;
import com.alchemy.iListDto.IListTrackSponsor;

@Repository
public interface TrackSponsorRepository extends JpaRepository<TrackSponsor, Long> {

	TrackSponsor findByLearningTrackIdAndSponsorId(Long learningTrackId, Long sponsorId);

	@Query(value = "select ts.track_id as LearningTrack, lt.learning_track_name as LearningTrackName, ts.sponsor_id as SponsorId,ts.sponsor_message as SponsorMessage, s.sponsor_name as SponsorName, s.description as Description \r\n"
			+ ",lt.objective as Objective, lt.curriculum as Curriculum, lt.end_date as EndDate \r\n"
			+ "from track_sponsor ts\r\n" + "join sponsors s on s.id= ts.sponsor_id\r\n"
			+ "join learning_track lt on lt.id= ts.track_id where ts.is_active=true and s.is_active=true and lt.is_active=true", nativeQuery = true)
	Page<IListTrackSponsor> findAllList(String search, Pageable pageable, Class<IListTrackSponsor> class1);

	public List<TrackSponsor> findByLearningTrackId(Long trackId);

	@Query(value = "select sponsor_id  from track_sponsor where track_id=:id", nativeQuery = true)
	public List<Long> findSponsorIdByLearningTrackId(@Param("id") Long id);
	
	@Query(value = "select ts.id  from track_sponsor ts where ts.sponsor_id=:id", nativeQuery = true)
	List<Long> findBySponserIds(@Param("id") Long id);

	@Transactional
	@Modifying
	void deleteByLearningTrack(LearningTrackEntity learningTrackEntity);

	List<TrackSponsor> findBySponsorAndLearningTrack(SponsorMaster sponsor, LearningTrackEntity trackEntity);

	List<TrackSponsor> findByLearningTrack(LearningTrackEntity learningTrackEntity);

	List<TrackSponsor> findBySponsor(SponsorMaster sponsorMaster);

}
