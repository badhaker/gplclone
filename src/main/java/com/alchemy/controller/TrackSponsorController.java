package com.alchemy.controller;

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

import com.alchemy.dto.ErrorResponseDto;
import com.alchemy.dto.ListResponseDto;
import com.alchemy.dto.PaginationResponse;
import com.alchemy.dto.SuccessResponseDto;
import com.alchemy.dto.TrackSponsorDto;
import com.alchemy.iListDto.IListTrackSponsor;
import com.alchemy.serviceInterface.TrackSponsorInterface;
import com.alchemy.utils.ApiUrls;
import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageKey;
import com.alchemy.utils.GlobalFunctions;
import com.alchemy.utils.SuccessMessageCode;
import com.alchemy.utils.SuccessMessageKey;

@RestController
@RequestMapping(ApiUrls.TRACKSPONSOR)
public class TrackSponsorController {

	@Autowired
	private TrackSponsorInterface trackSponsorInterface;

	@PreAuthorize("hasRole('TrackSponsor_Add')")
	@PostMapping
	public ResponseEntity<?> addTrackSponser(@Valid @RequestBody TrackSponsorDto trackSponserDto,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {
		try {
			TrackSponsorDto trackSponserDto2 = trackSponsorInterface.addTrackSponsor(userId, trackSponserDto);
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.TRACKSPONSOR_ADDED,
					SuccessMessageKey.TRACKSPONSOR_M033101, trackSponserDto2), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.SPONSER_E032701),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('TrackSponsor_Delete')")
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteSponserById(@PathVariable(name = "id") Long id,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {
		try {
			trackSponsorInterface.deleteTrackSponsor(id, userId);

			return ResponseEntity.ok(new SuccessResponseDto(SuccessMessageCode.TRACKSPONSOR_DELETED,
					SuccessMessageKey.TRACKSPONSOR_M033102, id));
		} catch (Exception e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.TRACKSPONSOR_E033101),
					HttpStatus.NOT_FOUND);

		}
	}

	@PreAuthorize("hasRole('TrackSponsor_List')")
	@GetMapping()
	public ResponseEntity<?> getAllTrackSponsors(
			@RequestParam(defaultValue = Constant.DEFAULT_PAGENUMBER, value = Constant.PAGENUMBER) String pageNo,
			@RequestParam(defaultValue = Constant.DEFAULT_PAGESIZE, value = Constant.PAGESIZE) String pageSize) {

		Page<IListTrackSponsor> list = this.trackSponsorInterface.getAllTrackSponsor(pageSize, pageNo, pageSize);

		PaginationResponse paginationResponse = new PaginationResponse();

		paginationResponse.setPageSize(list.getSize());
		paginationResponse.setTotal(list.getTotalElements());
		paginationResponse.setPageNumber(list.getNumber() + 1);

		return new ResponseEntity<>(new ListResponseDto(list.getContent(), paginationResponse), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('TrackSponsor_Add')")
	@PostMapping("/addTrackSponsor")
	public ResponseEntity<?> addTrackSponsers(@Valid @RequestBody TrackSponsorDto trackSponserDto,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {
		try {
			trackSponserDto = trackSponsorInterface.addTrackSponsors(trackSponserDto, userId);
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.TRACKSPONSOR_ADDED,
					SuccessMessageKey.TRACKSPONSOR_M033101, trackSponserDto), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.SPONSER_E032701),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("{id}")
	public ResponseEntity<?> updateTrackSponserById(@Valid @PathVariable("id") Long id,
			@RequestBody @Valid TrackSponsorDto trackSponserDto,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {

		try {
			trackSponserDto = this.trackSponsorInterface.updateTrackSponsor(trackSponserDto, id, userId);

			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.TRACKSPONSOR_UPDATED,
					SuccessMessageKey.TRACKSPONSOR_M033103, trackSponserDto), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.TRACKSPONSOR_E033101),
					HttpStatus.BAD_REQUEST);
		}
	}
}
