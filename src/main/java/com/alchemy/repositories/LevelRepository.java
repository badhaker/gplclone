package com.alchemy.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alchemy.entities.LevelEntity;
import com.alchemy.iListDto.IListLevel;

@Repository
public interface LevelRepository extends JpaRepository<LevelEntity, Long> {

	Page<IListLevel> findByOrderByIdDesc(Pageable pageable, Class<IListLevel> class1);

	@Query(value = "select l.id as Id ,l.level_name as LevelName ,l.description as Description from level l where l.is_active=true \r\n"
			+ "AND (:search = ' ' OR l.level_name ILIKE '%' || REPLACE(REPLACE(REPLACE(REPLACE(:search, '%', '\\%'), '_', '\\_'), '[', '\\['), ']', '\\]') || '%' \r\n"
			+ "OR l.description ILIKE '%' || REPLACE(REPLACE(REPLACE(REPLACE(:search, '%', '\\%'), '_', '\\_'), '[', '\\['), ']', '\\]') || '%')\r\n"
			+ "order by l.level_name ASC", nativeQuery = true)
	Page<IListLevel> findByLevelNameContainingIgnoreCase(@Param("search") String search, Pageable pageable,
			Class<IListLevel> class1);

	LevelEntity findByLevelNameIgnoreCase(String levelName);

	List<IListLevel> findByOrderByIdDesc(Class<IListLevel> class1);

}
