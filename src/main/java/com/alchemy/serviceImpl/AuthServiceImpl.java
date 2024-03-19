package com.alchemy.serviceImpl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.alchemy.dto.AssignRole;
import com.alchemy.dto.ForgotPasswordConfirmDto;
import com.alchemy.dto.OTPDto;
import com.alchemy.dto.OnboardDto;
import com.alchemy.dto.UserDto;
import com.alchemy.entities.ErrorLoggerEntity;
import com.alchemy.entities.GplApiResponceEntity;
import com.alchemy.entities.GplDepartmentEntity;
import com.alchemy.entities.GplFunctionEntity;
import com.alchemy.entities.InviteEntity;
import com.alchemy.entities.LevelEntity;
import com.alchemy.entities.MailTemplate;
import com.alchemy.entities.MyResponse;
import com.alchemy.entities.OtpEntity;
import com.alchemy.entities.UserEntity;
import com.alchemy.exceptionHandling.ResourceNotFoundException;
import com.alchemy.iListDto.IUserPermissionDto;
import com.alchemy.repositories.AuthRepository;
import com.alchemy.repositories.ErrorLoggerRepository;
import com.alchemy.repositories.GplDepartmentRepository;
import com.alchemy.repositories.GplFunctionRepository;
import com.alchemy.repositories.InviteReposiotry;
import com.alchemy.repositories.LevelRepository;
import com.alchemy.repositories.MailRepository;
import com.alchemy.repositories.OTPRepository;
import com.alchemy.repositories.RoleRepository;
import com.alchemy.repositories.UserRepository;
import com.alchemy.serviceInterface.AuthInterface;
import com.alchemy.serviceInterface.EmailInterface;
import com.alchemy.serviceInterface.InviteServiceInterface;
import com.alchemy.serviceInterface.OtpInterface;
import com.alchemy.serviceInterface.RolePermissionInterface;
import com.alchemy.serviceInterface.UserRoleInterface;
import com.alchemy.utils.CacheOperation;
import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.Validator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AuthServiceImpl implements AuthInterface, UserDetailsService {

	@Autowired
	private AuthRepository authRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RolePermissionInterface rolePermissionInterface;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	private UserRoleInterface userRoleInterface;

	@Autowired
	private OTPRepository otpRepository;

	@Autowired
	private EmailInterface emailInterface;

	@Autowired
	private LevelRepository levelRepository;
	@Autowired
	private CacheOperation cacheOperation;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MailRepository mailRepository;

	@Autowired
	private OtpInterface otpInterface;

	@Autowired
	private InviteReposiotry inviteReposiotry;

	@Autowired
	private GplApiIntegrationServiceImpl gplApiIntegrationServiceImpl;

	@Autowired
	private GplFunctionRepository functionRepository;

	@Value("${url}")
	private String frontendURL;

	@Autowired
	private InviteServiceInterface inviteServiceInterface;

	@Autowired
	private ErrorLoggerRepository loggerRepository;

	@Autowired
	private GplDepartmentRepository departmentRepository;

	@Autowired
	private UserRepository userRepository;
	private static final Logger LOG = LoggerFactory.getLogger(AuthServiceImpl.class);

	@Override
	public InviteEntity onboardUser(OnboardDto dashboardDto) throws Exception {
		if (dashboardDto.getUuid() == null) {
			throw new ResourceNotFoundException(ErrorMessageCode.INVALID_UUID);
		}
		InviteEntity inviteEntity = this.inviteReposiotry.findbyUuid(dashboardDto.getUuid());

		if (null != inviteEntity) {

			if (dashboardDto.getPassword().equals(dashboardDto.getConfirmPassword())) {
				if (Validator.isValid(dashboardDto.getPassword())) {
					UserEntity userEntity = this.authRepository.findById(inviteEntity.getUserId()).get();
					userEntity.setPassword(passwordEncoder.encode(dashboardDto.getConfirmPassword()));
					userEntity.setIsAdmin(true);
					this.authRepository.save(userEntity);

					inviteReposiotry.delete(inviteEntity);
				} else {
					throw new ResourceNotFoundException(ErrorMessageCode.INVALID_PASSWORD_FORMAT);
				}
			} else {
				throw new ResourceNotFoundException(ErrorMessageCode.PASSWORD_DOES_NOT_MATCH);
			}
		} else {
			throw new ResourceNotFoundException(ErrorMessageCode.INVALID_UUID);
		}
		return inviteEntity;

	}

	@Override
	public UserDto registerUser(UserDto userDto) throws Exception {

		UserEntity userEmail = userRepository.findByEmail(userDto.getEmail());
		if (userEmail != null) {
			throw new ResourceNotFoundException(ErrorMessageCode.ALREADY_REGISTER);
		}
		for (int i = 0; i < userDto.getRoleId().size(); i++) {
			final Long roleId = userDto.getRoleId().get(i);
			this.roleRepository.findById(roleId)
					.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.ROLE_NOT_FOUND));
		}

		UserEntity userEntity = new UserEntity();
		userEntity.setName(userDto.getName());
		if (Validator.isValidforEmail(userDto.getEmail())) {
			userEntity.setEmail(userDto.getEmail());
		} else {
			throw new IllegalArgumentException(ErrorMessageCode.INVALID_EMAIL);
		}
		userEntity.setGender(userDto.getGender());
		userEntity.setPhoneNumber(userDto.getPhoneNumber());
		authRepository.save(userEntity);

		AssignRole role = new AssignRole(userEntity.getId(), userDto.getRoleId());
		userRoleInterface.add(role);

		UUID uuid = UUID.randomUUID();

		inviteServiceInterface.add(uuid, userEntity.getId());

		String url = frontendURL + uuid;

		MailTemplate mailtemplate = mailRepository.findBytemplatename(Constant.ONBOARD_TEMPLATE_NAME);
		if (null != mailtemplate) {
			String template = mailtemplate.getMailtemp();
			String replaceString = template.replace("USER_NAME", userEntity.getName()).replace("ONBOARDING_URL", url);

			LOG.info("AuthServiceImpl >> registerUser() >>  Onboarding >>  " + userEntity.getEmail());
			emailInterface.sendSimpleMessage(userEntity.getEmail(), "Onboarding - Welcome to GPL !!", replaceString);
			LOG.info("AuthServiceImpl >> registerUser() >>  Onboarding >>  " + userEntity.getEmail());
		} else {
			throw new ResourceNotFoundException(ErrorMessageCode.MAILTEMPLATE_NOT_FOUND);
		}

		return userDto;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		UserEntity userEntity = new UserEntity();

		if (cacheOperation.isRedisConnectionClose() == false) {
			userEntity = this.authRepository.findByEmailContainingIgnoreCase(email);
			ErrorLoggerEntity entity = new ErrorLoggerEntity();
			entity.setMessage(ErrorMessageCode.REDIS_CONNECTION_FAILED);
			Date date = new Date(System.currentTimeMillis());
			// Timestamp t=new Timestamp(d)
			entity.setCreatedAt(date);
			this.loggerRepository.save(entity);

			return new org.springframework.security.core.userdetails.User(userEntity.getEmail(),
					userEntity.getPassword(), getAuthority(userEntity));

		}

		if (!cacheOperation.isKeyExist(email, email)) {

			userEntity = this.authRepository.findByEmailContainingIgnoreCase(email);
			cacheOperation.addInCache(email, email, userEntity.toString());
		} else {
			try {

				String jsonString = (String) cacheOperation.getFromCache(email, email);

				Map<String, Object> map = objectMapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {
				});
				userEntity.setPassword((String) map.get("password"));
				userEntity.setEmail((String) map.get("email"));
				userEntity.setId(((Integer) map.get("id")).longValue());

				if (userEntity.getEmail().isEmpty()) {
					throw new ResourceNotFoundException(ErrorMessageCode.USER_PASSWORD_NOT_FOUND);
				}

			} catch (Exception e) {
				throw new ResourceNotFoundException(ErrorMessageCode.ENTER_VALID_INFORMATION);
			}
		}
		return new org.springframework.security.core.userdetails.User(userEntity.getEmail(), userEntity.getPassword(),
				getAuthority(userEntity));
	}

	// for compare password
	@Override
	public Boolean comparePassword(String password, String hashPassword) {

		return passwordEncoder.matches(password, hashPassword);

	}

	private ArrayList<SimpleGrantedAuthority> getAuthority(UserEntity user) {
		ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();

		if ((user.getId() + "permission") != null) {
			ArrayList<SimpleGrantedAuthority> authorities1 = new ArrayList<>();

			// ArrayList<String> permissions =
			// this.rolePermissionInterface.getPermissionByUserId(user.getId());

			// ArrayList<String> permissions = this.getPermissionByUser(user.getId());

			List<IUserPermissionDto> permissions = this.rolePermissionInterface.getPermissionsByUserId(user.getId());

			permissions.forEach(e -> {
				authorities1.add(new SimpleGrantedAuthority("ROLE_" + e.getpermissionName()));

			});

			authorities = authorities1;

		}
		return authorities;

	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<String> getPermissionByUser(Long userId) {

		ArrayList<String> list;
		LOG.info("REDIS Connection "+cacheOperation.isRedisConnectionClose());
		if (!cacheOperation.isRedisConnectionClose()) {
			list = this.rolePermissionInterface.getPermissionByUserId(userId);
			return list;
		}

		if (!cacheOperation.isKeyExist(userId + "permission", userId + "permission")) {

			list = this.rolePermissionInterface.getPermissionByUserId(userId);
			this.cacheOperation.addInCache(userId + "permission", userId + "permission", list);

		} else {
			list = (ArrayList<String>) cacheOperation.getFromCache(userId + "permission", userId + "permission");

		}

		return list;
	}

	@Override
	public ArrayList<String> getUserPermission(Long userId) throws IOException {
		ArrayList<String> permissions;
//		permissions = this.rolePermissionInterface.getPermissionByUserId(userId);
		permissions = this.getPermissionByUser(userId);
		return permissions;

	}

	@Override
	public Boolean updateUserWithPassword(ForgotPasswordConfirmDto payload) throws Exception {
		UserEntity userEntity = this.authRepository.findByEmailContainingIgnoreCase(payload.getEmail());

		if (null == userEntity) {
			throw new ResourceNotFoundException(ErrorMessageCode.INVALID_EMAIL);
		}

		OtpEntity otpEntity = this.otpRepository.findByOtp(payload.getOtp());
		Date date = new Date();
		Timestamp ts = new Timestamp(date.getTime());

		if (null == otpEntity) {
			throw new ResourceNotFoundException(ErrorMessageCode.INVALID_OTP);
		} else {
			if (!otpEntity.getEmail().equals(payload.getEmail()) && ts.compareTo(otpEntity.getExpireAt()) == -1) {
				throw new ResourceNotFoundException(ErrorMessageCode.INVALID_OTP);
			}
		}

		userEntity.setPassword(passwordEncoder.encode(payload.getPassword()));
		this.authRepository.save(userEntity);
		this.otpRepository.delete(otpEntity);
		return true;

	}

	@Override
	public void generateOtpAndSendEmail(OTPDto otpDto, Long userId, String emailTemplate) throws Exception {

		UserEntity userEntity = this.authRepository.findByEmailIgnoreCase(otpDto.getEmail());

		if (userEntity == null) {
			throw new ResourceNotFoundException(ErrorMessageCode.USER_NOT_PRESENT);
		}

		final int otp = emailInterface.generateOTP();

		MailTemplate mailtemplate = mailRepository.findBytemplatename(Constant.OTP_TEMPLATE_NAME);

		String otp1 = Integer.toString(otp);

		String template = mailtemplate.getMailtemp();
		String replaceString = template.replace("otp-alchemy", otp1);// replaces all occurrences of a to e

		final String url = replaceString;
		Calendar calender = Calendar.getInstance();
		calender.add(Calendar.MINUTE, 5);

		this.otpInterface.saveOtp(otpDto.getEmail(), otp1, userId, calender.getTime());

		LOG.info("AuthServiceImpl >> generateOtpAndSendEmail() >>  OtpSend >>  " + otpDto.getEmail());
		this.emailInterface.sendSimpleMessage(otpDto.getEmail(), "Reset your GPL password", url);
		LOG.info("AuthServiceImpl >> generateOtpAndSendEmail() >>  OtpSend >>  " + otpDto.getEmail());

	}

	@Override
	public void saveGplEmployeeDetail(UserEntity userEntity) throws HttpClientErrorException, Exception {
		MyResponse myResponse = gplApiIntegrationServiceImpl.gplApiResponseDetail(userEntity.getEmail());

		if (myResponse != null) {

			for (int i = 0; i < myResponse.getResp_data().size(); i++) {
				GplFunctionEntity functionName = null;
				GplFunctionEntity function = null;
				GplApiResponceEntity gplApiResponceEntity = myResponse.getResp_data().get(i);

				if (userEntity != null) {
					function = this.functionRepository.findByNameIgnoreCase(gplApiResponceEntity.getFunctionName());

					if (function == null) {

						function = new GplFunctionEntity();
						function.setName(gplApiResponceEntity.getFunctionName());
						functionName = functionRepository.save(function);
					}

					LevelEntity levelEntity = this.levelRepository
							.findByLevelNameIgnoreCase(gplApiResponceEntity.getLevel());

					if (levelEntity == null) {
						levelEntity = new LevelEntity();
						levelEntity.setLevelName(gplApiResponceEntity.getLevel());
						levelEntity.setDescription(gplApiResponceEntity.getLevel());
						levelRepository.save(levelEntity);
					}
					GplDepartmentEntity department = null;
					if (function != null) {
						department = this.departmentRepository.findByNameIgnoreCaseAndGplFunctionIdAndIsActiveTrue(
								gplApiResponceEntity.getDepartmentDepartmentName(), function);
					}
//					else {
//						department = this.departmentRepository.findByNameIgnoreCaseAndGplFunctionIdAndIsActiveTrue(
//								gplApiResponceEntity.getDepartmentDepartmentName(), functionName);
//					}
					if (department == null) {
						department = new GplDepartmentEntity();
						department.setName(gplApiResponceEntity.getDepartmentDepartmentName());
						department.setGplFunctionId(function);
						departmentRepository.save(department);
					}

					userEntity.setFunctionId(function);
					userEntity.setLevelId(levelEntity);
					userEntity.setName(gplApiResponceEntity.getFirstName() + " " + gplApiResponceEntity.getLastName());
					userEntity.setDepartmentId(department);
					userEntity.setEmployeeId(gplApiResponceEntity.getEmployeeId());
					userEntity.setEmployeeGrade(gplApiResponceEntity.getSalaryGradeSalaryGradeName());

//					String region = gplApiResponceEntity.getEmpJobInfoTCustomVchar13().split("-")[1];
//					userEntity.setRegion(region);
//					 userEntity.setProject(gplApiResponceEntity.getEmpJobInfoTCustomVchar15());
					userEntity.setPhoneNumber(gplApiResponceEntity.getCellPhoneInformationPhoneNumber());
					userEntity.setRegion(gplApiResponceEntity.getSubBusinessUnitName());
					userEntity.setPositionTitle(gplApiResponceEntity.getJobTitle());
					userEntity.setZone(gplApiResponceEntity.getSubBusinessUnitName());

					userRepository.save(userEntity);
				}

			}
		}
//		else {
//
//			throw new ResourceNotFoundException("Invalid godrej user");
//		}
	}

	@Override
	public UserEntity saveNewEnrollEmployee(String email) throws HttpClientErrorException, Exception {
		MyResponse myResponse = gplApiIntegrationServiceImpl.gplApiResponseDetail(email);
		UserEntity userEntity = new UserEntity();

		if (myResponse != null) {

			for (int i = 0; i < myResponse.getResp_data().size(); i++) {
				GplFunctionEntity functionName = null;
				GplFunctionEntity function = null;
				GplApiResponceEntity gplApiResponceEntity = myResponse.getResp_data().get(i);

//				if (userEntity != null) {
				function = this.functionRepository.findByNameIgnoreCase(gplApiResponceEntity.getFunctionName());

				if (function == null) {

					function = new GplFunctionEntity();
					function.setName(gplApiResponceEntity.getFunctionName());
					functionName = functionRepository.save(function);
				}

				LevelEntity levelEntity = this.levelRepository
						.findByLevelNameIgnoreCase(gplApiResponceEntity.getLevel());

				if (levelEntity == null) {
					levelEntity = new LevelEntity();
					levelEntity.setLevelName(gplApiResponceEntity.getLevel());
					levelEntity.setDescription(gplApiResponceEntity.getLevel());
					levelRepository.save(levelEntity);
				}

				GplDepartmentEntity department = null;
				if (function != null) {
					department = this.departmentRepository.findByNameIgnoreCaseAndGplFunctionIdAndIsActiveTrue(
							gplApiResponceEntity.getDepartmentDepartmentName(), function);
				}
				if (department == null) {
					department = new GplDepartmentEntity();
					department.setName(gplApiResponceEntity.getDepartmentDepartmentName());
					department.setGplFunctionId(function);
					departmentRepository.save(department);
				}
				userEntity.setEmail(email);
				userEntity.setFunctionId(function);
				userEntity.setLevelId(levelEntity);
				userEntity.setName(gplApiResponceEntity.getFirstName() + " " + gplApiResponceEntity.getLastName());
				userEntity.setDepartmentId(department);
				userEntity.setEmployeeId(gplApiResponceEntity.getEmployeeId());
				userEntity.setEmployeeGrade(gplApiResponceEntity.getSalaryGradeSalaryGradeName());
				userEntity.setPhoneNumber(gplApiResponceEntity.getCellPhoneInformationPhoneNumber());
				userEntity.setRegion(gplApiResponceEntity.getSubBusinessUnitName());
				userEntity.setPositionTitle(gplApiResponceEntity.getJobTitle());
				userEntity.setZone(gplApiResponceEntity.getSubBusinessUnitName());

				userRepository.save(userEntity);
//				}

			}
		}
		return userEntity;

	}
}
