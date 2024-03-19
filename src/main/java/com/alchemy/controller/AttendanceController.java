package com.alchemy.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alchemy.dto.AttendanceDto;
import com.alchemy.dto.AttendanceFilterDto;
import com.alchemy.dto.AttendanceStatusDto;
import com.alchemy.dto.ErrorResponseDto;
import com.alchemy.dto.IsVisibleDto;
import com.alchemy.dto.ListResponseDto;
import com.alchemy.dto.MarkAttendanceRequestDto;
import com.alchemy.dto.MultiLockRequestDto;
import com.alchemy.dto.PaginationResponse;
import com.alchemy.dto.StarperformerDto;
import com.alchemy.dto.SuccessResponseDto;
import com.alchemy.dto.UserTrackStatusDto;
import com.alchemy.dto.DeleteId;
import com.alchemy.iListDto.IListAttendance;
import com.alchemy.serviceInterface.AttendanceInterface;
import com.alchemy.utils.ApiUrls;
import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.ErrorMessageKey;
import com.alchemy.utils.GlobalFunctions;
import com.alchemy.utils.Pagination;
import com.alchemy.utils.SuccessMessageCode;
import com.alchemy.utils.SuccessMessageKey;

@RestController
@RequestMapping(ApiUrls.ATTENDANCE)
public class AttendanceController {

	@Autowired
	private AttendanceInterface attendanceInterface;

//	@PostMapping()
//	public ResponseEntity<?> addAttendance(@Valid @RequestBody AttendanceDto attendanceDto,
//			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) throws Exception {
//
//		try {
//			AttendanceDto dto = this.attendanceInterface.addAttendance(attendanceDto, userId);
//			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.ATTENDANCE_ADDED,
//					SuccessMessageKey.ATTENDANCE_M033701, dto), HttpStatus.OK);
//		} catch (Exception e) {
//
//			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.ATTENDANCE_E033701),
//					HttpStatus.BAD_REQUEST);
//		}
//
//	}

	@PreAuthorize("hasRole('Attendance_Upload')")
	@PostMapping(ApiUrls.ATTENDANCE_UPLOAD)
	public ResponseEntity<?> uploadAttendanceFile(@RequestParam("file") MultipartFile file,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId, @RequestParam String moduleName) {
		try {
			Long bulkId = this.attendanceInterface.saveExcelFile(file, userId, moduleName);
			attendanceInterface.saveToAttendanceTable(bulkId, userId);
			return new ResponseEntity<>(
					new SuccessResponseDto(SuccessMessageCode.ATTENDANCE_ADDED, SuccessMessageKey.ATTENDANCE_M033701),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageCode.DATA_NOT_FOUND),
					HttpStatus.BAD_REQUEST);
		}
	}

//	@PreAuthorize("hasRole('Attendance_List')")
	@PostMapping
	public ResponseEntity<?> getAllAttendance(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "", value = Constant.PAGENUMBER) String pageNo,
			@RequestParam(defaultValue = "", value = Constant.PAGESIZE) String pageSize,
			@RequestBody AttendanceFilterDto requestDto,

			HttpServletResponse response ,
			@RequestParam(defaultValue = "false") Boolean export) 
			throws IOException{
		
			String pageNumber = pageNo.isBlank() ? Constant.DEFAULT_PAGENUMBER : pageNo;
			String pages = pageSize.isBlank() ? Constant.DEFAULT_PAGESIZE : pageSize;
			
			Pageable pageable = new Pagination().getPagination(pageNumber, pages);
			if(export == true) {
				pageable = Pageable.unpaged();
			
			Page<IListAttendance> page = this.attendanceInterface.getAllAttendance(search,requestDto,
					response,pageable);
			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "attachment; filename=\"attendance.csv\"");
			attendanceInterface.exportAttendance(response, page);
			return ResponseEntity.ok().build();
			}
			else {
			Page<IListAttendance> page = this.attendanceInterface.getAllAttendance(search,requestDto, 
					response,pageable);
			
			PaginationResponse paginationResponse = new PaginationResponse();

			paginationResponse.setPageSize(page.getSize());
			paginationResponse.setTotal(page.getTotalElements());
			paginationResponse.setPageNumber(page.getNumber() + 1);

			return new ResponseEntity<>(new ListResponseDto(page.getContent(), paginationResponse), HttpStatus.OK);
		}
	}

	@PutMapping("{id}")
	public ResponseEntity<?> updateAttendance(@Valid @RequestBody AttendanceDto attendanceDto, @PathVariable Long id,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {
		try {

			AttendanceDto dto = this.attendanceInterface.updateAttendance(attendanceDto, id, userId);
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.ATTENDANCE_UPDATED,
					SuccessMessageKey.ATTENDANCE_M033701, dto), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.ATTENDANCE_E033701),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('Attendance_Lock')")
	@PutMapping(ApiUrls.ATTENDANCE_LOCK)
	public ResponseEntity<?> lockMultipleAttendance(@RequestBody MultiLockRequestDto id,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {

		try {
			attendanceInterface.lockMultipleAttendanceById(id,userId);

			return new ResponseEntity<>(
					new SuccessResponseDto(SuccessMessageCode.ATTENDANCE_LOCKED, SuccessMessageKey.ATTENDANCE_M033702),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(
					new ErrorResponseDto(e.getMessage(), ErrorMessageKey.ATTENDANCE_E033701),
					HttpStatus.BAD_REQUEST);
		}

	}
	
	@PreAuthorize("hasRole('Attendance_Status')")
	@PutMapping(ApiUrls.ATTENDANCE_STATUS)
	public ResponseEntity<?> markMultipleAttendance(@RequestBody MarkAttendanceRequestDto id,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {

		try {
			int count = attendanceInterface.markMultipleAttendanceById(id,userId);

			if (count > 0) {
	            return new ResponseEntity<>(
	                new SuccessResponseDto(SuccessMessageCode.ATTENDANCE_MARKED, SuccessMessageKey.ATTENDANCE_M033702),
					HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("", HttpStatus.OK);
	        }
			

		} catch (Exception e) {
			return new ResponseEntity<>(
					new ErrorResponseDto(e.getMessage(), ErrorMessageKey.ATTENDANCE_E033701),
					HttpStatus.BAD_REQUEST);
		}}

		@PreAuthorize("hasRole('Attendance_StatusUpdate')")
		@PatchMapping("/{id}")
		public ResponseEntity<?> updateEnrollStatus(@PathVariable("id") Long id,
				@RequestBody AttendanceStatusDto status) {
			try {
				this.attendanceInterface.updateAttendanceStatus(id, status);

				return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.ATTENDANCE_STATUS_UPDATED,
						SuccessMessageKey.ATTENDANCE_M033702), HttpStatus.OK);

			} catch (Exception e) {

				return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.ATTENDANCE_E033701),
						HttpStatus.BAD_REQUEST);

			}

		}
		
		@PreAuthorize("hasRole('Attendance_Multi_Delete')")
		@DeleteMapping
		public ResponseEntity<?> deleteMultipleAttendance(@RequestBody DeleteId id , 
				@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {
			try {
				this.attendanceInterface.deleteMultipleAttendance(id, userId);

				return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.ATTENDANCE_STATUS_UPDATED,
						SuccessMessageKey.ATTENDANCE_M033702), HttpStatus.OK);

			} catch (Exception e) {

				return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.ATTENDANCE_E033701),
						HttpStatus.BAD_REQUEST);

			}

		}
		
		@PreAuthorize("hasRole('Multiple_Star_Performer')")
		@PutMapping(ApiUrls.ATTENDANCE_STAR)
		public ResponseEntity<?> markMultipleStar(@RequestBody StarperformerDto id , 
				@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {
			try {
				this.attendanceInterface.markMultipleStarperformer(id, userId);

				return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.STARPERFORMER__UPDATED,
						SuccessMessageKey.ATTENDANCE_M033702), HttpStatus.OK);

			} catch (Exception e) {

				return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.ATTENDANCE_E033701),
						HttpStatus.BAD_REQUEST);

			}

		}
		
	}
	


