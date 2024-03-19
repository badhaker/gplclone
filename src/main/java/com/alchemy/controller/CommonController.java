package com.alchemy.controller;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alchemy.dto.ErrorResponseDto;
import com.alchemy.dto.ListResponseDto;
import com.alchemy.dto.PaginationResponse;
import com.alchemy.entities.FileUploadEntity;
import com.alchemy.iListDto.IFileInfoList;
import com.alchemy.serviceInterface.FileUploadInterface;
import com.alchemy.utils.ApiUrls;
import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.SuccessMessageCode;

@RestController

public class CommonController {

	@Autowired
	private FileUploadInterface fileUploadInterface;

	@PostMapping(ApiUrls.FILE_UPLOAD)
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request)
			throws Exception {

		@SuppressWarnings("unused")
		FileUploadEntity uploadEntity = new FileUploadEntity();

		try {
			uploadEntity = this.fileUploadInterface.storeFile(file, request);

		} catch (Exception e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "invalid UploadType"),
					HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.OK);

	}

	@GetMapping(ApiUrls.DOWNLOAD_FILE)
	public ResponseEntity<?> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws Exception {

		Resource resource = null;

		try {
			resource = fileUploadInterface.loadFileAsResource(fileName);

		} catch (Exception e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorMessageCode.FILE_NOT_FOUND),
					HttpStatus.NOT_FOUND);

		}

		//// Try to determine file's content type
		String contentType = null;

		try {

			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

		} catch (IOException ex) {

		}

		if (contentType == null) {

			contentType = "application/octet-stream";

		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
				.body(resource);

	}

	@GetMapping(ApiUrls.FILE_LIST)
	public ResponseEntity<?> getAllFileInformation(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = Constant.DEFAULT_PAGENUMBER, value = Constant.PAGENUMBER) String pageNo,
			@RequestParam(defaultValue = Constant.DEFAULT_PAGESIZE, value = Constant.PAGESIZE) String pageSize)
			throws ParseException {

		Page<IFileInfoList> page = this.fileUploadInterface.fileInformation(search, pageNo, pageSize);

		PaginationResponse paginationResponse = new PaginationResponse();

		paginationResponse.setPageSize(page.getSize());
		paginationResponse.setTotal(page.getTotalElements());
		paginationResponse.setPageNumber(page.getNumber() + 1);

		return new ResponseEntity<>(new ListResponseDto(page.getContent(), paginationResponse), HttpStatus.OK);

	}

	@DeleteMapping(ApiUrls.FILE_DELETE + "/{id}")
	public ResponseEntity<?> deleteFile(@PathVariable Long id) throws Exception {

		try {
			this.fileUploadInterface.delete(id);

			return new ResponseEntity<>(SuccessMessageCode.FILE_DELETE, HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "invalid UploadType"),
					HttpStatus.BAD_REQUEST);
		}

	}
}
