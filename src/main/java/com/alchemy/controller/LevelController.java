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
import com.alchemy.dto.LevelDto;
import com.alchemy.dto.ListResponseDto;
import com.alchemy.dto.PaginationResponse;
import com.alchemy.dto.SuccessResponseDto;
import com.alchemy.iListDto.IListLevel;
import com.alchemy.serviceInterface.LevelInterface;
import com.alchemy.utils.ApiUrls;
import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.ErrorMessageKey;
import com.alchemy.utils.GlobalFunctions;
import com.alchemy.utils.SuccessMessageCode;
import com.alchemy.utils.SuccessMessageKey;

@RestController
@RequestMapping(ApiUrls.LEVEL)
public class LevelController {

	@Autowired
	private LevelInterface levelInterface;

	@PreAuthorize("hasRole('Level_Add')")
	@PostMapping()
	public ResponseEntity<?> addLevel(@Valid @RequestBody LevelDto levelDto,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {
		try {
			LevelDto level = levelInterface.addLevel(levelDto, userId);
			return new ResponseEntity<>(
					new SuccessResponseDto(SuccessMessageCode.LEVEL_ADDED, SuccessMessageKey.LEVEL_M031404, level),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.LEVEL_E031401),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('Level_Update')")
	@PutMapping("{id}")
	public ResponseEntity<?> updateLevel(@Valid @RequestBody LevelDto levelDto, @PathVariable Long id,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {
		try {

			LevelDto levelDto2 = this.levelInterface.updateLevel(levelDto, id, userId);
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.LEVEL_UPDATED,
					SuccessMessageKey.LEVEL_M031403, levelDto2), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.LEVEL_E031401),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('Level_Delete')")
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteLevelById(@PathVariable Long id) {

		try {
			levelInterface.deleteLevelById(id);

			return new ResponseEntity<>(
					new SuccessResponseDto(SuccessMessageCode.LEVEL_DELETED, SuccessMessageKey.LEVEL_M031402),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(
					new ErrorResponseDto(ErrorMessageCode.LEVEL_NOT_FOUND, ErrorMessageKey.LEVEL_E031401),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('Level_List')")
	@GetMapping()
	public ResponseEntity<?> getAllLevels(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "", value = Constant.PAGENUMBER) String pageNo,
			@RequestParam(defaultValue = "", value = Constant.PAGESIZE) String pageSize) throws Exception {
		if (!pageNo.isBlank() || !pageSize.isBlank() || !search.isBlank()) {

			String pageNumber = pageNo.isBlank() ? Constant.DEFAULT_PAGENUMBER : pageNo;
			String pages = pageSize.isBlank() ? Constant.DEFAULT_PAGESIZE : pageSize;

			Page<IListLevel> page = this.levelInterface.getAllLevels(search, pageNumber, pages);
			PaginationResponse paginationResponse = new PaginationResponse();

			paginationResponse.setPageSize(page.getSize());
			paginationResponse.setTotal(page.getTotalElements());
			paginationResponse.setPageNumber(page.getNumber() + 1);

			return new ResponseEntity<>(new ListResponseDto(page.getContent(), paginationResponse), HttpStatus.OK);

		} else {

			List<IListLevel> findAllList = levelInterface.findAllList(search, IListLevel.class);
			return new ResponseEntity<>(
					new SuccessResponseDto(SuccessMessageCode.SUCCESS, SuccessMessageKey.LEVEL_M031404, findAllList),
					HttpStatus.OK);
		}
	}

}
