package com.alchemy.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alchemy.dto.DeleteId;
import com.alchemy.dto.ModuleDto;
import com.alchemy.entities.DepartmentEntity;
import com.alchemy.entities.ModuleMasterEntity;
import com.alchemy.exceptionHandling.ResourceNotFoundException;
import com.alchemy.iListDto.IModuleList;
import com.alchemy.repositories.ModuleMasterRepository;
import com.alchemy.serviceInterface.ModuleMasterInterface;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.Pagination;

@Service
public class ModuleMasterServiceImpl implements ModuleMasterInterface {

	@Autowired
	private ModuleMasterRepository moduleMasterRepository;

	@Override
	public ModuleDto addModules(Long userId, ModuleDto moduleDto) {
		ModuleMasterEntity moduleMaster = this.moduleMasterRepository
				.findByModuleNameIgnoreCaseAndIsActiveTrue(moduleDto.getModuleName());
		if (moduleMaster != null) {
			throw new ResourceNotFoundException(ErrorMessageCode.MODULE_ALREADY_PRESENT);
		}
		ModuleMasterEntity moduleMasterEntity = new ModuleMasterEntity();
		moduleMasterEntity.setModuleName(moduleDto.getModuleName());
		moduleMasterEntity.setCreatedBy(userId);
		moduleMasterRepository.save(moduleMasterEntity);
		return moduleDto;
	}

	@Override
	public ModuleDto updateModule(Long userId, ModuleDto moduleDto, Long id) throws Exception {
		ModuleMasterEntity moduleMasterEntity = this.moduleMasterRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.MODULE_NOT_FOUND));
		ModuleMasterEntity moduleMaster = this.moduleMasterRepository
				.findByModuleNameIgnoreCaseAndIsActiveTrue(moduleDto.getModuleName());
		if (moduleMaster != null) {
			if (moduleMaster.getId() != moduleMasterEntity.getId()) {
				throw new ResourceNotFoundException(ErrorMessageCode.MODULE_ALREADY_PRESENT);
			}
		}
		moduleMasterEntity.setModuleName(moduleDto.getModuleName());
		moduleMasterEntity.setUpdatedBy(userId);
		moduleMasterRepository.save(moduleMasterEntity);
		return moduleDto;
	}

	@Override
	public void deleteModule(Long userId, Long id) throws Exception {
		ModuleMasterEntity moduleMasterEntity = this.moduleMasterRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.MODULE_NOT_FOUND));
		moduleMasterEntity.setIsActive(false);
		moduleMasterEntity.setUpdatedBy(userId);
		moduleMasterRepository.save(moduleMasterEntity);

	}

	@Override
	public Page<IModuleList> getAllModules(String search, String pageNo, String pageSize) {
		Page<IModuleList> page;

		String pageNumber = pageNo.isBlank() ? "1" : pageNo;
		String pageIsize = pageSize.isBlank() ? "10" : pageSize;

		Pageable pageable = new Pagination().getPagination(pageNumber, pageIsize);
		if ((search == null) || (search == "") || (search.length() == 0)) {
			page = this.moduleMasterRepository.findByOrderByIdDesc(pageable, IModuleList.class);
		} else {
			page = this.moduleMasterRepository.findByModuleNameContainingIgnoreCase(search, pageable,
					IModuleList.class);
		}
		return page;
	}

	@Override
	public List<IModuleList> findAll(Class<IModuleList> class1) {
		List<IModuleList> moduleDto = this.moduleMasterRepository.findByOrderByIdDesc(IModuleList.class);
		return moduleDto;
	}

	@Override
    public void deleteMultipleModuleById(DeleteId ids, Long userId) {

			List<ModuleMasterEntity> entity = moduleMasterRepository.findAllById(ids.getIds());
			for (int i= 0 ; i<entity.size(); i++) {
				entity.get(i).setUpdatedBy(userId);
			}

			moduleMasterRepository.deleteAllByIdIn(ids.getIds());

		}
    }


