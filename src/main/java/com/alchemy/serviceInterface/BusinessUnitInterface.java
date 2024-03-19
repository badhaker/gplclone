package com.alchemy.serviceInterface;

import java.util.List;

import org.springframework.data.domain.Page;

import com.alchemy.dto.BusinessUnitDto;
import com.alchemy.iListDto.IListBusinessUnit;


public interface BusinessUnitInterface {

	BusinessUnitDto addBusinessUnit(BusinessUnitDto businessUnitDto,Long userId);
	
	BusinessUnitDto updateBusinessUnit(BusinessUnitDto businessUnitDto ,Long id,Long userId);
	
	public void deleteBusinessUnitById(Long businessUnitId);
	
	Page<IListBusinessUnit> getAllBusinessUnit(String search, String pageNo, String pageSize) throws Exception;

	List<IListBusinessUnit> findAllList(String search, Class<IListBusinessUnit> class1);
}
