package com.alchemy.serviceImpl;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alchemy.dto.CareerTracksDto;
import com.alchemy.dto.DeleteId;
import com.alchemy.entities.CareerTracksEntity;
import com.alchemy.entities.FileUploadEntity;
import com.alchemy.exceptionHandling.ResourceNotFoundException;
import com.alchemy.iListDto.IListCareerTracks;
import com.alchemy.repositories.CareerTracksRepository;
import com.alchemy.repositories.FileUploadRepository;
import com.alchemy.serviceInterface.CareerTracksInterface;
import com.alchemy.serviceInterface.FileUploadInterface;
import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.Pagination;
import com.alchemy.utils.Validator;

@Service
public class CareerTracksImpl implements CareerTracksInterface {

	@Autowired
	CareerTracksRepository careerTracksRepository;
	@Autowired
	FileUploadInterface fileUploadInterface;

	@Autowired
	FileUploadRepository fileUploadRepository;

	@Override
	public CareerTracksDto addCareerTracks(CareerTracksDto careerTracksDto, Long userId, MultipartFile file,
			MultipartFile thumbnailFile, HttpServletRequest request) throws Exception {
		CareerTracksEntity career = this.careerTracksRepository
				.findByNameIgnoreCaseAndIsActiveTrue(careerTracksDto.getName());
		if (career != null) {
			throw new IllegalArgumentException(ErrorMessageCode.CAREER_TRACK_ALREADY_EXIST);
		}
		CareerTracksEntity careerTracksEntity = new CareerTracksEntity();
		careerTracksEntity.setName(careerTracksDto.getName());

		careerTracksEntity.setCreatedBy(userId);

		if (file != null && !file.isEmpty()) {
			if (Validator.isValidforPdf(file)) {
				FileUploadEntity fileUploadEntity = fileUploadInterface.storeFile(file, request);
				careerTracksEntity.setFileId(fileUploadEntity.getId());
			} else {
				throw new IllegalArgumentException(ErrorMessageCode.PLEASE_UPLOAD_PDF);
			}
		} else {
			throw new IllegalArgumentException(ErrorMessageCode.FILE_REQUIRED);

		}

		if (thumbnailFile != null && !thumbnailFile.isEmpty()) {
			String originalFileName = thumbnailFile.getOriginalFilename();
			if (Validator.isValidforImageFile(originalFileName)) {
				FileUploadEntity fileUploadEntity = fileUploadInterface.storeFile(thumbnailFile, request);
				careerTracksEntity.setThumbnailFileId(fileUploadEntity);

			} else {
				throw new IllegalArgumentException(ErrorMessageCode.INVALID_IMAGE_FILE);
			}

			careerTracksRepository.save(careerTracksEntity);

		} else {
			throw new IllegalArgumentException(ErrorMessageCode.THUMBNAIL_FILE_REQUIRED);
		}
		return careerTracksDto;

	}

	@Override
	public CareerTracksDto updateCareerTracks(@Valid CareerTracksDto careerTracksDto, Long userId,
			HttpServletRequest request, Long id, MultipartFile file, MultipartFile thumbnailFile) throws Exception {

		CareerTracksEntity careerTracksEntity = careerTracksRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.CAREER_TRACKS_NOT_FOUND));

		CareerTracksEntity career = this.careerTracksRepository
				.findByNameIgnoreCaseAndIsActiveTrue(careerTracksDto.getName());
		if (career != null) {
			if (career.getId() != careerTracksEntity.getId()) {
				throw new IllegalArgumentException(ErrorMessageCode.CAREER_TRACK_ALREADY_EXIST);
			}
		}

		careerTracksEntity.setName(careerTracksDto.getName());
		careerTracksEntity.setUpdatedBy(userId);
		if (careerTracksDto.isFileUpdate()) {
			if (file != null && !file.isEmpty()) {
				if (Validator.isValidforPdf(file)) {
					if (careerTracksEntity.getFileId() != null) {
						fileUploadInterface.delete(careerTracksEntity.getFileId());

					}
					FileUploadEntity fileUploadEntity = fileUploadInterface.storeFile(file, request);
					careerTracksEntity.setFileId(fileUploadEntity.getId());
				} else {
					throw new IllegalArgumentException(ErrorMessageCode.PLEASE_UPLOAD_PDF);
				}

			}
		}

		if (careerTracksDto.isThumbnailFileUpdate())

		{
			if (thumbnailFile != null && !thumbnailFile.isEmpty()) {
				String originalFileName = thumbnailFile.getOriginalFilename();
				if (Validator.isValidforImageFile(originalFileName)) {

					if (careerTracksEntity.getThumbnailFileId() != null) {

						Optional<FileUploadEntity> thumbnailFileId = fileUploadRepository
								.findById(careerTracksEntity.getThumbnailFileId().getId());
						FileUploadEntity fileUpload = careerTracksEntity.getThumbnailFileId();
						FileUploadEntity fileUploadEntity = fileUploadInterface.storeFile(thumbnailFile, request);
						careerTracksEntity.setThumbnailFileId(fileUploadEntity);
						if (thumbnailFileId.isPresent()) {
							fileUploadInterface.delete(fileUpload.getId());

						}

					}

				} else {
					throw new IllegalArgumentException(ErrorMessageCode.INVALID_IMAGE_FILE);
				}
			}
		}
		careerTracksRepository.save(careerTracksEntity);
		return careerTracksDto;
	}

	@Override
	public void deleteCareerTracks(Long id, Long userId) {

		CareerTracksEntity careerTracksEntity = careerTracksRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.CAREER_TRACKS_NOT_FOUND));

		careerTracksEntity.setActive(false);
		careerTracksEntity.setUpdatedBy(userId);
		careerTracksRepository.save(careerTracksEntity);

	}

	@Override
	public Page<IListCareerTracks> getAllCareerTracks(String search, String pageNumber, String pageSize) {
		Page<IListCareerTracks> iListCareerTracks;

		String pageNo = pageNumber.isBlank() ? Constant.DEFAULT_PAGENUMBER : pageNumber;
		String pages = pageSize.isBlank() ? Constant.DEFAULT_PAGESIZE : pageSize;
		Pageable pageable = new Pagination().getPagination(pageNo, pages);

		if (pageNo.isBlank() && pageSize.isBlank()) {
			pageable = Pageable.unpaged();
		}

		iListCareerTracks = this.careerTracksRepository.findByNameContaining(search, pageable, IListCareerTracks.class);
		return iListCareerTracks;

	}

	@Override
	public void deleteMultiplecareertracksById(DeleteId ids, Long userId) {

		List<CareerTracksEntity> entity = careerTracksRepository.findAllById(ids.getIds());
		if (entity.size() != ids.getIds().size()) {
			throw new IllegalArgumentException(ErrorMessageCode.CAREER_TRACKS_NOT_FOUND);
		}

		for (CareerTracksEntity entity2 : entity) {
			entity2.setUpdatedBy(userId);
			this.careerTracksRepository.save(entity2);

		}

		careerTracksRepository.deleteAllByIdIn(ids.getIds());
	}
}
