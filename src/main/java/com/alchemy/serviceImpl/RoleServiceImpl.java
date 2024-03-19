package com.alchemy.serviceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alchemy.dto.RoleDto;
import com.alchemy.entities.PermissionEntity;
import com.alchemy.entities.RoleEntity;
import com.alchemy.entities.RolePermissionEntity;
import com.alchemy.entities.UserRoleEntity;
import com.alchemy.exceptionHandling.ResourceNotFoundException;
import com.alchemy.iListDto.IListRole;
import com.alchemy.iListDto.IListRolePermissions;
import com.alchemy.repositories.PermissionRepository;
import com.alchemy.repositories.RolePermissionRepository;
import com.alchemy.repositories.RoleRepository;
import com.alchemy.repositories.UserRoleRepository;
import com.alchemy.serviceInterface.RoleInterface;
import com.alchemy.utils.CacheOperation;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.Pagination;

@Service
@Transactional
public class RoleServiceImpl implements RoleInterface {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private RolePermissionRepository rolePermissionRepository;

	@Autowired
	private PermissionRepository permissionRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private CacheOperation cacheOperation;

	@Override
	public RoleDto addRole(RoleDto roleDto, Long userId) {
		RoleEntity entity = this.roleRepository.findByRoleNameIgnoreCase(roleDto.getRoleName());
		if (entity != null) {
			throw new ResourceNotFoundException(ErrorMessageCode.ROLE_ALREADY_PRESENT);
		}
		RoleEntity roleEntity = new RoleEntity();

		roleEntity.setRoleName(roleDto.getRoleName().toUpperCase().trim());
		roleEntity.setDescription(roleDto.getDescription());
		roleEntity.setCreatedBy(userId);
		this.roleRepository.save(roleEntity);

		List<RolePermissionEntity> rolePermissionEntities = new ArrayList<>();

		if (roleDto.getPermissionId() != null) {
			for (int i = 0; i < roleDto.getPermissionId().size(); i++) {

				PermissionEntity permissionEntity = this.permissionRepository.findById(roleDto.getPermissionId().get(i))
						.orElseThrow(() -> new ResourceNotFoundException("Permission not found"));

				RolePermissionEntity rolePermissionEntity = new RolePermissionEntity(roleEntity, permissionEntity);
				rolePermissionEntities.add(rolePermissionEntity);
				this.rolePermissionRepository.saveAll(rolePermissionEntities);
			}

		}
		cacheOperation.removeAllKeysStartingWith();
		return roleDto;
	}

	@Override
	public RoleDto updateRoles(RoleDto roleDto, Long id, Long userId) {
		RoleEntity roleEntity = this.roleRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.ROLE_NOT_FOUND));

		RoleEntity roleName = this.roleRepository.findByRoleNameIgnoreCaseAndIsActiveTrue(roleDto.getRoleName());
		if (roleName != null) {
			if (roleName.getId() != roleEntity.getId()) {
				throw new ResourceNotFoundException(ErrorMessageCode.ROLE_ALREADY_PRESENT);
			}
		}
		roleEntity.setRoleName(roleDto.getRoleName().toUpperCase());
		roleEntity.setDescription(roleDto.getDescription());
		roleEntity.setUpdatedBy(userId);
		this.roleRepository.save(roleEntity);

//		List<Long> permissions = this.rolePermissionRepository.getPermissionIdUsingRoleId(id);

//		List<Long> idsToBeDeleted = new ArrayList<>();
		Set<Long> newPermissionIds = new HashSet<>(roleDto.getPermissionId());

//		idsToBeDeleted.addAll(permissions);
//		idsToBeDeleted.removeAll(newPermissionIds);

		rolePermissionRepository.deleteByRoleId(roleEntity);

//		newPermissionIds.addAll(permissions);

		List<PermissionEntity> permissionEntity = this.permissionRepository.findByIdIn(newPermissionIds);

		List<RolePermissionEntity> rolePermissionEntities = new ArrayList<>();

		for (int i = 0; i < permissionEntity.size(); i++) {
			RolePermissionEntity rolePermissionEntity = new RolePermissionEntity(roleEntity, permissionEntity.get(i));
			rolePermissionEntities.add(rolePermissionEntity);
		}

		rolePermissionRepository.saveAll(rolePermissionEntities);
		cacheOperation.removeAllKeysStartingWith();
		return roleDto;
	}

	@Override
	public Page<IListRole> getAllRoles(String search, String pageNumber, String pageSize) throws Exception {
		Page<IListRole> iListRole;

		Pageable pageable = new Pagination().getPagination(pageNumber, pageSize);

		if (search == "" || search == null || search.length() == 0) {

			iListRole = this.roleRepository.findByOrderByIdDesc(pageable, IListRole.class);
		} else {

			iListRole = this.roleRepository.findByRoleNameContainingIgnoreCase(search, pageable, IListRole.class);
		}
		return iListRole;
	}

	@Override
	public void deleteRoleById(Long id, Long userId) {
		RoleEntity roleEntity = roleRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.ROLE_NOT_FOUND));
		roleEntity.setIsActive(false);
		this.roleRepository.save(roleEntity);
		List<UserRoleEntity> userRoleEntity = userRoleRepository.findByRoleId(id);
		cacheOperation.removeAllKeysStartingWith();
		userRoleRepository.deleteAll(userRoleEntity);
	}

	@Override
	public List<IListRole> getAllRole() {
		List<IListRole> iListRoles = this.roleRepository.findByOrderByRoleNameAsc(IListRole.class);
		return iListRoles;
	}

	@Override
	public List<IListRolePermissions> getPermissionsByRoleId(Long roleId) {

		this.roleRepository.findById(roleId)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.ROLE_NOT_FOUND));

		List<IListRolePermissions> listPermissions = this.rolePermissionRepository.findPermissionIdByRoleId(roleId,
				IListRolePermissions.class);

		return listPermissions;
	}

}
