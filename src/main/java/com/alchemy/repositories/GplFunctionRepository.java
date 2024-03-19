package com.alchemy.repositories;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.alchemy.entities.GplFunctionEntity;
import com.alchemy.iListDto.IListGplFunctionDto;

import io.lettuce.core.dynamic.annotation.Param;

public interface GplFunctionRepository extends JpaRepository<GplFunctionEntity, Long> {

	@Query(value = "select gf.id as Id ,gf.name as Name from gpl_function gf where gf.is_active=true AND (:search = ' ' OR gf.name ILIKE '%' || REPLACE(REPLACE(REPLACE(REPLACE(:search, '%', '\\%'), '_', '\\_'), '[', '\\['), ']', '\\]') || '%') order by gf.name   ASC", nativeQuery = true)
	Page<IListGplFunctionDto> findByName(@Param("search") String search, Pageable pageable,
			Class<IListGplFunctionDto> class1);

	ArrayList<GplFunctionEntity> findByIdIn(ArrayList<Long> departmentId);

	GplFunctionEntity findByNameIgnoreCase(String name);

	GplFunctionEntity findByNameIgnoreCaseAndIsActiveTrue(String name);

	@Transactional
	@Modifying
	@Query("UPDATE GplFunctionEntity b SET b.isActive = false,updated_by=:userId WHERE b.id IN :idsToBeDeleted")
	void deleteGplFunctionById(@Param("userId") Long userId, @Param("idsToBeDeleted") List<Long> idsToBeDeleted);

}
