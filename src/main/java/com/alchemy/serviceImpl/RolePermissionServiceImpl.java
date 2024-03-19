package com.alchemy.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.alchemy.dto.AssignPermission;
import com.alchemy.dto.RolePermissionDto;
import com.alchemy.entities.PermissionEntity;
import com.alchemy.entities.RoleEntity;
import com.alchemy.entities.RolePermissionEntity;
import com.alchemy.entities.UserRoleEntity;
import com.alchemy.exceptionHandling.ResourceNotFoundException;
import com.alchemy.iListDto.IListRolePermissions;
import com.alchemy.iListDto.IListUserRoles;
import com.alchemy.iListDto.IUserPermissionDto;
import com.alchemy.repositories.PermissionRepository;
import com.alchemy.repositories.RolePermissionRepository;
import com.alchemy.repositories.RoleRepository;
import com.alchemy.repositories.UserRoleRepository;
import com.alchemy.serviceInterface.RolePermissionInterface;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.Pagination;

@Service
public class RolePermissionServiceImpl implements RolePermissionInterface {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PermissionRepository permissionRepository;

	@Autowired
	private RolePermissionRepository rolePermissionRepository;
	
	@Autowired
	private UserRoleRepository userRoleRepository;

	@Override
	public void add(AssignPermission assignPermission) {
		try {
			ArrayList<RolePermissionEntity> rolePermissions = new ArrayList<>();
			RoleEntity role = this.roleRepository.findById(assignPermission.getRoleId())
					.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.ROLE_NOT_FOUND));

			for (int i = 0; i < assignPermission.getPermissionId().size(); i++) {
				final Long permissionID = assignPermission.getPermissionId().get(i);
				PermissionEntity permissionEntity = this.permissionRepository.findById(permissionID)
						.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.PERMISSION_NOT_FOUND));

				RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();
				rolePermissionEntity.setRoleId(role);
				rolePermissionEntity.setPermissionId(permissionEntity);

				rolePermissions.add(rolePermissionEntity);
			}
			rolePermissionRepository.saveAll(rolePermissions);
		} catch (Exception e) {
			throw new ResourceNotFoundException(e.getMessage());

		}

	}

	@Override
	public Page<IListRolePermissions> getAllRolePermissions(String search, String pageNo, String pageSize) {
		Page<IListRolePermissions> iListRolePermissions;

		Pageable pageable = new Pagination().getPagination(pageNo, pageSize);
		iListRolePermissions = this.rolePermissionRepository.findByRolePermissions(search, pageable, IListRolePermissions.class);
		return iListRolePermissions;
	}

	@Override
	public ArrayList<String> getPermissionByUserId(Long userId) {
		ArrayList<UserRoleEntity> roleIds = userRoleRepository.getRolesOfUser(userId);
		ArrayList<Long> roles = new ArrayList<>();

		for (int i = 0; i < roleIds.size(); i++) {
			roles.add(roleIds.get(i).getRoleId().getId());
		}
		ArrayList<RolePermissionEntity> rolesPermission = rolePermissionRepository.findPermissionByRole(roles);
		ArrayList<Long> permissions = new ArrayList<>();

		for (int i = 0; i < rolesPermission.size(); i++) {
			permissions.add(rolesPermission.get(i).getPermissionId().getId());
		}

		ArrayList<PermissionEntity> permissionEntity = permissionRepository.findByIdIn(permissions);
		ArrayList<String> actionName = new ArrayList<>();

		for (int i = 0; i < permissionEntity.size(); i++) {
			actionName.add(permissionEntity.get(i).getActionName());
		}
		return actionName;
	}

	@Override
	public List<IUserPermissionDto> getPermissionsByUserId(long id) {
		List<IUserPermissionDto> list=this.rolePermissionRepository.getPermissionById(id, IUserPermissionDto.class);
		
		return list;
		
		
		
	}


}
