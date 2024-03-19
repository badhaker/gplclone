package com.alchemy.controller;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.multipart.MultipartFile;

import com.alchemy.dto.CareerTracksDto;
import com.alchemy.dto.DeleteId;
import com.alchemy.dto.ErrorResponseDto;
import com.alchemy.dto.ListResponseDto;
import com.alchemy.dto.PaginationResponse;
import com.alchemy.dto.SuccessResponseDto;
import com.alchemy.iListDto.IListCareerTracks;
import com.alchemy.serviceInterface.CareerTracksInterface;
import com.alchemy.utils.ApiUrls;
import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.ErrorMessageKey;
import com.alchemy.utils.GlobalFunctions;
import com.alchemy.utils.SuccessMessageCode;
import com.alchemy.utils.SuccessMessageKey;

@RestController
@RequestMapping(ApiUrls.CAREERTRACKS)
public class CareerTracksController {

	@Autowired
	public CareerTracksInterface careerTracksInterface;

	@PreAuthorize("hasRole('CareerTrack_Add')")
	@PostMapping()
	public ResponseEntity<?> addCareer(@Valid CareerTracksDto careerTracksDto,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId,
			@RequestParam(name = "file", required = false) MultipartFile file,
			@RequestParam(name = "thumbnailFile", required = false) MultipartFile thumbnailFile,
			HttpServletRequest request) {

		try {

			CareerTracksDto careerTracks = careerTracksInterface.addCareerTracks(careerTracksDto, userId, file,
					thumbnailFile, request);
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.CAREER_TRACKS_ADDED_SUCCESSFULLY,
					SuccessMessageKey.CAREERTRACKS_M033601, careerTracks), HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.CAREERTRACKS_E033606),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('CareerTrack_Update')")
	@PutMapping("{id}")
	public ResponseEntity<?> updateCareerTrack(@Valid CareerTracksDto careerTracksDto,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId,
			@RequestParam(name = "file", required = false) MultipartFile file,
			@RequestParam(name = "thumbnailFile", required = false) MultipartFile thumbnailFile,
			HttpServletRequest request, @PathVariable("id") Long id) {

		try {

			CareerTracksDto careerTracks = careerTracksInterface.updateCareerTracks(careerTracksDto, userId, request,
					id, file, thumbnailFile);
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.CAREER_TRACKS_UPDATED_SUCCESSFULLY,
					SuccessMessageKey.CAREERTRACKS_M033602, careerTracks), HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.CAREERTRACKS_E033606),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('CareerTrack_Delete')")
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteCareerTracks(@PathVariable(name = "id") Long id,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {
		try {
			careerTracksInterface.deleteCareerTracks(id, userId);

			return ResponseEntity.ok(new SuccessResponseDto(SuccessMessageCode.CAREER_TRACKS_DELETED_SUCCESSFULLY,
					SuccessMessageKey.CAREERTRACKS_M033603, id));
		} catch (Exception e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.CAREERTRACKS_E033607),
					HttpStatus.NOT_FOUND);

		}
	}

	@PreAuthorize("hasRole('CareerTrack_List')")
	@GetMapping
	public ResponseEntity<?> getAllCareerTracks(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "", value = Constant.PAGENUMBER) String pageNo,
			@RequestParam(defaultValue = "", value = Constant.PAGESIZE) String pageSize) throws Exception {

		Page<IListCareerTracks> page = this.careerTracksInterface.getAllCareerTracks(search, pageNo, pageSize);

		PaginationResponse paginationResponse = new PaginationResponse();

		paginationResponse.setPageSize(page.getSize());
		paginationResponse.setTotal(page.getTotalElements());
		paginationResponse.setPageNumber(page.getNumber() + 1);

		return new ResponseEntity<>(new ListResponseDto(page.getContent(), paginationResponse), HttpStatus.OK);

	}

	@PreAuthorize("hasRole('Multi_Delete_In_CT')")
	@DeleteMapping
	public ResponseEntity<?> deleteMultipleTracksById(@RequestBody DeleteId ids,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {

		try {

			careerTracksInterface.deleteMultiplecareertracksById(ids, userId);

			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.CAREER_TRACKS_DELETED_SUCCESSFULLY,
					SuccessMessageKey.CAREERTRACKS_M033603), HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity<>(new ErrorResponseDto(ErrorMessageCode.CAREER_TRACKS_NOT_FOUND,
					ErrorMessageKey.CAREERTRACKS_E033606), HttpStatus.BAD_REQUEST);
		}

	}

}
