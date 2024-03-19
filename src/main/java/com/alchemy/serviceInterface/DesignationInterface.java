package com.alchemy.serviceInterface;

import java.util.List;

import org.springframework.data.domain.Page;

import com.alchemy.dto.DeleteId;
import com.alchemy.dto.DesignationDto;
import com.alchemy.iListDto.IListDesignation;

public interface DesignationInterface {

	public DesignationDto addDesignation(DesignationDto designationDto);

	public DesignationDto editDesignation(Long designationId, DesignationDto designationDto);

	public void deleteDesignation(Long designationId);

	public List<IListDesignation> getAllDesignations(Class<IListDesignation> class1);

	Page<IListDesignation> getAllDesignations(String search, String pageNo, String pageSize) throws Exception;

	public void deleteMultipleDesignationById(DeleteId id, Long userId);
}
