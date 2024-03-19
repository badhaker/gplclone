package com.alchemy.repositories;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alchemy.entities.LearningTrackEntity;
import com.alchemy.entities.SubTrackEntity;
import com.alchemy.entities.UserEntity;
import com.alchemy.entities.UserTrackEntity;
import com.alchemy.iListDto.IListEnroll;
import com.alchemy.iListDto.IListEnrollStatusCount;
import com.alchemy.iListDto.IListUserEnroll;
import com.alchemy.iListDto.IListUserTrack;

@Repository
public interface UserTrackRepository extends JpaRepository<UserTrackEntity, Long> {

	@Query(value = "select count(ut.enroll_status) as EnrollStatus from userTrack ut where ut.enroll_status=0", nativeQuery = true)
	IListEnrollStatusCount findByCountEnroll(Class<IListEnrollStatusCount> class1);

//	@Query(value = "select * from user_track ut where ut.user_id=:userId and ut.track_id=:trackId", nativeQuery = true)
	List<UserTrackEntity> findByUserEntityIdAndTrackId(@Param("userId") Long userId, LearningTrackEntity trackId);

	@Query(value = "select * from user_track ut where ut.user_id=:userId and ut.track_id=:trackId and ut.subtrack_id isnull", nativeQuery = true)
	UserTrackEntity findByUserEntityIdAndTrackId(@Param("trackId") LearningTrackEntity entity,
			@Param("userId") UserEntity userEntity);

	@Query(value = "select u.id as UserId ,u.user_name as UserName,\r\n"
			+ "			lt.id as TrackId,lt.learning_track_name as TrackName,ut.file_id as FileUrl,f.original_name AS OriginalName,lt.file_card_id ImageUrl,fi.original_name as OriginalImageName, \r\n"
			+ "			st.id as SubTrackId ,st.name as SubTrackName ,st.end_date as SubEndDate ,st.start_date as SubStartDate  ,\r\n"
			+ "			ut.complete_date as CompleteDate ,ut.enroll_status as EnrollStatus,lt.summary as Summary, ut.status as TrackStatus, ut.pre_assesment as PreAssesmentScore, ut.post_assesment as PostAssesmentScore \r\n"
			+ "			from learning_track lt\r\n"
			+ "			left join user_Track ut on ut.track_id=lt.id and ut.is_active=true and lt.is_active=true\r\n"
			+ "			left join file_upload f on f.id = ut.file_id left join file_upload fi on fi.id=lt.file_card_id\r\n"
			+ "			join users u on u.id=ut.user_id and u.is_active=true\r\n"
			+ "			left join sub_track_entity st on ut.subtrack_id=st.id and st.is_active=true\r\n"
			+ "			where ut.user_id=:userId and ut.enroll_status=0 order by ut.track_id asc ,ut.updated_at asc", nativeQuery = true)
	List<IListUserEnroll> findByUserId(@Param("userId") Long userId, Class<IListUserEnroll> class1);

	@Query(value = "select ut.id as Id, ut.user_id as UserId, u.user_name as UserName , ut.track_id as TrackId, \r\n "
			+ "lt.learning_track_name as TrackName , ut.enroll_status as EnrollStatus \r\n"
			+ ",u.department_id as DepartmentId, u.employee_level as EmployeeLevel\r\n" + "from user_track ut \r\n"
			+ "left join users u on u.id=ut.user_id\r\n"
			+ "left join learning_track lt on lt.id= ut.track_id where ut.subtrack_id is null\r\n"
			+ "order by ut.id desc", nativeQuery = true)
	List<IListUserTrack> findByOrderByIdDesc(Class<IListUserTrack> class1);

	@Query(value = "select ut.id as Id, ut.user_id as UserId, u.user_name as UserName , ut.track_id as TrackId,u.employee_id as EmployeeEdp,u.employee_grade as EmployeeGrade,\r\n"
			+ "lt.learning_track_name as TrackName , ut.enroll_status as EnrollStatus, u.email as Email , l.level_name as EmployeeLevel\r\n"
			+ ",u.function_id as FunctionId,  func.name as FunctionName, u.region as Region, u.zone as Zone, u.position_title as PositionTitle \r\n"
			+ "from user_track ut\r\n" + "left join users u on u.id=ut.user_id\r\n"
			+ "left join learning_track lt on lt.id= ut.track_id \r\n"
			+ "left join gpl_function func on func.id=u.function_id\r\n" + "left join level l on l.id=u.level_id  \r\n"
			+ "where ut.subtrack_id is null\r\n"
			+ "AND (:search = ' ' \r\n"
			+ "OR lt.learning_track_name ILIKE '%' || REPLACE(REPLACE(REPLACE(REPLACE(:search, '%', '\\%'), '_', '\\_'), '[', '\\['), ']', '\\]') || '%' \r\n"
			+ "OR u.user_name ILIKE '%' || REPLACE(REPLACE(REPLACE(REPLACE(:search, '%', '\\%'), '_', '\\_'), '[', '\\['), ']', '\\]') || '%' \r\n"
			+ "OR u.email ILIKE '%' || REPLACE(REPLACE(REPLACE(REPLACE(:search, '%', '\\%'), '_', '\\_'), '[', '\\['), ']', '\\]') || '%')\r\n"
			+ "AND (:email = '' OR EXISTS (\r\n" + "    SELECT 1\r\n"
			+ "    FROM unnest(string_to_array(:email, ',')) AS email\r\n"
			+ "    WHERE LOWER(CAST(u.email AS text)) LIKE '%' || LOWER(email) || '%'\r\n" + "))\r\n"
			+ "AND (:track = '' OR  ut.track_id  IN (SELECT unnest(cast(string_to_array(:track, ',')as bigint[]))))\r\n"
			+ "AND (:name = '' OR EXISTS (\r\n" + "    SELECT 1\r\n"
			+ "    FROM unnest(string_to_array(:name, ',')) AS name\r\n"
			+ "    WHERE LOWER(CAST(u.user_name AS text)) LIKE '%' || LOWER(name) || '%'\r\n" + "))\r\n"
			+ "AND (:status= '' OR ut.enroll_status IN (select unnest(cast(string_to_array(:status, ',') as bigint[]))))\r\n"
			+ "order by ut.id desc", countQuery = "select count(*) from (select ut.id as Id, ut.user_id as UserId, u.user_name as UserName , ut.track_id as TrackId,\r\n"
					+ "			lt.learning_track_name as TrackName ,u.employee_id as EmployeeEdp,u.employee_grade as EmployeeGrade, ut.enroll_status as EnrollStatus, u.email as Email , l.level_name as EmployeeLevel\r\n"
					+ "			,u.function_id as FunctionId,  func.name as FunctionName,u.region as Region, u.zone as Zone , u.position_title as PositionTitle\r\n"
					+ "			from user_track ut\r\n" + "			left join users u on u.id=ut.user_id\r\n"
					+ "			left join learning_track lt on lt.id= ut.track_id \r\n"
					+ "			left join gpl_function func on func.id=u.function_id\r\n"
					+ "         left join level l on l.id=u.level_id  \r\n"
					+ "			where ut.subtrack_id is null\r\n"
					+ "AND (:search = ' ' \r\n"
					+ "OR lt.learning_track_name ILIKE '%' || REPLACE(REPLACE(REPLACE(REPLACE(:search, '%', '\\%'), '_', '\\_'), '[', '\\['), ']', '\\]') || '%' \r\n"
					+ "OR u.user_name ILIKE '%' || REPLACE(REPLACE(REPLACE(REPLACE(:search, '%', '\\%'), '_', '\\_'), '[', '\\['), ']', '\\]') || '%' \r\n"
					+ "OR u.email ILIKE '%' || REPLACE(REPLACE(REPLACE(REPLACE(:search, '%', '\\%'), '_', '\\_'), '[', '\\['), ']', '\\]') || '%')\r\n"
					+ "         AND (:email = '' OR EXISTS (\r\n" + "         SELECT 1\r\n"
					+ "         FROM unnest(string_to_array(:email, ',')) AS email\r\n"
					+ "         WHERE LOWER(CAST(u.email AS text)) LIKE '%' || LOWER(email) || '%'))\r\n"
					+ "         AND (:track = '' OR  ut.track_id  IN (SELECT unnest(cast(string_to_array(:track, ',')as bigint[]))))\r\n"
					+ "         AND (:name = '' OR EXISTS (\r\n" + "         SELECT 1\r\n"
					+ "         FROM unnest(string_to_array(:name, ',')) AS name\r\n"
					+ "         WHERE LOWER(CAST(u.user_name AS text)) LIKE '%' || LOWER(name) || '%'))\r\n"
					+ "         AND (:status= '' OR ut.enroll_status IN (select unnest(cast(string_to_array(:status, ',') as bigint[]))))\r\n"
					+ "			group by ut.id , ut.user_id , u.user_name , ut.track_id ,u.email,\r\n"
					+ "			lt.learning_track_name , ut.enroll_status, u.employee_id ,u.employee_grade\r\n"
					+ "			,u.function_id , func.name,l.level_name, u.region, u.zone , u.position_title \r\n"
					+ "			order by ut.id desc) alias	", nativeQuery = true)
	Page<IListUserTrack> findAllList(@Param("search") String search, @Param("track") String track,
			@Param("name") String name, @Param("status") String status, @Param("email") String email, Pageable pageable,
			Class<IListUserTrack> class1);

	List<UserTrackEntity> findByDepartmentIdId(Long deptId);

	@Query(value = "select u.employee_id as EmployeeEdp,u.user_name as Name,u.email as Email,u.zone as Zone,u.region as Region,u.project as Project,\r\n"
			+ "u.employee_level as EmployeeLevel,\r\n"
			+ "u.employee_grade as EmployeeGrade, u.position_title as PositionTitle,\r\n"
			+ "func.name as FunctionName, lt.learning_track_name as LearningTrackName, ut.enroll_status as EnrollmentStatus\r\n"
			+ "from user_track ut \r\n" + "left join users u ON u.id = ut.user_id\r\n"
			+ "left join gpl_function func on func.id=u.function_id\r\n"
			+ "left join learning_track lt ON lt.id = ut.track_id\r\n"
			+ "where ut.subtrack_id is null ORDER BY ut.id DESC", nativeQuery = true)
	List<IListEnroll> getEnrollNowUsers(HttpServletResponse response, Class<IListEnroll> class1);

	UserTrackEntity findBySubtrackId(SubTrackEntity subtrackId);

	List<UserTrackEntity> findByTrackIdId(Long learningTrackId);

	ArrayList<UserTrackEntity> findBytrackIdId(Long learningTrackId);

	List<UserTrackEntity> findByTrackId(LearningTrackEntity learningTrackId);

	ArrayList<UserTrackEntity> findBysubtrackIdId(Long subTrackEntity);

	@Query(value = "SELECT * FROM user_track WHERE track_id = :trackId AND user_id = :userId AND subtrack_id = :subTrackId AND is_active = true", nativeQuery = true)
	UserTrackEntity findByTrackIdAndUserEntityAndSubtrackId(@Param("trackId") LearningTrackEntity entity,
			@Param("userId") UserEntity userEntity, @Param("subTrackId") SubTrackEntity subTrackEntity);

	@Query(value = "SELECT * FROM user_track ut\r\n" + "join learning_track t ON t.id = ut.track_id\r\n"
			+ "join users u ON u.id = ut.user_id\r\n"
			+ "WHERE t.learning_track_name =:trackId AND u.email =:userId AND ut.is_active = true", nativeQuery = true)
	List<UserTrackEntity> findByTrackIdAndUserId(@Param("trackId") String trackId, @Param("userId") String userId);

	@Query(value = "SELECT * FROM user_track WHERE  user_id = :userId AND track_id = :trackId AND subtrack_id is not null AND is_active = true", nativeQuery = true)
	List<UserTrackEntity> findByTrackIdAndUserId(@Param("trackId") LearningTrackEntity learningTrackEntity,
			@Param("userId") UserEntity userEntity);

	@Query(value = "SELECT * FROM user_track WHERE track_id = :trackId AND user_id = :userId AND subtrack_id is null AND is_active = true", nativeQuery = true)
	UserTrackEntity findByUserIdAndTrackId(@Param("userId") UserEntity userEntity,
			@Param("trackId") LearningTrackEntity learningTrackEntity);

	List<UserTrackEntity> findByTrackIdAndUserEntity(LearningTrackEntity learningTrackEntity, UserEntity userEntity);

	@Query(value = "SELECT id FROM user_track WHERE track_id = :trackId AND user_id = :userId ", nativeQuery = true)
	List<Long> findByTrackEnityAndUserEntity(@Param("trackId") Long trackId, @Param("userId") Long userId);

	UserTrackEntity findByUserEntityAndTrackId(UserEntity userEntity, LearningTrackEntity learningTrackEntity);

	@Query(value = "SELECT lt.learning_track_name from user_track ut join learning_track lt on lt.id=ut.track_id where ut.id= :id", nativeQuery = true)
	String findTrackNameById(Long id);

//	@Transactional
//	@Modifying
//	@Query("update UserTrackEntity set enroll_status = :status,updatedBy = :userId where id in :ids and subtrack_id is null")
//	void markEnrollStatus(List<Long> ids, int status, Long userId);

	@Transactional
	@Modifying
	@Query("update UserTrackEntity set enroll_status = :status,updatedBy = :userId where id in :ids and enroll_status in (2,3)  ")
	void markEnrollStatusForSubtrack(List<Long> ids, int status, Long userId);

}
