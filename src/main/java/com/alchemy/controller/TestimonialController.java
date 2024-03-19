package com.alchemy.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import com.alchemy.dto.DeleteId;
import com.alchemy.dto.ErrorResponseDto;
import com.alchemy.dto.IsVisibleDto;
import com.alchemy.dto.ListResponseDto;
import com.alchemy.dto.PaginationResponse;
import com.alchemy.dto.SuccessResponseDto;
import com.alchemy.dto.TestimonialDto;
import com.alchemy.dto.VisibleContentDto;
import com.alchemy.iListDto.IListTestimonial;
import com.alchemy.serviceInterface.TestimonialInterface;
import com.alchemy.utils.ApiUrls;
import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.ErrorMessageKey;
import com.alchemy.utils.GlobalFunctions;
import com.alchemy.utils.SuccessMessageCode;
import com.alchemy.utils.SuccessMessageKey;

@RestController
@RequestMapping(ApiUrls.TESTIMONIAL)
public class TestimonialController {

	@Autowired
	private TestimonialInterface testimonialInterface;

	@PreAuthorize("hasRole('Testimonial_Add')")
	@PostMapping
	public ResponseEntity<?> addTestimonial(@Valid TestimonialDto testimonialDto,
			@RequestParam(name = "file", required = false) @NotNull MultipartFile file, HttpServletRequest request,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) throws Exception {
		try {

			testimonialInterface.addTestimonial(testimonialDto, userId, file, request);

			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.TESTIMONIAL_ADDED,
					SuccessMessageKey.TESTIMONIAL_M031801, testimonialDto), HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.TESTIMONIAL_E031801),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('Testimonial_Update')")
	@PutMapping("/{id}")
	public ResponseEntity<?> editTestimonial(@PathVariable("id") Long testimonialId,
			@Valid TestimonialDto testimonialDto,
			@RequestParam(name = "file", required = false) @NotNull MultipartFile file, HttpServletRequest request,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) throws Exception {
		try {

			testimonialInterface.editTestimonial(testimonialId, testimonialDto, userId, file, request);
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.TESTIMONIAL_UPDATED,
					SuccessMessageKey.TESTIMONIAL_M031802, testimonialDto), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.TESTIMONIAL_E031802),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('Testimonial_Delete')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteTestimonial(@PathVariable("id") Long testimonialId) {

		try {
			testimonialInterface.deleteTestimonial(testimonialId);
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.TESTIMONIAL_DELETED,
					SuccessMessageKey.TESTIMONIAL_M031803), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.TESTIMONIAL_E031802),
					HttpStatus.OK);
		}
	}

	@PreAuthorize("hasRole('Testimonial_List')")
	@GetMapping
	public ResponseEntity<?> getAllTestimonial(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "", value = Constant.PAGENUMBER) String pageNo,
			@RequestParam(defaultValue = "", value = Constant.PAGESIZE) String pageSize) throws Exception {
		try {
			if (!pageNo.isBlank() || !pageSize.isBlank() || !search.isBlank()) {
				String pageNumber = pageNo.isBlank() ? Constant.DEFAULT_PAGENUMBER : pageNo;
				String pageSiz = pageSize.isBlank() ? Constant.DEFAULT_PAGESIZE : pageSize;

				Page<IListTestimonial> page = this.testimonialInterface.getAllTestimonials(search, pageNumber, pageSiz);
				PaginationResponse paginationResponse = new PaginationResponse();

				paginationResponse.setPageSize(page.getSize());
				paginationResponse.setTotal(page.getTotalElements());
				paginationResponse.setPageNumber(page.getNumber() + 1);

				return new ResponseEntity<>(new ListResponseDto(page.getContent(), paginationResponse), HttpStatus.OK);
			} else {

				List<IListTestimonial> testimonialList = this.testimonialInterface
						.findAllTestimonial(IListTestimonial.class);
				return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.TESTIMONIAL_FETCHED,
						SuccessMessageKey.TESTIMONIAL_M031801, testimonialList), HttpStatus.OK);
			}
		} catch (Exception e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.TESTIMONIAL_E031801),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('Testimonial_Edit')")
	@PatchMapping("/{id}")
	public ResponseEntity<?> updateTestimonial(@PathVariable("id") Long id, @RequestBody IsVisibleDto dto) {
		try {
			this.testimonialInterface.updateTestimonialIsVisible(id, dto);

			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.TESTIMONIAL_UPDATED,
					SuccessMessageKey.TESTIMONIAL_M031802), HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.TESTIMONIAL_E031802),
					HttpStatus.BAD_REQUEST);

		}

	}

	@DeleteMapping
	public ResponseEntity<?> deleteMultipleTestimonialById(@RequestBody DeleteId id,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {

		try {
			testimonialInterface.deleteMultipleTestimonialById(id, userId);

			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.TESTIMONIAL_DELETED,
					SuccessMessageKey.TESTIMONIAL_M031803), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(
					new ErrorResponseDto(ErrorMessageCode.TESTIMONIAL_NOT_PRESENT, ErrorMessageKey.TESTIMONIAL_E031801),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('Testimonial_Update')")
	@PutMapping("/bulkStatusUpdate")
	public ResponseEntity<?> updateIsVisibleMultiSelect(@RequestBody VisibleContentDto visibleContentDto) {

		try {
			testimonialInterface.updateTestimonialIsVisibleMultiSelect(visibleContentDto);
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.TESTIMONIAL_UPDATED,
					SuccessMessageKey.TESTIMONIAL_M031803), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(
					new ErrorResponseDto(ErrorMessageCode.TESTIMONIAL_NOT_PRESENT, ErrorMessageKey.TESTIMONIAL_E031801),
					HttpStatus.BAD_REQUEST);
		}

	}
}
