package com.alchemy.repositories;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alchemy.dto.DeleteId;
import com.alchemy.entities.DepartmentEntity;
import com.alchemy.iListDto.IListDepartment;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {

	@Transactional
	@Modifying
	@Query("update DepartmentEntity set isActive = false where id in :ids")
	void deleteAllByIdIn(@Param("ids") List<Long> ids);
	
	DepartmentEntity findByNameIgnoreCase(String name);

	Page<IListDepartment> findByOrderByIdDesc(Pageable pageable, Class<IListDepartment> class1);

	@Query(value = "select d.id as Id ,d.department_name as Name ,d.description as Description from department d where d.is_active=true \r\n"
			+ "and (:search = '' OR d.department_name ILIKE %:search% OR d.description ILIKE %:search%) order by d.department_name ASC", nativeQuery = true)
	Page<IListDepartment> findByNameContainingIgnoreCase(@Param("search") String search, Pageable pageable,
			Class<IListDepartment> class1);

	List<IListDepartment> findByOrderByIdDesc(Class<IListDepartment> class1);

	ArrayList<DepartmentEntity> findByIdIn(ArrayList<Long> departmentId);

	DepartmentEntity findByNameIgnoreCaseAndIsActiveTrue(String name);

	DepartmentEntity findByName(String string);

}
