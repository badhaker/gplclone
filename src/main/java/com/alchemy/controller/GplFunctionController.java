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
import org.springframework.web.multipart.MultipartFile;

import com.alchemy.dto.DeleteId;
import com.alchemy.dto.ErrorResponseDto;
import com.alchemy.dto.GplFunctionDto;
import com.alchemy.dto.ListResponseDto;
import com.alchemy.dto.PaginationResponse;
import com.alchemy.dto.SuccessResponseDto;
import com.alchemy.exceptionHandling.ResourceNotFoundException;
import com.alchemy.iListDto.IListGplFunctionDto;
import com.alchemy.serviceInterface.GplFunctionInterface;
import com.alchemy.utils.ApiUrls;
import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.ErrorMessageKey;
import com.alchemy.utils.GlobalFunctions;
import com.alchemy.utils.SuccessMessageCode;
import com.alchemy.utils.SuccessMessageKey;

@RestController
@RequestMapping(ApiUrls.GPL_FUNCTION)
public class GplFunctionController {
	@Autowired
	GplFunctionInterface gplFunctionInterface;

	@PreAuthorize("hasRole('Gpl_Function_Add')")
	@PostMapping
	public ResponseEntity<?> addGplFunction(@Valid @RequestBody GplFunctionDto gplFunctionDto,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {
		try {

			GplFunctionDto gplFunction = gplFunctionInterface.addGplFunction(gplFunctionDto, userId);

			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.GPL_FUNCTION_ADDED,
					SuccessMessageKey.GPL_FUNCTION_M031901, gplFunction), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.GPLFUNCTION_E031901),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('Gpl_Function_Update')")
	@PutMapping("/{id}")
	public ResponseEntity<?> updateGplFunction(@Valid @RequestBody GplFunctionDto gplFunctionDto, @PathVariable Long id,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {
		try {

			GplFunctionDto updategplFunctionDto = gplFunctionInterface.updateGplFunction(id, userId, gplFunctionDto);

			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.GPL_FUNCTION_UPDATED,
					SuccessMessageKey.GPL_FUNCTION_M031902, updategplFunctionDto), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.GPLFUNCTION_E031901),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('Gpl_Function_Delete')")
	@DeleteMapping()
	public ResponseEntity<?> deleteGplFunction(@RequestBody DeleteId id,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {
		try {

			gplFunctionInterface.deleteGplFunction(id, userId);

			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.GPL_FUNCTION_DELETED,
					SuccessMessageKey.GPL_FUNCTION_M031903), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.GPLFUNCTION_E031901),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('Gpl_Function_List')")
	@GetMapping
	public ResponseEntity<?> getAllGplFunction(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "", value = Constant.PAGENUMBER) String pageNo,
			@RequestParam(defaultValue = "", value = Constant.PAGESIZE) String pageSize) {
		Page<IListGplFunctionDto> iListGplFunctionDto = gplFunctionInterface.getAllGplFunction(search, pageNo,
				pageSize);
		PaginationResponse paginationResponse = new PaginationResponse();
		paginationResponse.setPageNumber(iListGplFunctionDto.getNumber());
		paginationResponse.setPageSize(iListGplFunctionDto.getSize());
		paginationResponse.setTotal(iListGplFunctionDto.getTotalElements());

		return new ResponseEntity<>(new ListResponseDto(iListGplFunctionDto.getContent(), paginationResponse),
				HttpStatus.OK);

	}

	// @PreAuthorize("hasRole('Master_Data')")
	@PostMapping("/upload-master-details")
	public ResponseEntity<?> uploadMasterDetails(MultipartFile file,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {
		try {
			if (file != null) {
				this.gplFunctionInterface.uploadMasterDetails(file, userId);
				return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.GPL_CAREER_ASPIRATIONS,
						SuccessMessageKey.GPL_CAREER_ASPIRATIONS_M0311103), HttpStatus.OK);
			} else {
				throw new ResourceNotFoundException(ErrorMessageCode.PLEASE_UPLOAD_FILE);
			}

		} catch (Exception e) {
			return ResponseEntity
					.ok(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.GPL_CAREER_ASPIRATIONS_E03701));
		}

	}

}
