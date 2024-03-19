package com.alchemy.serviceInterface;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.alchemy.dto.DeleteId;
import com.alchemy.dto.SponsorDto;
import com.alchemy.iListDto.IListSponsorDto;

public interface SponsorInterface {
	
	public SponsorDto addSponser(SponsorDto sponserDto, Long userId,MultipartFile file, HttpServletRequest request) throws Exception;

	void deleteSponser(Long id, Long userId);

	Page<IListSponsorDto> getAllSponsers(String search, String pageNo, String pageSize)throws Exception;

	SponsorDto updateSponser(SponsorDto sponserDto, Long id, Long userId, MultipartFile file, HttpServletRequest request) throws Exception;

	public List<IListSponsorDto> findAllSponsors(Class<IListSponsorDto> class1);
	
	IListSponsorDto findById(Long sponsorId);

	public void deleteMultipleSponsorsById(DeleteId id, Long userId);

	
}
