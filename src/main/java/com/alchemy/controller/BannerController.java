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

import com.alchemy.dto.BannerDto;
import com.alchemy.dto.DeleteId;
import com.alchemy.dto.ErrorResponseDto;
import com.alchemy.dto.IsVisibleDto;
import com.alchemy.dto.ListResponseDto;
import com.alchemy.dto.PaginationResponse;
import com.alchemy.dto.SuccessResponseDto;
import com.alchemy.dto.VisibleContentDto;
import com.alchemy.iListDto.IListBanner;
import com.alchemy.serviceInterface.BannerInterface;
import com.alchemy.utils.ApiUrls;
import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageKey;
import com.alchemy.utils.GlobalFunctions;
import com.alchemy.utils.SuccessMessageCode;
import com.alchemy.utils.SuccessMessageKey;

@RestController
@RequestMapping(ApiUrls.BANNER)
public class BannerController {

	@Autowired
	private BannerInterface bannerInterface;

	@PreAuthorize("hasRole('Banner_Add')")
	@PostMapping()
	public ResponseEntity<?> addBanner(@Valid BannerDto bannerDto,
			@RequestParam(name = "file", required = false) @NotNull MultipartFile file, HttpServletRequest request,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) throws Exception {

		try {

			BannerDto banner = this.bannerInterface.addBanner(bannerDto, file, request, userId);
			return new ResponseEntity<>(
					new SuccessResponseDto(SuccessMessageCode.BANNER_ADDED, SuccessMessageKey.BANNER_M032104, banner),
					HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.BANNER_E032101),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('Banner_Update')")
	@PutMapping("{id}")
	public ResponseEntity<?> updateBanner(@Valid BannerDto bannerDto, @PathVariable Long id,
			@RequestParam(name = "file", required = false) MultipartFile file, HttpServletRequest request,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {
		try {

			BannerDto banner = this.bannerInterface.updateBanner(bannerDto, id, file, request, userId);
			return new ResponseEntity<>(
					new SuccessResponseDto(SuccessMessageCode.BANNER_UPDATED, SuccessMessageKey.BANNER_M032103, banner),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.BANNER_E032101),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('Banner_List')")
	@GetMapping()
	public ResponseEntity<?> getAllBanners(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "", value = Constant.PAGENUMBER) String pageNo,
			@RequestParam(defaultValue = "", value = Constant.PAGESIZE) String pageSize) throws Exception {
		if (!pageNo.isBlank() || !pageSize.isBlank() || !search.isBlank()) {

			String pageNumber = pageNo.isBlank() ? Constant.DEFAULT_PAGENUMBER : pageNo;
			String pages = pageSize.isBlank() ? Constant.DEFAULT_PAGESIZE : pageSize;

			Page<IListBanner> page = this.bannerInterface.getAllBanners(search, pageNumber, pages);
			PaginationResponse paginationResponse = new PaginationResponse();

			paginationResponse.setPageSize(page.getSize());
			paginationResponse.setTotal(page.getTotalElements());
			paginationResponse.setPageNumber(page.getNumber() + 1);
			return new ResponseEntity<>(new ListResponseDto(page.getContent(), paginationResponse), HttpStatus.OK);

		} else {

			List<IListBanner> findAllList = bannerInterface.findAllList(search, IListBanner.class);
			return new ResponseEntity<>(
					new SuccessResponseDto(SuccessMessageCode.SUCCESS, SuccessMessageKey.BANNER_M032104, findAllList),
					HttpStatus.OK);
		}
	}

	@PreAuthorize("hasRole('Banner_Delete')")
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteBannerById(@PathVariable Long id) {

		try {
			bannerInterface.deleteBannerById(id);

			return new ResponseEntity<>(
					new SuccessResponseDto(SuccessMessageCode.BANNER_DELETED, SuccessMessageKey.BANNER_M032102),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.BANNER_E032101),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('Banner_Is_Visible')")
	@PatchMapping("/{id}")
	public ResponseEntity<?> updateBannerIsVisible(@PathVariable("id") Long id, @RequestBody IsVisibleDto dto) {
		try {
			this.bannerInterface.updateBannerIsVisible(id, dto);

			return new ResponseEntity<>(
					new SuccessResponseDto(SuccessMessageCode.BANNER_UPDATED, SuccessMessageKey.BANNER_M032103),
					HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.TESTIMONIAL_E031802),
					HttpStatus.BAD_REQUEST);

		}

	}

	@PreAuthorize("hasRole('Banner_Multi_Delete')")
	@DeleteMapping("/delete/ids")
	public ResponseEntity<?> deleteBannerByIds(@RequestBody DeleteId deleteIdsDto) {
		try {
			this.bannerInterface.deleteMultipleBannerByIds(deleteIdsDto);
			return new ResponseEntity<>(
					new SuccessResponseDto(SuccessMessageCode.BANNER_DELETED, SuccessMessageKey.BANNER_M032103),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.TESTIMONIAL_E031802),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('Banner_Update')")
	@PutMapping("/bulkStatusUpdate")
	public ResponseEntity<?> showContentIsVisibleByIds(@RequestBody VisibleContentDto visibleContentDto) {
		try {
			this.bannerInterface.isVisibleMultiSelect(visibleContentDto);
			return new ResponseEntity<>(new SuccessResponseDto("Updated succesfully", SuccessMessageKey.BANNER_M032103),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.TESTIMONIAL_E031802),
					HttpStatus.BAD_REQUEST);
		}
	}

}
