package com.alchemy.serviceInterface;

import javax.validation.Valid;

import org.springframework.data.domain.Page;

import com.alchemy.dto.DeleteId;
import com.alchemy.dto.GplDepartmentDto;
import com.alchemy.iListDto.IListGplDepartmentDto;

public interface GplDepartmentInterface {

	GplDepartmentDto addGplDepartment(GplDepartmentDto gplDepartmentDto, Long userId);

	GplDepartmentDto updateGplDepartment(GplDepartmentDto gplDepartmentDto, Long id, Long userId);

	Page<IListGplDepartmentDto> getAllGplDepartment(String search, String pageNo, String pageSize, String functionId);

	void deleteGplDepartment(@Valid DeleteId Ids, Long userId);

}
