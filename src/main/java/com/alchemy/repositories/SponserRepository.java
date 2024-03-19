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
import com.alchemy.entities.SponsorMaster;
import com.alchemy.iListDto.IListSponsorDto;

@Repository
public interface SponserRepository extends JpaRepository<SponsorMaster, Long> {

	Page<IListSponsorDto> findByOrderByIdDesc(Pageable pageable, Class<IListSponsorDto> class1);

	Page<IListSponsorDto> findByNameContainingIgnoreCase(String search, Pageable pageable,
			Class<IListSponsorDto> class1);

	@Query(value = "select s.id, s.sponsor_name as SponsorName , s.description as Description,s.profile as Profile,"
			+ "s.file_id as FileId , fu.original_name as FileUrl, \r\n"
			+ "d.name as DesignationName,d.id as DesignationId, u.id as UserId, u.user_name as UserName\r\n"
			+ "from sponsors s \r\n" + "left join users u on s.user_id=u.id and u.is_active=true\r\n"
			+ "left join designation d on d.id=s.designation_id "
			+ "left join file_upload fu on fu.id=s.file_id\r\n"
			+ "and d.is_active=true where s.is_active=true \r\n"
			+ "AND (:search = ' ' \r\n"
			+ "OR s.sponsor_name ILIKE '%' || REPLACE(REPLACE(REPLACE(REPLACE(:search, '%', '\\%'), '_', '\\_'), '[', '\\['), ']', '\\]') || '%' \r\n"
			+ "OR d.name ILIKE '%' || REPLACE(REPLACE(REPLACE(REPLACE(:search, '%', '\\%'), '_', '\\_'), '[', '\\['), ']', '\\]') || '%')\r\n"
			+ "order by s.id desc", nativeQuery = true)
	Page<IListSponsorDto> findAllList(@Param("search") String search, Pageable pageable, Class<IListSponsorDto> class1);
	
	@Query(value = "select s.id, s.sponsor_name as SponsorName , s.description as Description,s.profile as Profile,  \r\n"
			+ "d.name as DesignationName,d.id as DesignationId, u.id as UserId, u.user_name as UserName"
			+ ", s.file_id as FileId, fu.original_name as FileUrl\r\n"
			+ "from sponsors s \r\n" +"left join users u on s.user_id=u.id and u.is_active=true  \r\n"
			+" left join file_upload fu on fu.id=s.file_id "
			+ "left join designation d on d.id=s.designation_id and d.is_active=true\r\n"
			+ "where s.is_active=true order by s.id desc", nativeQuery = true)
	List<IListSponsorDto> findByOrderByIdDesc(Class<IListSponsorDto> class1);

	@Query(value = "select s.id, s.sponsor_name as SponsorName , s.description as Description,s.profile as Profile,"
			+ " s.file_id as FileId, fu.original_name as FileUrl ,\r\n"
			+ "d.name as DesignationName,d.id as DesignationId, u.id as UserId, u.user_name as UserName\r\n"
			+ "from sponsors s \r\n" + "left join users u on s.user_id=u.id and u.is_active=true  "
			+ "left join file_upload fu on fu.id=s.file_id\r\n"
			+ "left join designation d on d.id=s.designation_id and d.is_active=true \r\n"
			+ "where s.is_active=true  and s.id= :id", nativeQuery = true)
	IListSponsorDto findBySponsorId(@Param("id") Long id, Class<IListSponsorDto> class1);

	SponsorMaster findByNameContainingIgnoreCase(String search);

	List<SponsorMaster> findByIdIn(List<Long> long1);

	List<SponsorMaster> findBydesignationIdId(Long designationId);

	SponsorMaster findByNameIgnoreCase(String sponsor1Names);

	SponsorMaster findByNameContainingIgnoreCaseAndDesignationId(String sponsor1Names, Long cell);

	SponsorMaster findByNameAndDesignationId(String sponsor1Names, DesignationEntity entity);

	SponsorMaster findByName(String sponsor1Names);

	@Transactional
	@Modifying
	@Query("update SponsorMaster set isActive = false where id in :ids")
	void deleteAllByIdIn(List<Long> ids);

}
