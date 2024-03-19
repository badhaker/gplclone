package com.alchemy.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.alchemy.entities.GplDepartmentEntity;
import com.alchemy.entities.GplFunctionEntity;
import com.alchemy.iListDto.IListGplDepartmentDto;

import io.lettuce.core.dynamic.annotation.Param;

public interface GplDepartmentRepository extends JpaRepository<GplDepartmentEntity, Long> {

	@Query(value = "SELECT gd.id AS GplDepartmentId, gd.name AS GplDepartmentName, gf.id AS GplFunctionId, gf.name AS GplFunctionName\r\n"
			+ "	FROM gpl_department gd\r\n"
			+ "	JOIN gpl_function gf ON gf.id = gd.gpl_function_id AND gf.is_active = true\r\n"
			+ "	WHERE gd.is_active = true AND (:search = ' ' OR gd.name ILIKE '%' || REPLACE(REPLACE(REPLACE(REPLACE(:search, '%', '\\%'), '_', '\\_'), '[', '\\['), ']', '\\]') || '%' \r\n"
			+ "	OR gf.name ILIKE '%' || REPLACE(REPLACE(REPLACE(REPLACE(:search, '%', '\\%'), '_', '\\_'), '[', '\\['), ']', '\\]') || '%' )\r\n"
			+ "	AND (:functionId = '' OR gf.id IN (select unnest(cast(string_to_array(:functionId, ',') as bigint[]))))\r\n"
			+ "	ORDER BY gd.name asc", nativeQuery = true)
	Page<IListGplDepartmentDto> findByName(@Param("search") String search, Pageable pageable,
			@Param("functionId") String functionId, Class<IListGplDepartmentDto> class1);

//	GplDepartmentEntity findByNameIgnoreCaseAndIsActiveTrue(String name);

	@Transactional
	@Modifying
	@Query("UPDATE GplDepartmentEntity   SET is_Active = false,updated_by=:userId WHERE gpl_function_id IN :idsToBeDeleted")
	void deleteGplDepartmentById(@Param("userId") Long userId, @Param("idsToBeDeleted") List<Long> idsToBeDeleted);

	@Transactional
	@Modifying
	@Query("UPDATE GplDepartmentEntity   SET is_active = false,updated_by=:userId WHERE Id IN :idsToBeDeleted")
	void deleteGplDepartmentByIds(@Param("userId") Long userId, @Param("idsToBeDeleted") List<Long> idsToBeDeleted);

	List<GplDepartmentEntity> findByNameIgnoreCase(String stringCellValue);

	GplDepartmentEntity findByNameIgnoreCaseAndIsActiveTrueAndGplFunctionId(String department,
			GplFunctionEntity savedEntity);

	GplDepartmentEntity findByNameIgnoreCaseAndGplFunctionIdAndIsActiveTrue(String departmentDepartmentName,
			GplFunctionEntity function);

}
