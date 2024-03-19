package com.alchemy.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.alchemy.entities.GplRoleEntity;
import com.alchemy.iListDto.IListGplRoleById;
import com.alchemy.iListDto.IListGplRoleDto;

import io.lettuce.core.dynamic.annotation.Param;

public interface GplRoleRepository extends JpaRepository<GplRoleEntity, Long> {

	@Query(value = "select gr.id as GplRoleId, gr.name as GplRoleName ,gd.id as GplDepartmentId ,gd.name as GplDepartmentName ,gf.id as FunctionId ,gf.name as FunctionName from gpl_role gr\r\n"
			+ "	join gpl_department gd on gd.id=gr.gpl_department_id  and  gd.is_active=true\r\n"
			+ "	join gpl_function gf on gf.id=gd.gpl_function_id and gf.is_active=true\r\n"
			+ "	where gr.is_active=true \r\n"
			+ "AND (:search = ' ' OR gr.name ILIKE '%' || REPLACE(REPLACE(REPLACE(REPLACE(:search, '%', '\\%'), '_', '\\_'), '[', '\\['), ']', '\\]') || '%' \r\n"
			+ "		OR gf.name ILIKE '%' || REPLACE(REPLACE(REPLACE(REPLACE(:search, '%', '\\%'), '_', '\\_'), '[', '\\['), ']', '\\]') || '%' \r\n"
			+ "		OR gd.name ILIKE '%' || REPLACE(REPLACE(REPLACE(REPLACE(:search, '%', '\\%'), '_', '\\_'), '[', '\\['), ']', '\\]') || '%')\r\n"
			+ "order by gr.id desc", nativeQuery = true)
	Page<IListGplRoleDto> findByName(@Param("search") String search, Pageable pageable, Class<IListGplRoleDto> class1);

	GplRoleEntity findByNameIgnoreCaseAndIsActiveTrue(String name);

	@Transactional
	@Modifying
	@Query("UPDATE GplRoleEntity   SET is_active = false,updated_by=:userId WHERE Id IN :idsToBeDeleted")
	void deleteGplRoleByIds(@Param("userId") Long userId, @Param("idsToBeDeleted") List<Long> idsToBeDeleted);

	@Transactional
	@Modifying
	@Query("UPDATE GplRoleEntity   SET is_Active = false,updated_by=:userId WHERE gpl_department_id IN :idsToBeDeleted")
	void deleteGplDepartmentByIds(@Param("userId") Long userId, @Param("idsToBeDeleted") List<Long> idsToBeDeleted);
//
//	Page<IListGplRoleDto> findByNameContainingIgnoreCaseOrderByIdDesc(String search, Pageable pageable,
//			Class<IListGplRoleDto> class1);

	@Query(value = "select gr.id as GplRoleId, gr.name as GplRoleName ,gd.id as GplDepartmentId ,gd.name as GplDepartmentName ,gf.id as FunctionId ,gf.name as FunctionName from gpl_role gr\r\n"
			+ "			join gpl_department gd on gd.id=gr.gpl_department_id  and  gd.is_active=true\r\n"
			+ "			join gpl_function gf on gf.id=gd.gpl_function_id and gf.is_active=true\r\n"
			+ "			where gr.is_active=true and gd.id=:id\r\n" + "", nativeQuery = true)
	List<IListGplRoleById> getByDepartmentId(@Param("id") Long id);

}
