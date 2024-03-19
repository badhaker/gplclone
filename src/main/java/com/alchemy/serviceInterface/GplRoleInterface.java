package com.alchemy.serviceInterface;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;

import com.alchemy.dto.DeleteId;
import com.alchemy.dto.GplRoleDto;
import com.alchemy.iListDto.IListGplRoleById;
import com.alchemy.iListDto.IListGplRoleDto;

public interface GplRoleInterface {

	GplRoleDto addGplRole(@Valid GplRoleDto gplRoleDto, Long userId);

	GplRoleDto updateGplRole(GplRoleDto gplRoleDto, Long id, Long userId);

	void deleteGplRole(DeleteId ids, Long userId);

	Page<IListGplRoleDto> getAllGplRole(String search, String pageNo, String pageSize);

	List<IListGplRoleById> getByDepartmentId(Long id);

}
