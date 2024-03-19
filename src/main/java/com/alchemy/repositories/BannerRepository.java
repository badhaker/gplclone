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

import com.alchemy.entities.BannerEntity;
import com.alchemy.iListDto.IListBanner;

@Repository
public interface BannerRepository extends JpaRepository<BannerEntity, Long> {

	@Query(value = "select b.id as Id,b.is_visible as IsVisible, b.show_content as ShowContent,b.url as VideoUrl,\r\n"
			+ "b.description as Description,b.file_id as FileId,b.banner_name as Name,fu.original_name as FileUrl,\r\n"
			+ "b.display_order as DisplayOrder from banner b \r\n" + "left join file_upload fu on fu.id=b.file_id\r\n"
			+ "where b.is_active =true   order by b.display_order asc", nativeQuery = true)
	Page<IListBanner> findByOrderByIdDesc(Pageable pageable, Class<IListBanner> class1);

	@Query(value = "select b.id as Id,b.is_visible as IsVisible, b.show_content as ShowContent,b.url as VideoUrl,\r\n"
			+ "b.description as Description,b.file_id as FileId,b.banner_name as Name,fu.original_name as FileUrl,\r\n"
			+ "b.display_order as DisplayOrder from banner b \r\n" + "left join file_upload fu on fu.id=b.file_id\r\n"
			+ "where b.is_active =true AND (:search = ' '\r\n"
			+ "	OR b.banner_name ILIKE '%' || REPLACE(REPLACE(REPLACE(REPLACE(:search, '%', '\\%'), '_', '\\_'), '[', '\\['), ']', '\\]') || '%' \r\n"
			+ "	OR b.description ILIKE '%' || REPLACE(REPLACE(REPLACE(REPLACE(:search, '%', '\\%'), '_', '\\_'), '[', '\\['), ']', '\\]') || '%')\r\n"
			+ " order by b.display_order asc", nativeQuery = true)
	Page<IListBanner> findAllBanners(@Param("search") String search, Pageable pageable, Class<IListBanner> class1);
	
	BannerEntity findByNameIgnoreCaseAndIsActiveTrue(String name);

	@Query(value = "select b.id as Id,b.is_visible as IsVisible, b.show_content as ShowContent,b.url as VideoUrl,\r\n"
			+ "b.description as Description,b.file_id as FileId,b.banner_name as Name,fu.original_name as FileUrl,\r\n"
			+ "b.display_order as DisplayOrder from banner b \r\n" + "left join file_upload fu on fu.id=b.file_id\r\n"
			+ "where b.is_active =true order by b.display_order asc", nativeQuery = true)
	List<IListBanner> findAllByOrderByDisplayOrderAsc(Class<IListBanner> class1);

	@Transactional
	@Modifying
	@Query("UPDATE BannerEntity b SET b.isActive = false WHERE b.id IN :bannerIds")
	void deleteBannersById(@Param("bannerIds") List<Long> bannerIds);

	@Transactional
	@Modifying
	@Query("UPDATE BannerEntity b SET b.isVisible = :isVisble WHERE b.id IN :bannerIds")
	void isVisibleBannerByIds(@Param("isVisble") Boolean isVisible, @Param("bannerIds") List<Long> bannerIds);

	Boolean existsByDisplayOrder(Integer num);

	Boolean existsByDisplayOrderAndId(Integer num, Long id);

//	@Query(value = "select * from banner b   where b.id>:id and b.is_active=true order by b.display_order asc", nativeQuery = true)
//	List<BannerEntity> findByDisplayOrderGreaterThanOrderByDisplayOrderAsc(@Param("id") Long id);

	@Query(value = "select count(*) from banner where is_visible=true and is_active=true", nativeQuery = true)
	Long countOfIsVisibleInBanner();

	List<BannerEntity> findAllByOrderByDisplayOrderAsc();



}
