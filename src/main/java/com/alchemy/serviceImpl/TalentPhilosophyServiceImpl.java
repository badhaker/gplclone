package com.alchemy.serviceImpl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alchemy.dto.TalentPhilosophyDto;
import com.alchemy.entities.FileUploadEntity;
import com.alchemy.entities.TalentPhilosophyEntity;
import com.alchemy.exceptionHandling.ResourceNotFoundException;
import com.alchemy.iListDto.IListTalentPhilosophy;
import com.alchemy.repositories.TalentPhilosophyRepository;
import com.alchemy.serviceInterface.FileUploadInterface;
import com.alchemy.serviceInterface.TalentPhilosophyInterface;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.Pagination;
import com.alchemy.utils.Validator;

@Service
public class TalentPhilosophyServiceImpl implements TalentPhilosophyInterface {

	@Autowired
	private TalentPhilosophyRepository talentPhilosophyRepository;

	@Autowired
	private FileUploadInterface fileUploadInterface;

	@Override
	public TalentPhilosophyDto addTalentPhilosophy(TalentPhilosophyDto talentPhilosophyDto, MultipartFile file,
			HttpServletRequest request, Long userId) throws Exception {

		List<TalentPhilosophyEntity> talentPhilosophyList = this.talentPhilosophyRepository.findAll();

		if (talentPhilosophyList.size() == 1) {
			TalentPhilosophyEntity entity = talentPhilosophyList.get(0);
			entity.setName(talentPhilosophyDto.getName());
			entity.setChroMessage(talentPhilosophyDto.getChroMessage());
			if (talentPhilosophyDto.getIsFileUpdated() == false) {
				if (entity.getFileId() == null) {
					throw new ResourceNotFoundException("Please upload image.");
				}
			} else {
				if (file != null && !file.isEmpty()) {
					String originalFilename = file.getOriginalFilename();
					if (Validator.isValidforImageFile(originalFilename)) {

						Long fileId = entity.getFileId().getId();
						FileUploadEntity newFile = fileUploadInterface.storeFile(file, request);
						entity.setFileId(newFile);
						if (fileId != null) {
							this.fileUploadInterface.delete(fileId);
						}
					} else {
						throw new IllegalArgumentException(ErrorMessageCode.VALID_IMAGE);
					}
				} else {
					throw new IllegalArgumentException(ErrorMessageCode.IMAGE_REQUIRED);
				}
			}
			entity.setUpdatedBy(userId);
			talentPhilosophyRepository.save(entity);

		} else {
			TalentPhilosophyEntity newEntity = new TalentPhilosophyEntity();
			newEntity.setName(talentPhilosophyDto.getName());
			newEntity.setChroMessage(talentPhilosophyDto.getChroMessage());
			newEntity.setCreatedBy(userId);

			if (file != null && !file.isEmpty()) {
				String originalFilename = file.getOriginalFilename();
				if (Validator.isValidforImageFile(originalFilename)) {
					FileUploadEntity newFile = fileUploadInterface.storeFile(file, request);
					newEntity.setFileId(newFile);
				} else {
					throw new IllegalArgumentException(ErrorMessageCode.VALID_IMAGE);
				}
			} else {
				throw new IllegalArgumentException(ErrorMessageCode.IMAGE_REQUIRED);
			}
			talentPhilosophyRepository.deleteAll();
			talentPhilosophyRepository.save(newEntity);

		}

		return talentPhilosophyDto;

	}

	@Override
	public Page<IListTalentPhilosophy> getAlltalentPhilosophy(String search, String pageNo, String pageSize)
			throws Exception {

		Page<IListTalentPhilosophy> iListTalentPhilosophies;
		Pageable pageable = new Pagination().getPagination(pageNo, pageSize);
		iListTalentPhilosophies = this.talentPhilosophyRepository.findByOrderByIdDesc(pageable,
				IListTalentPhilosophy.class);
		return iListTalentPhilosophies;

	}

}
