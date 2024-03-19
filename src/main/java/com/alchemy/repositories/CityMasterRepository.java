package com.alchemy.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alchemy.entities.CityMasterEntity;
import com.alchemy.iListDto.IListCityMaster;

@Repository
public interface CityMasterRepository extends JpaRepository<CityMasterEntity, Long> {

	Page<IListCityMaster> findByCityContainingIgnoreCaseOrderByIdDesc(String search, Pageable pageable,
			Class<IListCityMaster> class1);

	CityMasterEntity findByCityIgnoreCaseAndIsActiveTrue(String cityNmae);

}
