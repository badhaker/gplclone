package com.alchemy.controller;

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
import com.alchemy.dto.GplDepartmentDto;
import com.alchemy.dto.ListResponseDto;
import com.alchemy.dto.PaginationResponse;
import com.alchemy.dto.SuccessResponseDto;
import com.alchemy.iListDto.IListGplDepartmentDto;
import com.alchemy.serviceInterface.GplDepartmentInterface;
import com.alchemy.utils.ApiUrls;
import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageKey;
import com.alchemy.utils.GlobalFunctions;
import com.alchemy.utils.SuccessMessageCode;
import com.alchemy.utils.SuccessMessageKey;

@RestController
@RequestMapping(ApiUrls.GPL_DEPARTMENT)
public class GplDepartmentController {

	@Autowired
	GplDepartmentInterface gplDepartmentInterface;

	@PreAuthorize("hasRole('Gpl_Department_Add')")
	@PostMapping()
	public ResponseEntity<?> addGplDepartment(@Valid @RequestBody GplDepartmentDto gplDepartmentDto,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {
		try {

			GplDepartmentDto gplDepartment = gplDepartmentInterface.addGplDepartment(gplDepartmentDto, userId);
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.GPL_DEPARTMENT_ADDED,
					SuccessMessageKey.GPL_DEPARTMENT_M0311001, gplDepartment), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.GPLDEPARTMENT_E0311001),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('Gpl_Department_Update')")
	@PutMapping("/{id}")
	public ResponseEntity<?> updateGplDepartment(@Valid @RequestBody GplDepartmentDto gplDepartmentDto,
			@PathVariable Long id, @RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {
		try {

			GplDepartmentDto gplDepartment = gplDepartmentInterface.updateGplDepartment(gplDepartmentDto, id, userId);
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.GPL_DEPARTMENT_UPDATED,
					SuccessMessageKey.GPL_DEPARTMENT_M0311002, gplDepartment), HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.GPLDEPARTMENT_E0311001),
					HttpStatus.BAD_REQUEST);

		}

	}

	@PreAuthorize("hasRole('Gpl_Department_Delete')")
	@DeleteMapping()
	public ResponseEntity<?> deleteGplDepartment(@Valid @RequestBody DeleteId ids,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {
		try {

			gplDepartmentInterface.deleteGplDepartment(ids, userId);
			return new ResponseEntity<>(
					new SuccessResponseDto(SuccessMessageCode.GPL_DEPARTMENT_DELETED,
							SuccessMessageKey.GPL_FUNCTION_M031903, SuccessMessageKey.GPL_DEPARTMENT_M0311003),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.GPLDEPARTMENT_E0311001),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('Gpl_Department_List')")
	@GetMapping
	public ResponseEntity<?> getAllGplDepartment(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "", value = Constant.PAGENUMBER) String pageNo,
			@RequestParam(defaultValue = "", value = Constant.PAGESIZE) String pageSize,
			@RequestParam(defaultValue = "") String functionId) {
		Page<IListGplDepartmentDto> iListGplDepartment = gplDepartmentInterface.getAllGplDepartment(search, pageNo,
				pageSize, functionId);

		PaginationResponse paginationResponse = new PaginationResponse();
		paginationResponse.setPageNumber(iListGplDepartment.getNumber() + 1);
		paginationResponse.setPageSize(iListGplDepartment.getSize());
		paginationResponse.setTotal(iListGplDepartment.getTotalElements());

		return new ResponseEntity<>(new ListResponseDto(iListGplDepartment.getContent(), paginationResponse),
				HttpStatus.OK);

	}

}
