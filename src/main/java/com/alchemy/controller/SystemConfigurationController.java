package com.alchemy.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alchemy.dto.ConfigPatchDto;
import com.alchemy.dto.ErrorResponseDto;
import com.alchemy.dto.FooterTextDto;
import com.alchemy.dto.SuccessResponseDto;
import com.alchemy.entities.SystemConfiguration;
import com.alchemy.iListDto.IListFooterDocumentDto;
import com.alchemy.repositories.SystemConfigurationRepository;
import com.alchemy.serviceInterface.SystemConfigurationInterface;
import com.alchemy.utils.ApiUrls;
import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageKey;
import com.alchemy.utils.SuccessMessageCode;
import com.alchemy.utils.SuccessMessageKey;

@RestController
@RequestMapping(ApiUrls.SYSTEM_CONFIG)
public class SystemConfigurationController {

	@Autowired
	SystemConfigurationRepository systemConfigurationRepository;

	@Autowired
	SystemConfigurationInterface systemConfigurationInterface;

	@Value("${saml.certificate}")
	private String SamlValue;

	final ArrayList<String> keyArray = new ArrayList<String>(
			Arrays.asList(Constant.CAREER_ASPIRATION, Constant.SAML_CERTIFICATE, Constant.ABOUT_US_PDF,
					Constant.FAQ_PDF, Constant.POLICY_PDF, Constant.ALCHEMIZE_LEFT_TEXT, Constant.ALCHEMIZE_RIGHT_TEXT,
					Constant.GPL_INTRO_TEXT, Constant.ARCHITECTURE_IMAGE_WEB, Constant.ARCHITECTURE_IMAGE_MOB,
					Constant.GOGREJ_CAPABILITY_FACTORS_IMAGE, Constant.DEFAULT_TRACK_IMAGE));

	@PostConstruct
	public void addBannerLimit() {
		SystemConfiguration findBykey = this.systemConfigurationRepository.findBykey(Constant.BANNER_LIMIT);
		if (findBykey == null) {
			this.systemConfigurationInterface.showBannerValidation();
		}

	}

	@PostConstruct
	public void add() {

		ArrayList<String> data = systemConfigurationRepository.getAllRows();

		ArrayList<SystemConfiguration> list = new ArrayList<>();

		for (int i = 0; i < keyArray.size(); i++) {

			final boolean existInDB = data.contains(keyArray.get(i));

			if (!existInDB) {
				SystemConfiguration newConfiguration = new SystemConfiguration();

				switch (keyArray.get(i)) {
				case Constant.CAREER_ASPIRATION:

					newConfiguration.setKey(Constant.CAREER_ASPIRATION);
					newConfiguration.setFlag(true);

					break;

				case Constant.SAML_CERTIFICATE:

					newConfiguration.setKey(Constant.SAML_CERTIFICATE);
					newConfiguration.setFlag(true);
					newConfiguration.setValue(SamlValue);

					break;

				case Constant.ABOUT_US_PDF:

					newConfiguration.setKey(Constant.ABOUT_US_PDF);
					newConfiguration.setFlag(true);

					break;

				case Constant.FAQ_PDF:

					newConfiguration.setKey(Constant.FAQ_PDF);
					newConfiguration.setFlag(true);

					break;

				case Constant.POLICY_PDF:

					newConfiguration.setKey(Constant.POLICY_PDF);
					newConfiguration.setFlag(true);

					break;

				case Constant.ALCHEMIZE_LEFT_TEXT:

					newConfiguration.setKey(Constant.ALCHEMIZE_LEFT_TEXT);
					newConfiguration.setFlag(true);

					break;

				case Constant.ALCHEMIZE_RIGHT_TEXT:

					newConfiguration.setKey(Constant.ALCHEMIZE_RIGHT_TEXT);
					newConfiguration.setFlag(true);

					break;
				case Constant.GPL_INTRO_TEXT:

					newConfiguration.setKey(Constant.GPL_INTRO_TEXT);
					newConfiguration.setFlag(true);

					break;

				case Constant.ARCHITECTURE_IMAGE_WEB:

					newConfiguration.setKey(Constant.ARCHITECTURE_IMAGE_WEB);
					newConfiguration.setFlag(true);

					break;
				case Constant.ARCHITECTURE_IMAGE_MOB:

					newConfiguration.setKey(Constant.ARCHITECTURE_IMAGE_MOB);
					newConfiguration.setFlag(true);

					break;

				case Constant.GOGREJ_CAPABILITY_FACTORS_IMAGE:
					newConfiguration.setKey(Constant.GOGREJ_CAPABILITY_FACTORS_IMAGE);
					newConfiguration.setFlag(true);
					break;

				case Constant.DEFAULT_TRACK_IMAGE:
					newConfiguration.setKey(Constant.DEFAULT_TRACK_IMAGE);
					newConfiguration.setFlag(true);
					break;
				}

				list.add(newConfiguration);
			}
		}

		systemConfigurationRepository.saveAll(list);

	}

	@PreAuthorize("hasRole('Config_Patch')")
	@PatchMapping("/career-aspiration")
	public ResponseEntity<?> addConfig(@RequestBody @Valid ConfigPatchDto dto) {
		try {
			systemConfigurationInterface.updateCareerAspirationFlag(dto);

			return new ResponseEntity<>(
					new SuccessResponseDto(SuccessMessageCode.UPDATED, SuccessMessageKey.SYSTEM_CONFIG_M033901),
					HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.LEARNING_TRACK_E031701),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('Footer_Document_Update')")
	@PutMapping("/footer-document")
	public ResponseEntity<?> addPdf(@RequestParam(name = "file", required = false) MultipartFile file,
			HttpServletRequest request, @RequestParam(defaultValue = "") String key) {

		try {
			systemConfigurationInterface.addPdf(file, request, key);
			return new ResponseEntity<>(
					new SuccessResponseDto(SuccessMessageCode.PDF_UPDATED, SuccessMessageKey.SYSTEM_CONFIG_M033901),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.FILE_UPLOAD_E03701),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('Footer_Text_Update')")
	@PutMapping("/footer-Text")
	public ResponseEntity<?> addFooterText(@Valid @RequestBody FooterTextDto footerTextDto) {
		try {

			systemConfigurationInterface.addFooterText(footerTextDto);
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.FOOTER_TEXT_ADDED,
					SuccessMessageKey.SYSTEM_CONFIG_M033901), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.FOOTER_TEXT_E0311101),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('Footer_Document_Edit')")
	@PatchMapping("/footer-document")
	public ResponseEntity<?> updateFlag(@Valid @RequestParam(defaultValue = "") String key,
			@RequestParam Boolean flag) {
		try {
			systemConfigurationInterface.updateFlag(key, flag);
			return new ResponseEntity<>(
					new SuccessResponseDto(SuccessMessageCode.FLAG_UPDATED, SuccessMessageKey.SYSTEM_CONFIG_M033901),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.FILE_UPLOAD_E03701),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('Footer_Document_List')")
	@GetMapping("/footer-document")
	public ResponseEntity<?> getAllPdfUrl() throws Exception {
		try {
			List<IListFooterDocumentDto> list = this.systemConfigurationInterface.getAllPdf();

			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageCode.SUCCESS,
					SuccessMessageKey.FOOTER_DOCUMENT_M0311103, list), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageKey.FOOTER_DOCUMENT_E0311101),
					HttpStatus.BAD_REQUEST);
		}

	}

}
