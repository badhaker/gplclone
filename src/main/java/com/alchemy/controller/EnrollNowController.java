package com.alchemy.controller;

import java.io.IOException;


import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.alchemy.dto.ErrorResponseDto;
import com.alchemy.dto.SuccessResponseDto;
import com.alchemy.serviceInterface.EnrollNowInterface;
import com.alchemy.utils.ApiUrls;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.GlobalFunctions;
import com.alchemy.utils.SuccessMessageCode;
import com.alchemy.utils.SuccessMessageKey;


@RestController
@RequestMapping(ApiUrls.ENROLL_NOW)
public class EnrollNowController {

	@Autowired
	private EnrollNowInterface enrollNowInterface;

	@PreAuthorize("hasRole('Enroll_Uplaod')")
	@PostMapping(ApiUrls.ENROLL_NOW_UPLOAD)
	public ResponseEntity<?> uploadEnrollNowBulkUpload(@RequestParam("file") MultipartFile file,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId, @RequestParam String moduleName) {
		try {
			 Long bulkId=this.enrollNowInterface.saveExcelFile(file, userId, moduleName);
			 enrollNowInterface.saveToEnrollNowTable(bulkId, userId);
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.ENROLL_NOW_DATA_ADDED,
					SuccessMessageKey.ENROLL_NOW_M033801), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageCode.DATA_NOT_FOUND),
					HttpStatus.BAD_REQUEST);
		}
	}

//	@PreAuthorize("hasRole('Export_Enroll_List')")
//	@GetMapping("/export")
//	public void exportToCSV(HttpServletResponse response) throws IOException {
//	    response.setContentType("text/csv");
//	    String headerKey = "Content-Disposition";
//	    String headerValue = "attachment; filename = Enroll_Now_Information.csv";
//	    response.setHeader(headerKey, headerValue);
//
//	    enrollNowInterface.exportEnrollNowToExcel(response);
//	    
//	}


//	@GetMapping()
//	public ResponseEntity<?> getAllUserEnroll(@RequestParam(defaultValue = "") String search,
//			@RequestParam(defaultValue = Constant.DEFAULT_PAGENUMBER, value = Constant.PAGENUMBER) String pageNo,
//			@RequestParam(defaultValue = Constant.DEFAULT_PAGESIZE, value = Constant.PAGESIZE) String pageSize) {
//
//		Page<IListEnroll> page = this.enrollNowInterface.getAllUserEnroll(search, pageNo, pageSize);
//		PaginationResponse paginationResponse = new PaginationResponse();
//
//		paginationResponse.setPageSize(page.getSize());
//		paginationResponse.setTotal(page.getTotalElements());
//		paginationResponse.setPageNumber(page.getNumber() + 1);
//
//		return new ResponseEntity<>(new ListResponseDto(page.getContent(), paginationResponse), HttpStatus.OK);
//
//		}
}
