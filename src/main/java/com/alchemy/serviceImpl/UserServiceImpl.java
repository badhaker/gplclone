package com.alchemy.serviceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alchemy.dto.AssignRole;
import com.alchemy.dto.DeleteId;
import com.alchemy.dto.UserUpdateDto;
import com.alchemy.entities.MailTemplate;
import com.alchemy.entities.UserEntity;
import com.alchemy.exceptionHandling.ResourceNotFoundException;
import com.alchemy.iListDto.IListCareerAspiration;
import com.alchemy.iListDto.IListUserDto;
import com.alchemy.repositories.MailRepository;
import com.alchemy.repositories.RoleRepository;
import com.alchemy.repositories.SystemConfigurationRepository;
import com.alchemy.repositories.UserRepository;
import com.alchemy.serviceInterface.EmailInterface;
import com.alchemy.serviceInterface.InviteServiceInterface;
import com.alchemy.serviceInterface.UserInterface;
import com.alchemy.serviceInterface.UserRoleInterface;
import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.Pagination;

@Service
@Transactional
public class UserServiceImpl implements UserInterface {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	private EmailInterface emailInterface;
	@Autowired
	private UserRoleInterface userRoleInterface;

	@Autowired
	private SystemConfigurationRepository systemConfigurationRepository;

	@Autowired
	private MailRepository mailRepository;

	@Value("${url}")
	private String frontendURL;

	@Autowired
	private InviteServiceInterface inviteServiceInterface;

	@Override
	public UserUpdateDto editUser(Long userId, UserUpdateDto userDto) throws Exception {

		UserEntity userEntity = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.USER_NOT_PRESENT));

		for (int i = 0; i < userDto.getRoleId().size(); i++) {
			final Long roleId = userDto.getRoleId().get(i);
			this.roleRepository.findById(roleId)
					.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.ROLE_NOT_FOUND));
		}
		userEntity.setName(userDto.getName());
		userEntity.setGender(userDto.getGender());
		userEntity.setPhoneNumber(userDto.getPhoneNumber());
//		userEntity.setDepartmentId(departmentEntity.getId());
//		userEntity.setLevelId(levelEntity.getId());
//		userEntity.setDesignationId(designationEntity.getId());
		userRepository.save(userEntity);

		if (userEntity.getPassword() == null || userEntity.getPassword().isEmpty()) {

			UUID uuid = UUID.randomUUID();

			inviteServiceInterface.add(uuid, userEntity.getId());

			String url = frontendURL + uuid;

			MailTemplate mailtemplate = mailRepository.findBytemplatename(Constant.ONBOARD_TEMPLATE_NAME);
			if (null != mailtemplate) {
				String template = mailtemplate.getMailtemp();
				String replaceString = template.replace("USER_NAME", userEntity.getName()).replace("ONBOARDING_URL",
						url);

				emailInterface.sendSimpleMessage(userEntity.getEmail(), "Onboarding - Welcome to GPL !!",
						replaceString);
			} else {
				throw new ResourceNotFoundException(ErrorMessageCode.MAILTEMPLATE_NOT_FOUND);
			}
		}

		AssignRole role = new AssignRole(userEntity.getId(), userDto.getRoleId());
		userRoleInterface.add(role);
		return userDto;

	}

	@Override
	public void deleteUser(Long userId) throws Exception {
		UserEntity userEntity = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.USER_NOT_PRESENT));
		userEntity.setIsActive(false);
		userRepository.save(userEntity);
	}

	@Override
	public Page<IListUserDto> getAllUsers(String search, String pageNo, String pageSize) {
		Pageable pagingPageable = new Pagination().getPagination(pageNo, pageSize);

		return this.userRepository.findByNameIgnoreCase(search, pagingPageable, IListUserDto.class);
	}

	@Override
	public List<IListUserDto> exportUserToExcel(HttpServletResponse response) throws IOException {
		List<IListUserDto> iListUserDtos = userRepository.getUsers(response, IListUserDto.class);

		if (iListUserDtos.size() == 0) {
			return iListUserDtos;
		}

		StringBuilder builder = new StringBuilder();
		builder.append("Name").append(",").append("Email").append(",").append("Gender").append(",")
				.append("PhoneNumber").append(",").append("RoleName").append(",").append("CareerAspiration")
				.append(",");
		builder.append('\n');

		for (IListUserDto user : iListUserDtos) {
			builder.append(user.getName() != null ? user.getName() : "").append(",")
					.append(user.getEmail() != null ? user.getEmail() : "").append(",")
					.append(user.getGender() != null ? user.getGender() : "").append(",")
					.append(user.getPhoneNumber() != null ? user.getPhoneNumber() : "").append(",")
					.append(user.getRoleName() != null ? user.getRoleName() : "").append(",")
					.append((user.getCareerAspiration() != null
							? "\"" + user.getCareerAspiration().replaceAll("[\r\n\t]", " ") + "\""
							: ""));
			builder.append('\n');

		}
		PrintWriter writer = response.getWriter();
		writer.print(builder.toString());
		writer.flush();
		writer.close();
		return iListUserDtos;
	}

	@Override
	public Page<IListCareerAspiration> getAllCareerAspiration(String search, String pageNo, String pageSize) {
		Page<IListCareerAspiration> iListCreerAspiration;

		Pageable pagingPageable = new Pagination().getPagination(pageNo, pageSize);

		iListCreerAspiration = this.userRepository.findByName(search, pagingPageable, IListCareerAspiration.class);

		return iListCreerAspiration;
	}

	@Override
	public List<IListUserDto> findAllUsers() {
		List<IListUserDto> list = this.userRepository.findAllUsers(IListUserDto.class);
		return list;
	}

	@Override
	public void deleteMultipleUsersById(DeleteId ids, Long userId) {

		List<UserEntity> entity = userRepository.findAllById(ids.getIds());
		for (int i = 0; i < entity.size(); i++) {
			entity.get(i).setUpdatedBy(userId);
		}

		userRepository.deleteAllByIdIn(ids.getIds());

	}
}
