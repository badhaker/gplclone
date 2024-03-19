package com.alchemy.serviceInterface;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.alchemy.iListDto.IListEnroll;
import com.alchemy.iListDto.IListUserTrack;

public interface EnrollNowInterface {

	public Long saveExcelFile(MultipartFile multipartFile, Long userId,String moduleName)throws IOException,Exception;

	Page<IListUserTrack> exportEnrollNowToExcel(HttpServletResponse response, Page<IListUserTrack> list) throws IOException;

	public void saveToEnrollNowTable(Long bulkUpload, Long userId) throws Exception;
	
//	public Page<IListEnroll> getAllUserEnroll(String search, String pageNo, String pageSize);
	
}
