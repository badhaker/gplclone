package com.alchemy.serviceInterface;


import java.util.List;

import org.springframework.data.domain.Page;
import com.alchemy.dto.AssignRole;
import com.alchemy.iListDto.IListUserRoles;

public interface UserRoleInterface {

	void add(AssignRole assignRole);

	Page<IListUserRoles> getAllUserRole(String search, String pageNo, String pageSize);

	List<String> getRoleByUserId(Long roleId);
}
