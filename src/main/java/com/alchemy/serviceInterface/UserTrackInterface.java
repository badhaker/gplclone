package com.alchemy.serviceInterface;

import com.alchemy.dto.UserTrackStatusDto;
import com.alchemy.iListDto.IListUserTrack;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.alchemy.dto.EnrollFilterDto;
import com.alchemy.dto.MultipleEnrollStatusDto;
import com.alchemy.dto.UserTrackDto;

public interface UserTrackInterface {
	
	public UserTrackDto addUserTrack(UserTrackDto userTrackDto, Long userId, String token) throws Exception;

	public void updateUserTrackStatus(Long id, UserTrackStatusDto userTopicDto) throws MessagingException, FileNotFoundException, IOException;

	public List<IListUserTrack> getAllUserTracks(Class<IListUserTrack> class1);

	public int markMultipleEnrollById(MultipleEnrollStatusDto id, Long userId)throws MessagingException;

	Page<IListUserTrack> findAllUserTracks(String search, EnrollFilterDto dto, HttpServletResponse response,
			Pageable pageable);



}
