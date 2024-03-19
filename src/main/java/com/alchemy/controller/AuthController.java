package com.alchemy.controller;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.http.conn.HttpHostConnectException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.alchemy.dto.AuthResponseDto;
import com.alchemy.dto.ErrorResponseDto;
import com.alchemy.dto.ForgotPasswordConfirmDto;
import com.alchemy.dto.JwtRequest;
import com.alchemy.dto.OTPDto;
import com.alchemy.dto.OnboardDto;
import com.alchemy.dto.SuccessResponseDto;
import com.alchemy.dto.ThirdParyApiLoginCredentialsDto;
import com.alchemy.dto.UserDto;
import com.alchemy.entities.LoggerEntity;
import com.alchemy.entities.MyResponse;
import com.alchemy.entities.SystemConfiguration;
import com.alchemy.entities.ThirdPartyLoginCredentials;
import com.alchemy.entities.UserEntity;
import com.alchemy.exceptionHandling.ResourceNotFoundException;
import com.alchemy.repositories.AuthRepository;
import com.alchemy.repositories.LoggerRepository;
import com.alchemy.repositories.RolePermissionRepository;
import com.alchemy.repositories.SystemConfigurationRepository;
import com.alchemy.repositories.ThirdPartyCredentialsRepo;
import com.alchemy.serviceImpl.AuthServiceImpl;
import com.alchemy.serviceImpl.UserTrackServiceImpl;
import com.alchemy.serviceInterface.AuthInterface;
import com.alchemy.serviceInterface.GplApiIntegrationInterface;
import com.alchemy.serviceInterface.JwtTokenUtilInterface;
import com.alchemy.serviceInterface.UserRoleInterface;
import com.alchemy.utils.ApiUrls;
import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.ErrorMessageKey;
import com.alchemy.utils.GlobalFunctions;
import com.alchemy.utils.SuccessMessageCode;
import com.alchemy.utils.SuccessMessageKey;

@RestController
@RequestMapping(ApiUrls.AUTH)
public class AuthController {

	private static final String OTP_TEMPLATE_NAME = "otpTemplate";

	@Autowired
	private AuthInterface authInterface;

	@Autowired
	private AuthRepository authRepository;

	@Autowired
	private JwtTokenUtilInterface jwtTokenUtilInterface;

	@Autowired
	private LoggerRepository loggerRepository;

	@Autowired
	private UserRoleInterface userRoleInterface;

	@Autowired
	private GplApiIntegrationInterface gplApiIntegrationInterface;

	@Autowired
	private AuthServiceImpl authServiceImpl;

	@Autowired
	private SystemConfigurationRepository systemConfigurationRepository;

	@Autowired
	private RolePermissionRepository rolePermissions;
	
	private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

	@PostMapping(ApiUrls.REGISTER)
	public ResponseEntity<?> register(@Valid @RequestBody UserDto userDto) throws Exception {

		try {
			authInterface.registerUser(userDto);
			return new ResponseEntity<>(
					new SuccessResponseDto(SuccessMessageCode.USER_REGISTER, SuccessMessageKey.USER_M031101, userDto),
					HttpStatus.OK);
		} catch (Exception e) {
			if (e.getCause() instanceof ConstraintViolationException) {
				return new ResponseEntity<>(
						new ErrorResponseDto(ErrorMessageCode.ALREADY_REGISTER, ErrorMessageKey.USER_E031102),
						HttpStatus.BAD_REQUEST);
			} else if (e.getCause() instanceof UnknownHostException) {
				return new ResponseEntity<>(
						new ErrorResponseDto(ErrorMessageCode.INVALID_EMAIL, ErrorMessageKey.USER_E031102),
						HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.USER_E031102),
						HttpStatus.BAD_REQUEST);
			}
		}

	}

//Todo
// SSO login token will be store in auth logger
	@PostMapping(ApiUrls.LOGIN)
	public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody JwtRequest createAuthenticationToken)
			throws Exception {

		try {
			UserEntity user = authRepository.findByEmailIgnoreCaseAndIsActiveTrue(createAuthenticationToken.getEmail());

			if (user == null) {
				throw new ResourceNotFoundException(ErrorMessageCode.INVALID_INFORMATION);
			}
			if (user.getIsAdmin().equals(false) && user.getPassword() == null) {
				throw new ResourceNotFoundException(ErrorMessageCode.USER_NOT_ADMIN);
			}

			if (this.authInterface.comparePassword(createAuthenticationToken.getPassword(), user.getPassword())) {

				final UserDetails userDetails = this.authInterface
						.loadUserByUsername(createAuthenticationToken.getEmail());

				final String token = this.jwtTokenUtilInterface.generateToken(userDetails);

				ArrayList<String> permissions = authInterface.getPermissionByUser(user.getId());

				// List<IUserPermissionDto>
				// permissions=this.rolePermissions.getPermissionById(user.getId());

				List<String> roles = this.userRoleInterface.getRoleByUserId(user.getId());

				String refreshToken = jwtTokenUtilInterface.refreshToken(token, userDetails);

				LoggerEntity loggerEntity = loggerRepository.getLog(user.getId());

				if (loggerEntity == null) {
					loggerEntity = new LoggerEntity();
				}
				Calendar cal = Calendar.getInstance();
				Date date = cal.getTime();
				loggerEntity.setLoginAt(date);
				cal.add(Calendar.HOUR_OF_DAY, 20);
				// loggerEntity.setGplToken(token);
				// loggerEntity.setExpiredAt(cal.getTime());
				loggerEntity.setUserId(user);

				try {
					authInterface.saveGplEmployeeDetail(user);
				} catch (Exception e) {

				}

				loggerRepository.save(loggerEntity);

				SystemConfiguration configuration = systemConfigurationRepository.findByKey(Constant.CAREER_ASPIRATION);

//				return new ResponseEntity<>(
//						new SuccessResponseDto(SuccessMessageCode.SUCCESS, SuccessMessageKey.USER_M031102,
//								new AuthResponseDto(token, refreshToken, permissions, user.getEmail(), user.getName(),
//										user.getId(), user.getCareerAspiration(), configuration.getFlag(), roles)),
//						HttpStatus.OK);

				return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.SUCCESS,
						SuccessMessageKey.USER_M031102, new AuthResponseDto(token, refreshToken, permissions,
								user.getEmail(), user.getName(), user.getId(), roles, configuration.getFlag())),
						HttpStatus.OK);

			} else {
				return new ResponseEntity<>(new ErrorResponseDto(ErrorMessageCode.INVALID_INFORMATION,
						ErrorMessageKey.GLOBAL_EXCEPTION_E032108), HttpStatus.BAD_REQUEST);

			}

		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.GLOBAL_EXCEPTION_E032108),
					HttpStatus.BAD_REQUEST);

		}
	}

	@PostMapping(ApiUrls.REFRESH_TOKEN)
	public ResponseEntity<?> refreshAndGetAuthenticationToken(@RequestParam(defaultValue = "") String refreshToken)
			throws Exception {

		String email = jwtTokenUtilInterface.getUsernameFromToken(refreshToken);
		UserEntity userEntity = authRepository.findByEmailIgnoreCaseAndIsActiveTrue(email);
		if (userEntity == null) {
			return new ResponseEntity<>(
					new ErrorResponseDto(ErrorMessageCode.USER_NOT_PRESENT, ErrorMessageKey.USER_E031101),
					HttpStatus.UNAUTHORIZED);
		}
		final UserDetails userDetails = authInterface.loadUserByUsername(email);
		if (jwtTokenUtilInterface.canTokenBeRefreshed(refreshToken)
				&& jwtTokenUtilInterface.validateToken(refreshToken, userDetails)
				&& jwtTokenUtilInterface.getTokenType(refreshToken).equalsIgnoreCase(Constant.TOKEN_TYPE_REFRESH)) {

			String newAccessToken = jwtTokenUtilInterface.generateToken(userDetails);

			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.ACCESS_TOKEN_GENERATED,
					SuccessMessageKey.USER_M031104, newAccessToken), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(
					new ErrorResponseDto(ErrorMessageCode.INVALID_TOKEN, ErrorMessageKey.USER_E031203),
					HttpStatus.UNAUTHORIZED);

		}

	}

	@Transactional
	@PostMapping(ApiUrls.FORGOT_PASSWORD)
	public ResponseEntity<?> forgotPassword(@Valid @RequestBody OTPDto otpDto, HttpServletRequest request)
			throws Exception {

		try {

			UserEntity userEntity = this.authRepository.findByEmailIgnoreCase(otpDto.getEmail());

			if (userEntity == null) {
				return new ResponseEntity<>(
						new ErrorResponseDto(ErrorMessageCode.USER_NOT_PRESENT, ErrorMessageKey.USER_E031101),
						HttpStatus.BAD_REQUEST);
			}
			if (userEntity.getIsAdmin().equals(false)) {
				throw new ResourceNotFoundException(ErrorMessageCode.USER_NOT_ADMIN);
			}
			authInterface.generateOtpAndSendEmail(otpDto, userEntity.getId(), OTP_TEMPLATE_NAME);

			return new ResponseEntity<>(
					new SuccessResponseDto(SuccessMessageCode.OTP_SENT, SuccessMessageKey.USER_M031105, otpDto),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.USER_E031101),
					HttpStatus.BAD_REQUEST);

		}
	}

	@PutMapping(ApiUrls.FORGOT_PASSWORD_CONFIRM)
	public ResponseEntity<?> createForgotPasswordConfirm(@Valid @RequestBody ForgotPasswordConfirmDto payload)
			throws Exception {

		try {
			this.authInterface.updateUserWithPassword(payload);

			return new ResponseEntity<>(
					new SuccessResponseDto(SuccessMessageCode.PASSWORD_UPDATED, SuccessMessageKey.USER_M031106),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.USER_E031101),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('GPL_THIRD_PARTY_CREDENTIALS_Add')")
	@PostMapping(ApiUrls.GPL_THIRD_PARTY_CREDENTIALS)
	public ResponseEntity<?> addThirdPartyLoginCredentials(@Valid @RequestBody ThirdParyApiLoginCredentialsDto dto) {
		try {
			gplApiIntegrationInterface.addThirdParyApiLoginCredentials(dto);
			return new ResponseEntity<>(new SuccessResponseDto("Credentials added.", SuccessMessageKey.USER_M031106),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.USER_E031101),
					HttpStatus.BAD_REQUEST);
		}

	}
	
	@Autowired
	ThirdPartyCredentialsRepo thirdPartyCredentialsRepo;
	
	@PreAuthorize("hasRole('GPL_THIRD_PARTY_CREDENTIALS_LIST')")
	@GetMapping(ApiUrls.GPL_THIRD_PARTY_CREDENTIALS)
	public List<ThirdPartyLoginCredentials> getCredentials(){
		try {
			return thirdPartyCredentialsRepo.findAll();
		}
		catch (Exception e) {
			return null;
		}
	}

	@GetMapping(ApiUrls.GPL_EMPLOYEE_INFO)
	public ResponseEntity<?> getResponceFromGpl(@RequestAttribute("X-user-email") String tokenEmail)
			throws Exception, HttpClientErrorException {

		try {
			LOG.info("Entering into ServiceIMPL");
			MyResponse commonResponce = gplApiIntegrationInterface.gplApiResponseDetail(tokenEmail);
			if (commonResponce != null) {
				return new ResponseEntity<>(new SuccessResponseDto("GPL Response ", SuccessMessageKey.USER_M031106,
						commonResponce.getResp_data()), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new ErrorResponseDto("Responce can not get.", ErrorMessageKey.USER_E031101),
						HttpStatus.BAD_REQUEST);
			}
		} catch (SocketTimeoutException | UnknownHostException e) {
			LOG.info("SocketTimeoutException");
			// Catch more specific exceptions
			return new ResponseEntity<>(
					new ErrorResponseDto(ErrorMessageCode.GPL_THIRD_PARTY_API_DOWN, ErrorMessageKey.USER_E031101),
					HttpStatus.BAD_REQUEST);
		} catch (HttpHostConnectException e) {
			LOG.info("HttpHostConnectException");
			return new ResponseEntity<>(
					new ErrorResponseDto(ErrorMessageCode.GPL_THIRD_PARTY_API_DOWN, ErrorMessageKey.USER_E031101),
					HttpStatus.BAD_REQUEST);
		} catch (HttpClientErrorException e) {

			return new ResponseEntity<>(
					new ErrorResponseDto(ErrorMessageCode.GPL_THIRD_PARTY_API_DOWN, ErrorMessageKey.USER_E031101),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			LOG.info("Exception");
			e.printStackTrace();
			return new ResponseEntity<>(
					new ErrorResponseDto(e.getMessage(), ErrorMessageKey.USER_E031101),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping(ApiUrls.ONBOARD)
	public ResponseEntity<?> onboard(@RequestBody OnboardDto dashboardDto) {
		try {
			authServiceImpl.onboardUser(dashboardDto);
			return new ResponseEntity<>(
					new SuccessResponseDto(SuccessMessageCode.ONBOARDING_SUCCESSFUL, SuccessMessageKey.USER_M031107),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.USER_E031207),
					HttpStatus.BAD_REQUEST);
		}
	}
}
