package com.alchemy.serviceInterface;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.alchemy.dto.DeleteId;
import com.alchemy.dto.PermissionDto;
import com.alchemy.dto.PermissionModuleList;
import com.alchemy.exceptionHandling.ResourceNotFoundException;
import com.alchemy.iListDto.IListPermissionDto;
import com.alchemy.iListDto.PermissionWithSFDetail;

public interface PermissionInterface {

	void addPermission(@Valid PermissionDto dto, Long userId) throws Exception;

	void deletePermission(Long id, Long userId);

	public List<IListPermissionDto> getAllPermissions();

	public PermissionDto getPermissionById(Long id);

	public PermissionDto updatePermission(PermissionDto dto, Long id, Long userId) throws ResourceNotFoundException;

	public Page<IListPermissionDto> getAllPermissions(String search, String pageNo, String pageSize);

	List<PermissionModuleList> modulePermissionList();

	List<String> getUserPermissions(Long userId);

	public void uploadPermissions(MultipartFile file, Long userId) throws IOException;

	List<IListPermissionDto> findAllList(Class<IListPermissionDto> class1);

	void deleteMultiplePermissionsById(DeleteId id, Long userId);

	PermissionWithSFDetail getUserPermissionsAndSFDetail(Long userId);

}
