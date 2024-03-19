package com.alchemy.serviceInterface;

import java.util.List;

import org.springframework.data.domain.Page;

import com.alchemy.dto.DeleteId;
import com.alchemy.dto.DepartmentDto;
import com.alchemy.dto.RoleDto;
import com.alchemy.iListDto.IListDepartment;

public interface DepartmentInterface {

	Page<IListDepartment> getAllDepartment(String search, String pageNo, String pageSize) throws Exception;

	public void deleteDepartmentById(Long deptId,Long userId);
	
	public void deleteMultipleDepartmentById(DeleteId id, Long userId);

	DepartmentDto updateDepartment(DepartmentDto deptDto, Long id, Long userId);

	DepartmentDto addDepartment(DepartmentDto deptDto, Long userId);

	List<IListDepartment> findAllDepartmentList(Class<IListDepartment> class1);

	

}
