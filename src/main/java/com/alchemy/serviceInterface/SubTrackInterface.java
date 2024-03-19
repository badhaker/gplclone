package com.alchemy.serviceInterface;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.alchemy.dto.DeleteId;
import com.alchemy.dto.SubTrackDto;
import com.alchemy.dto.SubTrackUpdateDto;
import com.alchemy.iListDto.IListSubTrack;

@Service
public interface SubTrackInterface {

	Page<IListSubTrack> getAllSubTrack(String search, String learningTrackId, String pageNo, String pageSize)
			throws Exception;

	void deleteSubTrack(Long subTrackId);

	SubTrackDto addSubTrack(@Valid SubTrackDto subTrackDto, Long userId) throws Exception;

//	SubTrackDto editSubTrack(@Valid SubTrackDto subTrackDto, Long userId, Long id) throws Exception;

	List<IListSubTrack> getAllSubTracks(String learningTrackId, Class<IListSubTrack> class1);

	SubTrackUpdateDto editSubTrack(@Valid SubTrackUpdateDto subTrackDto, Long userId, Long id) throws Exception;

	void multiDeleteSubTrack(DeleteId ids, long id);
	
}
