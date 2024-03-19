package com.alchemy.serviceImpl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alchemy.dto.DeleteId;
import com.alchemy.dto.TrainersMasterDto;
import com.alchemy.entities.DesignationEntity;
import com.alchemy.entities.FileUploadEntity;
import com.alchemy.entities.TrainersMasterEntity;
import com.alchemy.entities.UserEntity;
import com.alchemy.exceptionHandling.ResourceNotFoundException;
import com.alchemy.iListDto.IListTrainer;
import com.alchemy.repositories.DesignationRepository;
import com.alchemy.repositories.TrackTrainerRepository;
import com.alchemy.repositories.TrainersMasterRepository;
import com.alchemy.repositories.UserRepository;
import com.alchemy.serviceInterface.FileUploadInterface;
import com.alchemy.serviceInterface.TrainersMasterInterface;
import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.Pagination;
import com.alchemy.utils.Validator;

@Service
public class TrainersMasterServiceImpl implements TrainersMasterInterface {

	@Autowired
	private TrainersMasterRepository trainersMasterRepository;

	@Autowired
	private DesignationRepository designationRepository;
//  The commented line of code indicates if the requirement of image change from url to actual file upload.
	@Autowired
	private FileUploadInterface fileUploadInterface;

	@Autowired
	TrackTrainerRepository trackTrainerRepository;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private FileUploadImpl fileUploadImpl;

	@Override
	public TrainersMasterDto addTrainer(TrainersMasterDto trainersMasterDto, Long userId, MultipartFile file,
			HttpServletRequest request) throws Exception {
		TrainersMasterEntity entity = this.trainersMasterRepository.findByNameIgnoreCase(trainersMasterDto.getName());
		if (entity != null) {
			throw new ResourceNotFoundException(ErrorMessageCode.TRAINER_ALREADY_PRESENT);
		}
		DesignationEntity designationEntity = this.designationRepository.findById(trainersMasterDto.getDesignationId())
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.DESIGNATION_NOT_FOUND));
		TrainersMasterEntity trainersMasterEntity = new TrainersMasterEntity();
		trainersMasterEntity.setName(trainersMasterDto.getName());
		trainersMasterEntity.setDescription(trainersMasterDto.getDescription());
		trainersMasterEntity.setDesignation(designationEntity);
		trainersMasterEntity.setCreatedBy(userId);

		if (file != null && !file.isEmpty()) {
			String originalFilename = file.getOriginalFilename();
			if (Validator.isValidforImageFile(originalFilename)) {
				FileUploadEntity fileUploadEntity = this.fileUploadImpl.storeFile(file, request);
				trainersMasterEntity.setFileId(fileUploadEntity);
			} else {
				throw new IllegalAccessException(ErrorMessageCode.VALID_IMAGE);
			}

		} else {
			throw new IllegalAccessException(ErrorMessageCode.FILE_REQUIRED);
		}

		if (trainersMasterDto.getUser() != null) {
			UserEntity user2 = userRepo.findById(trainersMasterDto.getUser())
					.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.USER_NOT_PRESENT));
			trainersMasterEntity.setUser(user2);
			if (!trainersMasterDto.getName().equals(user2.getName())) {

				throw new ResourceNotFoundException(ErrorMessageCode.INVALID_TRAINER_NAME);
			}
		}

//		if (file != null) {
//			FileUploadEntity fileUploadEntity = fileUploadInterface.storeFile(file, request);
//			trainersMasterEntity.setImageUrl(fileUploadEntity.getId());
//		}
		trainersMasterRepository.save(trainersMasterEntity);
		return trainersMasterDto;
	}

	@Override
	public TrainersMasterDto editTrainer(Long trainerId, TrainersMasterDto trainersMasterDto, Long userId,
			HttpServletRequest request, MultipartFile file) throws Exception {

		TrainersMasterEntity trainersMasterEntity = this.trainersMasterRepository.findById(trainerId)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.TRAINER_NOT_PRESENT));
		DesignationEntity designationEntity = this.designationRepository.findById(trainersMasterDto.getDesignationId())
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.DESIGNATION_NOT_FOUND));

		String newName = trainersMasterDto.getName().trim();
		if (!newName.equalsIgnoreCase(trainersMasterEntity.getName())) {
			TrainersMasterEntity entity1 = this.trainersMasterRepository.findByNameIgnoreCase(newName);
			if (entity1 != null) {
				throw new ResourceNotFoundException(ErrorMessageCode.TRAINER_ALREADY_PRESENT);
			}
		}
		trainersMasterEntity.setName(trainersMasterDto.getName());
		trainersMasterEntity.setDescription(trainersMasterDto.getDescription());
		trainersMasterEntity.setDesignation(designationEntity);
		trainersMasterEntity.setUpdatedBy(userId);

		if (trainersMasterDto.isFileUpdate() == true) {
			if (file != null && !file.isEmpty()) {
				String originalFileName = file.getOriginalFilename();
				if (Validator.isValidforImageFile(originalFileName)) {
					if (trainersMasterEntity.getFileId() != null) {
						this.fileUploadImpl.delete(trainersMasterEntity.getFileId().getId());
					}
					FileUploadEntity entity = this.fileUploadImpl.storeFile(file, request);
					trainersMasterEntity.setFileId(entity);
				} else {
					throw new IllegalArgumentException(ErrorMessageCode.VALID_IMAGE);
				}
			} else {
				throw new IllegalArgumentException(ErrorMessageCode.FILE_REQUIRED);
			}

		}

		if (trainersMasterDto.getUser() != null) {
			UserEntity user2 = userRepo.findById(trainersMasterDto.getUser())
					.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.USER_NOT_PRESENT));
			trainersMasterEntity.setUser(user2);
		}
		if (trainersMasterDto.getUser() == null) {
			trainersMasterEntity.setUser(null);
		}

//		if (file != null) {
//			FileUploadEntity fileUploadEntity = fileUploadInterface.storeFile(file, request);
//			trainersMasterEntity.setImage(fileUploadEntity.getId());
//		}
		trainersMasterRepository.save(trainersMasterEntity);
		return trainersMasterDto;
	}

	@Override
	public void deleteTrainer(Long trainerId) {
		TrainersMasterEntity trainersMasterEntity = this.trainersMasterRepository.findById(trainerId)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.TRAINER_NOT_PRESENT));
		trainersMasterEntity.setIsActive(false);

		List<Long> trackTrainer = trackTrainerRepository.findByTrainerIds(trainerId);
		this.trainersMasterRepository.save(trainersMasterEntity);
		this.trackTrainerRepository.deleteAllByIdInBatch(trackTrainer);
	}

	@Override
	public Page<IListTrainer> getAllTrainers(String search, String pageNo, String pageSize) throws Exception {
		Page<IListTrainer> iListTrainer;

		String pageNumber = pageNo.isBlank() ? Constant.DEFAULT_PAGENUMBER : pageNo;
		String pageSizes = pageSize.isBlank() ? Constant.DEFAULT_PAGESIZE : pageSize;

		Pageable pageable = new Pagination().getPagination(pageNumber, pageSizes);

		if (search == "" || search == null || search.length() == 0) {

			iListTrainer = this.trainersMasterRepository.findByOrderByIdDesc(pageable, IListTrainer.class);
		} else {
			iListTrainer = this.trainersMasterRepository.findAllTrainers(search, pageable, IListTrainer.class);
		}
		return iListTrainer;
	}

	@Override
	public List<IListTrainer> findAllTrainers() {
		List<IListTrainer> list = this.trainersMasterRepository.findAllTrainers(IListTrainer.class);
		return list;
	}

	@Override
	public IListTrainer findById(Long trainerId) {
		IListTrainer trainer = this.trainersMasterRepository.findByTrainerId(trainerId, IListTrainer.class);
		if (trainer == null) {
			throw new ResourceNotFoundException(ErrorMessageCode.TRAINER_NOT_PRESENT);
		}
		return trainer;

	}

	@Override
	public void deleteMultipleTrainersById(DeleteId ids, Long userId) {

		List<TrainersMasterEntity> entity = trainersMasterRepository.findAllById(ids.getIds());
		for (int i = 0; i < entity.size(); i++) {
			entity.get(i).setUpdatedBy(userId);
		}

		trainersMasterRepository.deleteAllByIdIn(ids.getIds());

	}
}
