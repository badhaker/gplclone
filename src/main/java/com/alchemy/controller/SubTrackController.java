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

import com.alchemy.dto.DeleteId;
import com.alchemy.dto.ErrorResponseDto;
import com.alchemy.dto.ListResponseDto;
import com.alchemy.dto.PaginationResponse;
import com.alchemy.dto.SubTrackDto;
import com.alchemy.dto.SubTrackUpdateDto;
import com.alchemy.dto.SuccessResponseDto;
import com.alchemy.iListDto.IListSubTrack;
import com.alchemy.serviceInterface.SubTrackInterface;
import com.alchemy.utils.ApiUrls;
import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageKey;
import com.alchemy.utils.GlobalFunctions;
import com.alchemy.utils.SuccessMessageCode;
import com.alchemy.utils.SuccessMessageKey;

@RestController
@RequestMapping(ApiUrls.SUBTRACK)
public class SubTrackController {

	@Autowired
	private SubTrackInterface subTrackInterface;

	@PreAuthorize("hasRole('SubTrack_Add')")
	@PostMapping()
	public ResponseEntity<?> addSubTrack(@Valid @RequestBody SubTrackDto subTrackDto,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) throws Exception {

		try {
			this.subTrackInterface.addSubTrack(subTrackDto, userId);
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.SUBTRACK_ADDED,
					SuccessMessageKey.SUB_TRACK_M033304, subTrackDto), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.SUBTRACK_E033301),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('SubTrack_Update')")
	@PutMapping("/{id}")
	public ResponseEntity<?> editSubTrack(@Valid @RequestBody SubTrackUpdateDto subTrackDto, @PathVariable Long id,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) throws Exception {

		try {
			this.subTrackInterface.editSubTrack(subTrackDto, userId, id);

			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.SUBTRACK_UPDATED,
					SuccessMessageKey.SUB_TRACK_M033303, subTrackDto), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.SUBTRACK_E033301),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('SubTrack_List')")
	@GetMapping()
	public ResponseEntity<?> getAllSubTrack(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "") String learningTrackId,
			@RequestParam(defaultValue = "", value = Constant.PAGENUMBER) String pageNo,
			@RequestParam(defaultValue = "", value = Constant.PAGESIZE) String pageSize) {
		try {
			if (!pageNo.isBlank() || !pageSize.isBlank() || !search.isBlank()) {
				String pageNumber = pageNo.isBlank() ? Constant.DEFAULT_PAGENUMBER : pageNo;
				String pages = pageSize.isBlank() ? Constant.DEFAULT_PAGESIZE : pageSize;

				Page<IListSubTrack> page = this.subTrackInterface.getAllSubTrack(search, learningTrackId, pageNumber,
						pages);

				PaginationResponse paginationResponse = new PaginationResponse();

				paginationResponse.setPageSize(page.getSize());
				paginationResponse.setTotal(page.getTotalElements());
				paginationResponse.setPageNumber(page.getNumber() + 1);

				return new ResponseEntity<>(new ListResponseDto(page.getContent(), paginationResponse), HttpStatus.OK);
			} else {

				List<IListSubTrack> list = this.subTrackInterface.getAllSubTracks(learningTrackId, IListSubTrack.class);
				return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.SUBTRACK_GET,
						SuccessMessageKey.SUB_TRACK_M033301, list), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.SUBTRACK_E033302),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('SubTrack_Delete')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteSubTrack(@PathVariable("id") Long id) throws Exception {

		try {
			this.subTrackInterface.deleteSubTrack(id);

			return new ResponseEntity<>(
					new SuccessResponseDto(SuccessMessageCode.SUBTRACK_DELETED, SuccessMessageKey.SUB_TRACK_M033302),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.SUBTRACK_E033302),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('SubTrack_Delete')")
	@DeleteMapping()
	public ResponseEntity<?> deleteMultipleSubTrack(@RequestBody DeleteId ids,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId
			)
	{
		try {
			
			this.subTrackInterface.multiDeleteSubTrack(ids, userId);
			
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.SUBTRACK_DELETED, SuccessMessageKey.SUB_TRACK_M033302),HttpStatus.OK);
			
		}
		catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.SUBTRACK_E033302),
					HttpStatus.BAD_REQUEST);
		}
		
	}
	

}
