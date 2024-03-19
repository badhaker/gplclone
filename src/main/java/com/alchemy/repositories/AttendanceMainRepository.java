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

import com.alchemy.entities.Attendance;
import com.alchemy.entities.LearningTrackEntity;
import com.alchemy.entities.SubTrackEntity;
import com.alchemy.entities.UserEntity;
import com.alchemy.iListDto.IListAttendance;

@Repository
public interface AttendanceMainRepository extends JpaRepository<Attendance, Long> {

	@Query(value = "SELECT a.id AS Id, a.date AS Date, a.employee_id AS UserId, u.user_name AS Name, u.email AS Email, a.zone AS Zone, a.function_name AS FunctionName,\r\n"
			+ "a.track_id AS LearningTrackId, a.subtrack_id AS SubtrackId, \r\n"
			+ "a.complete_date_of_attendance AS CompleteDateOfAttendance ,a.created_at as CreatedAt,a.star_performer as StarPerformer,u.employee_id as EmployeeEdp\r\n"
			+ ",lt.learning_track_name as LearningTrackName , sub.name as SubTrackName\r\n" + "FROM attendance a\r\n"
			+ "LEFT JOIN users u ON u.id = a.employee_id\r\n" + "Left join learning_track lt ON lt.id = a.track_id\r\n"
			+ "left join sub_track_entity sub ON sub.id = a.subtrack_id\r\n" + "WHERE a.is_Active = true\r\n"
			+ "ORDER BY a.id DESC", nativeQuery = true)
	Page<IListAttendance> findByOrderByIdDesc(Pageable pageable, Class<IListAttendance> class1);

	Attendance findByLearningTrackIdAndUserIdAndSubTrackId(LearningTrackEntity leaId, UserEntity userId,
			SubTrackEntity subId);

	Attendance findByUserId(Long employeeEdp);

	@Query(value = "SELECT a.id AS Id, a.updated_at AS Date, a.employee_id AS UserId,\r\n"
			+ "u.user_name AS Name,\r\n"
			+ "u.email AS Email,a.lock_attendance as LockAttendance,a.attendance_status as Attendance, \r\n"
			+ "u.zone AS Zone,u.employee_grade as Grade,u.position_title AS Position,\r\n"
			+ "u.region AS Region , u.level_id as LevelId, lev.level_name as EmployeeLevel ,\r\n"
			+ "u.function_id AS FunctionId,\r\n"
			+ "func.name AS FunctionName,\r\n"
			+ "a.track_id AS LearningTrackId,\r\n"
			+ "a.subtrack_id AS SubtrackId,\r\n"
			+ "a.complete_date_of_attendance AS CompleteDateOfAttendance,\r\n"
			+ "a.created_at AS CreatedAt, a.pre_assesment as PreAssesmentScore, a.post_assesment as PostAssesmentScore,\r\n"
			+ "a.star_performer AS StarPerformer,\r\n"
			+ "u.employee_id AS EmployeeEdp,\r\n"
			+ "lt.learning_track_name AS LearningTrackName,\r\n"
			+ "sub.name AS SubTrackName\r\n"
			+ "FROM\r\n"
			+ "attendance a\r\n"
			+ "LEFT JOIN\r\n"
			+ "users u ON u.id = a.employee_id\r\n"
			+ "LEFT JOIN\r\n"
			+ "learning_track lt ON lt.id = a.track_id\r\n"
			+ "LEFT JOIN\r\n"
			+ "sub_track_entity sub ON sub.id = a.subtrack_id\r\n"
			+ "LEFT JOIN\r\n"
			+ "gpl_function func ON func.id= u.function_id\r\n"
			+ "JOIN\r\n"
			+ "level lev ON lev.id= u.level_id \r\n"
			+ "WHERE\r\n"
			+ "a.is_active = true\r\n"
			+ "AND (:search = ' ' OR func.name ILIKE '%' || REPLACE(REPLACE(REPLACE(REPLACE(:search, '%', '\\%'), '_', '\\_'), '[', '\\['), ']', '\\]') || '%' \r\n"
			+ "		OR u.employee_id ILIKE '%' || REPLACE(REPLACE(REPLACE(REPLACE(:search, '%', '\\%'), '_', '\\_'), '[', '\\['), ']', '\\]') || '%' \r\n"
			+ "		OR u.user_name ILIKE '%' || REPLACE(REPLACE(REPLACE(REPLACE(:search, '%', '\\%'), '_', '\\_'), '[', '\\['), ']', '\\]') || '%' \r\n"
			+ "		OR a.zone ILIKE '%' || REPLACE(REPLACE(REPLACE(REPLACE(:search, '%', '\\%'), '_', '\\_'), '[', '\\['), ']', '\\]') || '%')\r\n"
			+ "AND (:emaployeeEdp = '' OR EXISTS (\r\n" + "   SELECT 1\r\n"
			+ "   FROM unnest(string_to_array(:emaployeeEdp, ',')) AS emaployeeEdp\r\n"
			+ "   WHERE LOWER(CAST(u.employee_id AS text)) LIKE '%' || LOWER(emaployeeEdp) || '%'\r\n" + "))\r\n"
			+ "AND (:email = '' OR EXISTS (\r\n" + "    SELECT 1\r\n"
			+ "    FROM unnest(string_to_array(:email, ',')) AS email\r\n"
			+ "    WHERE LOWER(CAST(u.email AS text)) LIKE '%' || LOWER(email) || '%'\r\n" + "))\r\n"
			+ "AND (:ezone = '' OR EXISTS (\r\n" + "   SELECT 1\r\n"
			+ "   FROM unnest(string_to_array(:ezone, ',')) AS ezone\r\n"
			+ "   WHERE LOWER(CAST(u.zone AS text)) LIKE '%' || LOWER(ezone) || '%'\r\n" + "))\r\n"
			+ "AND (:function = '' OR  CAST(u.function_id AS text) IN (SELECT unnest(string_to_array(:function, ','))))\r\n"
			+ "AND (:region = '' OR EXISTS (\r\n" + "    SELECT 1\r\n"
			+ "    FROM unnest(string_to_array(:region, ',')) AS region\r\n"
			+ "    WHERE LOWER(CAST(u.region AS text)) LIKE '%' || LOWER(region) || '%'\r\n" + "))\r\n"
			+ "AND (:level = '' OR EXISTS (\r\n" + "    SELECT 1\r\n"
			+ "    FROM unnest(string_to_array(:level, ',')) AS level\r\n"
			+ "    WHERE LOWER(CAST(lev.level_name AS text)) LIKE '%' || LOWER(level) || '%'\r\n" + "))\r\n"
			+ "AND (:grade = '' OR EXISTS (\r\n" + "    SELECT 1\r\n"
			+ "    FROM unnest(string_to_array(:grade, ',')) AS grade\r\n"
			+ "    WHERE LOWER(CAST(u.employee_grade AS text)) LIKE '%' || LOWER(grade) || '%'\r\n" + "))\r\n"
			+ "AND (:position = '' OR EXISTS (\r\n" + "    SELECT 1\r\n"
			+ "    FROM unnest(string_to_array(:position, ',')) AS position\r\n"
			+ "    WHERE LOWER(CAST(u.position_title AS text)) LIKE '%' || LOWER(position) || '%'\r\n" + "))\r\n"
			+ "AND (:name = '' OR EXISTS (\r\n" + "    SELECT 1\r\n"
			+ "    FROM unnest(string_to_array(:name, ',')) AS name\r\n"
			+ "    WHERE LOWER(CAST(u.user_name AS text)) LIKE '%' || LOWER(name) || '%'\r\n" + "))\r\n"
			+ "AND (:track = '' OR  a.track_id  IN (SELECT unnest(cast(string_to_array(:track, ',')as bigint[]))))\r\n"
			+ "AND (:subtrack = '' OR  a.subtrack_id IN (SELECT unnest(cast(string_to_array(:subtrack, ',')as bigint[]))))\r\n"
			+ "AND (:subtrackCompleteDate = '' OR TO_CHAR(a.complete_date_of_attendance, 'YYYY-MM-DD') IN (SELECT unnest(string_to_array(:subtrackCompleteDate, ','))))\r\n"
			+ "AND (:starPerformer = '' OR  CAST(a.star_performer AS text) IN (SELECT unnest(string_to_array(:starPerformer, ','))))\r\n"
			+ "AND (:status= '' OR a.attendance_status IN (select unnest(cast(string_to_array(:status, ',') as bigint[]))))\r\n"
			+ "	ORDER BY\r\n" + "	 a.id DESC", nativeQuery = true)
	Page<IListAttendance> findByName(@Param("search") String search, @Param("name") String name,
			@Param("emaployeeEdp") String edp, @Param("email") String email, @Param("function") String function,
			@Param("ezone") String zone, @Param("region") String region, @Param("level") String level,
			@Param("grade") String grade, @Param("position") String position, @Param("track") String track,
			@Param("subtrack") String subtrack, @Param("subtrackCompleteDate") String subtrackCompleteDate,
			@Param("starPerformer") String starPerformer, @Param("status") String status, Pageable pageable,
			Class<IListAttendance> class1);

	
	    	
//	@Query(value = "SELECT a.id AS Id, a.date AS Date, a.employee_id AS UserId, u.user_name AS Name, u.email AS Email, a.zone AS Zone, a.function_name AS FunctionName,\r\n"
//			+ "a.track_id AS LearningTrackId, a.subtrack_id AS SubtrackId, \r\n"
//			+ "a.complete_date_of_attendance AS CompleteDateOfAttendance ,a.created_at as CreatedAt,a.star_performer as StarPerformer,u.employee_id as EmployeeEdp\r\n"
//			+ ",lt.learning_track_name as LearningTrackName , sub.name as SubTrackName\r\n"
//			+ "FROM attendance a\r\n"
//			+ "LEFT JOIN users u ON u.id = a.employee_id\r\n"
//			+ "Left join learning_track lt ON lt.id = a.track_id\r\n"
//			+ "left join sub_track_entity sub ON sub.id = a.subtrack_id\r\n"
//			+ "WHERE a.is_Active = true\r\n"
//			+ "ORDER BY a.id DESC",nativeQuery = true)
//	List<IListAttendance> findAllAttendance(Class<IListAttendance> class1);

	@Query(value = "SELECT a.id AS Id, a.date AS Date, a.employee_id AS UserId, u.user_name AS Name, u.email AS Email, a.zone AS Zone, a.function_name AS FunctionName,\r\n"
			+ "a.track_id AS LearningTrackId, a.subtrack_id AS SubtrackId, \r\n"
			+ "a.complete_date_of_attendance AS CompleteDateOfAttendance ,a.created_at as CreatedAt,a.star_performer as StarPerformer,u.employee_id as EmployeeEdp\r\n"
			+ ",lt.learning_track_name as LearningTrackName , sub.name as SubTrackName\r\n" + "FROM attendance a\r\n"
			+ "LEFT JOIN users u ON u.id = a.employee_id\r\n" + "Left join learning_track lt ON lt.id = a.track_id\r\n"
			+ "left join sub_track_entity sub ON sub.id = a.subtrack_id\r\n" + "WHERE a.is_Active = true\r\n"
			+ "ORDER BY a.id DESC", nativeQuery = true)
	List<IListAttendance> exportAttendance(HttpServletResponse response, Class<IListAttendance> class1);

	@Transactional
	@Modifying
	@Query("update Attendance set lock_attendance = :lock, updatedBy = :userId where id IN :ids")
	void lockAttendanceByIdIn(List<Long> ids, @Param("lock") Boolean boolean1, Long userId);

	@Transactional
	@Modifying
	@Query("update Attendance set attendance_status = :attendance,updatedBy = :userId where id in :ids and lock_attendance = false")
	void markAttendance(List<Long> ids, @Param("attendance") int attendanceStatus, Long userId);

	@Transactional
	@Modifying
	@Query("update Attendance set isActive = false ,updatedBy = :userId where id in :ids")
	void deleteAllByIdIn(List<Long> ids, Long userId);

	ArrayList<Attendance> findBySubTrackId(SubTrackEntity subTrackId);

	ArrayList<Attendance> findByLearningTrackId(LearningTrackEntity learningTrackEntity);

	ArrayList<Attendance> findByLearningTrackIdAndUserId(LearningTrackEntity leaId, UserEntity userId);

	@Transactional
	@Modifying
	@Query("update Attendance set star_performer = :star,updatedBy = :userId where id in :ids and lock_attendance = false")
	void markAllByIdIn(List<Long> ids,@Param("star") Boolean star, Long userId); 
	 
//	@Transactional
//	@Modifying
//	@Query(value = "UPDATE attendance\r\n"
//			+ "			SET complete_date_of_attendance = (\r\n"
//			+ "			    SELECT end_date\r\n"
//			+ "			    FROM sub_track_entity\r\n"
//			+ "			    WHERE id = attendance.subtrack_id\r\n"
//			+ "			)\r\n"
//			+ "			WHERE id in :ids \r\n"
//			+ "",nativeQuery = true)
//	void setCompleteDate(@Param("ids")  List<Long> ids);

}
