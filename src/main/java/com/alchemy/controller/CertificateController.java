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
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.alchemy.dto.CertificateDto;
import com.alchemy.dto.ErrorResponseDto;
import com.alchemy.dto.ListResponseDto;
import com.alchemy.dto.PaginationResponse;
import com.alchemy.dto.SuccessResponseDto;
import com.alchemy.exceptionHandling.ResourceNotFoundException;
import com.alchemy.iListDto.IListCertificateTemp;
import com.alchemy.serviceImpl.EmailServiceImpl;
import com.alchemy.serviceInterface.CertificateInterface;
import com.alchemy.utils.ApiUrls;
import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageKey;
import com.alchemy.utils.GlobalFunctions;
import com.alchemy.utils.SuccessMessageCode;
import com.alchemy.utils.SuccessMessageKey;

@RestController
@RequestMapping(ApiUrls.CERTIFICATE)
public class CertificateController {

	@Autowired
	private CertificateInterface certificateInterface;
	
	@PreAuthorize("hasRole('Certificate_Add')")
	@PostMapping
	public ResponseEntity<?> addTemplate(@RequestBody @Valid CertificateDto templateDto,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {
		try {

			certificateInterface.addTemplate(templateDto, userId);
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.CERTIFICATE_ADDED,
					SuccessMessageCode.SUCCESS, templateDto), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.CERTIFICATE_E032301),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('Certificate_Update')")
	@PutMapping("/{id}")
	public ResponseEntity<?> editTemplate(@PathVariable Long id, @RequestBody @Valid CertificateDto templateDto,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {
		try {

			certificateInterface.editTemplate(id, templateDto, userId);
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.CERTIFICATE_UPDATED,
					SuccessMessageCode.UPDATED, templateDto), HttpStatus.OK);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.CERTIFICATE_E032301),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('Certificate_List')")
	@GetMapping
	public ResponseEntity<?> getAllTemplate(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "", value = Constant.PAGENUMBER) String pageNo,
			@RequestParam(defaultValue = "", value = Constant.PAGESIZE) String pageSize) {
		try {
			if (!pageNo.isBlank() || !pageSize.isBlank() || !search.isBlank()) {
				String pageNumber = pageNo.isBlank() ? Constant.DEFAULT_PAGENUMBER : pageNo;
				String pageSiz = pageSize.isBlank() ? Constant.DEFAULT_PAGESIZE : pageSize;

				Page<IListCertificateTemp> users = this.certificateInterface.templateList(search, pageNumber, pageSiz);

				PaginationResponse paginationResponse = new PaginationResponse();

				paginationResponse.setPageSize(users.getSize());
				paginationResponse.setTotal(users.getTotalElements());
				paginationResponse.setPageNumber(users.getNumber() + 1);

				return new ResponseEntity<>(new ListResponseDto(users.getContent(), paginationResponse), HttpStatus.OK);
			} else {

				List<IListCertificateTemp> certificateList = this.certificateInterface
						.findAllCertificates(IListCertificateTemp.class);
				return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.SUCCESS,
						SuccessMessageKey.CERTIFICATE_M032301, certificateList), HttpStatus.OK);
			}
		} catch (Exception e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.CERTIFICATE_E032301),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('Certificate_Delete')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteTemplate(@PathVariable Long id) {
		try {
			certificateInterface.deleteTemplate(id);
			return new ResponseEntity<>(
					new SuccessResponseDto(SuccessMessageCode.CERTIFICATE_DELETED, SuccessMessageCode.SUCCESS),
					HttpStatus.OK);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.CERTIFICATE_E032302),
					HttpStatus.BAD_REQUEST);
		}

	}
	
	
	

}