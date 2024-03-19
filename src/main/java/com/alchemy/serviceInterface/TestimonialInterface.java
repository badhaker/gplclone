package com.alchemy.serviceInterface;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.alchemy.dto.DeleteId;
import com.alchemy.dto.IsVisibleDto;
import com.alchemy.dto.TestimonialDto;
import com.alchemy.dto.VisibleContentDto;
import com.alchemy.iListDto.IListTestimonial;

public interface TestimonialInterface {

	public void deleteMultipleTestimonialById(DeleteId id, Long userId);

	public void deleteTestimonial(Long testimonialId);

	Page<IListTestimonial> getAllTestimonials(String search, String pageNo, String pageSize);

	public TestimonialDto addTestimonial(@Valid TestimonialDto testimonialDto, Long userId, MultipartFile file,
			HttpServletRequest request) throws Exception;

	public TestimonialDto editTestimonial(Long testimonialId, TestimonialDto testimonialDto, Long userId,
			MultipartFile file, HttpServletRequest request) throws Exception;

	List<IListTestimonial> findAllTestimonial(Class<IListTestimonial> class1);

	public void updateTestimonialIsVisible(Long id, IsVisibleDto dto);

	public void updateTestimonialIsVisibleMultiSelect(VisibleContentDto visibleContentDto);

}
