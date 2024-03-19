package com.alchemy.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alchemy.entities.CareerTracksEntity;
import com.alchemy.iListDto.IListCareerTracks;

public interface CareerTracksRepository extends JpaRepository<CareerTracksEntity, Long> {

	@Query(value = "select ct.id as Id ,ct.name as Name ,fu.id as FileId ,fu.original_name as Original,tfu.id AS ThumbnailFileId, tfu.original_name AS ThumbnailFileName from career_tracks ct\r\n"
			+ "left join file_upload fu on fu.id=ct.file_id\r\n"
			+ "LEFT JOIN file_upload tfu ON tfu.id = ct.thumbnail_id  where ct.is_active=true\r\n"
			+ "AND (:search = ' ' OR ct.name ILIKE '%' || REPLACE(REPLACE(REPLACE(REPLACE(:search, '%', '\\%'), '_', '\\_'), '[', '\\['), ']', '\\]') || '%') \r\n"
			+ "order by ct.id desc\r\n", nativeQuery = true)
	Page<IListCareerTracks> findByNameContaining(@Param("search") String search, Pageable pageable,
			Class<IListCareerTracks> class1);

	CareerTracksEntity findByNameIgnoreCase(String name);

	CareerTracksEntity findByNameIgnoreCaseAndIsActiveTrue(String name);

	@Transactional
	@Modifying
	@Query("update CareerTracksEntity set isActive = false where id in :ids")
	void deleteAllByIdIn(@Param("ids") List<Long> ids);

}
