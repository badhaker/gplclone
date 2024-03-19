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

import com.alchemy.entities.TestimonialEntity;
import com.alchemy.iListDto.IListTestimonial;

@Repository
public interface TestimonialRepository extends JpaRepository<TestimonialEntity, Long> {

	@Query(value = "select t.id as Id ,t.testimonial as Testimonial,t.user_name as UserName ,t.is_visible as IsVisible ,t.description as Description,\r\n"
			+ "desig.id as DesignationId, "
			+ "t.file_id as FileId ,fu.original_name as FileUrl,desig.name as DesignationName,"
			+ "fn.id as FunctionId, " + "fn.name as FunctionName from testimonial t\r\n"
			+ "left join designation desig on t.designation_id=desig.id and desig.is_active=true "
			+ "left join file_upload fu on t.file_id=fu.id \r\n"
			+ "left join gpl_function fn on t.function_id=fn.id and fn.is_active=true where t.is_active=true order by t.id DESC", nativeQuery = true)
	Page<IListTestimonial> findByOrderByIdDesc(Pageable pageable, Class<IListTestimonial> class1);

	@Query(value = "select t.id as Id ,t.testimonial as Testimonial,t.user_name as UserName ,t.is_visible as IsVisible ,t.description as Description,\r\n"
			+ "desig.id as DesignationId,"
			+ "t.file_id as FileId , fu.original_name as FileUrl , desig.name as DesignationName,fn.id as FunctionId, fn.name as FunctionName from testimonial t\r\n"
			+ "left join designation desig on t.designation_id=desig.id and desig.is_active=true\r\n"
			+ "left join gpl_function fn on t.function_id=fn.id  and fn.is_active=true "
			+ "left join file_upload fu on fu.id=t.file_id \r\n" + "where t.is_active=true \r\n"
			+ "AND (:search = ' ' OR t.user_name ILIKE '%' || REPLACE(REPLACE(REPLACE(REPLACE(:search, '%', '\\%'), '_', '\\_'), '[', '\\['), ']', '\\]') || '%' \r\n"
			+ "		OR t.testimonial ILIKE '%' || REPLACE(REPLACE(REPLACE(REPLACE(:search, '%', '\\%'), '_', '\\_'), '[', '\\['), ']', '\\]') || '%' \r\n"
			+ "		OR desig.name ILIKE '%' || REPLACE(REPLACE(REPLACE(REPLACE(:search, '%', '\\%'), '_', '\\_'), '[', '\\['), ']', '\\]') || '%' \r\n"
			+ "		OR fn.name ILIKE '%' || REPLACE(REPLACE(REPLACE(REPLACE(:search, '%', '\\%'), '_', '\\_'), '[', '\\['), ']', '\\]') || '%')\r\n"
			+ "order by t.user_name ASC", nativeQuery = true)
	Page<IListTestimonial> findByTestimonialContainingIgnoreCase(String search, Pageable pageable,
			Class<IListTestimonial> class1);

	TestimonialEntity findByuserNameIgnoreCase(String userName);

	@Query(value = "select t.id as Id ,t.testimonial as Testimonial,t.user_name as UserName ,t.is_visible as IsVisible ,t.description as Description,\r\n"
			+ "desig.id as DesignationId,"
			+ "t.file_id as FileId, fu.original_name as FileUrl, desig.name as DesignationName,"
			+ "fn.id as FunctionId, fn.name as FunctionName from testimonial t\r\n"
			+ "left join designation desig on t.designation_id=desig.id and desig.is_active=true"
			+ " left join file_upload fu on fu.id= t.file_id \r\n"
			+ "left join gpl_function fn on t.function_id=fn.id and fn.is_active=true \r\n"
			+ "where t.is_active=true order by t.id DESC\r\n" + "", nativeQuery = true)
	List<IListTestimonial> findByOrderByIdDesc(Class<IListTestimonial> class1);

	List<TestimonialEntity> findByDesignationId(Long designationId);

	List<TestimonialEntity> findByFunctionId(Long funcId);

	@Transactional
	@Modifying
	@Query("update TestimonialEntity set isActive = false where id in :ids")
	void deleteAllByIdIn(List<Long> ids);

	@Transactional
	@Modifying
	@Query("UPDATE TestimonialEntity t SET t.isVisible = :isVisble WHERE t.id IN :Ids")
	void isVisibleTestimonialByIds(@Param("isVisble") Boolean isVisible, @Param("Ids") List<Long> bannerIds);

}
