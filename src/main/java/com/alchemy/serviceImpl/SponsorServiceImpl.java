package com.alchemy.serviceImpl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alchemy.dto.DeleteId;
import com.alchemy.dto.SponsorDto;
import com.alchemy.entities.DesignationEntity;
import com.alchemy.entities.FileUploadEntity;
import com.alchemy.entities.SponsorMaster;
import com.alchemy.entities.TrackSponsor;
import com.alchemy.entities.UserEntity;
import com.alchemy.exceptionHandling.ResourceNotFoundException;
import com.alchemy.iListDto.IListSponsorDto;
import com.alchemy.repositories.DesignationRepository;
import com.alchemy.repositories.SponserRepository;
import com.alchemy.repositories.TrackSponsorRepository;
import com.alchemy.repositories.UserRepository;
import com.alchemy.serviceInterface.FileUploadInterface;
import com.alchemy.serviceInterface.SponsorInterface;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.Pagination;
import com.alchemy.utils.Validator;

@Service
public class SponsorServiceImpl implements SponsorInterface {

	@Autowired
	private SponserRepository sponserRepo;

//	@Autowired
//	private FileUploadInterface fileUploadInterface;

	@Autowired
	private DesignationRepository designationRepo;

//	@Autowired
//	private FileUploadRepository fileUploadRepository;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private FileUploadImpl fileUploadImpl;

	@Autowired
	private FileUploadInterface fileUploadInterface;

	@Override
	public SponsorDto addSponser(SponsorDto sponserDto, Long userId, MultipartFile file, HttpServletRequest request)
			throws Exception {
		DesignationEntity designation = designationRepo.findById(sponserDto.getDesignationId())
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.DESIGNATION_NOT_FOUND));
		SponsorMaster master = this.sponserRepo.findByNameIgnoreCase(sponserDto.getName());
		if (master != null) {
			throw new ResourceNotFoundException(ErrorMessageCode.SPONSER_ALREADY_PRESENT);
		}
		SponsorMaster sponserMaster = new SponsorMaster();
		sponserMaster.setName(sponserDto.getName());
		sponserMaster.setDescription(sponserDto.getDescription());
		sponserMaster.setCreatedBy(userId);
		sponserMaster.setProfile(sponserDto.getProfile());
		sponserMaster.setDesignationId(designation);

		if (file != null && !file.isEmpty()) {
			String originalFilename = file.getOriginalFilename();
			if (Validator.isValidforImageFile(originalFilename)) {
				FileUploadEntity fileUploadEntity = this.fileUploadImpl.storeFile(file, request);
				sponserMaster.setFileId(fileUploadEntity);
			} else {
				throw new IllegalArgumentException(ErrorMessageCode.VALID_IMAGE);
			}

		} else {
			throw new IllegalArgumentException(ErrorMessageCode.FILE_REQUIRED);
		}

		if (sponserDto.getUser() != null) {
			UserEntity user = userRepo.findById(sponserDto.getUser())
					.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.USER_NOT_PRESENT));
			sponserMaster.setUser(user);
			if (!sponserDto.getName().equals(user.getName())) {
				throw new ResourceNotFoundException(ErrorMessageCode.INVALID_SPONSOR_NAME);
			}
		}

		sponserRepo.save(sponserMaster);
		return sponserDto;
	}

	@Autowired
	TrackSponsorRepository TrackSponsorRepository;

	@Override
	public void deleteSponser(Long id, Long userId) {
		SponsorMaster sponserEntity = sponserRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.SPONSER_NOT_FOUND));
		sponserEntity.setIsActive(false);
		sponserEntity.setUpdatedBy(userId);
		List<TrackSponsor> trackSponsor = TrackSponsorRepository.findBySponsor(sponserEntity);
		for (int i = 0; i < trackSponsor.size(); i++) {
			trackSponsor.get(i).setIsActive(false);
			trackSponsor.get(i).setSponsor(null);
		}
		sponserRepo.save(sponserEntity);
		TrackSponsorRepository.saveAll(trackSponsor);
	}

	@Override
	public Page<IListSponsorDto> getAllSponsers(String search, String pageNo, String pageSize) {

		Page<IListSponsorDto> iListSponserDto;
		Pageable pageable = new Pagination().getPagination(pageNo, pageSize);
		iListSponserDto = this.sponserRepo.findAllList(search, pageable, IListSponsorDto.class);

		return iListSponserDto;

	}

	@Override
	public List<IListSponsorDto> findAllSponsors(Class<IListSponsorDto> class1) {
		List<IListSponsorDto> sponsorDto = this.sponserRepo.findByOrderByIdDesc(IListSponsorDto.class);
		return sponsorDto;
	}

	@Override
	public SponsorDto updateSponser(SponsorDto sponserDto, Long id, Long userId, MultipartFile file,
			HttpServletRequest request) throws Exception {
		SponsorMaster sponserEntity = sponserRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.SPONSER_NOT_FOUND));

		DesignationEntity designation = designationRepo.findById(sponserDto.getDesignationId())
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.DESIGNATION_NOT_FOUND));

		String newName = sponserDto.getName().trim();
		if (!newName.equalsIgnoreCase(sponserEntity.getName())) {
			SponsorMaster entity1 = this.sponserRepo.findByNameIgnoreCase(newName);
			if (entity1 != null) {
				throw new ResourceNotFoundException(ErrorMessageCode.SPONSER_ALREADY_PRESENT);
			}
		}

		sponserEntity.setName(sponserDto.getName());
		sponserEntity.setDescription(sponserDto.getDescription());
		sponserEntity.setDesignationId(designation);
		sponserEntity.setProfile(sponserDto.getProfile());
		sponserEntity.setUpdatedBy(userId);

		if (sponserDto.isFileUpdate() == true) {

			if (file != null && !file.isEmpty()) {

				String originalFilename = file.getOriginalFilename();

				if (Validator.isValidforImageFile(originalFilename)) {
					if (sponserEntity.getFileId() != null) {
						this.fileUploadImpl.delete(sponserEntity.getFileId().getId());
					}
					FileUploadEntity entity = this.fileUploadImpl.storeFile(file, request);

					sponserEntity.setFileId(entity);
				} else {
					throw new IllegalArgumentException(ErrorMessageCode.VALID_IMAGE);
				}
			} else {
				throw new IllegalArgumentException(ErrorMessageCode.FILE_REQUIRED);
			}
		}

		if (sponserDto.getUser() != null) {
			UserEntity user = userRepo.findById(sponserDto.getUser())
					.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.USER_NOT_PRESENT));
			sponserEntity.setUser(user);
		}

		if (sponserDto.getUser() == null) {
			sponserEntity.setUser(null);
		}
		this.sponserRepo.save(sponserEntity);
		return sponserDto;

	}

	@Override
	public IListSponsorDto findById(Long id) {
		IListSponsorDto sponsor = this.sponserRepo.findBySponsorId(id, IListSponsorDto.class);
		if (sponsor == null) {
			throw new ResourceNotFoundException(ErrorMessageCode.SPONSER_NOT_FOUND);
		}
		return sponsor;
	}

	@Override
	public void deleteMultipleSponsorsById(DeleteId ids, Long userId) {
		List<SponsorMaster> entity = sponserRepo.findAllById(ids.getIds());
		for (int i = 0; i < entity.size(); i++) {
			entity.get(i).setUpdatedBy(userId);
		}

		sponserRepo.deleteAllByIdIn(ids.getIds());

	}

}
