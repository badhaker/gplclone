package com.alchemy.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
import com.alchemy.dto.LearningTrackDto;
import com.alchemy.dto.LearningTrackUpdateDto;
import com.alchemy.dto.LearningTrackUpdateFileDto;
import com.alchemy.dto.ListResponseDto;
import com.alchemy.dto.PaginationResponse;
import com.alchemy.dto.SuccessResponseDto;
import com.alchemy.dto.VisibleContentDto;
import com.alchemy.iListDto.IListEnrollStatusCount;
import com.alchemy.iListDto.IListLearningTrack;
import com.alchemy.iListDto.IListLearningTrackDetail;
import com.alchemy.iListDto.IListUserDetails;
import com.alchemy.serviceInterface.LearningTrackInterface;
import com.alchemy.utils.ApiUrls;
import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.ErrorMessageKey;
import com.alchemy.utils.GlobalFunctions;
import com.alchemy.utils.SuccessMessageCode;
import com.alchemy.utils.SuccessMessageKey;

@RestController
@RequestMapping(ApiUrls.LEARNINGTRACK)
public class LearningTrackController {

	@Autowired
	private LearningTrackInterface learningTrackInterface;

	@PreAuthorize("hasRole('LearningTrack_Update')")
	@PutMapping("{id}")
	public ResponseEntity<?> updateLearningTrack(@Valid LearningTrackUpdateFileDto learningTrackDto,
			BindingResult bindingResult, @RequestParam(name = "brochure", required = false) MultipartFile brochure,
			@RequestParam(name = "banner", required = false) MultipartFile banner,
			@RequestParam(name = "bannerCard", required = false) MultipartFile bannerCard, HttpServletRequest request,
			@PathVariable Long id, @RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId,
			@RequestParam(name = "nudgedFile", required = false) MultipartFile nudgedFile) {
		if (bindingResult.hasErrors()) {
			List<String> details = new ArrayList<>();
			for (ObjectError error : bindingResult.getAllErrors()) {

				details.add(error.getDefaultMessage());
			}
			ErrorResponseDto error = new ErrorResponseDto();
			error.setMessage(details.get(0).split("\\*", 2)[0]);
			error.setMsgKey(details.get(0).split("\\*", 2)[1]);

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
		}
		try {

			LearningTrackUpdateFileDto learningTrackDto2 = this.learningTrackInterface.updateLearningTrack(
					learningTrackDto, id, brochure, banner, bannerCard, request, userId, nudgedFile);
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.LEARNING_TRACK_UPDATED,
					SuccessMessageKey.LEARNING_TRACK_M031703, learningTrackDto2), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.LEARNING_TRACK_E031701),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('LearningTrack_Delete')")
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteLearningTrackById(@PathVariable Long id,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {

		try {
			learningTrackInterface.deleteLearningTrackById(id, userId);

			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.LEARNING_TRACK_DELETED,
					SuccessMessageKey.LEARNING_TRACK_M031702), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(ErrorMessageCode.LEARNING_TRACK_NOT_FOUND,
					ErrorMessageKey.LEARNING_TRACK_E031701), HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('LearningTrack_List')")
	@GetMapping()
	public ResponseEntity<?> getAllLearningTracks(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "", value = Constant.PAGENUMBER) String pageNo,
			@RequestParam(defaultValue = "", value = Constant.PAGESIZE) String pageSize,
			@RequestParam(defaultValue = "", value = Constant.PAGESIZE) String fromAdmin,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_PERMISSIONS) ArrayList<String> permissiosName)
			throws Exception {

		Page<IListLearningTrack> iListlearningTrack = this.learningTrackInterface.getAllLearningTracks(search, userId,
				pageNo, pageSize, permissiosName, fromAdmin);
		PaginationResponse paginationResponse = new PaginationResponse();

		paginationResponse.setPageSize(iListlearningTrack.getSize());
		paginationResponse.setTotal(iListlearningTrack.getTotalElements());
		paginationResponse.setPageNumber(iListlearningTrack.getNumber() + 1);

		return new ResponseEntity<>(new ListResponseDto(iListlearningTrack.getContent(), paginationResponse),
				HttpStatus.OK);

	}

	@PreAuthorize("hasRole('LearningTrack_Add')")
	@PostMapping
	public ResponseEntity<?> addLearningTrackTrainerAndSponsor(@Valid LearningTrackDto learningTrackDto,
			BindingResult bindingResult, @RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId,
			@RequestParam(name = "brochure", required = false) MultipartFile brochure,
			@RequestParam(name = "banner", required = false) MultipartFile banner,
			@RequestParam(name = "bannerCard", required = false) MultipartFile bannerCard, HttpServletRequest request,
			@RequestParam(name = "nudgedFile", required = false) MultipartFile nudgedFile) {
		if (bindingResult.hasErrors()) {
			List<String> details = new ArrayList<>();
			for (ObjectError error : bindingResult.getAllErrors()) {

				details.add(error.getDefaultMessage());
			}
			ErrorResponseDto error = new ErrorResponseDto();
			error.setMessage(details.get(0).split("\\*", 2)[0]);
			error.setMsgKey(details.get(0).split("\\*", 2)[1]);

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
		}

		try {
			LearningTrackDto learningTrackDto2 = learningTrackInterface.addLearningTrackTrainerAndSponsor(
					learningTrackDto, brochure, banner, bannerCard, request, userId, nudgedFile);
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.LEARNING_TRACK_ADDED,
					SuccessMessageKey.LEARNING_TRACK_M031704, learningTrackDto2), HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.LEARNING_TRACK_E031701),
					HttpStatus.BAD_REQUEST);
		}

	}

//	@PreAuthorize("hasRole('LearningTrack_List')")
//	@GetMapping(ApiUrls.ALL)
//	public ResponseEntity<?> getLearningTrackDropdown() {
//		List<LearningTrackDropdownList> list = this.learningTrackInterface.findAllLearningTrackDropdown();
//		return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.LEARNING_TRACK_GET,
//				SuccessMessageKey.LEARNING_TRACK_M031701, list), HttpStatus.OK);
//	}

	@PreAuthorize("hasRole('LearningTrack_Details')")
	@GetMapping("/enroll-count")
	public ResponseEntity<?> getEnrollStatusCount() {
		try {
			IListEnrollStatusCount iListEnrollStatusCount = this.learningTrackInterface.getEnrollStatusCount();
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.ENROLL_STATUS_GET,
					SuccessMessageKey.ENROLL_STATUS_M031701, iListEnrollStatusCount), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.ENROLL_STATUS_E031801),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('LearningTrack_DetailsById')")
	@GetMapping("/{id}")
	public ResponseEntity<?> getLearningTrackDeatilById(@PathVariable("id") Long id) {
		try {
			IListLearningTrackDetail datail = this.learningTrackInterface.findById(id);
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.LEARNING_TRACK_GET,
					SuccessMessageKey.LEARNING_TRACK_M031701, datail), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.LEARNING_TRACK_E031701),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('LearningTrack_BulkUpload')")
	@PostMapping("/uploadTracks")
	public ResponseEntity<?> learningTrackBulkUpload(@RequestParam(name = "file", required = false) MultipartFile file,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId,
			@RequestParam(defaultValue = "") String moduleName) {

		try {

			Long bulkInfoId = learningTrackInterface.learningTrackBulkUpload(file, userId, moduleName);
			learningTrackInterface.bulkUploadInLearningTrack(bulkInfoId, userId);
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.LEARNING_TRACK_FILE_UPLOADED,
					SuccessMessageKey.LEARNING_TRACK_M031701), HttpStatus.OK);

		} catch (Exception e) {
			e.getStackTrace();
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.LEARNING_TRACK_E031701),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('LearningTrack_UserEnroll')")
	@GetMapping("/enroll-list")
	public ResponseEntity<?> getUserEnroll(@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {
		try {
			IListUserDetails iListUserEnroll = this.learningTrackInterface.getUserEnroll(userId);
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.ENROLL_STATUS_GET,
					SuccessMessageKey.ENROLL_STATUS_M031701, iListUserEnroll), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.ENROLL_STATUS_E031801),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> learningTrackUpdate(@PathVariable("id") Long id, @RequestBody LearningTrackUpdateDto dto,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {
		try {
			this.learningTrackInterface.learningTrackIsVisibleOrNot(id, dto, userId);
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.LEARNING_TRACK_STATUS_UPDATED,
					SuccessMessageKey.LEARNING_TRACK_M031705), HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.LEARNING_TRACK_E031701),
					HttpStatus.BAD_REQUEST);

		}

	}

	@PreAuthorize("hasRole('Multi_Delete_In_LT')")
	@DeleteMapping
	public ResponseEntity<?> deleteMultipleTracksById(@RequestBody DeleteId id,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {

		try {
			learningTrackInterface.deleteMultipleTracksById(id, userId);

			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.LEARNING_TRACK_DELETED,
					SuccessMessageKey.LEARNING_TRACK_M031702), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(ErrorMessageCode.LEARNING_TRACK_NOT_FOUND,
					ErrorMessageKey.LEARNING_TRACK_E031701), HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('LearningTrack_Update')")
	@PutMapping("/bulkStatusUpdate")
	public ResponseEntity<?> showContentIsVisibleByIds(@RequestBody VisibleContentDto visibleContentDto) {
		try {
			this.learningTrackInterface.updateIsVisibleForLearningTrackMultiSelect(visibleContentDto);
			return new ResponseEntity<>(
					new SuccessResponseDto("Updated succesfully", SuccessMessageKey.LEARNING_TRACK_M031702),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.LEARNING_TRACK_E031701),
					HttpStatus.BAD_REQUEST);
		}
	}

}
