package com.alchemy.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.alchemy.entities.ModuleMasterEntity;
import com.alchemy.iListDto.IModuleList;

@Repository
public interface ModuleMasterRepository extends JpaRepository<ModuleMasterEntity, Long> {

	Page<IModuleList> findByOrderByIdDesc(Pageable pageable, Class<IModuleList> class1);

	Page<IModuleList> findByModuleNameContainingIgnoreCase(String search, Pageable pageable, Class<IModuleList> class1);

	List<IModuleList> findByOrderByIdDesc(Class<IModuleList> class1);

	ModuleMasterEntity findByModuleNameContainingIgnoreCase(String moduleName);

	ModuleMasterEntity findByModuleNameIgnoreCaseAndIsActiveTrue(String moduleName);

	ModuleMasterEntity findByModuleNameIgnoreCase(String moduleName);

	@Transactional
	@Modifying
	@Query("update ModuleMasterEntity set isActive = false where id in :ids")
	void deleteAllByIdIn(List<Long> ids);

}
