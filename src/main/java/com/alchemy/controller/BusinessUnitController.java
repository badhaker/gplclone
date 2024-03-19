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

import com.alchemy.dto.BusinessUnitDto;
import com.alchemy.dto.ErrorResponseDto;
import com.alchemy.dto.ListResponseDto;
import com.alchemy.dto.PaginationResponse;
import com.alchemy.dto.SuccessResponseDto;
import com.alchemy.iListDto.IListBusinessUnit;
import com.alchemy.serviceInterface.BusinessUnitInterface;
import com.alchemy.utils.ApiUrls;
import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.ErrorMessageKey;
import com.alchemy.utils.GlobalFunctions;
import com.alchemy.utils.SuccessMessageCode;
import com.alchemy.utils.SuccessMessageKey;

@RestController
@RequestMapping(ApiUrls.BUSINESS_UNIT)
public class BusinessUnitController {

	@Autowired
	private BusinessUnitInterface businessUnitInterface;

	@PreAuthorize("hasRole('BusinessUnit_Add')")
	@PostMapping()
	public ResponseEntity<?> addBusinessUnit(@Valid @RequestBody BusinessUnitDto businessUnitDto,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {
		try {
			BusinessUnitDto businessUnitDto2 = businessUnitInterface.addBusinessUnit(businessUnitDto, userId);
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.BUSINESS_UNIT_ADDED,
					SuccessMessageKey.BUSINESS_UNIT_M032504, businessUnitDto2), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.BUSINESS_UNIT_E032501),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('BusinessUnit_Update')")
	@PutMapping("{id}")
	public ResponseEntity<?> updateBusinessUnit(@Valid @RequestBody BusinessUnitDto businessUnitDto,
			@PathVariable Long id, @RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {
		try {
			BusinessUnitDto businessUnitDto2 = businessUnitInterface.updateBusinessUnit(businessUnitDto, id, userId);
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.BUSINESS_UNIT_UPDATED,
					SuccessMessageKey.BUSINESS_UNIT_M032503, businessUnitDto2), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(ErrorMessageCode.BUSINESS_UNIT_NOT_FOUND,
					ErrorMessageKey.BUSINESS_UNIT_E032501), HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('BusinessUnit_Delete')")
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteBusinessUnitById(@PathVariable Long id) {

		try {
			businessUnitInterface.deleteBusinessUnitById(id);

			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.BUSINESS_UNIT_DELETED,
					SuccessMessageKey.BUSINESS_UNIT_M032502), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(ErrorMessageCode.BUSINESS_UNIT_NOT_FOUND,
					ErrorMessageKey.BUSINESS_UNIT_E032501), HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('BusinessUnit_List')")
	@GetMapping()
	public ResponseEntity<?> getAllBusinessUnits(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "", value = Constant.PAGENUMBER) String pageNo,
			@RequestParam(defaultValue = "", value = Constant.PAGESIZE) String pageSize) throws Exception {
		if (!pageNo.isBlank() || !pageSize.isBlank() || !search.isBlank()) {

			String pageNumber = pageNo.isBlank() ? Constant.DEFAULT_PAGENUMBER : pageNo;
			String pages = pageSize.isBlank() ? Constant.DEFAULT_PAGESIZE : pageSize;

			Page<IListBusinessUnit> page = this.businessUnitInterface.getAllBusinessUnit(search, pageNumber, pages);
			PaginationResponse paginationResponse = new PaginationResponse();

			paginationResponse.setPageSize(page.getSize());
			paginationResponse.setTotal(page.getTotalElements());
			paginationResponse.setPageNumber(page.getNumber() + 1);

			return new ResponseEntity<>(new ListResponseDto(page.getContent(), paginationResponse), HttpStatus.OK);

		} else {

			List<IListBusinessUnit> findAllList = businessUnitInterface.findAllList(search, IListBusinessUnit.class);
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.SUCCESS,
					SuccessMessageKey.BUSINESS_UNIT_M032501, findAllList), HttpStatus.OK);
		}
	}
}
