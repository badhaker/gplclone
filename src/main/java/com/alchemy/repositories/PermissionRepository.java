package com.alchemy.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alchemy.entities.PermissionEntity;
import com.alchemy.iListDto.IListOfPermissionsAndSFDetail;
import com.alchemy.iListDto.IListPermission;
import com.alchemy.iListDto.IListPermissionDto;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {

	PermissionEntity findByActionNameIgnoreCase(String trim);

	@Query(value = "select p.module_id As moduleMasterEntityId,p.method ,p.url,p.id as Id,p.action_name as ActionName,p.description as Description\r\n"
			+ "from permissions p where p.action_name ilike %:search% and p.is_active=true order by p.id desc", nativeQuery = true)
	Page<IListPermissionDto> findByActionName(@Param("search") String search, Pageable pagingPageable,
			Class<IListPermissionDto> class1);

	List<IListPermissionDto> findByOrderByActionNameAsc(Class<IListPermissionDto> class1);

	ArrayList<PermissionEntity> findByIdIn(ArrayList<Long> permissions);

	@Query(value = "select * from permissions where action_name=:actionName", nativeQuery = true)
	PermissionEntity findByActionName(@Param("actionName") String actionName);

	@Query(value = "select p.id as PermissionId ,p.action_name as PermissionName, p.description as Description ,m.id as ModuleId ,m.module_name as ModuleName, p.url as Url, p.method as Method from module_master m\r\n"
			+ "join permissions p on p.module_id=m.id where  p.is_active=true And m.is_active=true", nativeQuery = true)
	List<IListPermission> findByModuleWisePermission(Class<IListPermission> class1);

	List<PermissionEntity> findByIdIn(Set<Long> newPermissionIds);

	@Query(value = "select distinct p.action_name from users u \r\n" + "join user_role ur on u.id=ur.user_id\r\n"
			+ "join role_permission rp on ur.role_id=rp.role_id\r\n" + "join permissions p on rp.permission_id=p.id\r\n"
			+ "where u.id=:userId AND ur.is_active=true AND rp.is_active=true AND p.is_active=true", nativeQuery = true)
	List<String> getAllUserPermissions(@Param("userId") Long userId);

	List<IListPermissionDto> findByOrderByIdDesc(Class<IListPermissionDto> class1);

	@Transactional
	@Modifying
	@Query("update PermissionEntity set isActive = false where id in :ids")
	void deleteAllByIdIn(List<Long> ids);

	@Query(value = "select   u.id AS UserId,u.user_name as UserName, l.id as LevelId,l.level_name AS LevelName,gf.id as FunctionId,gf.name AS FunctionName, \r\n"
			+ "u.employee_grade as EmployeeGrade,u.employee_id as EmployeeEdp,dept.id as DepartmentId, dept.name as Department,  u.position_title as PositionTitle,u.region as Region,u.zone as Zone from users u \r\n"
			+ "join level l on l.id=u.level_id join gpl_function gf on gf.id=u.function_id \r\n"
			+ "join gpl_department dept on dept.id=u.department_id \r\n"
			+ "where u.id=:userId AND u.is_active=true and gf.is_active=true and l.is_active=true", nativeQuery = true)
	IListOfPermissionsAndSFDetail getAllUserPermissionsAndSFDetail(@Param("userId") Long userId,
			Class<IListOfPermissionsAndSFDetail> class1);
}
