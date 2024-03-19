package com.alchemy.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alchemy.dto.ErrorResponseDto;
import com.alchemy.dto.ListResponseDto;
import com.alchemy.dto.PaginationResponse;
import com.alchemy.dto.RoleDto;
import com.alchemy.dto.SuccessResponseDto;
import com.alchemy.exceptionHandling.ResourceNotFoundException;
import com.alchemy.iListDto.IListRole;
import com.alchemy.iListDto.IListRolePermissions;
import com.alchemy.serviceInterface.RoleInterface;
import com.alchemy.utils.ApiUrls;
import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageKey;
import com.alchemy.utils.GlobalFunctions;
import com.alchemy.utils.SuccessMessageCode;
import com.alchemy.utils.SuccessMessageKey;

@RestController
@RequestMapping(ApiUrls.ROLES)
public class RoleController {

	@Autowired
	private RoleInterface roleInterface;

	@PreAuthorize("hasRole('Role_Add')")
	@PostMapping
	public ResponseEntity<?> addRoles(@Valid @RequestBody RoleDto roleDto,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {

		try {
			RoleDto roleDto1 = this.roleInterface.addRole(roleDto, userId);

			return new ResponseEntity<>(
					new SuccessResponseDto(SuccessMessageCode.ROLE_ADDED, SuccessMessageKey.ROLE_M031204, roleDto1),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.ROLE_E031201),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('Role_Update')")
	@PutMapping("/{id}")
	public ResponseEntity<?> updateRole(@Valid @RequestBody RoleDto roleDto, @PathVariable Long id,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {
		try {
			RoleDto roleDto1 = this.roleInterface.updateRoles(roleDto, id, userId);

			return new ResponseEntity<>(
					new SuccessResponseDto(SuccessMessageCode.ROLE_UPDATED, SuccessMessageKey.ROLE_M031203, roleDto1),
					HttpStatus.OK);

		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.ROLE_E031201),
					HttpStatus.BAD_REQUEST);
		}
	}

	// @PreAuthorize("hasRole('Role_Delete')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteRoleById(@PathVariable("id") Long id,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {

		try {
			roleInterface.deleteRoleById(id, userId);

			return new ResponseEntity<>(
					new SuccessResponseDto(SuccessMessageCode.ROLE_DELETED, SuccessMessageKey.ROLE_M031202),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.ROLE_E031201),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('Role_List')")
	@GetMapping
	public ResponseEntity<?> getAllRoles(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = Constant.DEFAULT_PAGENUMBER, value = Constant.PAGENUMBER) String pageNo,
			@RequestParam(defaultValue = Constant.DEFAULT_PAGESIZE, value = Constant.PAGESIZE) String pageSize)
			throws Exception {
		Page<IListRole> page = this.roleInterface.getAllRoles(search, pageNo, pageSize);
		PaginationResponse paginationResponse = new PaginationResponse();

		paginationResponse.setPageSize(page.getSize());
		paginationResponse.setTotal(page.getTotalElements());
		paginationResponse.setPageNumber(page.getNumber() + 1);

		return new ResponseEntity<>(new ListResponseDto(page.getContent(), paginationResponse), HttpStatus.OK);

	}

	@PreAuthorize("hasRole('Role_Detalis')")
	@GetMapping(ApiUrls.GET_ALL)
	public ResponseEntity<?> getAllRoleByNameAsc() {
		List<IListRole> iListRoles = this.roleInterface.getAllRole();
		return new ResponseEntity<>(
				new SuccessResponseDto(SuccessMessageCode.SUCCESS, SuccessMessageKey.ROLE_M031201, iListRoles),
				HttpStatus.OK);
	}

	@PreAuthorize("hasRole('Role_Details')")
	@GetMapping("/{id}" + ApiUrls.PERMISSIONS)
	public ResponseEntity<?> getPermissionsByRoleId(@PathVariable("id") Long roleId) {
		try {
			List<IListRolePermissions> iListPermissionDtos = this.roleInterface.getPermissionsByRoleId(roleId);

			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.PERMISSION_FETCHED,
					SuccessMessageKey.ROLE_M031205, iListPermissionDtos), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.ROLE_E031201),
					HttpStatus.BAD_REQUEST);
		}
	}
}
