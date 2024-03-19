package com.alchemy.serviceInterface;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alchemy.entities.FileUploadEntity;
import com.alchemy.iListDto.IFileInfoList;

@Service
public interface FileUploadInterface {

	FileUploadEntity storeFile(MultipartFile file, HttpServletRequest request) throws Exception;

	Resource loadFileAsResource(String fileName) throws Exception;

	boolean delete(Long id);

	Page<IFileInfoList> fileInformation(String search, String pagNo, String pageSize);

	FileUploadEntity storePdf(MultipartFile file, String originalFileName) throws Exception;

	FileUploadEntity storeImage(MultipartFile image, String originalFileName);

}
