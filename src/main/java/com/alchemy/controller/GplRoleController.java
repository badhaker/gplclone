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

import com.alchemy.dto.DeleteId;
import com.alchemy.dto.ErrorResponseDto;
import com.alchemy.dto.GplRoleDto;
import com.alchemy.dto.ListResponseDto;
import com.alchemy.dto.PaginationResponse;
import com.alchemy.dto.SuccessResponseDto;
import com.alchemy.iListDto.IListGplRoleById;
import com.alchemy.iListDto.IListGplRoleDto;
import com.alchemy.serviceInterface.GplRoleInterface;
import com.alchemy.utils.ApiUrls;
import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageKey;
import com.alchemy.utils.GlobalFunctions;
import com.alchemy.utils.SuccessMessageCode;
import com.alchemy.utils.SuccessMessageKey;

@RestController
@RequestMapping(ApiUrls.GPL_ROLE)
public class GplRoleController {

	@Autowired
	private GplRoleInterface gplRoleInterface;

	@PreAuthorize("hasRole('Gpl_Role_Add')")
	@PostMapping
	public ResponseEntity<?> addGplRole(@Valid @RequestBody GplRoleDto gplRoleDto,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {

		try {

			GplRoleDto gplRole = gplRoleInterface.addGplRole(gplRoleDto, userId);
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.GPL_ROLE_ADDED,
					SuccessMessageKey.GPL_ROLE_M0311101, gplRole), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.GPLROLE_E0311101),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('Gpl_Role_Update')")
	@PutMapping("/{id}")
	public ResponseEntity<?> updateGplRole(@PathVariable Long id, @Valid @RequestBody GplRoleDto gplRoleDto,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {
		try {

			GplRoleDto gplRole = gplRoleInterface.updateGplRole(gplRoleDto, id, userId);
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.GPL_ROLE_UPDATED,
					SuccessMessageKey.GPL_ROLE_M0311102, gplRole), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.GPLROLE_E0311101),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('Gpl_Role_Delete')")
	@DeleteMapping()
	public ResponseEntity<?> deleteGplRole(@RequestBody DeleteId ids,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {
		try {
			gplRoleInterface.deleteGplRole(ids, userId);

			return new ResponseEntity<>(
					new SuccessResponseDto(SuccessMessageCode.GPL_ROLE_DELETED, SuccessMessageKey.GPL_ROLE_M0311103),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.GPLROLE_E0311101),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('Gpl_Role_List')")
	@GetMapping()
	public ResponseEntity<?> getAllGplRole(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "", value = Constant.PAGENUMBER) String pageNo,
			@RequestParam(defaultValue = "", value = Constant.PAGESIZE) String pageSize) {
		Page<IListGplRoleDto> IListGplRoleDto = gplRoleInterface.getAllGplRole(search, pageNo, pageSize);

		PaginationResponse paginationResponse = new PaginationResponse();
		paginationResponse.setPageNumber(IListGplRoleDto.getNumber() + 1);
		paginationResponse.setPageSize(IListGplRoleDto.getSize());
		paginationResponse.setTotal(IListGplRoleDto.getTotalElements());

		return new ResponseEntity<>(new ListResponseDto(IListGplRoleDto.getContent(), paginationResponse),
				HttpStatus.OK);

	}

	@PreAuthorize("hasRole('Gpl_Role_List')")
	@GetMapping("/{id}")
	public ResponseEntity<?> getByDepartmentId(@PathVariable("id") Long id) {

		try {

			List<IListGplRoleById> iListGplRoleById = gplRoleInterface.getByDepartmentId(id);

			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.GPL_ROLE_GET,
					SuccessMessageKey.GPL_ROLE_M0311104, iListGplRoleById), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.GPLROLE_E0311104),
					HttpStatus.BAD_REQUEST);

		}

	}

}
