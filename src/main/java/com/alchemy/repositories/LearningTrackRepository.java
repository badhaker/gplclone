package com.alchemy.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.alchemy.entities.LearningTrackEntity;
import com.alchemy.iListDto.IListLearningTrack;
import com.alchemy.iListDto.IListLearningTrackDetail;

@Repository
@Transactional
public interface LearningTrackRepository extends JpaRepository<LearningTrackEntity, Long> {

	LearningTrackEntity findByNameContainingIgnoreCase(String name);

	IListLearningTrackDetail findByIdAndIsActiveTrue(Long id, Class<IListLearningTrackDetail> class1);

	LearningTrackEntity findByNameIgnoreCaseAndIsActiveTrue(String trackName);

	LearningTrackEntity findByNameIgnoreCase(String stringCellValue);

	@Query(value = "SELECT lt.id AS Id,lt.learning_track_name AS Name,ut.enroll_status AS EnrollStatus,\r\n"
			+ "									ut.status AS Status,   lt.summary AS Summary ,lt.enroll_close_date as EnrollCloseDate,lt.enroll_start_date as EnrollStartDate, \r\n"
			+ "									lt.end_date AS EndDate,    lt.start_date AS StartDate,\r\n"
			+ "									String_agg(d.function_names,',')   AS FunctionNames,String_agg(lev.level_names,',')  AS LevelNames,\r\n"
			+ "									f.original_name AS OriginalName, f.filename as FileName,  f.id AS FileId, \r\n"
			+ "									ut.is_star_performer as IsStarPerformer,lt.is_visible as IsVisible,bu.original_name AS BannerUrl,bcu.original_name AS BannerCardUrl,ftt.original_name as NudgedFileId FROM learning_track lt \r\n"
			+ "									left join user_track ut on ut.track_id=lt.id and ut.user_id=:userId  and ut.subtrack_id isnull and ut.is_active=true\r\n"
			+ "									LEFT JOIN file_upload f ON f.id = lt.file_id \r\n"
			+ "									LEFT JOIN file_upload bu ON bu.id = lt.banner_file_id\r\n"
			+ "									LEFT JOIN file_upload bcu ON bcu.id = lt.file_card_id\r\n"
			+ "									LEFT JOIN file_upload ftt on ftt.id=lt.nudged_file_id \r\n"
			+ "									LEFT JOIN (SELECT tf.track_id,STRING_AGG(fun.name, ',') AS function_names FROM\r\n"
			+ "									track_function tf JOIN gpl_function fun ON fun.id = tf.function_id GROUP BY tf.track_id) d ON d.track_id = lt.id\r\n"
			+ "									LEFT JOIN (SELECT tl.track_id,STRING_AGG(l.level_name, ',') AS level_names FROM\r\n"
			+ "	track_level tl JOIN level l ON l.id = tl.level_id GROUP BY tl.track_id) lev ON lev.track_id = lt.id\r\n"
			+ "	WHERE lt.is_active = TRUE  \r\n"
			+ " AND (:search = ' ' OR lt.learning_track_name ILIKE '%' || REPLACE(REPLACE(REPLACE(REPLACE(:search, '%', '\\%'), '_', '\\_'), '[', '\\['), ']', '\\]') || '%') \r\n"
			+ "									GROUP BY lt.id,lt.learning_track_name,ut.enroll_status,ut.status,lt.summary,lt.enroll_close_date,\r\n"
			+ "									lt.end_date,lt.start_date,f.original_name,f.filename,f.id,ut.is_star_performer,lt.is_visible,bu.original_name,bcu.original_name,ut.id,ftt.original_name\r\n"
			+ "									order by lt.id desc 	 \r\n" + "\r\n" + "", nativeQuery = true)
	Page<IListLearningTrack> findByNameContainingIgnoreCaseOrderByIdDesc(@Param("search") String search,
			@Param("userId") Long userId, Pageable pageable, Class<IListLearningTrack> class1);

	@Query(value = "SELECT lt.id AS Id, lt.learning_track_name AS Name, ut.enroll_status AS EnrollStatus, \r\n"
			+ "ut.status AS Status, lt.summary AS Summary, lt.enroll_close_date as EnrollCloseDate, \r\n"
			+ "lt.enroll_start_date as EnrollStartDate, lt.end_date AS EndDate, lt.start_date AS StartDate, \r\n"
			+ "String_agg(d.function_names, ',') AS FunctionNames, String_agg(lev.level_names, ',') AS LevelNames, \r\n"
			+ "f.original_name AS OriginalName, f.filename AS FileName, f.id AS FileId, \r\n"
			+ "ut.is_star_performer AS IsStarPerformer, lt.is_visible AS IsVisible, \r\n"
			+ "bu.original_name AS BannerUrl, bcu.original_name AS BannerCardUrl,ftt.original_name as NudgedFileId  FROM learning_track lt \r\n"
			+ "LEFT JOIN user_track ut ON ut.track_id = lt.id AND ut.user_id =:userId and ut.is_active=true\r\n"
			+ "LEFT JOIN file_upload f ON f.id = lt.file_id  LEFT JOIN file_upload bu ON bu.id = lt.banner_file_id \r\n"
			+ "LEFT JOIN file_upload bcu ON bcu.id = lt.file_card_id \r\n"
			+ "LEFT JOIN nudged_tracks nt on nt.track_id=lt.id and nt.user_id =:userId\r\n"
			+ "LEFT JOIN file_upload ftt on ftt.id=lt.nudged_file_id \r\n"
			+ "LEFT JOIN (SELECT tf.track_id, STRING_AGG(fun.name, ',') AS function_names \r\n"
			+ "FROM track_function tf             JOIN gpl_function fun ON fun.id = tf.function_id \r\n"
			+ "GROUP BY tf.track_id) d ON d.track_id = lt.id \r\n"
			+ "LEFT JOIN (SELECT tl.track_id, STRING_AGG(l.level_name, ',') AS level_names \r\n"
			+ "FROM track_level tl             JOIN level l ON l.id = tl.level_id \r\n"
			+ "GROUP BY tl.track_id) lev ON lev.track_id = lt.id \r\n"
			+ "WHERE lt.is_active = TRUE AND lt.is_visible = true and ut.subtrack_id isnull\r\n"
			+ "AND (:search = ' ' OR lt.learning_track_name ILIKE '%' || REPLACE(REPLACE(REPLACE(REPLACE(:search, '%', '\\%'), '_', '\\_'), '[', '\\['), ']', '\\]') || '%') \r\n"
			+ "GROUP BY lt.id, lt.learning_track_name, ut.enroll_status, ut.status, lt.summary, lt.enroll_close_date, \r\n"
			+ "lt.end_date, lt.start_date, f.original_name, f.filename, f.id, ut.is_star_performer, \r\n"
			+ "lt.is_visible, bu.original_name, bcu.original_name, ut.id ,nt.track_id,ftt.original_name ORDER BY CASE \r\n"
			+ "when nt.track_id is not null then 1\r\n" + "WHEN ut.enroll_status = '0' THEN 2 \r\n"
			+ "WHEN ut.enroll_status = '2' THEN 3 \r\n" + "WHEN ut.enroll_status = '3' THEN 4 \r\n"
			+ "WHEN ut.enroll_status isnull and lt.enroll_start_date <= CURRENT_DATE AND lt.enroll_close_date >= CURRENT_DATE THEN 5 \r\n"
			+ "WHEN ut.enroll_status = '1' THEN 6\r\n"
			+ "WHEN ut.enroll_status isnull and  CURRENT_DATE < lt.enroll_start_date  THEN 7             ELSE 8 \r\n"
			+ "END ASC,ut.updated_at desc, lt.id DESC", nativeQuery = true)
	Page<IListLearningTrack> findByNameForUser(@Param("search") String search, @Param("userId") Long userId,
			Pageable pageable, Class<IListLearningTrack> class1);

	@Transactional
	@Modifying
	@Query("update LearningTrackEntity set isActive = false where id in :ids")
	void deleteAllByIdIn(List<Long> ids);

	@Transactional
	@Modifying
	@Query(value = "update LearningTrackEntity set isVisible = :isVisble where id in :ids")
	void updateLearningTrackIsVisible(@Param("isVisble") Boolean value, @Param("ids") List<Long> ids);

}
