package com.alchemy.serviceImpl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alchemy.dto.DeleteId;
import com.alchemy.dto.GplRoleDto;
import com.alchemy.entities.GplDepartmentEntity;
import com.alchemy.entities.GplRoleEntity;
import com.alchemy.exceptionHandling.ResourceNotFoundException;
import com.alchemy.iListDto.IListGplRoleById;
import com.alchemy.iListDto.IListGplRoleDto;
import com.alchemy.repositories.GplDepartmentRepository;
import com.alchemy.repositories.GplRoleRepository;
import com.alchemy.serviceInterface.GplRoleInterface;
import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.Pagination;

@Service
public class GplRoleImpl implements GplRoleInterface {

	@Autowired
	private GplRoleRepository gplRoleRepository;

	@Autowired
	GplDepartmentRepository gplDepartmentRepository;

	@Override
	public GplRoleDto addGplRole(@Valid GplRoleDto gplRoleDto, Long userId) {

		GplRoleEntity gplRole = gplRoleRepository.findByNameIgnoreCaseAndIsActiveTrue(gplRoleDto.getName());

		if (gplRole != null) {

			throw new ResourceNotFoundException(ErrorMessageCode.GPL_ROLE_PRESENT);
		}

		GplDepartmentEntity gplDepartmentEntity = gplDepartmentRepository.findById(gplRoleDto.getGplDepartmentId())
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.GPL_DEPARTMENT_NOT_FOUND));
		GplRoleEntity gplRoleEntity = new GplRoleEntity();
		gplRoleEntity.setName(gplRoleDto.getName());
		gplRoleEntity.setCreatedBy(userId);
		gplRoleEntity.setGplDepartmentEntity(gplDepartmentEntity);
		gplRoleRepository.save(gplRoleEntity);
		return gplRoleDto;
	}

	@Override
	public GplRoleDto updateGplRole(GplRoleDto gplRoleDto, Long id, Long userId) {

		GplRoleEntity gplRoleName = gplRoleRepository.findByNameIgnoreCaseAndIsActiveTrue(gplRoleDto.getName());

		GplRoleEntity gplRoleEntity = gplRoleRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.GPL_ROLE_NOT_FOUND));
		GplDepartmentEntity gplDepartmentEntity = gplDepartmentRepository.findById(gplRoleDto.getGplDepartmentId())
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.GPL_DEPARTMENT_NOT_FOUND));

		if (gplRoleName != null) {
			if (gplRoleName.getId() != gplRoleEntity.getId()) {
				throw new ResourceNotFoundException(ErrorMessageCode.GPL_DEPARTMENT_PRESENT);
			}
		}
		gplRoleEntity.setName(gplRoleDto.getName());
		gplRoleEntity.setUpdatedBy(userId);
		gplRoleEntity.setGplDepartmentEntity(gplDepartmentEntity);
		gplRoleRepository.save(gplRoleEntity);

		return gplRoleDto;
	}

	@Override
	public void deleteGplRole(DeleteId ids, Long userId) {
		if (ids.getIds().isEmpty()) {
			throw new ResourceNotFoundException(ErrorMessageCode.GPL_ROLE_DELETE);
		}
		gplRoleRepository.deleteGplRoleByIds(userId, ids.getIds());

	}

	@Override
	public Page<IListGplRoleDto> getAllGplRole(String search, String pageNo, String pageSize) {
		Page<IListGplRoleDto> iListGplRoleDto;

		String pageNumber = pageNo.isBlank() ? Constant.DEFAULT_PAGENUMBER : pageNo;
		String pages = pageSize.isBlank() ? Constant.DEFAULT_PAGESIZE : pageSize;
		Pageable pageable = new Pagination().getPagination(pageNumber, pages);
		if (pageNo.isBlank() && pageSize.isBlank()) {

			pageable = Pageable.unpaged();
		}

		iListGplRoleDto = this.gplRoleRepository.findByName(search, pageable, IListGplRoleDto.class);

		return iListGplRoleDto;

	}

	@Override
	public List<IListGplRoleById> getByDepartmentId(Long id) {

		List<IListGplRoleById> iListGplRoleById = this.gplRoleRepository.getByDepartmentId(id);
		return iListGplRoleById;
	}

}
