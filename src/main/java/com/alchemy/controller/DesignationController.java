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
import com.alchemy.dto.DesignationDto;
import com.alchemy.dto.ErrorResponseDto;
import com.alchemy.dto.ListResponseDto;
import com.alchemy.dto.PaginationResponse;
import com.alchemy.dto.SuccessResponseDto;
import com.alchemy.iListDto.IListDesignation;
import com.alchemy.serviceInterface.DesignationInterface;
import com.alchemy.utils.ApiUrls;
import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageKey;
import com.alchemy.utils.GlobalFunctions;
import com.alchemy.utils.SuccessMessageCode;
import com.alchemy.utils.SuccessMessageKey;

@RestController
@RequestMapping(ApiUrls.DESIGNATION)
public class DesignationController {

	@Autowired
	private DesignationInterface designationInterface;

	@PreAuthorize("hasRole('Designation_Add')")
	@PostMapping()
	public ResponseEntity<?> addDesignation(@Valid @RequestBody DesignationDto designationDto) {
		try {

			designationInterface.addDesignation(designationDto);
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.DESIGNATION_ADDED,
					SuccessMessageKey.DESIGNATION_M031301, designationDto), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.DESIGNATION_E031302),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('Designation_Update')")
	@PutMapping("/{id}")
	public ResponseEntity<?> editDesignation(@PathVariable("id") Long desiganationId,
			@Valid @RequestBody DesignationDto designationDto) {
		try {
			designationInterface.editDesignation(desiganationId, designationDto);
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.DESIGNATION_EDITED,
					SuccessMessageKey.DESIGNATION_M031302, designationDto), HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.DESIGNATION_E031302),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('Designation_Delete')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteDesignation(@PathVariable("id") Long desiganationId) {
		try {

			designationInterface.deleteDesignation(desiganationId);
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.DESIGNATION_DELETED,
					SuccessMessageKey.DESIGNATION_M031303), HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.DESIGNATION_E031302),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('Designation_List')")
	@GetMapping
	public ResponseEntity<?> getAllDesignations(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "", value = Constant.PAGENUMBER) String pageNo,
			@RequestParam(defaultValue = "", value = Constant.PAGESIZE) String pageSize) {

		if (!pageNo.isBlank() || !pageSize.isBlank() || !search.isBlank()) {
			String pageNum = pageNo.isBlank() ? Constant.DEFAULT_PAGENUMBER : pageNo;
			String pageSiz = pageSize.isBlank() ? Constant.DEFAULT_PAGESIZE : pageSize;
			try {

				Page<IListDesignation> page = this.designationInterface.getAllDesignations(search, pageNum, pageSiz);

				PaginationResponse paginationResponse = new PaginationResponse();

				paginationResponse.setPageSize(page.getSize());
				paginationResponse.setTotal(page.getTotalElements());
				paginationResponse.setPageNumber(page.getNumber() + 1);

				return new ResponseEntity<>(new ListResponseDto(page.getContent(), paginationResponse), HttpStatus.OK);

			} catch (Exception e) {

				return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.DESIGNATION_E031302),
						HttpStatus.BAD_REQUEST);
			}

		} else {
			List<IListDesignation> iListDesignations = this.designationInterface
					.getAllDesignations(IListDesignation.class);
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.SUCCESS,
					SuccessMessageKey.DESIGNATION_M031301, iListDesignations), HttpStatus.OK);
		}
	}

	@PreAuthorize("hasRole('Designation_Delete')")
	@DeleteMapping
	public ResponseEntity<?> deleteMultipleDesignationById(@RequestBody DeleteId id,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {

		try {
			designationInterface.deleteMultipleDesignationById(id, userId);

			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.DESIGNATION_DELETED,
					SuccessMessageKey.DESIGNATION_M031303), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.DESIGNATION_E031302),
					HttpStatus.BAD_REQUEST);
		}

	}
}
