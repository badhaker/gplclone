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
import org.springframework.web.multipart.MultipartFile;

import com.alchemy.dto.DeleteId;
import com.alchemy.dto.ErrorResponseDto;
import com.alchemy.dto.ListResponseDto;
import com.alchemy.dto.PaginationResponse;
import com.alchemy.dto.PermissionDto;
import com.alchemy.dto.PermissionModuleList;
import com.alchemy.dto.SuccessResponseDto;
import com.alchemy.exceptionHandling.ResourceNotFoundException;
import com.alchemy.iListDto.IListPermissionDto;
import com.alchemy.iListDto.PermissionWithSFDetail;
import com.alchemy.serviceInterface.PermissionInterface;
import com.alchemy.utils.ApiUrls;
import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.ErrorMessageKey;
import com.alchemy.utils.GlobalFunctions;
import com.alchemy.utils.SuccessMessageCode;
import com.alchemy.utils.SuccessMessageKey;

@RestController
@RequestMapping(ApiUrls.PERMISSIONS)
public class PermissionController {

	@Autowired
	private PermissionInterface permissionInterface;

	@PreAuthorize("hasRole('Permission_Add')")
	@PostMapping()
	public ResponseEntity<?> addPermission(@Valid @RequestBody PermissionDto dto,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {
		try {
			this.permissionInterface.addPermission(dto, userId);

			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.PERMISSION_ADDED,
					SuccessMessageKey.PERMISSION_M031301, dto), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.PERMISSION_E031303),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('Permission_List')")
	@GetMapping()
	public ResponseEntity<?> getAllPermissions(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "", value = Constant.PAGENUMBER) String pageNo,
			@RequestParam(defaultValue = "", value = Constant.PAGESIZE) String pageSize) {
		try {
			if (!pageNo.isBlank() || !pageSize.isBlank() || !search.isBlank()) {

				String pageNumber = pageNo.isBlank() ? Constant.DEFAULT_PAGENUMBER : pageNo;
				String pages = pageSize.isBlank() ? Constant.DEFAULT_PAGESIZE : pageSize;

				Page<IListPermissionDto> list = this.permissionInterface.getAllPermissions(search, pageNumber, pages);
				PaginationResponse paginationResponse = new PaginationResponse();

				paginationResponse.setPageSize(list.getSize());
				paginationResponse.setTotal(list.getTotalElements());
				paginationResponse.setPageNumber(list.getNumber() + 1);
				return new ResponseEntity<>(new ListResponseDto(list.getContent(), paginationResponse), HttpStatus.OK);
			} else {

				List<IListPermissionDto> findAllList = permissionInterface.findAllList(IListPermissionDto.class);
				return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.SUCCESS,
						SuccessMessageKey.PERMISSION_M031304, findAllList), HttpStatus.OK);
			}

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.PERMISSION_E031301),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('Permission_Detail')")
	@GetMapping("{id}")
	public ResponseEntity<?> getPermissionById(@PathVariable("id") Long id) {
		try {

			PermissionDto entity = this.permissionInterface.getPermissionById(id);

			return new ResponseEntity<>(
					new SuccessResponseDto(SuccessMessageCode.SUCCESS, SuccessMessageKey.PERMISSION_M031304, entity),
					HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.PERMISSION_E031301),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('Permission_Update')")
	@PutMapping("{id}")
	public ResponseEntity<?> updatePermission(@PathVariable("id") Long id, @Valid @RequestBody PermissionDto dto,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) throws ResourceNotFoundException {
		try {

			PermissionDto permissionEntity = this.permissionInterface.updatePermission(dto, id, userId);
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.PERMISSION_UPDATED,
					SuccessMessageKey.PERMISSION_M031302, permissionEntity), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.PERMISSION_E031301),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('Permission_Delete')")
	@DeleteMapping("{id}")
	public ResponseEntity<?> deletePermission(@PathVariable(name = "id") Long id,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) throws ResourceNotFoundException {
		try {
			permissionInterface.deletePermission(id, userId);

			return ResponseEntity.ok(new SuccessResponseDto(SuccessMessageCode.PERMISSION_DELETED,
					SuccessMessageKey.PERMISSION_M031303, id));
		} catch (Exception e) {
			return ResponseEntity.ok(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.PERMISSION_E031301));
		}
	}

	@PreAuthorize("hasRole('Permission_List')")
	@GetMapping(ApiUrls.GET_ALL)
	public ResponseEntity<?> getAllPermissionsByActionName() {

		List<IListPermissionDto> iListPermissionDtos = this.permissionInterface.getAllPermissions();

		return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.SUCCESS,
				SuccessMessageKey.PERMISSION_M031304, iListPermissionDtos), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('Permission_List')")
	@GetMapping(ApiUrls.BY_MODULE)
	public ResponseEntity<?> listAllPermissions() {

		List<PermissionModuleList> list = this.permissionInterface.modulePermissionList();

		return new ResponseEntity<>(new SuccessResponseDto("Success", " Module permissions list", list), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('Permission_Details')")
	@GetMapping(ApiUrls.USER + "/{id}")
	public ResponseEntity<?> getUserPermissions(
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {
		List<String> list = permissionInterface.getUserPermissions(userId);

		return new ResponseEntity<>(new SuccessResponseDto("Success", SuccessMessageKey.PERMISSION_M031304, list),
				HttpStatus.OK);
	}

	@PreAuthorize("hasRole('Permission_Details')")
	@GetMapping(ApiUrls.USER_SF_DETAIL)
	public ResponseEntity<?> getUserPermissionsAndSf(
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {
		PermissionWithSFDetail list = permissionInterface.getUserPermissionsAndSFDetail(userId);

		return new ResponseEntity<>(new SuccessResponseDto("Success", SuccessMessageKey.PERMISSION_M031304, list),
				HttpStatus.OK);
	}

	@PreAuthorize("hasRole('UPLOAD_FILE')")
	@PostMapping(ApiUrls.UPLOAD)
	public ResponseEntity<?> uploadPermissionFile(MultipartFile file,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {
		try {
			if (file != null) {
				this.permissionInterface.uploadPermissions(file, userId);
				return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.PERMISSION_ADDED,
						SuccessMessageKey.PERMISSION_M031304), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(
						new SuccessResponseDto(ErrorMessageCode.FILE_NOT_FOUND, ErrorMessageKey.FILE_UPLOAD_E03701),
						HttpStatus.BAD_REQUEST);
			}

		}

		catch (Exception e) {
			return ResponseEntity.ok(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.FILE_UPLOAD_E03701));
		}

	}

	@DeleteMapping
	public ResponseEntity<?> deleteMultiplePermissionsById(@RequestBody DeleteId id,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {

		try {
			permissionInterface.deleteMultiplePermissionsById(id, userId);

			return new ResponseEntity<>(
					new SuccessResponseDto(SuccessMessageCode.PERMISSION_DELETED, SuccessMessageKey.PERMISSION_M031303),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(
					new ErrorResponseDto(ErrorMessageCode.PERMISSION_NOT_FOUND, ErrorMessageKey.PERMISSION_E031301),
					HttpStatus.BAD_REQUEST);
		}

	}
}
