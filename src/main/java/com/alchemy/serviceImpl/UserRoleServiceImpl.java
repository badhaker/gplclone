package com.alchemy.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alchemy.dto.AssignRole;
import com.alchemy.entities.RoleEntity;
import com.alchemy.entities.UserEntity;
import com.alchemy.entities.UserRoleEntity;
import com.alchemy.exceptionHandling.ResourceNotFoundException;
import com.alchemy.iListDto.IListRole;
import com.alchemy.iListDto.IListUserRoles;
import com.alchemy.iListDto.RoleList;
import com.alchemy.iListDto.UserRoleList;
import com.alchemy.repositories.RoleRepository;
import com.alchemy.repositories.UserRepository;
import com.alchemy.repositories.UserRoleRepository;
import com.alchemy.serviceInterface.UserRoleInterface;
import com.alchemy.utils.CacheOperation;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.Pagination;

@Service
public class UserRoleServiceImpl implements UserRoleInterface {

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private CacheOperation chaCacheOperation;

	@Override
	public void add(AssignRole assignRole) {
		try {
			ArrayList<UserRoleEntity> userRoles = new ArrayList<>();
			UserEntity user = this.userRepository.findById(assignRole.getUserId())
					.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.USER_NOT_PRESENT));

			for (int i = 0; i < assignRole.getRoleId().size(); i++) {
				final Long roleID = assignRole.getRoleId().get(i);
				RoleEntity role = this.roleRepository.findById(roleID)
						.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.ROLE_NOT_FOUND));

				UserRoleEntity userRoleEntity = new UserRoleEntity(user, role);
				userRoleEntity.setRoleId(role);
				userRoleEntity.setUserId(user);

				userRoles.add(userRoleEntity);
			}
			ArrayList<UserRoleEntity> list = userRoleRepository.getRolesOfUser(user.getId());
			userRoleRepository.deleteAll(list);
			if(chaCacheOperation.isConnectionClosed()) {
			chaCacheOperation.removeFromCache(user.getEmail());
			chaCacheOperation.removeFromCache(user.getId() + "permission");
			}
			userRoleRepository.saveAll(userRoles);
		} catch (Exception e) {
			throw new ResourceNotFoundException(e.getMessage());

		}
	}

	@Override
	public Page<IListUserRoles> getAllUserRole(String search, String pageNo, String pageSize) {
		Page<IListUserRoles> page;

		Pageable pageable = new Pagination().getPagination(pageNo, pageSize);
		page = this.userRoleRepository.findByUserRoles(search, pageable, IListUserRoles.class);

		List<UserRoleList> userRoleList = new ArrayList<>();

		if (page != null) {
			page.forEach(pageRole -> {

				if (userRoleList.isEmpty() || !checkUserExists(userRoleList, pageRole.getUserId())) {
					UserRoleList userRole = new UserRoleList();
					userRole.setUserId(pageRole.getUserId());
					userRole.setUserName(pageRole.getUserName());

					List<RoleList> roles = new ArrayList<>();
					RoleList role = new RoleList();

					role.setRoleName(pageRole.getRoleName());
					roles.add(role);
					userRole.setRoleList(roles);
					userRoleList.add(userRole);

				} else {
					UserRoleList userRole = getUserRoleFromList(userRoleList, pageRole.getUserId());
					RoleList role = new RoleList();

					role.setRoleName(pageRole.getRoleName());
					userRole.getRoleList().add(role);
				}

			});
		}
		return page;

	}

	private UserRoleList getUserRoleFromList(List<UserRoleList> userRoleList, Long userId) {
		Optional<UserRoleList> userRoleOptional = userRoleList.stream().filter(role -> role.getUserId().equals(userId))
				.findFirst();
		if (userRoleOptional.isPresent()) {
			return userRoleOptional.get();
		}
		return null;
	}

	private boolean checkUserExists(List<UserRoleList> userRoleList, Long userId) {
		boolean userExits = false;

		userExits = userRoleList.stream().anyMatch(project -> project.getUserId().equals(userId));
		return userExits;
	}

	@Override
	public List<String> getRoleByUserId(Long roleId) {
		List<String> roleNames = new ArrayList<>();
		List<IListRole> iListRoles;

		iListRoles = this.userRoleRepository.findRoleByUserId(roleId);

		for (int i = 0; i < iListRoles.size(); i++) {
			roleNames.add(iListRoles.get(i).getRoleName());
		}

		return roleNames;
	}

}
