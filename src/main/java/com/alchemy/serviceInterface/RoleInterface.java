package com.alchemy.serviceInterface;

import java.util.List;

import org.springframework.data.domain.Page;

import com.alchemy.dto.RoleDto;
import com.alchemy.iListDto.IListRole;
import com.alchemy.iListDto.IListRolePermissions;

public interface RoleInterface {

	Page<IListRole> getAllRoles(String search, String pageNo, String pageSize) throws Exception;

	RoleDto updateRoles(RoleDto roleDto, Long id, Long userId);

	RoleDto addRole(RoleDto roleDto, Long userId);

	public void deleteRoleById(Long roleId, Long userId);

	List<IListRole> getAllRole();

	List<IListRolePermissions> getPermissionsByRoleId(Long roleId);
}
