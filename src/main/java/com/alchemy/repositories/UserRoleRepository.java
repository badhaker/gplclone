package com.alchemy.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alchemy.entities.RoleEntity;
import com.alchemy.entities.UserRoleEntity;
import com.alchemy.iListDto.IListRole;
import com.alchemy.iListDto.IListUserRoles;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {

	@Query(value = "select multi.user_id as UserId,ur.user_name as UserName,multi.RoleName from users ur \r\n"
			+ "inner join(select ur.user_id,string_agg(r.role_name,', ' ) as RoleName  from user_role ur\r\n"
			+ "inner  join roles  r on r.id=ur.role_id  and r.is_active=true and ur.is_active=true\r\n"
			+ "inner join users u on u.id=ur.user_id and u.is_active=true\r\n"
			+ "group by ur.user_id) as multi on multi.user_id=ur.id \r\n"
			+ "AND (ur.user_name ILIKE %:search% OR multi.RoleName ILIKE %:search%) order by ur.user_name asc "
			+ " ", nativeQuery = true)
	Page<IListUserRoles> findByUserRoles(@Param("search") String search, Pageable pageable,
			Class<IListUserRoles> class1);

	@Query(value = "SELECT * FROM user_role u WHERE u.user_id=:user_id", nativeQuery = true)
	ArrayList<UserRoleEntity> getRolesOfUser(@Param("user_id") Long userId);

	@Query(value = "select ur.user_id as userId,ur.role_id,r.role_name as roleName from user_role ur inner join roles r on ur.role_id=r.id  where ur.user_id=?", nativeQuery = true)
	List<IListRole> findRoleByUserId(@Param("userId") Long roleId);

	public List<UserRoleEntity> deleteByRoleId(RoleEntity roleId);

	@Query(value = "select * from user_role where role_id=:id", nativeQuery = true)
	List<UserRoleEntity> findByRoleId(Long id);

}
