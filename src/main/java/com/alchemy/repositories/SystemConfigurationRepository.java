package com.alchemy.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alchemy.entities.SystemConfiguration;
import com.alchemy.iListDto.IListFooterDocumentDto;

public interface SystemConfigurationRepository extends JpaRepository<SystemConfiguration, Long> {

	SystemConfiguration findByKey(String careerAspiration);

	SystemConfiguration findBykey(String key);

	@Query(value = "select fu.id as Id,fu.original_name as OriginalName ,sc.updated_at as UpdatedAt,fu.filename as FileName, sc.key as Key ,sc.value as Text ,sc.flag as Flag from system_configuration sc\r\n"
			+ "left join file_upload fu on fu.id=sc.file_id", nativeQuery = true)
	ArrayList<IListFooterDocumentDto> findByOrderByKeyDesc(Class<IListFooterDocumentDto> class1);

	@Query(value = "select f.key from system_configuration as f", nativeQuery = true)
	ArrayList<String> getAllRows();

}
