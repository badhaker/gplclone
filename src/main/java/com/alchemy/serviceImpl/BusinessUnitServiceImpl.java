package com.alchemy.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.alchemy.dto.BusinessUnitDto;
import com.alchemy.entities.BusinessUnitEntity;
import com.alchemy.exceptionHandling.ResourceNotFoundException;
import com.alchemy.iListDto.IListBusinessUnit;
import com.alchemy.iListDto.IListLevel;
import com.alchemy.repositories.BusinessUnitRepository;
import com.alchemy.serviceInterface.BusinessUnitInterface;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.Pagination;


@Service
public class BusinessUnitServiceImpl implements BusinessUnitInterface{

	@Autowired
	private BusinessUnitRepository businessUnitRepository;
	
	@Override
	public BusinessUnitDto addBusinessUnit(BusinessUnitDto businessUnitDto,Long userId) {
		BusinessUnitEntity unitEntity = businessUnitRepository.findByNameIgnoreCase(businessUnitDto.getName());
		if(unitEntity != null) {
			throw new ResourceNotFoundException(ErrorMessageCode.BUSINESS_UNIT_ALREADY_PRESENT);
		}
		BusinessUnitEntity businessUnitEntity = new BusinessUnitEntity();
		businessUnitEntity.setName(businessUnitDto.getName());
		businessUnitEntity.setDescription(businessUnitDto.getDescription());
		businessUnitEntity.setCreatedBy(userId);
		businessUnitRepository.save(businessUnitEntity);
		return businessUnitDto;
	}
	
	@Override
	public BusinessUnitDto updateBusinessUnit(BusinessUnitDto businessUnitDto, Long id,Long userId) {
		BusinessUnitEntity businessUnitEntity 	= this.businessUnitRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.BUSINESS_UNIT_NOT_FOUND));
		BusinessUnitEntity unitEntity = businessUnitRepository.findByNameIgnoreCase(businessUnitDto.getName());
		if(unitEntity != null) {
			if(unitEntity.getId() != businessUnitEntity.getId()) {
				throw new ResourceNotFoundException(ErrorMessageCode.BUSINESS_UNIT_ALREADY_PRESENT);
			}
		}
		businessUnitEntity.setName(businessUnitDto.getName());
		businessUnitEntity.setDescription(businessUnitDto.getDescription());
		businessUnitEntity.setUpdatedBy(userId);
		this.businessUnitRepository.save(businessUnitEntity);
		return businessUnitDto;
	}

	@Override
	public void deleteBusinessUnitById(Long businessUnitId) {
		businessUnitRepository.findById(businessUnitId).orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.BUSINESS_UNIT_NOT_FOUND));

		this.businessUnitRepository.deleteById(businessUnitId);
	}

	@Override
	public Page<IListBusinessUnit> getAllBusinessUnit(String search, String pageNo, String pageSize) throws Exception {

		Page<IListBusinessUnit> iListBusinessUnit;

		Pageable pageable = new Pagination().getPagination(pageNo, pageSize);

		if (search == "" || search == null || search.length() == 0) {

			iListBusinessUnit = this.businessUnitRepository.findByOrderByIdDesc(pageable, IListBusinessUnit.class);
		} else {

			iListBusinessUnit = this.businessUnitRepository.findByNameIgnoreCase(search, pageable, IListBusinessUnit.class);
		}
		return iListBusinessUnit;
	}

	@Override
	public List<IListBusinessUnit> findAllList(String search, Class<IListBusinessUnit> class1) {
		List<IListBusinessUnit> businessUnits = this.businessUnitRepository.findByOrderByIdDesc(IListBusinessUnit.class);
		return businessUnits;
	}

}
