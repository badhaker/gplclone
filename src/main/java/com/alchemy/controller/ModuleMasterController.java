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
import com.alchemy.dto.ListResponseDto;
import com.alchemy.dto.ModuleDto;
import com.alchemy.dto.PaginationResponse;
import com.alchemy.dto.SuccessResponseDto;
import com.alchemy.iListDto.IModuleList;
import com.alchemy.serviceInterface.ModuleMasterInterface;
import com.alchemy.utils.ApiUrls;
import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.ErrorMessageKey;
import com.alchemy.utils.GlobalFunctions;
import com.alchemy.utils.SuccessMessageCode;
import com.alchemy.utils.SuccessMessageKey;

@RestController
@RequestMapping(ApiUrls.MODULE)
public class ModuleMasterController {

	@Autowired
	private ModuleMasterInterface moduleMasterInterface;

	// @PreAuthorize("hasRole('Module_Add')")
	@PostMapping()
	public ResponseEntity<?> addModules(@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId,
			@Valid @RequestBody ModuleDto moduleDto) throws Exception {
		try {

			ModuleDto moduleMasterEntity = this.moduleMasterInterface.addModules(userId, moduleDto);
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.MODULE_ADDED,
					SuccessMessageKey.MODULE_M033001, moduleMasterEntity), HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.MODULE_E033001),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('Module_Update')")
	@PutMapping("/{id}")
	public ResponseEntity<?> updateModules(@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId,
			@Valid @RequestBody ModuleDto moduleDto, @PathVariable Long id) {
		try {

			ModuleDto module = moduleMasterInterface.updateModule(userId, moduleDto, id);
			return new ResponseEntity<>(
					new SuccessResponseDto(SuccessMessageCode.MODULE_UPDATED, SuccessMessageKey.MODULE_M033002, module),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.MODULE_E033002),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('Module_Delete')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteModule(@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId,
			@PathVariable Long id) {
		try {
			this.moduleMasterInterface.deleteModule(userId, id);
			return new ResponseEntity<>(
					new SuccessResponseDto(SuccessMessageCode.MODULE_DELETE, SuccessMessageKey.MODULE_M033003),
					HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), SuccessMessageKey.MODULE_M033002),
					HttpStatus.BAD_REQUEST);

		}
	}

	@PreAuthorize("hasRole('Module_List')")
	@GetMapping
	public ResponseEntity<?> getAllModules(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "", value = Constant.PAGENUMBER) String pageNo,
			@RequestParam(defaultValue = "", value = Constant.PAGESIZE) String pageSize) {

		if (!pageNo.isBlank() || !pageSize.isBlank() || !search.isBlank()) {

			String pageNumber = pageNo.isBlank() ? Constant.DEFAULT_PAGENUMBER : pageNo;
			String page = pageSize.isBlank() ? Constant.DEFAULT_PAGESIZE : pageSize;

			Page<IModuleList> imoduleList = this.moduleMasterInterface.getAllModules(search, pageNumber, page);

			PaginationResponse paginationResponse = new PaginationResponse();

			paginationResponse.setPageSize(imoduleList.getSize());
			paginationResponse.setPageNumber(imoduleList.getNumber() + 1);
			paginationResponse.setTotal(imoduleList.getTotalElements());

			return new ResponseEntity<>(
					new SuccessResponseDto(SuccessMessageCode.MODULE_GET, SuccessMessageKey.MODULE_M033004,
							new ListResponseDto(imoduleList.getContent(), paginationResponse)),
					HttpStatus.OK);

		} else {

			List<IModuleList> moduleDto = this.moduleMasterInterface.findAll(IModuleList.class);
			return new ResponseEntity<>(
					new SuccessResponseDto(SuccessMessageCode.MODULE_GET, SuccessMessageKey.MODULE_M033004, moduleDto),
					HttpStatus.OK);
		}

	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteMultipleModulesById(@RequestBody DeleteId id,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {

		try {
			moduleMasterInterface.deleteMultipleModuleById(id,userId);

			return new ResponseEntity<>(
					new SuccessResponseDto(SuccessMessageCode.MODULE_DELETE, SuccessMessageKey.PERMISSION_M031302),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(
					new ErrorResponseDto(ErrorMessageCode.MODULE_NOT_FOUND, ErrorMessageKey.MODULE_E033002),
					HttpStatus.BAD_REQUEST);
		}

	}

}
