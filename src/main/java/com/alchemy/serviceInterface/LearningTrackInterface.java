package com.alchemy.serviceInterface;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.alchemy.dto.DeleteId;
import com.alchemy.dto.LearningTrackDto;
import com.alchemy.dto.LearningTrackUpdateDto;
import com.alchemy.dto.LearningTrackUpdateFileDto;
import com.alchemy.dto.VisibleContentDto;
import com.alchemy.iListDto.IListEnrollStatusCount;
import com.alchemy.iListDto.IListLearningTrack;
import com.alchemy.iListDto.IListLearningTrackDetail;
import com.alchemy.iListDto.IListUserDetails;

public interface LearningTrackInterface {

	LearningTrackDto addLearningTrackTrainerAndSponsor(LearningTrackDto learningTrackDto, MultipartFile file,
			MultipartFile banner, MultipartFile bannerCart, HttpServletRequest request, Long userId,
			MultipartFile nudgedFile) throws Exception;

	LearningTrackUpdateFileDto updateLearningTrack(LearningTrackUpdateFileDto learningTrackDto, Long id,
			MultipartFile file, MultipartFile banner, MultipartFile bannerCard, HttpServletRequest request, Long userId,
			MultipartFile nudgedFile) throws Exception;

	public void deleteLearningTrackById(Long learningTrackId, Long userId);

	IListLearningTrackDetail findById(Long trackId);

	public Long learningTrackBulkUpload(MultipartFile multipartFile, Long userId, String moduleName)
			throws IOException, Exception;

	public void bulkUploadInLearningTrack(Long bulkId, Long userId);

	IListEnrollStatusCount getEnrollStatusCount();

	Page<IListLearningTrack> getAllLearningTracks(String search, Long userId, String pageNo, String pageSize,
			ArrayList<String> permissiosName, String fromAdmin) throws Exception;

	// List<LearningTrackDropdownList> findAllLearningTrackDropdown();

	IListUserDetails getUserEnroll(Long userId);

	public void learningTrackIsVisibleOrNot(Long id, LearningTrackUpdateDto dto, Long userId);

	void deleteMultipleTracksById(DeleteId id, Long userId);

	void updateIsVisibleForLearningTrackMultiSelect(VisibleContentDto isVisibleDto);

}
