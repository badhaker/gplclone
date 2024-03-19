package com.alchemy.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alchemy.dto.ConfigPatchDto;
import com.alchemy.dto.FooterTextDto;
import com.alchemy.entities.FileUploadEntity;
import com.alchemy.entities.SystemConfiguration;
import com.alchemy.exceptionHandling.ResourceNotFoundException;
import com.alchemy.iListDto.IListFooterDocumentDto;
import com.alchemy.repositories.SystemConfigurationRepository;
import com.alchemy.serviceInterface.FileUploadInterface;
import com.alchemy.serviceInterface.SystemConfigurationInterface;
import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.Validator;

@Service
public class SystemConfigutationServiceImpl implements SystemConfigurationInterface {

	@Autowired
	SystemConfigurationRepository systemConfigurationRepository;

	@Autowired
	FileUploadInterface fileUploadInterface;

	@Override
	public void updateCareerAspirationFlag(ConfigPatchDto configPatchDto) {
		// TODO Auto-generated method stub
		SystemConfiguration configuration = systemConfigurationRepository.findByKey(Constant.CAREER_ASPIRATION);
		configuration.setFlag(configPatchDto.getFlag());
		systemConfigurationRepository.save(configuration);
	}

	@Override
	public void addPdf(MultipartFile file, HttpServletRequest request, String key) throws Exception {

		if (key.isBlank()) {
			throw new ResourceNotFoundException(ErrorMessageCode.KEY_REQUIRED);

		}

		if (key.equals(Constant.CAREER_ASPIRATION) || key.equals(Constant.SAML_CERTIFICATE)) {
			throw new ResourceNotFoundException(ErrorMessageCode.UPLOAD_KEY);
		}

		if (file != null) {

			Boolean checkKey = key.equals(Constant.ARCHITECTURE_IMAGE_WEB)
					|| key.equals(Constant.ARCHITECTURE_IMAGE_MOB);

			if (!checkKey) {

				if (key.equals(Constant.GOGREJ_CAPABILITY_FACTORS_IMAGE) || key.equals(Constant.DEFAULT_TRACK_IMAGE)) {

					if (!Validator.isValidforImageFile(file.getOriginalFilename())) {
						throw new IllegalArgumentException(ErrorMessageCode.VALID_IMAGE);
					}
				} else if (!file.getOriginalFilename().endsWith(".pdf")) {

					throw new ResourceNotFoundException(ErrorMessageCode.UPLOAD_PDF);

				}

			}

			SystemConfiguration systemConfiguration = systemConfigurationRepository.findBykey(key);
			if (systemConfiguration == null) {
				throw new ResourceNotFoundException(ErrorMessageCode.KEY_NOT_FOUND);
			}
			FileUploadEntity fileId = systemConfiguration.getFileId();
			FileUploadEntity fileUploadEntity = fileUploadInterface.storeFile(file, request);
			systemConfiguration.setFileId(fileUploadEntity);
			systemConfigurationRepository.save(systemConfiguration);
			if (fileId != null) {

				fileUploadInterface.delete(fileId.getId());

			}

		} else {
			throw new ResourceNotFoundException(ErrorMessageCode.PLEASE_UPLOAD_PDF);
		}

	}

	@Override
	public List<IListFooterDocumentDto> getAllPdf() {

		ArrayList<IListFooterDocumentDto> iListFooterDocumentDto = systemConfigurationRepository
				.findByOrderByKeyDesc(IListFooterDocumentDto.class);

		List<IListFooterDocumentDto> filteredList = iListFooterDocumentDto.stream()
				.filter(dto -> !dto.getkey().equals(Constant.SAML_CERTIFICATE)
						&& !dto.getkey().equals(Constant.CAREER_ASPIRATION))

				.collect(Collectors.toList());

		return filteredList;
	}

	@Override
	public void updateFlag(@Valid String key, Boolean flag) {

		if (key.isBlank()) {
			throw new ResourceNotFoundException(ErrorMessageCode.KEY_REQUIRED);
		}
		SystemConfiguration systemConfiguration = systemConfigurationRepository.findBykey(key);
		if (systemConfiguration == null) {
			throw new ResourceNotFoundException(ErrorMessageCode.KEY_NOT_FOUND);

		}
		systemConfiguration.setFlag(flag);
		systemConfigurationRepository.save(systemConfiguration);
	}

	@Override
	public void addFooterText(@Valid FooterTextDto footerTextDto) {
		if (footerTextDto.getFooterText().isBlank()) {
			throw new ResourceNotFoundException(ErrorMessageCode.FOOTER_TEXT);
		}
		if (footerTextDto.getKey().isBlank()) {
			throw new ResourceNotFoundException(ErrorMessageCode.KEY_REQUIRED);
		}
		SystemConfiguration systemConfiguration = systemConfigurationRepository.findBykey(footerTextDto.getKey());
		if (systemConfiguration == null) {
			throw new ResourceNotFoundException(ErrorMessageCode.KEY_NOT_FOUND);

		}
		systemConfiguration.setValue(footerTextDto.getFooterText());
		systemConfigurationRepository.save(systemConfiguration);

	}

	@Override
	public void showBannerValidation() {

		SystemConfiguration configuration = new SystemConfiguration();
		configuration.setFlag(true);
		configuration.setKey(Constant.BANNER_LIMIT);
		configuration.setValue("6");
		systemConfigurationRepository.save(configuration);
	}

}
