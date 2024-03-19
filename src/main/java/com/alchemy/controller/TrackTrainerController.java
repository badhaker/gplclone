package com.alchemy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alchemy.dto.ErrorResponseDto;
import com.alchemy.dto.ListResponseDto;
import com.alchemy.dto.PaginationResponse;
import com.alchemy.dto.SuccessResponseDto;
import com.alchemy.dto.TrackTrainerDto;
import com.alchemy.iListDto.IListTrackTrainer;
import com.alchemy.serviceInterface.TrackTrainerInterface;
import com.alchemy.utils.ApiUrls;
import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageKey;
import com.alchemy.utils.GlobalFunctions;
import com.alchemy.utils.SuccessMessageCode;
import com.alchemy.utils.SuccessMessageKey;

@RestController
@RequestMapping(ApiUrls.TRACK_TRAINER)
public class TrackTrainerController {

	@Autowired
	private TrackTrainerInterface trackTrainerInterface;

	@PreAuthorize("hasRole('TrackTrainer_Add')")
	@PostMapping
	public ResponseEntity<?> addTrainerToLearningTrack(@RequestBody TrackTrainerDto trackTrainerDto,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {
		try {
			trackTrainerInterface.addTrainerToTrack(trackTrainerDto, userId);

			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.TRACK_TRAINER_ADDED,
					SuccessMessageKey.TRACK_TRAINER_M033201, trackTrainerDto), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.TRACK_TRAINER_E033201),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('TrackTrainer_Delete')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteTrainerToLearningTrack(@PathVariable("id") Long id) {
		try {
			trackTrainerInterface.deleteTrackTrainer(id);

			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.TRACK_TRAINER_DELETED,
					SuccessMessageKey.TRACK_TRAINER_M033203), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.TRACK_TRAINER_E033202),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('TrackTrainer_List')")
	@GetMapping()
	public ResponseEntity<?> getAllTrackTrainers(
			@RequestParam(defaultValue = Constant.DEFAULT_PAGENUMBER, value = Constant.PAGENUMBER) String pageNo,
			@RequestParam(defaultValue = Constant.DEFAULT_PAGESIZE, value = Constant.PAGESIZE) String pageSize) {

		Page<IListTrackTrainer> trackTrainerList = this.trackTrainerInterface.findAllTrackTrainers(pageNo, pageSize);

		PaginationResponse paginationResponse = new PaginationResponse();

		paginationResponse.setPageSize(trackTrainerList.getSize());
		paginationResponse.setPageNumber(trackTrainerList.getNumber() + 1);
		paginationResponse.setTotal(trackTrainerList.getTotalElements());

		return new ResponseEntity<>(new ListResponseDto(trackTrainerList.getContent(), paginationResponse),
				HttpStatus.OK);

	}

}
