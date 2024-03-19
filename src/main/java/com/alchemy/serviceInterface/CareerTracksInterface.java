package com.alchemy.serviceInterface;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.alchemy.dto.CareerTracksDto;
import com.alchemy.dto.DeleteId;
import com.alchemy.iListDto.IListCareerTracks;

public interface CareerTracksInterface {

	CareerTracksDto addCareerTracks(CareerTracksDto careerTracksDto, Long userId, MultipartFile file,
			MultipartFile thumbnailFile, HttpServletRequest request) throws Exception;

	CareerTracksDto updateCareerTracks(@Valid CareerTracksDto careerTracksDto, Long userId, HttpServletRequest request,
			Long id, MultipartFile file, MultipartFile thumbnailFile) throws Exception;

	void deleteCareerTracks(Long id, Long userId);

	Page<IListCareerTracks> getAllCareerTracks(String search, String pageNum, String pageSiz);

	void deleteMultiplecareertracksById(DeleteId ids, Long userId) throws Exception;
}
