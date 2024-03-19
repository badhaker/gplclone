package com.alchemy.repositories;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alchemy.dto.DeleteId;
import com.alchemy.entities.LearningTrackEntity;
import com.alchemy.entities.SubTrackEntity;
import com.alchemy.iListDto.IListSubTrack;

@Repository
public interface SubTrackRepository extends JpaRepository<SubTrackEntity, Long> {

	@Query(value = "select ste.id as subtrackId,ste.name  as subtrackName, \r\n" + " ste.start_Date as StartDate ,\r\n"
			+ "	ste.end_Date as EndDate,lt.id as learningTrackId from sub_track_entity ste\r\n"
			+ "join learning_track lt On lt.id=ste.learning_track_entity  and ste.is_active=true order by ste.id desc", nativeQuery = true)
	Page<IListSubTrack> findByOrderByIdDesc(Pageable pageable, Class<IListSubTrack> class1);

	@Query(value = "select ste.id as SubtrackId,ste.name  as SubtrackName, ste.start_Date as StartDate, ste.end_Date as EndDate,ste.learning_track_entity as LearningTrackId\r\n"
			+ "from sub_track_entity ste where ste.is_active = true\r\n"
			+ "AND (:search = ' ' \r\n"
			+ "OR ste.name ILIKE '%' || REPLACE(REPLACE(REPLACE(REPLACE(:search, '%', '\\%'), '_', '\\_'), '[', '\\['), ']', '\\]') || '%')\r\n"
			+ "AND (:learningTrackId = '' OR ste.learning_track_entity IN (SELECT UNNEST(CAST(STRING_TO_ARRAY(:learningTrackId, ',') AS BIGINT[])))) \r\n "
			+ "group by ste.id order by ste.id desc", nativeQuery = true)
	Page<IListSubTrack> findAllSubTrack(@Param("search") String search,
			@Param("learningTrackId") String learningTrackId, Pageable pageable, Class<IListSubTrack> class1);

	SubTrackEntity findByName(String name);

	SubTrackEntity findByNameIgnoreCase(String name);

//	@Query(value = "select ste.id as SubtrackId,ste.name as SubtrackName,ste.learning_track_entity as LearningTrackId \r\n"
//			+ "			from sub_track_entity ste  where ste.learning_track_entity=:learningTrackId and ste.name=:name and is_active=true ", nativeQuery = true)
//	IListSubTrack findByNameAndlearning_track_entity(@Param("name") String name,
//			@Param("learningTrackId") Long learningTrackId);
	
	@Query(value = "SELECT ste.id AS SubtrackId, ste.name AS SubtrackName, ste.learning_track_entity AS LearningTrackId "
            + "FROM sub_track_entity ste "
            + "WHERE ste.learning_track_entity = :learningTrackId AND LOWER(ste.name) = LOWER(:name) AND is_active = true ", nativeQuery = true)
IListSubTrack findByNameAndlearning_track_entity(@Param("name") String name,
                                                       @Param("learningTrackId") Long learningTrackId);



	SubTrackEntity findByLearningTrackEntityAndNameIgnoreCase(LearningTrackEntity trackId, String subTrackName);

	ArrayList<SubTrackEntity> findByLearningTrackEntityId(Long id);

	@Query(value = "select * from sub_track_entity where learning_track_entity=:trackId And is_active=true", nativeQuery = true)
	ArrayList<SubTrackEntity> findAllByLearningTrackEntityId(@Param("trackId") Long id);

	@Query(value = "select ste.id as SubtrackId,ste.name  as SubtrackName, ste.start_Date as StartDate ,\r\n"
			+ "			ste.end_Date as EndDate,lt.id as LearningTrackId from sub_track_entity ste\r\n"
			+ "			join learning_track lt On lt.id=ste.learning_track_entity  \r\n"
			+ "			AND (:learningTrackId = '' OR ste.learning_track_entity IN (SELECT UNNEST(CAST(STRING_TO_ARRAY(:learningTrackId, ',') AS BIGINT[])))) \r\n"
			+ "			and ste.is_active=true order by ste.id asc	", nativeQuery = true)
	List<IListSubTrack> findByOrderByIdDesc(@Param("learningTrackId") String learningTrackId,
			Class<IListSubTrack> class1);

	ArrayList<SubTrackEntity> findByNameIn(List<String> subName);

	List<SubTrackEntity> findByLearningTrackEntity(LearningTrackEntity learningTrackEntity);

	SubTrackEntity findByLearningTrackEntityAndName(LearningTrackEntity trackEntity, String subTrackName);

	@Transactional
	@Modifying
	@Query(value = "update SubTrackEntity set isActive=false where id in :ids")
	void multiDeleteById(List<Long> ids);
	

}
