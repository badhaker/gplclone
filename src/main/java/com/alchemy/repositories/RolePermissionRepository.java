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
import com.alchemy.entities.RolePermissionEntity;
import com.alchemy.iListDto.IListRolePermissions;
import com.alchemy.iListDto.IUserPermissionDto;


@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermissionEntity, Long> {

	@Query(value = "select rp.role_id as RoleId, r.role_name as RoleName , rp.permission_id as PermissionId , p.action_name as ActionName from role_permission rp\r\n"
			+ "join roles r on r.id=rp.role_id \r\n" + "join permissions p on p.id=rp.permission_id \r\n"
			+ "where rp.is_active =true\r\n"
			+ "AND(:search = '' OR p.action_name ILIKE %:search% OR r.role_name ILIKE %:search%)", nativeQuery = true)
	Page<IListRolePermissions> findByRolePermissions(@Param("search") String search, Pageable pageable,
			Class<IListRolePermissions> class1);

	@Query(value = "SELECT * FROM role_permission rp WHERE rp.role_id  IN :roleIds  AND rp.is_active = true", nativeQuery = true)
	ArrayList<RolePermissionEntity> findPermissionByRole(@Param("roleIds") ArrayList<Long> roleIds);

	@Query(value = "select rp.role_id as RoleId ,rp.permission_id as PermissionId, p.action_name as ActionName,\r\n"
			+ "r.role_name as RoleName from role_permission rp \r\n"
			+ "inner join permissions p on rp.permission_id=p.id inner join roles r on r.id=rp.role_id where role_id=? order by p.id Desc", nativeQuery = true)
	List<IListRolePermissions> findPermissionIdByRoleId(Long roleId, Class<IListRolePermissions> class1);

	@Query(value = "select permission_id from role_permission where role_id=:id", nativeQuery = true)
	List<Long> getPermissionIdUsingRoleId(Long id);

	void deleteByRoleId(RoleEntity roleEntity);

	@Query(value = "select * from role_permission where permission_id=:id", nativeQuery = true)
	List<RolePermissionEntity> findByPermissionId(@Param("id") Long id);

	@Query(value = "select p.action_name from permissions p join role_permission rp on rp.permission_id=p.id\r\n"
			+ "		join roles r on r.id=rp.role_id  join user_role ur on ur.role_id=r.id join users u on ur.user_id=u.id  \r\n"
			+ "			where u.id=:userId and ur.is_active=true and rp.is_active=true", nativeQuery = true)
	ArrayList<String> getPermissionOfUser(@Param("userId") Long userId);
	
	
	@Query(value = "\r\n"
			+ "select u.id as Id, u.user_name as Name, r.role_name as RoleName, p.action_name as permissionName from users u \r\n"
			+ "join user_role ur on u.id= ur.user_id\r\n"
			+ "join roles r on r.id= ur.role_id\r\n"
			+ "join role_permission rp on r.id=rp.role_id\r\n"
			+ "join permissions p on p.id= rp.permission_id where u.id=:userId and p.is_active='true' ", nativeQuery = true)
	List<IUserPermissionDto> getPermissionById(@Param("userId") long userId, Class<IUserPermissionDto> class1);
	
}
