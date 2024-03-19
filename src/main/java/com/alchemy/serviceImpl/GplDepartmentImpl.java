package com.alchemy.serviceImpl;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alchemy.dto.DeleteId;
import com.alchemy.dto.GplDepartmentDto;
import com.alchemy.entities.GplDepartmentEntity;
import com.alchemy.entities.GplFunctionEntity;
import com.alchemy.exceptionHandling.ResourceNotFoundException;
import com.alchemy.iListDto.IListGplDepartmentDto;
import com.alchemy.repositories.GplDepartmentRepository;
import com.alchemy.repositories.GplFunctionRepository;
import com.alchemy.repositories.GplRoleRepository;
import com.alchemy.serviceInterface.GplDepartmentInterface;
import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.Pagination;

@Service
public class GplDepartmentImpl implements GplDepartmentInterface {

	@Autowired
	GplDepartmentRepository gplDepartmentRepository;

	@Autowired
	GplFunctionRepository gplFunctionRepository;

	@Autowired
	GplRoleRepository gplRoleRepository;

	@Override
	public GplDepartmentDto addGplDepartment(GplDepartmentDto gplDepartmentDto, Long userId) {

		GplFunctionEntity functionEntity = gplFunctionRepository.findById(gplDepartmentDto.getGplFunctionId())
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.GPL_FUNCTION_NOT_FOUND));

		GplDepartmentEntity gplDepartmentName = gplDepartmentRepository
				.findByNameIgnoreCaseAndGplFunctionIdAndIsActiveTrue(gplDepartmentDto.getName(), functionEntity);

		if (gplDepartmentName != null) {

			throw new ResourceNotFoundException(ErrorMessageCode.GPL_DEPARTMENT_PRESENT);
		}
		GplFunctionEntity gplFunctionEntity = gplFunctionRepository.findById(gplDepartmentDto.getGplFunctionId())
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.GPL_FUNCTION_NOT_FOUND));
		GplDepartmentEntity gplDepartmentEntity = new GplDepartmentEntity();
		gplDepartmentEntity.setName(gplDepartmentDto.getName());
		gplDepartmentEntity.setCreatedBy(userId);
		gplDepartmentEntity.setGplFunctionId(gplFunctionEntity);
		gplDepartmentRepository.save(gplDepartmentEntity);
		return gplDepartmentDto;
	}

	@Override
	public GplDepartmentDto updateGplDepartment(GplDepartmentDto gplDepartmentDto, Long id, Long userId) {
		GplDepartmentEntity gplDepartmentEntity = gplDepartmentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.GPL_DEPARTMENT_NOT_FOUND));

		GplFunctionEntity gplFunctionEntity = gplFunctionRepository.findById(gplDepartmentDto.getGplFunctionId())
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.GPL_FUNCTION_NOT_FOUND));
		GplDepartmentEntity gplDepartmentName = gplDepartmentRepository
				.findByNameIgnoreCaseAndGplFunctionIdAndIsActiveTrue(gplDepartmentDto.getName(), gplFunctionEntity);

		if (gplDepartmentName != null) {
			if (gplDepartmentName.getId() != gplDepartmentEntity.getId()) {
				throw new ResourceNotFoundException(ErrorMessageCode.GPL_DEPARTMENT_PRESENT);
			}
		}
		gplDepartmentEntity.setName(gplDepartmentDto.getName());
		gplDepartmentEntity.setGplFunctionId(gplFunctionEntity);
		gplDepartmentEntity.setUpdatedBy(userId);
		gplDepartmentRepository.save(gplDepartmentEntity);
		return gplDepartmentDto;
	}

	@Override
	public void deleteGplDepartment(@Valid DeleteId ids, Long userId) {
		if (ids.getIds().isEmpty()) {
			throw new ResourceNotFoundException(ErrorMessageCode.GPL_DEPARTMENT_DELETE);
		}

		gplDepartmentRepository.deleteGplDepartmentByIds(userId, ids.getIds());
		gplRoleRepository.deleteGplDepartmentByIds(userId, ids.getIds());
	}

	@Override
	public Page<IListGplDepartmentDto> getAllGplDepartment(String search, String pageNo, String pageSize,
			String functionId) {
		Page<IListGplDepartmentDto> iListGplDepartmentDto;

		String pageNumber = pageNo.isBlank() ? Constant.DEFAULT_PAGENUMBER : pageNo;
		String pages = pageSize.isBlank() ? Constant.DEFAULT_PAGESIZE : pageSize;
		Pageable pageable = new Pagination().getPagination(pageNumber, pages);
		if (pageNo.isBlank() && pageSize.isBlank()) {

			pageable = Pageable.unpaged();
		}

		iListGplDepartmentDto = this.gplDepartmentRepository.findByName(search, pageable, functionId,
				IListGplDepartmentDto.class);

		return iListGplDepartmentDto;

	}

}
