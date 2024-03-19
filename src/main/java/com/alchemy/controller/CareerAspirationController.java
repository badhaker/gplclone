package com.alchemy.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alchemy.dto.AspirationDto;
import com.alchemy.dto.CareerAspExportDto;
import com.alchemy.dto.CareerAspirationPrefereceDto;
import com.alchemy.dto.DeleteId;
import com.alchemy.dto.ErrorResponseDto;
import com.alchemy.dto.ListResponseDto;
import com.alchemy.dto.PaginationResponse;
import com.alchemy.dto.SuccessResponseDto;
import com.alchemy.serviceInterface.CareerAspirationInterface;
import com.alchemy.utils.ApiUrls;
import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageKey;
import com.alchemy.utils.GlobalFunctions;
import com.alchemy.utils.Pagination;
import com.alchemy.utils.SuccessMessageCode;
import com.alchemy.utils.SuccessMessageKey;

@RestController
@RequestMapping((ApiUrls.CAREER_ASPIRATION))
public class CareerAspirationController {
	@Autowired
	private CareerAspirationInterface careerAspirationInterface;

	@PreAuthorize("hasRole('CareerAspiration_Add')")
	@PostMapping()
	public ResponseEntity<?> addCareerAspiration(@RequestParam(name = "file", required = false) MultipartFile file,
			HttpServletRequest request, @RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId,
			@Valid AspirationDto aspirationDto) throws Exception {

		try {

			aspirationDto = this.careerAspirationInterface.addaspiration(file, request, aspirationDto, userId);

			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.CAREER_ASPIRATION_ADDED,
					SuccessMessageKey.CAREER_ASPIRATION_M034001, aspirationDto), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.CAREER_ASPIRATION_E034001),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('CareerAspiration_List')")
	@GetMapping(value = ApiUrls.PREFERENCES)
	public ResponseEntity<?> getAspirationPreference(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = Constant.DEFAULT_PAGENUMBER, value = Constant.PAGENUMBER) String pagenumber,
			@RequestParam(defaultValue = Constant.DEFAULT_PAGESIZE, value = Constant.PAGESIZE) String pagesize) {
		try {
			List<CareerAspirationPrefereceDto> careerAspirationPreferences = this.careerAspirationInterface
					.getAllApirationPreference(search, pagesize, pagenumber);

			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.CAREER_ASPIRATIONS_FETCHED,
					SuccessMessageKey.CAREER_ASPIRATIONS_M031102, careerAspirationPreferences), HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.CAREER_ASPIRATION_E034007),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@PreAuthorize("hasRole('CareerAspiration_List')")
	@GetMapping()
	public ResponseEntity<?> getCareerAspirationByUser(
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {
		try {
			CareerAspirationPrefereceDto careerAspiration = this.careerAspirationInterface.getApiration(userId);
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.CAREER_ASPIRATIONS_FETCHED,
					SuccessMessageKey.CAREER_ASPIRATIONS_M031102, careerAspiration), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.CAREER_ASPIRATION_E034007),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@PreAuthorize("hasRole('CareerAspiration_List')")
	@GetMapping("/users")
	public ResponseEntity<?> getUserAspiration(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "") String function, @RequestParam(defaultValue = "") String department,
			@RequestParam(defaultValue = Constant.DEFAULT_PAGENUMBER, value = Constant.PAGENUMBER) String pageNumber,
			@RequestParam(defaultValue = Constant.DEFAULT_PAGESIZE, value = Constant.PAGESIZE) String pageSize,
			@RequestParam(defaultValue = "") String exportIds, @RequestParam(defaultValue = "") Boolean blankRecords,
			@RequestParam(defaultValue = "false") boolean export, HttpServletResponse response) {
		try {
			String pageNo = pageNumber.isBlank() ? Constant.DEFAULT_PAGENUMBER : pageNumber;
			String pages = pageSize.isBlank() ? Constant.DEFAULT_PAGESIZE : pageSize;
			Pageable pageable = new Pagination().getPagination(pageNo, pages);

			if (export == true) {
				pageable = Pageable.unpaged();

				Page<CareerAspExportDto> page = careerAspirationInterface.getUserAspiration(search, function,
						department, pageable, blankRecords, exportIds);

				response.setContentType("text/csv");
				response.setHeader("Content-Disposition", "attachment; filename=\"career_exp.csv\"");
				careerAspirationInterface.exportToExcel(response, page);
				return ResponseEntity.ok().build();
			} else {
				Page<CareerAspExportDto> careerAspiration = this.careerAspirationInterface.getUserAspiration(search,
						function, department, pageable, blankRecords, exportIds);

				PaginationResponse paginationResponse = new PaginationResponse();
				paginationResponse.setPageNumber(careerAspiration.getNumber() + 1);
				paginationResponse.setPageSize(careerAspiration.getSize());
				paginationResponse.setTotal((long) careerAspiration.getContent().size());

				int no = pageNumber.isBlank() ? Integer.parseInt(Constant.DEFAULT_PAGENUMBER)
						: Integer.parseInt(pageNumber);
				int size = pageSize.isBlank() ? Integer.parseInt(Constant.DEFAULT_PAGESIZE)
						: Integer.parseInt(pageSize);

				return new ResponseEntity<>(new ListResponseDto(careerAspiration.getContent().subList((no - 1) * size,
						careerAspiration.getContent().size() < (no * size) ? careerAspiration.getContent().size()
								: no * size),
						paginationResponse), HttpStatus.OK);
			}

		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.CAREER_ASPIRATION_E034007),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('CareerAspirationDelete')")
	@DeleteMapping()
	public ResponseEntity<?> deleteMultiple(@RequestBody DeleteId ids,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {
		try {

			careerAspirationInterface.deleteMultiple(ids, userId);

			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.CAREER_ASPIRATIONS_DELETED,
					SuccessMessageKey.GPL_CAREER_ASPIRATIONS_M0311103), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.CAREER_ASPIRATION_E034008),
					HttpStatus.BAD_REQUEST);
		}
	}

}
