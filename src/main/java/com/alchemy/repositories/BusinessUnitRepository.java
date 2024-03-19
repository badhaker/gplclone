package com.alchemy.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.alchemy.entities.BusinessUnitEntity;
import com.alchemy.iListDto.IListBusinessUnit;

@Repository
public interface BusinessUnitRepository extends JpaRepository<BusinessUnitEntity, Long>{

	Page<IListBusinessUnit> findByOrderByIdDesc(Pageable pageable, Class<IListBusinessUnit> class1);

	@Query(value = "select b.id as Id ,b.business_unit_name as Name,b.description as Description from business_unit b where b.is_active = true\r\n"
			+ "and  (:search = '' OR b.business_unit_name ILIKE %:search% OR b.description ILIKE %:search%) order by b.business_unit_name ASC" , nativeQuery =  true)
	Page<IListBusinessUnit> findByNameIgnoreCase(@Param("search") String search, Pageable pageable,
			Class<IListBusinessUnit> class1);

	List<IListBusinessUnit> findByOrderByIdDesc(Class<IListBusinessUnit> class1);
	BusinessUnitEntity findByNameIgnoreCase(String name);
}
