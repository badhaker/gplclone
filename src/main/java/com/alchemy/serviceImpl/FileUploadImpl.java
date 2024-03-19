package com.alchemy.serviceImpl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.alchemy.entities.FileUploadEntity;
import com.alchemy.exceptionHandling.FileStorageException;
import com.alchemy.iListDto.IFileInfoList;
import com.alchemy.repositories.FileUploadRepository;
import com.alchemy.serviceInterface.FileUploadInterface;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.FileStorageProperties;
import com.alchemy.utils.Pagination;

@Service
public class FileUploadImpl implements FileUploadInterface {
	@Autowired
	private FileUploadRepository fileUploadRepository;

	private final Path fileStorageLocation;

	public FileUploadImpl(FileStorageProperties fileStorageProperties) throws Exception {

		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {

			throw new FileStorageException(ErrorMessageCode.DIRECTORY_NOT_CREATED, ex);
		}

	}

	@Override
	public FileUploadEntity storeFile(MultipartFile file, HttpServletRequest request) throws Exception {

		String fileNames = UUID.randomUUID() + "_" + file.getOriginalFilename().replace(",", "");

		String fileName = StringUtils.cleanPath(fileNames);

		try {
			if (fileName.contains("..")) {

				throw new FileStorageException(ErrorMessageCode.INVALID_FILE + fileName);
			}

			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			FileUploadEntity fileUploadEntity = new FileUploadEntity();
			fileUploadEntity.setEncoding(request.getCharacterEncoding());
			fileUploadEntity.setFilename(file.getOriginalFilename());
			fileUploadEntity.setMimetype(file.getContentType());
			fileUploadEntity.setOriginalName(fileName);
			fileUploadEntity.setSize(file.getSize());
			FileUploadEntity fileDetail = fileUploadRepository.save(fileUploadEntity);
			return fileDetail;
		} catch (Exception ex) {
			throw new FileStorageException(
					ErrorMessageCode.FILE_NOT_UPLOADED + fileName + ErrorMessageCode.PLEASE_TRY_AGAIN, ex);

		}
	}

	@Override
	public FileUploadEntity storePdf(MultipartFile file, String originalFileName) throws Exception {

		String fileNames = file.getOriginalFilename();
		String fileName = StringUtils.cleanPath(fileNames);

		try {
			if (fileName.contains("..")) {

				throw new FileStorageException(ErrorMessageCode.INVALID_FILE + fileName);
			}
			String fileExtension = ".pdf";
			Path targetLocation = this.fileStorageLocation.resolve(fileName + fileExtension);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			FileUploadEntity fileUploadEntity = new FileUploadEntity();
			fileUploadEntity.setFilename(originalFileName);
			fileUploadEntity.setMimetype(file.getContentType());
			fileUploadEntity.setOriginalName(originalFileName);
			fileUploadEntity.setSize(file.getSize());
			FileUploadEntity fileDetail = fileUploadRepository.save(fileUploadEntity);
			return fileDetail;
		} catch (Exception ex) {
			throw new FileStorageException(
					ErrorMessageCode.FILE_NOT_UPLOADED + fileName + ErrorMessageCode.PLEASE_TRY_AGAIN, ex);

		}
	}

	@Override
	public Resource loadFileAsResource(String fileName) throws Exception {
		try {
			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());

			if (resource.exists()) {
				return resource;

			} else {
				throw new Exception(ErrorMessageCode.FILE_NOT_FOUND);
			}

		} catch (MalformedURLException ex) {
			throw new Exception(ErrorMessageCode.FILE_NOT_FOUND);
		}
	}

	@Override
	public boolean delete(Long id) {
		try {
			FileUploadEntity uploadEntity = this.fileUploadRepository.findById(id)
					.orElseThrow(() -> new FileNotFoundException(ErrorMessageCode.FILE_NOT_FOUND));

			Path file = fileStorageLocation.resolve(uploadEntity.getFilename());

			this.fileUploadRepository.deleteById(uploadEntity.getId());
			return Files.deleteIfExists(file);

		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public Page<IFileInfoList> fileInformation(String search, String pagNo, String pageSize) {
		Page<IFileInfoList> list;
		Pageable pageable = new Pagination().getPagination(pagNo, pageSize);
		if (search == "" || search == null || search.length() == 0) {
			list = this.fileUploadRepository.findByOrderByIdDesc(pageable, IFileInfoList.class);
		} else {
			list = fileUploadRepository.findByOriginalNameContainingIgnoreCase(search, pageable, IFileInfoList.class);
		}

		return list;
	}

	@Override
	public FileUploadEntity storeImage(MultipartFile file, String originalFileName) {

		String fileNames = file.getOriginalFilename();
		String fileName = StringUtils.cleanPath(fileNames);

		try {
			if (fileName.contains("..")) {

				throw new FileStorageException(ErrorMessageCode.INVALID_FILE + fileName);
			}
			String fileExtension = ".png";
			Path targetLocation = this.fileStorageLocation.resolve(fileName + fileExtension);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			FileUploadEntity fileUploadEntity = new FileUploadEntity();
			fileUploadEntity.setFilename(originalFileName);
			fileUploadEntity.setMimetype(file.getContentType());
			fileUploadEntity.setOriginalName(originalFileName);
			fileUploadEntity.setSize(file.getSize());
			FileUploadEntity fileDetail = fileUploadRepository.save(fileUploadEntity);
			return fileDetail;
		} catch (Exception ex) {
			throw new FileStorageException(
					ErrorMessageCode.FILE_NOT_UPLOADED + fileName + ErrorMessageCode.PLEASE_TRY_AGAIN, ex);

		}
	}

}
