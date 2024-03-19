package com.alchemy.serviceInterface;

import java.util.List;

import org.springframework.data.domain.Page;

import com.alchemy.dto.DeleteId;
import com.alchemy.dto.ModuleDto;
import com.alchemy.iListDto.IModuleList;

public interface ModuleMasterInterface {

	ModuleDto addModules(Long userId, ModuleDto moduleDto);

	ModuleDto updateModule(Long userId, ModuleDto moduleDto, Long id) throws Exception;

	void deleteModule(Long userId, Long id) throws Exception;

	Page<IModuleList> getAllModules(String search, String pageNo, String pageSize);

	List<IModuleList> findAll(Class<IModuleList> class1);

	void deleteMultipleModuleById(DeleteId id, Long userId);

}
