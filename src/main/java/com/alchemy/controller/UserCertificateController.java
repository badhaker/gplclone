package com.alchemy.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.alchemy.dto.CertificateRequestDto;
import com.alchemy.dto.CertificateResponseDto;
import com.alchemy.dto.ErrorResponseDto;
import com.alchemy.dto.ListResponseDto;
import com.alchemy.dto.PaginationResponse;
import com.alchemy.dto.SuccessResponseDto;
import com.alchemy.dto.UserCertificateRequestDto;
import com.alchemy.iListDto.IListUserCertificate;
import com.alchemy.serviceImpl.EmailServiceImpl;
import com.alchemy.serviceImpl.UserCertificateServiceImpl;
import com.alchemy.serviceInterface.EmailInterface;
import com.alchemy.serviceInterface.UserCertificateInterface;
import com.alchemy.utils.ApiUrls;
import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageKey;
import com.alchemy.utils.GlobalFunctions;
import com.alchemy.utils.SuccessMessageCode;
import com.alchemy.utils.SuccessMessageKey;
import com.lowagie.text.DocumentException;

@RestController
@RequestMapping(ApiUrls.USER_CERTIFICATE)
public class UserCertificateController {
	
	
	
	@Autowired
	private UserCertificateInterface userCertificateInterface;
	
	@Autowired
	private UserCertificateServiceImpl impl;

	@Autowired
	private EmailServiceImpl emailServiceImpl;
	
	@Autowired
	private SpringTemplateEngine engine;
	
	@Autowired
	private EmailInterface email;


	@PreAuthorize("hasRole('UserCertificate_Add')")
	@PostMapping()
	public ResponseEntity<?> addUserCertificate(@Valid @RequestBody UserCertificateRequestDto userCertificateRequest) {
		try {
			userCertificateInterface.add(userCertificateRequest);
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.USER_CERTIFICATE_ADDED,
					SuccessMessageKey.USER_CERTIFICATE_M032804, userCertificateRequest), HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.USER_CERTIFICATE_E032802),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('UserCertificate_List')")
	@GetMapping()
	public ResponseEntity<?> getAllUserCertificates(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = Constant.DEFAULT_PAGENUMBER, value = Constant.PAGENUMBER) String pageNo,
			@RequestParam(defaultValue = Constant.DEFAULT_PAGESIZE, value = Constant.PAGESIZE) String pageSize)
			throws Exception {
		Page<IListUserCertificate> page = this.userCertificateInterface.getAllUserCertificates(search, pageNo,
				pageSize);
		PaginationResponse paginationResponse = new PaginationResponse();

		paginationResponse.setPageSize(page.getSize());
		paginationResponse.setTotal(page.getTotalElements());
		paginationResponse.setPageNumber(page.getNumber() + 1);

		return new ResponseEntity<>(new ListResponseDto(page.getContent(), paginationResponse), HttpStatus.OK);

	}
	
	@GetMapping("/certificate")
    public ResponseEntity<?> viewCertificate (@RequestParam(defaultValue ="") Long learningTrackId,
    		@RequestParam(defaultValue ="") Long subTrackId,
    		@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId){
		try {
		CertificateResponseDto str=	userCertificateInterface.getUserCertificate(learningTrackId,subTrackId, userId, null);
		
		return new ResponseEntity<>(new SuccessResponseDto("success",
				"success", str), HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "error"),
					HttpStatus.BAD_REQUEST);
		}
	}
	
}


