package com.alchemy.serviceInterface;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.web.multipart.MultipartFile;

import com.alchemy.dto.ConfigPatchDto;
import com.alchemy.dto.FooterTextDto;
import com.alchemy.iListDto.IListFooterDocumentDto;

public interface SystemConfigurationInterface {
	public void updateCareerAspirationFlag(ConfigPatchDto dto);

	public void addPdf(MultipartFile file, HttpServletRequest request, String key) throws Exception;

	public List<IListFooterDocumentDto> getAllPdf();

	public void updateFlag(@Valid String key, Boolean flag);

	public void addFooterText(@Valid FooterTextDto footerTextDto);

	public void showBannerValidation();

}
