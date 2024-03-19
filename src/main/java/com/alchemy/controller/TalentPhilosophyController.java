package com.alchemy.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alchemy.dto.ErrorResponseDto;
import com.alchemy.dto.ListResponseDto;
import com.alchemy.dto.PaginationResponse;
import com.alchemy.dto.SuccessResponseDto;
import com.alchemy.dto.TalentPhilosophyDto;
import com.alchemy.iListDto.IListTalentPhilosophy;
import com.alchemy.serviceInterface.TalentPhilosophyInterface;
import com.alchemy.utils.ApiUrls;
import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageKey;
import com.alchemy.utils.GlobalFunctions;
import com.alchemy.utils.SuccessMessageCode;
import com.alchemy.utils.SuccessMessageKey;

@RestController
@RequestMapping(ApiUrls.TALENT_PHILOSOPHY)
public class TalentPhilosophyController {

	@Autowired
	private TalentPhilosophyInterface talentPhilosophyInterface;

	@PreAuthorize("hasRole('TalentPhilosophy_Add')")
	@PostMapping()
	public ResponseEntity<?> addTalentPhilosophy(@Valid TalentPhilosophyDto talentPhilosophyDto,
			@RequestParam(name = "file", required = false) MultipartFile file, HttpServletRequest request,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {

		try {
			TalentPhilosophyDto talentPhilosophy = talentPhilosophyInterface.addTalentPhilosophy(talentPhilosophyDto,
					file, request, userId);
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.TALENT_PHILOSOPHY_ADDED,
					SuccessMessageKey.TALENT_PHILOSOPHY_M033504, talentPhilosophy), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.TALENT_PHILOSOPHY_E033501),
					HttpStatus.BAD_REQUEST);

		}
	}

	@PreAuthorize("hasRole('TalentPhilosophy_List')")
	@GetMapping()
	public ResponseEntity<?> getAllTalentPhilosophy(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = Constant.DEFAULT_PAGENUMBER, value = Constant.PAGENUMBER) String pageNo,
			@RequestParam(defaultValue = Constant.DEFAULT_PAGESIZE, value = Constant.PAGESIZE) String pageSize)
			throws Exception {

		Page<IListTalentPhilosophy> talentPhilosophyEntity = this.talentPhilosophyInterface
				.getAlltalentPhilosophy(search, pageNo, pageSize);

		PaginationResponse paginationResponse = new PaginationResponse();

		paginationResponse.setPageSize(talentPhilosophyEntity.getSize());
		paginationResponse.setTotal(talentPhilosophyEntity.getTotalElements());
		paginationResponse.setPageNumber(talentPhilosophyEntity.getNumber() + 1);

		return new ResponseEntity<>(new ListResponseDto(talentPhilosophyEntity.getContent(), paginationResponse),
				HttpStatus.OK);
	}
}
