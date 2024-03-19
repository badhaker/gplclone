package com.alchemy.serviceInterface;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.alchemy.dto.DeleteId;
import com.alchemy.dto.GplFunctionDto;
import com.alchemy.iListDto.IListGplFunctionDto;

public interface GplFunctionInterface {

	GplFunctionDto addGplFunction(@Valid GplFunctionDto gplFunctionDto, Long userId);

	GplFunctionDto updateGplFunction(Long id, Long userId, GplFunctionDto gplFunctionDto);

	void deleteGplFunction(DeleteId id, Long userId) throws Exception;

	Page<IListGplFunctionDto> getAllGplFunction(String search, String pageNo, String pageSize);

	void uploadMasterDetails(MultipartFile file, Long userId) throws IOException;

}
