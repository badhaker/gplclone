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
import com.alchemy.dto.DepartmentDto;
import com.alchemy.dto.ErrorResponseDto;
import com.alchemy.dto.ListResponseDto;
import com.alchemy.dto.PaginationResponse;
import com.alchemy.dto.SuccessResponseDto;
import com.alchemy.iListDto.IListDepartment;
import com.alchemy.repositories.DepartmentRepository;
import com.alchemy.serviceInterface.DepartmentInterface;
import com.alchemy.utils.ApiUrls;
import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.ErrorMessageKey;
import com.alchemy.utils.GlobalFunctions;
import com.alchemy.utils.SuccessMessageCode;
import com.alchemy.utils.SuccessMessageKey;

@RestController
@RequestMapping(ApiUrls.DEPARTMENT)
public class DepartmentController {

	@Autowired
	private DepartmentInterface deptIntf;
	
	@Autowired
	private DepartmentRepository repo;

	@PreAuthorize("hasRole('Department_Add')")
	@PostMapping
	public ResponseEntity<?> addDepartments(@Valid @RequestBody DepartmentDto deptDto,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {
		try {

			DepartmentDto deptDto1 = deptIntf.addDepartment(deptDto, userId);

			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.DEPARTMENT_ADDED,
					SuccessMessageKey.DEPARTMENT_M031601, deptDto1), HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.DEPARTMENT_E031602),
					HttpStatus.BAD_REQUEST);

		}
	}

	@PreAuthorize("hasRole('Department_Update')")
	@PutMapping("/{id}")
	public ResponseEntity<?> updateDepartment(@Valid @RequestBody DepartmentDto deptDto, @PathVariable Long id,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {
		try {
			DepartmentDto deptDto1 = this.deptIntf.updateDepartment(deptDto, id, userId);

			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.DEPARTMENT_UPDATED,

					SuccessMessageKey.DEPARTMENT_M031602, deptDto1), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.DEPARTMENT_E031602),

					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('Department_Delete')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteDepartmentById(@PathVariable Long id,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {

		try {
			deptIntf.deleteDepartmentById(id, userId);

			return new ResponseEntity<>(
					new SuccessResponseDto(SuccessMessageCode.DEPARTMENT_DELETED, SuccessMessageKey.DEPARTMENT_M031603),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(
					new ErrorResponseDto(ErrorMessageCode.DEPARTMENT_NOT_FOUND, ErrorMessageKey.DEPARTMENT_E031602),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('Department_List')")
	@GetMapping
	public ResponseEntity<?> getAllDepartments(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "", value = Constant.PAGENUMBER) String pageNo,
			@RequestParam(defaultValue = "", value = Constant.PAGESIZE) String pageSize) throws Exception {

		if (!pageNo.isBlank() || !pageSize.isBlank() || !search.isBlank()) {
			String pageNum = pageNo.isBlank() ? Constant.DEFAULT_PAGENUMBER : pageNo;
			String pageSiz = pageSize.isBlank() ? Constant.DEFAULT_PAGESIZE : pageSize;
			Page<IListDepartment> page = this.deptIntf.getAllDepartment(search, pageNum, pageSiz);
			PaginationResponse paginationResponse = new PaginationResponse();

			paginationResponse.setPageSize(page.getSize());
			paginationResponse.setTotal(page.getTotalElements());
			paginationResponse.setPageNumber(page.getNumber() + 1);

			return new ResponseEntity<>(new ListResponseDto(page.getContent(), paginationResponse), HttpStatus.OK);
		} else {
			List<IListDepartment> allList = deptIntf.findAllDepartmentList(IListDepartment.class);
			return new ResponseEntity<>(
					new SuccessResponseDto(SuccessMessageCode.SUCCESS, SuccessMessageKey.DEPARTMENT_M031605, allList),
					HttpStatus.OK);
		}

	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteMultipleDepartmentById(@RequestBody DeleteId id,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {

		try {
			deptIntf.deleteMultipleDepartmentById(id,userId);

			return new ResponseEntity<>(
					new SuccessResponseDto(SuccessMessageCode.DEPARTMENT_DELETED, SuccessMessageKey.DEPARTMENT_M031603),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(
					new ErrorResponseDto(ErrorMessageCode.DEPARTMENT_NOT_FOUND, ErrorMessageKey.DEPARTMENT_E031602),
					HttpStatus.BAD_REQUEST);
		}

	}

}
