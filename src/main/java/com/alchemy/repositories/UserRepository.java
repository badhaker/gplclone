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

import com.alchemy.entities.LevelEntity;
import com.alchemy.entities.UserEntity;
import com.alchemy.iListDto.IListCareerAspiration;
import com.alchemy.iListDto.IListUserDto;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	UserEntity findByEmailIgnoreCase(String email);

	UserEntity findByEmail(String email);

	@Query(value = "SELECT  u.career_aspiration  AS CareerAspiration, u.id as Id, u.user_name as Name, \r\n"
			+ "u.email as Email, u.gender as Gender, u.phone_number as PhoneNumber,\r\n"
			+ "multi.roles as RoleName FROM users u \r\n"
			+ "LEFT JOIN (select u.id, string_agg((r.role_name), ', ') AS roles \r\n"
			+ "from users u LEFT JOIN user_role ur on u.id=ur.user_id \r\n"
			+ "LEFT JOIN (select r.id, case when r.is_active=false then '' else r.role_name \r\n"
			+ "end as role_name from roles r) r on r.id=ur.role_id where ur.is_active=true group by u.id) \r\n"
			+ "as multi on multi.id=u.id	where u.is_active= true 	\r\n"
			+ "AND (:search = ' ' OR u.user_name ILIKE '%' || REPLACE(REPLACE(REPLACE(REPLACE(:search, '%', '\\%'), '_', '\\_'), '[', '\\['), ']', '\\]') || '%' \r\n"
			+ "OR u.email ILIKE '%' || REPLACE(REPLACE(REPLACE(REPLACE(:search, '%', '\\%'), '_', '\\_'), '[', '\\['), ']', '\\]') || '%')\r\n"
			+ "order by u.id desc", nativeQuery = true)
	Page<IListUserDto> findByNameIgnoreCase(@Param("search") String search, Pageable pagingPageable,
			Class<IListUserDto> class1);

//	@Query(value = "\r\n"
//			+ "    SELECT  u.career_aspiration  AS CareerAspiration, u.id as Id, u.user_name as Name, u.email as Email, u.gender as Gender, u.phone_number as PhoneNumber,\r\n"
//			+ "	multi.roles as RoleName FROM users u  	LEFT JOIN (select u.id, string_agg((r.role_name), ', ') AS roles from users u LEFT JOIN user_role ur on u.id=ur.user_id\r\n"
//			+ "		LEFT JOIN (select r.id, case when r.is_active=false then '' else r.role_name end as role_name from roles r) r on r.id=ur.role_id where ur.is_active=true group by u.id) as multi on multi.id=u.id	\r\n"
//			+ "	where (:search=' ' OR u.user_name ILIKE %:search%)", nativeQuery = true)
//	Page<IListUserDto> findByNameIgnoreCase(@Param("search") String search, Pageable pagingPageable,
//			Class<IListUserDto> class1);

	@Query(value = "SELECT u.id as Id ,u.user_name as Name, u.email as Email, u.gender as Gender, u.phone_number as PhoneNumber,multi.roles as RoleName,u.career_aspiration  AS CareerAspiration FROM users u\r\n"
			+ " left join (select u.id, string_agg((r.role_name), ', ') AS roles from users u join user_role ur on u.id=ur.user_id\r\n"
			+ "join (select r.id, case when r.is_active=false then '' else r.role_name end as role_name from roles r) r on r.id=ur.role_id where ur.is_active=true group by u.id) as multi on \r\n"
			+ "multi.id=u.id where u.is_active = true and u.email is not null order by u.updated_at desc", nativeQuery = true)
	List<IListUserDto> getUsers(HttpServletResponse response, Class<IListUserDto> class1);

	UserEntity findByNameIgnoreCase(String name);

	@Query(value = "select u.id as Id,u.user_name as Name, u.email as Email ,u.career_aspiration  AS CareerAspiration from users u \r\n"
			+ "where u.is_active = true and (:search=' ' OR u.user_name ILIKE %:search% OR u.email ILIKE %:search%) order by u.updated_at desc", nativeQuery = true)
	Page<IListCareerAspiration> findByName(@Param("search") String search, Pageable pagingPageable,
			Class<IListCareerAspiration> class1);

	List<UserEntity> findByLevelId(LevelEntity levelEntity);

	@Query(value = "SELECT u.id as Id ,u.user_name as Name, u.email as Email, u.gender as Gender, u.phone_number as PhoneNumber,multi.roles as RoleName,u.career_aspiration  AS CareerAspiration FROM users u\r\n"
			+ " left join (select u.id, string_agg((r.role_name), ', ') AS roles from users u join user_role ur on u.id=ur.user_id\r\n"
			+ "join (select r.id, case when r.is_active=false then '' else r.role_name end as role_name from roles r) r on r.id=ur.role_id where ur.is_active=true group by u.id) as multi on \r\n"
			+ "multi.id=u.id where u.is_active = true and u.email is not null order by u.updated_at desc", nativeQuery = true)
	List<IListUserDto> findAllUsers(Class<IListUserDto> class1);

	@Transactional
	@Modifying
	@Query("update UserEntity set isActive = false where id in :ids")
	void deleteAllByIdIn(List<Long> ids);

	@Query(value = "select u.email from users u \r\n" + "join user_role ur on ur.user_id=u.id\r\n"
			+ "join roles r on r.id=ur.role_id \r\n" + "join role_permission rp on rp.role_id=r.id\r\n"
			+ "join permissions p on p.id=rp.permission_id\r\n"
			+ "where lower(p.action_name)=lower('isAdmin') and p.is_active=true and rp.is_active=true and u.is_active=true and r.is_active=true and ur.is_active=true", nativeQuery = true)
	List<String> getUserByRoleAdmin();

	ArrayList<UserEntity> findByEmailIn(ArrayList<String> email);

}
