package com.alchemy.serviceInterface;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

import com.alchemy.dto.AssignPermission;
import com.alchemy.iListDto.IListRolePermissions;
import com.alchemy.iListDto.IUserPermissionDto;

public interface RolePermissionInterface {

	void add(AssignPermission assignPermission);

	public Page<IListRolePermissions> getAllRolePermissions(String search, String pageNo, String pageSize);

	ArrayList<String> getPermissionByUserId(Long id);
	
	List<IUserPermissionDto> getPermissionsByUserId(long id);
}
