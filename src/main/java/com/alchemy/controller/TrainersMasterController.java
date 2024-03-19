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
import com.alchemy.dto.SuccessResponseDto;
import com.alchemy.dto.TrainersMasterDto;
import com.alchemy.iListDto.IListTrainer;
import com.alchemy.serviceInterface.TrainersMasterInterface;
import com.alchemy.utils.ApiUrls;
import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.ErrorMessageKey;
import com.alchemy.utils.GlobalFunctions;
import com.alchemy.utils.SuccessMessageCode;
import com.alchemy.utils.SuccessMessageKey;

@RestController
@RequestMapping(ApiUrls.TRAINER)
public class TrainersMasterController {

	@Autowired
	private TrainersMasterInterface trainersMasterInterface;

	@PreAuthorize("hasRole('Trainer_Add')")
	@PostMapping()
	public ResponseEntity<?> addTrainer(@Valid TrainersMasterDto trainersMasterDto, HttpServletRequest request,
			@RequestParam(name = "file", required = false) @NotNull MultipartFile file,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) throws Exception {

		try {
			this.trainersMasterInterface.addTrainer(trainersMasterDto, userId, file, request);

			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.TRAINER_ADDED,
					SuccessMessageKey.TRAINER_M032901, trainersMasterDto), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.TRAINER_E032901),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('Trainer_Update')")
	@PutMapping("/{id}")
	public ResponseEntity<?> editTrainer(@PathVariable("id") Long trainerId, @Valid TrainersMasterDto trainersMasterDto,
			@RequestParam(name = "file", required = false) @NotNull MultipartFile file, HttpServletRequest request,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) throws Exception {

		try {
			this.trainersMasterInterface.editTrainer(trainerId, trainersMasterDto, userId, request, file);

			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.TRAINER_UPDATED,
					SuccessMessageKey.TRAINER_M032902, trainersMasterDto), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.TRAINER_E032901),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('Trainer_Delete')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteTrainer(@PathVariable("id") Long trainerId) throws Exception {

		try {
			this.trainersMasterInterface.deleteTrainer(trainerId);

			return new ResponseEntity<>(
					new SuccessResponseDto(SuccessMessageCode.TRAINER_DELETED, SuccessMessageKey.TRAINER_M032903),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.TRAINER_E032901),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('Trainer_List')")
	@GetMapping
	public ResponseEntity<?> getAllTrainers(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "", value = Constant.PAGENUMBER) String pageNo,
			@RequestParam(defaultValue = "", value = Constant.PAGESIZE) String pageSize) {
		try {

			if (!pageNo.isBlank() || !pageSize.isBlank() || !search.isBlank()) {
				String pageNumber = pageNo.isBlank() ? Constant.DEFAULT_PAGENUMBER : pageNo;
				String page = pageSize.isBlank() ? Constant.DEFAULT_PAGESIZE : pageSize;
				Page<IListTrainer> IListOfTrainers = this.trainersMasterInterface.getAllTrainers(search, pageNumber,
						page);

				PaginationResponse paginationResponse = new PaginationResponse();

				paginationResponse.setPageSize(IListOfTrainers.getSize());
				paginationResponse.setTotal(IListOfTrainers.getTotalElements());
				paginationResponse.setPageNumber(IListOfTrainers.getNumber() + 1);

				return new ResponseEntity<>(new ListResponseDto(IListOfTrainers.getContent(), paginationResponse),
						HttpStatus.OK);
			} else {

				List<IListTrainer> trainersList = this.trainersMasterInterface.findAllTrainers();
				return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.TRAINER_GET,
						SuccessMessageKey.TRAINER_M032904, trainersList), HttpStatus.OK);
			}
		} catch (Exception e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.TRAINER_E032901),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('Trainer_Detalis')")
	@GetMapping("/{id}")
	public ResponseEntity<?> getTrainerDetailsById(@PathVariable Long id) {
		try {
			IListTrainer details = this.trainersMasterInterface.findById(id);

			return new ResponseEntity<>(
					new SuccessResponseDto(SuccessMessageCode.SUCCESS, SuccessMessageKey.TRAINER_M032904, details),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.TRAINER_E032901),
					HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping
	public ResponseEntity<?> deleteMultipleTrainersById(@RequestBody DeleteId id,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {

		try {
			trainersMasterInterface.deleteMultipleTrainersById(id, userId);

			return new ResponseEntity<>(
					new SuccessResponseDto(SuccessMessageCode.TRAINER_DELETED, SuccessMessageKey.TRAINER_M032903),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(
					new ErrorResponseDto(ErrorMessageCode.TRAINER_NOT_PRESENT, ErrorMessageKey.TRAINER_E032901),
					HttpStatus.BAD_REQUEST);
		}

	}
}
