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

import com.alchemy.entities.CareerAspirationEntity;
import com.alchemy.entities.UserEntity;
import com.alchemy.iListDto.IListCareerAspiration;
import com.alchemy.iListDto.IListCareerAspirationPreference;
import com.alchemy.iListDto.IListUserCareerAspiration;

@Repository
public interface CareerAspirationRepository extends JpaRepository<CareerAspirationEntity, Long> {

	CareerAspirationEntity findByUserId(UserEntity userId);

	@Query(value = "select cr.additional_details as ExtraDetails ,  cr.next_career_move  as CareerMove ,cm1.city as City1,cm1.id as CityId1,cm2.city as City2,cm2.id as CityId2,cm3.city as City3,cm3.id as CityId3\r\n"
			+ ", fu.original_name as FileUrl , u.id as UserId\r\n"
			+ ",u.email as Email,u.user_name as User ,gr.name as Role,gr.id as RoleId , p.experience as Experience, gfun.name as Function,gfun.id as FuncitonId, gd.name as Department, gd.id as DepartmentId \r\n"
			+ "from career_aspiration cr \r\n" + "left join city_master cm1 on cm1.id=cr.city1\r\n"
			+ "left join city_master cm2 on cm2.id=cr.city2\r\n" + "left join city_master cm3 on cm3.id=cr.city3\r\n"
			+ "join preferences p on p.career_aspiration_id=cr.id\r\n"
			+ "join gpl_department gd on gd.id= p.department_id\r\n"
			+ "join gpl_function gfun on gfun.id=p.function_id\r\n"
			+ "join gpl_role gr on gr.id=p.role_id left join users u on u.id=cr.user_id\r\n"
			+ "join file_upload fu on fu.id = cr.file_id where u.is_active = true\r\n"
			+ "and (:search=' ' OR u.user_name ILIKE %:search% OR u.email ILIKE %:search%) and u.email is not null \r\n"
			+ "ORDER by cr.updated_at desc", nativeQuery = true)
	List<IListCareerAspirationPreference> getAllAspirationPreference(@Param("search") String search, Pageable pageable,
			Class<IListCareerAspirationPreference> class1);

	@Query(value = "select cr.additional_details as ExtraDetails ,   cr.next_career_move  as CareerMove, fu.original_name as FileUrl ,cm1.city as City1,cm1.id as CityId1,cm2.city as City2,cm2.id as CityId2,cm3.city as City3,cm3.id as CityId3\r\n"
			+ ",u.email as Email,u.user_name as User ,gr.id as RoleId ,gr.name as Role, p.experience as Experience, gfun.name as Function, gfun.id as FuncitonId, gd.name as Department, gd.id as DepartmentId from career_aspiration cr  \r\n"
			+ "join preferences p on p.career_aspiration_id=cr.id\r\n"
			+ "left join city_master cm1 on cm1.id=cr.city1\r\n" + "left join city_master cm2 on cm2.id=cr.city2\r\n"
			+ "left join city_master cm3 on cm3.id=cr.city3\r\n"
			+ "left join gpl_department gd on gd.id= p.department_id \r\n"
			+ "left join gpl_function gfun on gfun.id=p.function_id\r\n"
			+ "left join gpl_role gr on gr.id=p.role_id left join users u on u.id=cr.user_id\r\n"
			+ "left join file_upload fu on fu.id = cr.file_id where u.is_active = true and p.is_active=true and cr.created_by = :userId", nativeQuery = true)
	List<IListCareerAspirationPreference> getAspirationByUserId(@Param("userId") Long userId,
			Class<IListCareerAspirationPreference> class1);

	@Query(value = "SELECT u.employee_id AS Edp, u.user_name AS Name, f.name AS FunctionName, f.id AS FunctionId, u.email AS Email, "
			+ "dept.name AS DepartmentName, dept.id AS DepartmentId, cr.updated_at AS LastUpdated "
			+ "FROM career_aspiration cr " + "right join users u ON u.id = cr.user_id "
			+ "LEFT JOIN gpl_function f ON f.id = u.function_id "
			+ "LEFT JOIN gpl_department dept ON dept.id = u.department_id " + "WHERE u.is_active = true "
			+ "AND (:search = '' OR u.user_name ILIKE CONCAT('%', :search, '%') OR u.email ILIKE CONCAT('%', :search, '%') OR u.employee_id ILIKE CONCAT('%', :search, '%')) "
			+ "AND (:function = '' OR  f.id IN (SELECT unnest(cast(string_to_array(:function,',')as bigint[]))))"
			+ "AND (:department = '' OR  dept.id IN (SELECT unnest(cast(string_to_array(:department,',')as bigint[]))))"
			+ "AND u.email IS NOT NULL Order by u.user_name Asc", nativeQuery = true)
	Page<IListUserCareerAspiration> getCareerAspiration(@Param("search") String search,
			@Param("function") String function, @Param("department") String department, Pageable pageable,
			Class<IListUserCareerAspiration> class1);

	@Query(value = "SELECT u.employee_id AS Edp, u.user_name AS Name, f.name AS FunctionName, f.id AS FunctionId, u.email AS Email, "
			+ "dept.name AS DepartmentName, dept.id AS DepartmentId, cr.updated_at AS LastUpdated "
			+ "FROM career_aspiration cr " + "LEFT JOIN users u ON u.id = cr.user_id "
			+ "LEFT JOIN gpl_function f ON f.id = u.function_id "
			+ "LEFT JOIN gpl_department dept ON dept.id = u.department_id " + "WHERE u.is_active = true "
			+ "AND u.email IS NOT NULL "
			+ "AND (:search = '' OR u.user_name ILIKE CONCAT('%', :search, '%') OR u.email ILIKE CONCAT('%', :search, '%') OR u.employee_id ILIKE CONCAT('%', :search, '%')) "
			+ "AND (:function = '' OR  f.id IN (SELECT unnest(cast(string_to_array(:function,',')as bigint[]))))"
			+ "AND (:department = '' OR  dept.id IN (SELECT unnest(cast(string_to_array(:department,',')as bigint[]))))"
			+ "ORDER BY cr.updated_at DESC", nativeQuery = true)
	Page<IListUserCareerAspiration> getOnlyCareerAspiration(@Param("search") String search,
			@Param("function") String function, @Param("department") String department, Pageable pageable,
			Class<IListUserCareerAspiration> class1);

	@Query(value = "SELECT u.employee_id AS Edp, u.user_name AS Name, f.name AS FunctionName, f.id AS FunctionId, u.email AS Email, \r\n"
			+ "			dept.name AS DepartmentName, dept.id AS DepartmentId, cr.updated_at AS LastUpdated \r\n"
			+ "			FROM users u \r\n" + "			left join career_aspiration cr on u.id=cr.user_id\r\n"
			+ "			left join gpl_function f on f.id=u.function_id\r\n"
			+ "			left join gpl_department dept on dept.id =u.department_id\r\n"
			+ "			where u.is_active=true and cr.updated_at is NULL\r\n"
			+ "			AND (:search = '' OR u.user_name ILIKE CONCAT('%', :search, '%') OR u.email ILIKE CONCAT('%', :search, '%') OR u.employee_id ILIKE CONCAT('%', :search, '%')) \r\n"
			+ "			AND (:function = '' OR  f.id IN (SELECT unnest(cast(string_to_array(:function,',')as bigint[]))))\r\n"
			+ "			AND (:department = '' OR  dept.id IN (SELECT unnest(cast(string_to_array(:department,',')as bigint[]))))\r\n"
			+ "			", nativeQuery = true)
	Page<IListUserCareerAspiration> getCareerAspirationBlank(@Param("search") String search,
			@Param("function") String function, @Param("department") String department, Pageable pageable,
			Class<IListUserCareerAspiration> class1);

	@Query(value = "select cr.additional_details as ExtraDetails , cr.grade_enhancement as Grade , cr.next_career_move  as CareerMove  \r\n"
			+ "			, fu.original_name as FileUrl , u.id as UserId\r\n"
			+ "			,u.email as Email,u.user_name as User ,gr.name as Role, p.experience as Experience, gfun.name as Function, gd.name as Department from career_aspiration cr  \r\n"
			+ "			join preferences p on p.career_aspiration_id=cr.id\r\n"
			+ "			 join gpl_department gd on gd.id= p.department_id \r\n"
			+ "		 join gpl_function gfun on gfun.id=p.function_id\r\n"
			+ "			 join gpl_role gr on gr.id=p.role_id left join users u on u.id=cr.user_id\r\n"
			+ "			 join file_upload fu on fu.id = cr.file_id where u.is_active = true  and u.email is not null ORDER by cr.updated_at desc", nativeQuery = true)
	List<IListCareerAspirationPreference> findAll(Class<IListCareerAspirationPreference> class1);

	@Query(value = "SELECT u.id AS UserId , u.employee_id AS Edp, u.user_name AS Name, u.email AS Email,\r\n"
			+ "			gpl_func.name as FunctionName, gpl_dept.name as DepartmentName,\r\n"
			+ "			career.DepartmentId2 as DepartmentId1, career.DepartmentId1 as DepartmentId2, career.FunctionId2 as FunctionId1, career.FunctionId1 as FunctionId2,\r\n"
			+ "			career.RoleId2 as RoleId1, career.RoleId1 as RoleId2,career.City1 as City1,career.City2 as City2,career.City3 as City3,\r\n"
			+ "			career.Role2 as Role1, career.Role1 as Role2, career.DepartmentName2 as DepartmentName1, career.DepartmentName1 as DepartmentName2,\r\n"
			+ "			career.FunctionName2 as FunctionName1, career.FunctionName1 as FunctionName2, career.next_career_move as NextCareerMove,   career.original_name AS CareerFileURL, career.additional_details As AdditionalDetails, career.Experience2 as Experience1, career.Experience1 as Experience2, career.updated_at as LastUpdated\r\n"
			+ "			FROM users u LEFT JOIN  (\r\n"
			+ "			    SELECT cr.id,cr.user_id, cr.next_career_move,  fu.original_name, cr.additional_details,\r\n"
			+ "				dept1.id as DepartmentId1, dept2.id as DepartmentId2, func1.id as FunctionId1, func2.id as FunctionId2,\r\n"
			+ "				role1.id as RoleId1, role2.id as RoleId2,cm1.city as City1,cm2.city as City2,cm3.city as City3,\r\n"
			+ "			    dept1.name AS DepartmentName1, dept2.name AS DepartmentName2,\r\n"
			+ "			    func1.name AS FunctionName1, func2.name AS FunctionName2,\r\n"
			+ "			    role1.name AS Role1, role2.name AS Role2, p1.experience AS Experience1, p2.experience AS Experience2, cr.updated_at\r\n"
			+ "			    FROM career_aspiration cr       	JOIN preferences p1 ON cr.id=p1.career_aspiration_id\r\n"
			+ "			    left join city_master cm1 on cm1.id=cr.city1\r\n"
			+ "				left join city_master cm2 on cm2.id=cr.city2\r\n"
			+ "				left join city_master cm3 on cm3.id=cr.city3\r\n"
			+ "				LEFT JOIN preferences p2 ON cr.id=p2.career_aspiration_id\r\n"
			+ "			    LEFT JOIN gpl_department dept1 ON dept1.id = p1.department_id\r\n"
			+ "			    LEFT JOIN gpl_department dept2 ON dept2.id = p2.department_id\r\n"
			+ "			    LEFT JOIN gpl_function func1 ON func1.id = p1.function_id\r\n"
			+ "			    LEFT JOIN gpl_function func2 ON func2.id = p2.function_id\r\n"
			+ "			    LEFT JOIN gpl_role role1 ON role1.id = p1.role_id\r\n"
			+ "			    LEFT JOIN gpl_role role2 ON role2.id = p2.role_id    \r\n"
			+ "				LEFT JOIN file_upload fu ON fu.id = cr.file_id  	 	WHERE cr.is_active = true \r\n"
			+ "			) AS career ON u.id = career.user_id \r\n"
			+ "			LEFT JOIN gpl_function gpl_func on gpl_func.id = u.function_id\r\n"
			+ "			LEFT JOIN gpl_department gpl_dept on gpl_dept.id = u.department_id WHERE u.is_active = true \r\n"
			+ "			AND (:search = ' ' OR u.user_name ILIKE '%' || REPLACE(REPLACE(REPLACE(REPLACE(:search, '%', '\\\\%'), '_', '\\\\_'), '[', '\\\\['), ']', '\\\\]') || '%' \r\n"
			+ "	     	OR u.employee_id ILIKE '%' || REPLACE(REPLACE(REPLACE(REPLACE(:search, '%', '\\\\%'), '_', '\\\\_'), '[', '\\\\['), ']', '\\\\]') || '%' \r\n"
			+ "			OR u.email ILIKE '%' || REPLACE(REPLACE(REPLACE(REPLACE(:search, '%', '\\\\%'), '_', '\\\\_'), '[', '\\\\['), ']', '\\\\]') || '%')\r\n"
			+ "			AND (:function = '' OR u.function_id IN (SELECT unnest(cast(string_to_array(:function,',') AS bigint[]))))\r\n"
			+ "			AND (:exportIds = '' OR u.id IN (SELECT unnest(cast(string_to_array(:exportIds,',') AS bigint[]))))\r\n"
			+ "			AND (:department = '' OR u.department_id IN (SELECT unnest(cast(string_to_array(:department,',') AS bigint[]))))\r\n"
			+ "			AND ((:key = true AND (' ' = '' OR career.next_career_move IS NULL)) OR (:key = false AND (' ' = '' OR career.next_career_move IS NOT NULL)) OR (:key IS NULL AND ('' = '' OR career.next_career_move IS NULL OR career.next_career_move IS NOT NULL)))\r\n"
			+ "			ORDER BY career.updated_at ASC", nativeQuery = true)
	Page<IListCareerAspiration> getCrAspiration(@Param("search") String search, @Param("key") Boolean key,
			@Param("function") String function, @Param("department") String department,
			@Param("exportIds") String exportIds, Pageable pageable, Class<IListCareerAspiration> class1);

	@Transactional
	@Modifying
	@Query("UPDATE CareerAspirationEntity SET is_active = false,updated_by=:userId WHERE user_id IN :ids")
	void deleteAll(@Param("userId") Long userId, @Param("ids") List<Long> ids);

	@Query(value = "select id from career_aspiration  WHERE user_id IN :ids", nativeQuery = true)
	List<Long> findByUserId(@Param("ids") List<Long> ids);

}
