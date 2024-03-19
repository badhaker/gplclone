package com.alchemy.serviceInterface;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.alchemy.dto.BannerDto;
import com.alchemy.dto.DeleteId;
import com.alchemy.dto.IsVisibleDto;
import com.alchemy.dto.VisibleContentDto;
import com.alchemy.iListDto.IListBanner;

public interface BannerInterface {

	public void deleteBannerById(Long bannerId);

	Page<IListBanner> getAllBanners(String search, String pageNo, String pageSize) throws Exception;

	public BannerDto addBanner(BannerDto bannerDto, MultipartFile file, HttpServletRequest request, Long userId)
			throws Exception;

	public BannerDto updateBanner(@Valid BannerDto bannerDto, Long id, MultipartFile file, HttpServletRequest request,
			Long userId) throws Exception;

	public List<IListBanner> findAllList(String search, Class<IListBanner> class1);

	public void updateBannerIsVisible(Long id, IsVisibleDto dto);

	public void deleteMultipleBannerByIds(DeleteId deleteIdsDto);

	public void isVisibleMultiSelect(VisibleContentDto visibleContentDto);
}
