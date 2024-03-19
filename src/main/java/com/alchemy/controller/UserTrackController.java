package com.alchemy.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alchemy.dto.EnrollFilterDto;
import com.alchemy.dto.ErrorResponseDto;
import com.alchemy.dto.ListResponseDto;
import com.alchemy.dto.MarkAttendanceRequestDto;
import com.alchemy.dto.MultipleEnrollStatusDto;
import com.alchemy.dto.PaginationResponse;
import com.alchemy.dto.SuccessResponseDto;
import com.alchemy.dto.UserTrackDto;
import com.alchemy.dto.UserTrackStatusDto;
import com.alchemy.iListDto.IListAttendance;
import com.alchemy.iListDto.IListGplFunctionDto;
import com.alchemy.iListDto.IListUserTrack;
import com.alchemy.serviceInterface.EnrollNowInterface;
import com.alchemy.serviceInterface.UserTrackInterface;
import com.alchemy.utils.ApiUrls;
import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageKey;
import com.alchemy.utils.GlobalFunctions;
import com.alchemy.utils.Pagination;
import com.alchemy.utils.SuccessMessageCode;
import com.alchemy.utils.SuccessMessageKey;

@RestController
@RequestMapping(ApiUrls.USERTRACK)
public class UserTrackController {

	@Autowired
	private UserTrackInterface trackInterface;
	
	@Autowired
	private EnrollNowInterface enrollNowInterface;

	@PreAuthorize("hasRole('UserTrack_Add')")
	@PostMapping("/enroll")
	public ResponseEntity<?> enrollUserTrack(@RequestBody @Valid UserTrackDto userTrackDto,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId,
			@RequestHeader("Authorization") String token) {
		try {
			UserTrackDto userTrackDto1 = trackInterface.addUserTrack(userTrackDto, userId, token);
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.USERTRACK_ADDED,
					SuccessMessageKey.USERTRACK_M033401, userTrackDto1), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.USERTRACK_E033401),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('UserTrack_Update')")
	@PatchMapping("/{id}")
	public ResponseEntity<?> updateEnrollStatus(@PathVariable("id") Long id,
			@RequestBody UserTrackStatusDto userTopicDto) {
		try {
			this.trackInterface.updateUserTrackStatus(id, userTopicDto);

			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.USERTRACK_STATUS_UPDATED,
					SuccessMessageKey.USERTRACK_M033402), HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.USERTRACK_E033403),
					HttpStatus.BAD_REQUEST);

		}

	}

	@PostMapping
	public ResponseEntity<?> getAllUserTracks(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "", value = Constant.PAGENUMBER) String pageNo,
			@RequestParam(defaultValue = "", value = Constant.PAGESIZE) String pageSize,
			@RequestBody EnrollFilterDto dto,HttpServletResponse response ,
			@RequestParam(defaultValue = "false") Boolean export) throws IOException {
		
		String pageNumber = pageNo.isBlank() ? Constant.DEFAULT_PAGENUMBER : pageNo;
		String pages = pageSize.isBlank() ? Constant.DEFAULT_PAGESIZE : pageSize;
		
		Pageable pageable = new Pagination().getPagination(pageNumber, pages);
		if(export == true) {
			pageable = Pageable.unpaged();
		
		Page<IListUserTrack> page = trackInterface.findAllUserTracks(search,dto,
				response,pageable);
		response.setContentType("text/csv");
		response.setHeader("Content-Disposition", "attachment; filename=\"enrollment.csv\"");
		enrollNowInterface.exportEnrollNowToExcel(response, page);
		return ResponseEntity.ok().build();
		}
		else {
		Page<IListUserTrack> page = trackInterface.findAllUserTracks(search,dto,
		response,pageable);
		
		PaginationResponse paginationResponse = new PaginationResponse();

		paginationResponse.setPageSize(page.getSize());
		paginationResponse.setTotal(page.getTotalElements());
		paginationResponse.setPageNumber(page.getNumber() + 1);

		return new ResponseEntity<>(new ListResponseDto(page.getContent(), paginationResponse), HttpStatus.OK);
	}

	}
	
	@PreAuthorize("hasRole('UserTrack_Status')")
	@PutMapping(ApiUrls.ENROLL_STATUS)
	public ResponseEntity<?> multiEnrollStatus(@RequestBody MultipleEnrollStatusDto id,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {

		try {
			int count= trackInterface.markMultipleEnrollById(id,userId);
	
			if (count > 0) {
	            return new ResponseEntity<>(
	                new SuccessResponseDto(SuccessMessageCode.USERTRACK_STATUS_UPDATED, SuccessMessageKey.USERTRACK_M033402),
	                HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("", HttpStatus.OK);
	        }
		} catch (Exception e) {
			return new ResponseEntity<>(
					new ErrorResponseDto(e.getMessage(), ErrorMessageKey.USERTRACK_E033403),
					HttpStatus.BAD_REQUEST);
		}}
	
}
