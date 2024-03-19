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

import com.alchemy.entities.DesignationEntity;
import com.alchemy.iListDto.IListDesignation;

@Repository
public interface DesignationRepository extends JpaRepository<DesignationEntity, Long> {

	DesignationEntity findByNameIgnoreCase(String name);

	Page<IListDesignation> findByOrderByIdDesc(Pageable pageable, Class<IListDesignation> class1);

	Page<IListDesignation> findByNameContainingIgnoreCase(String search, Pageable pageable,
			Class<IListDesignation> class1);

	@Query(value = "select d.id as Id, d.name as Name ,d.description as Description \r\n"
			+ "from designation d where d.is_active=true \r\n"
			+ "AND (:search = ' ' OR d.name ILIKE '%' || REPLACE(REPLACE(REPLACE(REPLACE(:search, '%', '\\%'), '_', '\\_'), '[', '\\['), ']', '\\]') || '%' \r\n"
			+ "OR d.description ILIKE '%' || REPLACE(REPLACE(REPLACE(REPLACE(:search, '%', '\\%'), '_', '\\_'), '[', '\\['), ']', '\\]') || '%')\r\n"
			+ "order by d.name ASC \r\n" + "", nativeQuery = true)
	Page<IListDesignation> findByName(@Param("search") String search, Pageable pageable,
			Class<IListDesignation> class1);

	List<IListDesignation> findByOrderByIdDesc(Class<IListDesignation> class1);

	@Query(value = "select d.id as Id, d.name as Name ,d.description as Description \r\n"
			+ "from designation d where d.is_active=true and (:search = '' OR d.name ILIKE %:search% OR d.description ILIKE %:search%)  \r\n"
			+ "order by d.name ASC \r\n" + "", nativeQuery = true)
	List<IListDesignation> findByNameInList(@Param("search") String search, Class<IListDesignation> class1);

	DesignationEntity findByName(String cell);

	@Transactional
	@Modifying
	@Query("UPDATE DesignationEntity b SET b.isActive = false,updated_by=:userId WHERE b.id IN :ids")
	void deleteAllByIdIn(@Param("userId") Long userId, @Param("ids") List<Long> ids);

}
