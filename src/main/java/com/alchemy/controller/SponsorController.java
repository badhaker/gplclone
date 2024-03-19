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
import com.alchemy.dto.ListResponseDto;
import com.alchemy.dto.PaginationResponse;
import com.alchemy.dto.SponsorDto;
import com.alchemy.dto.SuccessResponseDto;
import com.alchemy.iListDto.IListSponsorDto;
import com.alchemy.serviceInterface.SponsorInterface;
import com.alchemy.utils.ApiUrls;
import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.ErrorMessageKey;
import com.alchemy.utils.GlobalFunctions;
import com.alchemy.utils.SuccessMessageCode;
import com.alchemy.utils.SuccessMessageKey;

@RestController
@RequestMapping(ApiUrls.SPONSOR)
public class SponsorController {

	@Autowired
	private SponsorInterface sponserInterface;

	@PreAuthorize("hasRole('Sponsor_Add')")
	@PostMapping
	public ResponseEntity<?> addSponser(@Valid SponsorDto sponserDto,
			@RequestParam(name = "file", required = false) @NotNull MultipartFile file, HttpServletRequest request,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {
		try {
			SponsorDto sponserDto2 = sponserInterface.addSponser(sponserDto, userId, file, request);
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.SPONSOR_ADDED,
					SuccessMessageKey.SPONSOR_M032701, sponserDto2), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.SPONSER_E032701),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('Sponsor_Delete')")
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteSponserById(@PathVariable(name = "id") Long id,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {
		try {
			sponserInterface.deleteSponser(id, userId);

			return ResponseEntity.ok(
					new SuccessResponseDto(SuccessMessageCode.SPONSOR_DELETED, SuccessMessageKey.SPONSOR_M032702, id));
		} catch (Exception e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.SPONSER_E032701),
					HttpStatus.NOT_FOUND);

		}
	}

	@PreAuthorize("hasRole('Sponsor_Update')")
	@PutMapping("{id}")
	public ResponseEntity<?> updateSponserById(@PathVariable("id") Long id, @Valid SponsorDto sponserDto,
			@RequestParam(name = "file", required = false) @NotNull MultipartFile file, HttpServletRequest request,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {

		try {

			SponsorDto sponserDto2 = this.sponserInterface.updateSponser(sponserDto, id, userId, file, request);

			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.SPONSOR_UPDATED,
					SuccessMessageKey.SPONSOR_M032703, sponserDto2), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.SPONSER_E032701),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('Sponsor_List')")
	@GetMapping()
	public ResponseEntity<?> getAllSponsors(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "", value = Constant.PAGENUMBER) String pageNo,
			@RequestParam(defaultValue = "", value = Constant.PAGESIZE) String pageSize) throws Exception {
		try {
			if (!pageNo.isBlank() || !pageSize.isBlank() || !search.isBlank()) {
				String pageNumber = pageNo.isBlank() ? Constant.DEFAULT_PAGENUMBER : pageNo;
				String pageSiz = pageSize.isBlank() ? Constant.DEFAULT_PAGESIZE : pageSize;

				Page<IListSponsorDto> page = this.sponserInterface.getAllSponsers(search, pageNumber, pageSiz);
				PaginationResponse paginationResponse = new PaginationResponse();

				paginationResponse.setPageSize(page.getSize());
				paginationResponse.setTotal(page.getTotalElements());
				paginationResponse.setPageNumber(page.getNumber() + 1);
				return new ResponseEntity<>(new ListResponseDto(page.getContent(), paginationResponse), HttpStatus.OK);
			} else {

				List<IListSponsorDto> sponsorsList = this.sponserInterface.findAllSponsors(IListSponsorDto.class);
				return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.SPONSORS_FETCHED,
						SuccessMessageKey.SPONSOR_M032704, sponsorsList), HttpStatus.OK);
			}
		} catch (Exception e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.SPONSER_E032701),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('Sponsor_Details')")
	@GetMapping("/{id}")
	public ResponseEntity<?> getSponsorDetailsById(@PathVariable Long id) {
		try {
			IListSponsorDto details = this.sponserInterface.findById(id);

			return new ResponseEntity<>(
					new SuccessResponseDto(SuccessMessageCode.SUCCESS, SuccessMessageKey.SPONSOR_M032704, details),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.SPONSER_E032701),
					HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping
	public ResponseEntity<?> deleteMultipleSponsorsById(@RequestBody DeleteId id,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {

		try {
			sponserInterface.deleteMultipleSponsorsById(id, userId);

			return new ResponseEntity<>(
					new SuccessResponseDto(SuccessMessageCode.SPONSOR_DELETED, SuccessMessageKey.SPONSOR_M032702),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(
					new ErrorResponseDto(ErrorMessageCode.SPONSOR_NOT_FOUND, ErrorMessageKey.SPONSER_E032701),
					HttpStatus.BAD_REQUEST);
		}

	}

}
