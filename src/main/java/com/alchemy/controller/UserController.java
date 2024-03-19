package com.alchemy.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alchemy.dto.DeleteId;
import com.alchemy.dto.ErrorResponseDto;
import com.alchemy.dto.ListResponseDto;
import com.alchemy.dto.PaginationResponse;
import com.alchemy.dto.SuccessResponseDto;
import com.alchemy.dto.UserUpdateDto;
import com.alchemy.iListDto.IListCareerAspiration;
import com.alchemy.iListDto.IListUserDto;
import com.alchemy.serviceInterface.AuthInterface;
import com.alchemy.serviceInterface.UserInterface;
import com.alchemy.utils.ApiUrls;
import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.ErrorMessageKey;
import com.alchemy.utils.GlobalFunctions;
import com.alchemy.utils.SuccessMessageCode;
import com.alchemy.utils.SuccessMessageKey;

@RestController
@RequestMapping(ApiUrls.USER)
public class UserController {

	@Autowired
	private UserInterface userInterface;

	@Autowired
	private AuthInterface authInterface;

	@PreAuthorize("hasRole('User_Update')")
	@PutMapping("/{id}")
	public ResponseEntity<?> editUser(@PathVariable("id") Long userId, @Valid @RequestBody UserUpdateDto userDto) {
		try {
			userInterface.editUser(userId, userDto);

			return new ResponseEntity<>(
					new SuccessResponseDto(SuccessMessageCode.USER_UPDATED, SuccessMessageKey.USER_M031102, userDto),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.USER_E031101),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('User_Delete')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") Long userId) {
		try {
			userInterface.deleteUser(userId);

			return new ResponseEntity<>(
					new SuccessResponseDto(SuccessMessageCode.USER_DELETED, SuccessMessageKey.ROLE_M031203),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.USER_E031101),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('User_List')")
	@GetMapping()
	public ResponseEntity<?> getAllUser(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "", value = Constant.PAGENUMBER) String pageNo,
			@RequestParam(defaultValue = "", value = Constant.PAGESIZE) String pageSize) throws ParseException {
		try {

			if (!pageNo.isBlank() || !pageSize.isBlank() || !search.isBlank()) {
				String pageNumber = pageNo.isBlank() ? Constant.DEFAULT_PAGENUMBER : pageNo;
				String page = pageSize.isBlank() ? Constant.DEFAULT_PAGESIZE : pageSize;
				Page<IListUserDto> users = this.userInterface.getAllUsers(search, pageNumber, page);

				PaginationResponse paginationResponse = new PaginationResponse();

				paginationResponse.setPageSize(users.getSize());
				paginationResponse.setTotal(users.getTotalElements());
				paginationResponse.setPageNumber(users.getNumber() + 1);

				return new ResponseEntity<>(new ListResponseDto(users.getContent(), paginationResponse), HttpStatus.OK);
			} else {

				List<IListUserDto> userDtos = this.userInterface.findAllUsers();
				return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.UESR_FETCHCHED_SUCCESSFULLY,
						SuccessMessageKey.USER_M031104, userDtos), HttpStatus.OK);
			}
		} catch (Exception e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.USER_E031101),
					HttpStatus.BAD_REQUEST);
		}

	}

//	@PreAuthorize("hasRole('User_CareerAspiration_Update')")
//	@PatchMapping("/{id}/career-aspiration")
//	public ResponseEntity<?> updateCareerAspiration(@PathVariable("id") Long userId,
//			@RequestBody CareerAspirationDto careerAspirationDto) {
//		try {
//			userInterface.upadeCareerAspiration(userId, careerAspirationDto);
//			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.USER_UPDATED,
//					SuccessMessageKey.USER_M031102, careerAspirationDto), HttpStatus.OK);
//		} catch (Exception e) {
//			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.USER_E031101),
//					HttpStatus.BAD_REQUEST);
//		}
//	}

	@PreAuthorize("hasRole('EXPORT_USERS')")
	@GetMapping("/export")
	public void exportToExcel(HttpServletResponse response) throws IOException {

		response.setContentType("text/csv");
		response.setHeader("Content-Disposition", "attachment; filename=\"carrer asipartion.csv\"");
		userInterface.exportUserToExcel(response);
	}

	@PreAuthorize("hasRole('Career_Aspiration')")
	@GetMapping("/career-aspiration")
	public ResponseEntity<?> getAllCareerAspiration(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = Constant.DEFAULT_PAGENUMBER, value = Constant.PAGENUMBER) String pageNo,
			@RequestParam(defaultValue = Constant.DEFAULT_PAGESIZE, value = Constant.PAGESIZE) String pageSize)
			throws ParseException {

		Page<IListCareerAspiration> users = this.userInterface.getAllCareerAspiration(search.trim(), pageNo, pageSize);

		PaginationResponse paginationResponse = new PaginationResponse();

		paginationResponse.setPageSize(users.getSize());
		paginationResponse.setTotal(users.getTotalElements());
		paginationResponse.setPageNumber(users.getNumber() + 1);

		return new ResponseEntity<>(new ListResponseDto(users.getContent(), paginationResponse), HttpStatus.OK);

	}

	@DeleteMapping
	public ResponseEntity<?> deleteMultipleUsersById(@RequestBody DeleteId id,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {

		try {
			userInterface.deleteMultipleUsersById(id, userId);

			return new ResponseEntity<>(
					new SuccessResponseDto(SuccessMessageCode.USER_DELETED, SuccessMessageKey.USER_M031103),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(
					new ErrorResponseDto(ErrorMessageCode.USER_NOT_FOUND, ErrorMessageKey.USER_E031101),
					HttpStatus.BAD_REQUEST);
		}

	}

//	@PostMapping(ApiUrls.ADD_USER)
//	public ResponseEntity<?> addUserOnlyAdminOrMentor(@Valid @RequestBody UserDto userDto)
//			throws DataIntegrityViolationException, Exception {
//		try {
//			this.authInterface.registerUser(userDto);
//
//			return new ResponseEntity<>(
//			new SuccessResponseDto(SuccessMessageCode.USER_ADDED, SuccessMessageKey.USER_M031107, userDto),
//			HttpStatus.CREATED);
//
//		} catch (Exception e) {
//			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.USER_E031101),
//					HttpStatus.BAD_REQUEST);
//
//		} catch (ConstraintViolationException e) {
//			return new ResponseEntity<>(
//					new ErrorResponseDto(ErrorMessageConstant.INVALID_INFORMATION, ErrorKeyConstant.USER_E031103),
//					HttpStatus.BAD_REQUEST);
//		}
//
//		catch (DataIntegrityViolationException e) {
//			return new ResponseEntity<>(new ErrorResponseDto(ErrorMessageConstant.ACCESS_REVOKED ,
//					ErrorKeyConstant.USER_E031103), HttpStatus.BAD_REQUEST);
//		}
//	}
//	}
}
