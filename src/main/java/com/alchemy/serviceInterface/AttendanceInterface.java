package com.alchemy.serviceInterface;

import java.io.IOException;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.alchemy.dto.AttendanceDto;
import com.alchemy.dto.DeleteId;
import com.alchemy.dto.IsVisibleDto;
import com.alchemy.dto.MarkAttendanceRequestDto;
import com.alchemy.dto.MultiLockRequestDto;
import com.alchemy.dto.StarperformerDto;
import com.alchemy.entities.UserTrackEntity;
import com.alchemy.dto.AttendanceFilterDto;
import com.alchemy.dto.AttendanceStatusDto;
import com.alchemy.iListDto.IListAttendance;
import com.opencsv.exceptions.CsvValidationException;

public interface AttendanceInterface {

//	AttendanceDto addAttendance(AttendanceDto attendanceDto,Long userId);
	
	public Page<IListAttendance> exportAttendance(HttpServletResponse response,Page<IListAttendance> responseList) throws IOException;
	
	public Long saveExcelFile(MultipartFile multipartFile, Long userId, String moduleName)throws IOException, Exception;

//	Page<IListAttendance> getAllAttendance(String search, String pageNo, String pageSize);
	
	public void saveAcceptedUserToAttendance(UserTrackEntity usertrack) ;
	
	public void saveToAttendanceTable(Long bulkUploadId, Long userId) throws Exception;

//	public List<IListAttendance> findAllList(String search, Class<IListAttendance> class1);
	
	public AttendanceDto updateAttendance(AttendanceDto attendanceDto, Long id, Long userId) throws Exception;

//	Page<IListAttendance> getAllAttendance(String search,String email, HttpServletResponse response, 
//			Pageable pageable) throws IOException;

	Page<IListAttendance> getAllAttendance(String search, AttendanceFilterDto requestDto,
//			String epd, String email,  String function, String zone,
//			String region, String level, String grade, String position, String track, String subtrack,
//			String subtrackCompleteDate, String starPerformer, 
			HttpServletResponse response, Pageable pageable)
			throws IOException;

	public void lockMultipleAttendanceById(MultiLockRequestDto id, Long userId);

	public int markMultipleAttendanceById(MarkAttendanceRequestDto id, Long userId);

	public void updateAttendanceStatus(Long id, AttendanceStatusDto status);

	public void deleteMultipleAttendance(DeleteId id, Long userId);

	public void markMultipleStarperformer(StarperformerDto id, Long userId);

//	List<IListAttendance> exportAttendance(HttpServletResponse response,List <IListAttendance> responseList)
//			throws IOException;
}
