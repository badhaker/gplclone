package com.alchemy.serviceInterface;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.alchemy.dto.AspirationDto;
import com.alchemy.dto.CareerAspExportDto;
import com.alchemy.dto.CareerAspirationPrefereceDto;
import com.alchemy.dto.DeleteId;

public interface CareerAspirationInterface {

	AspirationDto addaspiration(MultipartFile file, HttpServletRequest request, AspirationDto aspirationDto,
			Long userId) throws Exception;

	List<CareerAspirationPrefereceDto> getAllApirationPreference(String search, String pageSize, String pageNumber)
			throws Exception;

	CareerAspirationPrefereceDto getApiration(Long userId) throws Exception;

	Page<CareerAspExportDto> getUserAspiration(String search, String function, String department, Pageable pageable,
			Boolean blankRecords, String exportIds);

	public void exportToExcel(HttpServletResponse response, Page<CareerAspExportDto> page) throws IOException;

	public void deleteMultiple(DeleteId dto, Long userId);
}
