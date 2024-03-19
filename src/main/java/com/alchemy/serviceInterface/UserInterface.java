package com.alchemy.serviceInterface;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;

import com.alchemy.dto.DeleteId;
import com.alchemy.dto.UserUpdateDto;
import com.alchemy.iListDto.IListCareerAspiration;
import com.alchemy.iListDto.IListUserDto;

public interface UserInterface {

	public void deleteUser(Long userId) throws Exception;

	public Page<IListUserDto> getAllUsers(String search, String pageNo, String pageSize);

	public UserUpdateDto editUser(Long userId, UserUpdateDto userDto) throws Exception;

//	void upadeCareerAspiration(Long id, CareerAspirationDto aspirationDto);

	List<IListUserDto> exportUserToExcel(HttpServletResponse response) throws IOException;

	public Page<IListCareerAspiration> getAllCareerAspiration(String search, String pageNo, String pageSize);

	public List<IListUserDto> findAllUsers();

	public void deleteMultipleUsersById(DeleteId id, Long userId);

}
