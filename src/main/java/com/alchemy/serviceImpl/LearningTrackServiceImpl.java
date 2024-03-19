package com.alchemy.serviceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alchemy.dto.DeleteId;
import com.alchemy.dto.LearningTrackDto;
import com.alchemy.dto.LearningTrackUpdateDto;
import com.alchemy.dto.LearningTrackUpdateFileDto;
import com.alchemy.dto.SubTrackDtoForLearningTrack;
import com.alchemy.dto.VisibleContentDto;
import com.alchemy.entities.BulkUploadInformation;
import com.alchemy.entities.DesignationEntity;
import com.alchemy.entities.FileUploadEntity;
import com.alchemy.entities.GplFunctionEntity;
import com.alchemy.entities.LearningTrackBulkUploadTemporaryEntity;
import com.alchemy.entities.LearningTrackEntity;
import com.alchemy.entities.LevelEntity;
import com.alchemy.entities.ModuleMasterEntity;
import com.alchemy.entities.NudgedTracks;
import com.alchemy.entities.SponsorMaster;
import com.alchemy.entities.SubTrackEntity;
import com.alchemy.entities.TrackGplFunctionEntity;
import com.alchemy.entities.TrackLevelEntity;
import com.alchemy.entities.TrackSponsor;
import com.alchemy.entities.TrackTrainer;
import com.alchemy.entities.TrainersMasterEntity;
import com.alchemy.entities.UserEntity;
import com.alchemy.entities.UserTrackEntity;
import com.alchemy.exceptionHandling.ResourceNotFoundException;
import com.alchemy.iListDto.IListEnrollStatusCount;
import com.alchemy.iListDto.IListLearningTrack;
import com.alchemy.iListDto.IListLearningTrackDetail;
import com.alchemy.iListDto.IListSubTrack;
import com.alchemy.iListDto.IListUserDetails;
import com.alchemy.iListDto.IListUserEnroll;
import com.alchemy.iListDto.SubTrackList;
import com.alchemy.iListDto.TrackList;
import com.alchemy.repositories.BulkUploadInformationRepository;
import com.alchemy.repositories.DesignationRepository;
import com.alchemy.repositories.FileUploadRepository;
import com.alchemy.repositories.GplFunctionRepository;
import com.alchemy.repositories.LearningTrackBulkUploadRepository;
import com.alchemy.repositories.LearningTrackRepository;
import com.alchemy.repositories.LevelRepository;
import com.alchemy.repositories.LevelTrackReopsitory;
import com.alchemy.repositories.ModuleMasterRepository;
import com.alchemy.repositories.NudgedTracksRepository;
import com.alchemy.repositories.SponserRepository;
import com.alchemy.repositories.SubTrackRepository;
import com.alchemy.repositories.TrackGplFunctionRepository;
import com.alchemy.repositories.TrackSponsorRepository;
import com.alchemy.repositories.TrackTrainerRepository;
import com.alchemy.repositories.TrainersMasterRepository;
import com.alchemy.repositories.UserRepository;
import com.alchemy.repositories.UserTrackRepository;
import com.alchemy.serviceInterface.FileUploadInterface;
import com.alchemy.serviceInterface.LearningTrackInterface;
import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.GlobalFunctions;
import com.alchemy.utils.Pagination;
import com.alchemy.utils.Validator;
import com.opencsv.CSVReader;

@Transactional
@Service
public class LearningTrackServiceImpl implements LearningTrackInterface {
	@Autowired
	UserTrackRepository userTrackRepository;
	@Autowired
	private LearningTrackRepository learningTrackRepository;

	@Autowired
	private SponserRepository sponserRepository;

	@Autowired
	private TrainersMasterRepository trainersRepository;

	@Autowired
	private TrackSponsorRepository trackSponsorRepository;

	@Autowired
	private TrackTrainerRepository trackTrainerRepository;

	@Autowired
	private FileUploadInterface fileUploadInterface;

	@Autowired
	private BulkUploadInformationRepository bulkUploadInformationRepository;

	@Autowired
	private ModuleMasterRepository moduleMasterRepository;

	@Autowired
	private SubTrackRepository subTrackRepository;

	@Autowired
	private LearningTrackBulkUploadRepository learningTrackBulkUploadRepository;

	@Autowired
	FileUploadRepository fileUploadRepository;

	@Autowired
	DesignationRepository designationRepository;

	@Autowired
	FileUploadImpl fileUploadImpl;

	@Autowired
	private TrackGplFunctionRepository trackGplFunctionRepository;

	@Autowired
	private GplFunctionRepository gplFunctionRepository;

	@Autowired
	private LevelTrackReopsitory levelTrackReopsitory;

	@Autowired
	private LevelRepository levelRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	NudgedTracksRepository nudgedTracksRepository;

	@SuppressWarnings("resource")
	@Override
	public LearningTrackUpdateFileDto updateLearningTrack(LearningTrackUpdateFileDto learningTrackDto, Long id,
			MultipartFile file, MultipartFile banner, MultipartFile bannerCard, HttpServletRequest request, Long userId,
			MultipartFile nudgedFile) throws Exception {
		LearningTrackEntity learningTrackEntity = this.learningTrackRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.LEARNING_TRACK_NOT_FOUND));
		LearningTrackEntity trackEntity = this.learningTrackRepository
				.findByNameIgnoreCaseAndIsActiveTrue(learningTrackDto.getName());
		if (trackEntity != null) {
			if (trackEntity.getId() != learningTrackEntity.getId()) {
				throw new ResourceNotFoundException(ErrorMessageCode.LEARNING_TRACK_ALREADY_EXIST);
			}
		}

		learningTrackEntity.setName(learningTrackDto.getName());
		learningTrackEntity.setObjective(learningTrackDto.getObjective());
		learningTrackEntity.setUpdatedBy(userId);
		learningTrackEntity.setSummary(learningTrackDto.getSummary());

		if (learningTrackDto.getStartDate() != null) {
			learningTrackEntity
					.setStartDate(GlobalFunctions.dateConvertIntoTimestamp(learningTrackDto.getStartDate(), 0, 0, 1));
		}
		if (learningTrackDto.getEndDate() != null) {
			learningTrackEntity
					.setEndDate(GlobalFunctions.dateConvertIntoTimestamp(learningTrackDto.getEndDate(), 23, 59, 59));
		}
		if (learningTrackDto.getEnrollStartDate() != null) {
			learningTrackEntity.setEnrollStartDate(
					GlobalFunctions.dateConvertIntoTimestamp(learningTrackDto.getEnrollStartDate(), 0, 0, 1));
		}
		if (learningTrackDto.getEnrollCloseDate() != null) {
			learningTrackEntity.setEnrollCloseDate(
					GlobalFunctions.dateConvertIntoTimestamp(learningTrackDto.getEnrollCloseDate(), 23, 59, 59));
		}
		dateValidations(learningTrackDto.getEnrollStartDate(), learningTrackDto.getEnrollCloseDate(),
				learningTrackDto.getStartDate(), learningTrackDto.getEndDate(), learningTrackEntity);

		if (learningTrackDto.getIsBannerUpdated() != null && learningTrackDto.getIsBannerUpdated()) {

			if (banner == null || banner.isEmpty()) {
				FileUploadEntity fileId = learningTrackEntity.getBannerFileId();
				if (fileId != null) {
					fileUploadInterface.delete(fileId.getId());
				}
				learningTrackEntity.setBannerFileId(null);
			} else {
				String originalName = banner.getOriginalFilename();
				if (Validator.isValidforImageFile(originalName)) {
					if (learningTrackEntity.getBannerFileId() == null) {
						FileUploadEntity fileUploadEntity = fileUploadInterface.storeFile(banner, request);
						learningTrackEntity.setBannerFileId(fileUploadEntity);

					} else {
						fileUploadInterface.delete(learningTrackEntity.getBannerFileId().getId());
						FileUploadEntity fileUploadEntity = fileUploadInterface.storeFile(banner, request);
						learningTrackEntity.setBannerFileId(fileUploadEntity);
					}

				} else {
					throw new IllegalArgumentException(ErrorMessageCode.VALID_IMAGE);
				}
			}
		}
		if (learningTrackDto.getIsBannerCardUpdated() != null && learningTrackDto.getIsBannerCardUpdated()) {
			if (bannerCard == null || bannerCard.isEmpty()) {
				FileUploadEntity fileId = learningTrackEntity.getBannerCard();
				if (fileId != null) {
					fileUploadInterface.delete(fileId.getId());
				}
				learningTrackEntity.setBannerCard(null);
			} else {
				String originalName = bannerCard.getOriginalFilename();
				if (Validator.isValidforImageFile(originalName)) {
					if (learningTrackEntity.getBannerCard() == null) {
						FileUploadEntity fileUploadEntity = fileUploadInterface.storeFile(bannerCard, request);
						learningTrackEntity.setBannerCard(fileUploadEntity);
					} else {
						if (learningTrackEntity.getBannerCard() != null) {
							fileUploadInterface.delete(learningTrackEntity.getBannerCard().getId());
						}
						FileUploadEntity fileUploadEntity = fileUploadInterface.storeFile(bannerCard, request);
						learningTrackEntity.setBannerCard(fileUploadEntity);
					}
				} else {
					throw new IllegalArgumentException(ErrorMessageCode.VALID_IMAGE);
				}
			}
		}
		if (learningTrackDto.getIsFileUpdated() != null && learningTrackDto.getIsFileUpdated()) {
			if (file == null || file.isEmpty()) {
				if (learningTrackEntity.getFileId() != null && learningTrackEntity.getFileId().getId() != null) {
					fileUploadInterface.delete(learningTrackEntity.getFileId().getId());
				}
				learningTrackEntity.setFileId(null);
			}
			if (file != null && !file.isEmpty()) {
				if (learningTrackEntity.getFileId() == null) {
					if (Validator.isValidforPdf(file)) {
						FileUploadEntity fileUploadEntity = fileUploadInterface.storeFile(file, request);
						learningTrackEntity.setFileId(fileUploadEntity);
					} else {
						throw new IllegalArgumentException(ErrorMessageCode.PLEASE_UPLOAD_PDF);
					}
				} else {
					if (Validator.isValidforPdf(file)) {
						fileUploadInterface.delete(learningTrackEntity.getFileId().getId());
						FileUploadEntity fileUploadEntity = fileUploadInterface.storeFile(file, request);
						learningTrackEntity.setFileId(fileUploadEntity);
					} else {
						throw new IllegalArgumentException(ErrorMessageCode.PLEASE_UPLOAD_PDF);
					}

				}

			}
		}

		this.learningTrackRepository.save(learningTrackEntity);

		// the bellow code is for mapping tables
		List<TrackSponsor> trackSponsorsList = new ArrayList<>();
		List<TrackTrainer> trackTrainerList = new ArrayList<>();
		ArrayList<TrackGplFunctionEntity> TrackFunctiontList = new ArrayList<TrackGplFunctionEntity>();
		ArrayList<TrackLevelEntity> listTrackLevel = new ArrayList<>();
		ArrayList<SubTrackEntity> subTrackEntities = new ArrayList<>();
		ArrayList<UserTrackEntity> userTrackList = new ArrayList<UserTrackEntity>();

		if (learningTrackDto.getSubTrack() != null && !learningTrackDto.getSubTrack().isEmpty()) {
			for (int i = 0; i < learningTrackDto.getSubTrack().size(); i++) {
				if (learningTrackDto.getSubTrack().get(i).getFlagId() <= 0) {
					throw new ResourceNotFoundException("Please enter a flag in between 1 to 3");
				}
				switch (learningTrackDto.getSubTrack().get(i).getFlagId()) {
				case 1: {
					validateSubTrackData(learningTrackDto.getSubTrack().get(i));
					// for adding new subtracks
					IListSubTrack subTrack = this.subTrackRepository.findByNameAndlearning_track_entity(
							learningTrackDto.getSubTrack().get(i).getName(), learningTrackEntity.getId());

					if (subTrack != null) {
						throw new ResourceNotFoundException("Sub-tracks cannot have same name");
					} else {
						SubTrackEntity entity = new SubTrackEntity();
						entity.setName(learningTrackDto.getSubTrack().get(i).getName().trim());
						entity.setCreatedBy(userId);
						entity.setStartDate(GlobalFunctions.dateConvertIntoTimestamp(
								learningTrackDto.getSubTrack().get(i).getStartDate(), 0, 0, 1));
						entity.setLearningTrackEntity(learningTrackEntity);
						Date sDate = learningTrackDto.getSubTrack().get(i).getStartDate();
						Date eDate = learningTrackDto.getSubTrack().get(i).getEndDate();
						if (eDate.compareTo(sDate) >= 0) {
							entity.setEndDate(GlobalFunctions.dateConvertIntoTimestamp(
									learningTrackDto.getSubTrack().get(i).getEndDate(), 23, 59, 59));
						} else {
							throw new ResourceNotFoundException(ErrorMessageCode.SUBTRACK_TRACK_DATE_VALIDATION);
						}

						subTrackEntities.add(entity);
					}
					break;
				}

				case 2: {
					// updating sub track
					validateSubTrackData(learningTrackDto.getSubTrack().get(i));
					SubTrackEntity updateSubtrack = this.subTrackRepository
							.findById(learningTrackDto.getSubTrack().get(i).getSubTrackId())
							.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.SUBTRACK_NOT_PRESENT));
					updateSubtrack.setName(learningTrackDto.getSubTrack().get(i).getName());
					updateSubtrack.setUpdatedBy(userId);
					updateSubtrack.setStartDate(GlobalFunctions
							.dateConvertIntoTimestamp(learningTrackDto.getSubTrack().get(i).getStartDate(), 0, 0, 1));
					updateSubtrack.setLearningTrackEntity(learningTrackEntity);
					Date sDate = learningTrackDto.getSubTrack().get(i).getStartDate();
					Date eDate = learningTrackDto.getSubTrack().get(i).getEndDate();
					if (eDate.compareTo(sDate) >= 0) {
						updateSubtrack.setEndDate(GlobalFunctions.dateConvertIntoTimestamp(
								learningTrackDto.getSubTrack().get(i).getEndDate(), 23, 59, 59));
					} else {
						throw new ResourceNotFoundException(ErrorMessageCode.SUBTRACK_TRACK_DATE_VALIDATION);

					}
					subTrackEntities.add(updateSubtrack);
					break;

				}
				case 3: {
					// delete subTrack assingn to LT
					SubTrackEntity updateSubtrack = this.subTrackRepository
							.findById(learningTrackDto.getSubTrack().get(i).getSubTrackId())
							.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.SUBTRACK_NOT_PRESENT));
					updateSubtrack.setIsActive(false);
					updateSubtrack.setUpdatedBy(userId);
					ArrayList<UserTrackEntity> userTrackEntity = userTrackRepository
							.findBysubtrackIdId(updateSubtrack.getId());

					for (int j = 0; j < userTrackEntity.size(); j++) {
						userTrackEntity.get(j).setIsActive(false);
						userTrackList.addAll(userTrackEntity);
					}
					subTrackEntities.add(updateSubtrack);
					userTrackRepository.saveAll(userTrackList);
					break;
				}

				}

			}

			this.subTrackRepository.saveAll(subTrackEntities);
		}

		if (learningTrackDto.getSponsor() != null && !learningTrackDto.getSponsor().isEmpty()) {
			updateTraskSponsor(learningTrackDto, userId, learningTrackEntity, trackSponsorsList);
		}

		if (learningTrackDto.getTrainerId() != null && !learningTrackDto.getTrainerId().isEmpty()) {
			List<TrainersMasterEntity> trainersMasterEntity = this.trainersRepository
					.findByIdIn(learningTrackDto.getTrainerId());
			if (trainersMasterEntity.size() != learningTrackDto.getTrainerId().size()) {
				throw new ResourceNotFoundException(ErrorMessageCode.TRAINER_NOT_PRESENT);
			}
			for (int j = 0; j < trainersMasterEntity.size(); j++) {
				TrackTrainer trackTrainer = new TrackTrainer();
				trackTrainer.setLearningTrack(learningTrackEntity);
				trackTrainer.setTrainersMaster(trainersMasterEntity.get(j));
				trackTrainer.setCreatedBy(userId);
				trackTrainerList.add(trackTrainer);
			}
		}

		if (learningTrackDto.getFunctionId() != null && !learningTrackDto.getFunctionId().isEmpty()) {
			ArrayList<GplFunctionEntity> functionEntities = gplFunctionRepository
					.findByIdIn(learningTrackDto.getFunctionId());
			if (functionEntities.size() != learningTrackDto.getFunctionId().size()) {
				throw new ResourceNotFoundException(ErrorMessageCode.DEPARTMENT_NOT_FOUND);
			}
			for (int i = 0; i < functionEntities.size(); i++) {
				TrackGplFunctionEntity trackGplFunctionEntity = new TrackGplFunctionEntity();
				trackGplFunctionEntity.setLearningTrackEntity(learningTrackEntity);
				trackGplFunctionEntity.setGplFunctionEntity(functionEntities.get(i));
				trackGplFunctionEntity.setUpdatedBy(userId);
				TrackFunctiontList.add(trackGplFunctionEntity);
			}
		}

		if (learningTrackDto.getLevelId() != null && !learningTrackDto.getLevelId().isEmpty()) {
			List<LevelEntity> levelEntities = this.levelRepository.findAllById(learningTrackDto.getLevelId());

			if (levelEntities.size() != learningTrackDto.getLevelId().size()) {
				throw new ResourceNotFoundException(ErrorMessageCode.LEVEL_NOT_FOUND);
			}
			for (int i = 0; i < levelEntities.size(); i++) {

				TrackLevelEntity trackLevelEntity = new TrackLevelEntity();
				trackLevelEntity.setLearningTrackId(learningTrackEntity);
				trackLevelEntity.setLevelId(levelEntities.get(i));
				listTrackLevel.add(trackLevelEntity);

			}
		}
		ArrayList<NudgedTracks> nudgedTracksList = new ArrayList<>();

		if (nudgedFile == null) {
			FileUploadEntity fileId = learningTrackEntity.getNudgedFileId();
			if (fileId != null) {
				this.fileUploadInterface.delete(fileId.getId());
				this.nudgedTracksRepository.deletedeleteByLearningTrackId(learningTrackEntity.getId());
			}
			learningTrackEntity.setNudgedFileId(null);

		} else {
//			if (nudgedFile != null) {

			if (!nudgedFile.getOriginalFilename().endsWith(".csv")) {
				throw new ResourceNotFoundException(ErrorMessageCode.UPLOAD_CSV);

			}

			CSVReader csvReader1 = new CSVReader(new InputStreamReader(nudgedFile.getInputStream()));
			String[] headers = csvReader1.readNext();
			if (!"email".equalsIgnoreCase(headers[0].toString().trim().replaceAll("\\s+", ""))) {
				throw new ResourceNotFoundException("File doesn't contain the valid column");
			}

			if (headers.length > 1) {
				throw new ResourceNotFoundException(
						"The learning track file is not valid. The column name 'email' should have a size of 1");
			}

			InputStreamReader reader = new InputStreamReader(nudgedFile.getInputStream());
			BufferedReader bufferedReader = new BufferedReader(reader);
			CSVReader csvReader = new CSVReader(bufferedReader);

			List<String[]> records = csvReader.readAll();
			ArrayList<String> email = new ArrayList<>();
			for (int i = 1; i < records.size(); i++) {

				String[] record = records.get(i);
				for (int j = 0; j < record.length; j++) {
					if (record != null && !record.toString().isEmpty() && !"".equals(record.toString())) {
						if (Validator.isValidforEmail(record[j].toString())) {
							email.add(record[j]);
						} else {
							throw new ResourceNotFoundException("Email " + "'" + record[j] + "'" + "is not valid");
						}

					} else {
						throw new ResourceNotFoundException(
								"Email Cell at row " + i + " and column " + Constant.columnNames[j] + " is empty.");
					}
//					email.add(record[j]);

				}

			}

			ArrayList<UserEntity> userEntity = userRepository.findByEmailIn(email);
//			Optional<LearningTrackEntity> LearningTrack = learningTrackRepository.findById(learningTrackEntity.getId());

			if (learningTrackEntity.getNudgedFileId() != null) {

				this.fileUploadImpl.delete(learningTrackEntity.getNudgedFileId().getId());
				this.nudgedTracksRepository.deletedeleteByLearningTrackId(learningTrackEntity.getId());

			}

			FileUploadEntity fileUploadEntity = this.fileUploadImpl.storeFile(nudgedFile, request);
			learningTrackEntity.setNudgedFileId(fileUploadEntity);

			for (int i = 0; i < userEntity.size(); i++)

			{
				NudgedTracks nudgedTracks = new NudgedTracks();
				nudgedTracks.setTrackId(learningTrackEntity);
				nudgedTracks.setUpdatedBy(userId);
				nudgedTracks.setUserEntity(userEntity.get(i));
				nudgedTracksList.add(nudgedTracks);

			}

			this.nudgedTracksRepository.deletedeleteByLearningTrackId(learningTrackEntity.getId());
		}

		// this.trackSponsorRepository.deleteByLearningTrack(learningTrackEntity);
		this.trackTrainerRepository.deleteByLearningTrackId(learningTrackEntity.getId());
		this.trackGplFunctionRepository.deleteByLearningTrackEntityId(learningTrackEntity.getId());
		this.levelTrackReopsitory.deleteByLearningTrackId(learningTrackEntity);
		this.nudgedTracksRepository.saveAll(nudgedTracksList);
		this.trackSponsorRepository.saveAll(trackSponsorsList);
		this.trackTrainerRepository.saveAll(trackTrainerList);
		this.trackGplFunctionRepository.saveAll(TrackFunctiontList);
		this.levelTrackReopsitory.saveAll(listTrackLevel);

		return learningTrackDto;
	}

	private void validateSubTrackData(SubTrackDtoForLearningTrack subTrack) {
		if (subTrack.getName() == null || subTrack.getName().isEmpty()) {
			throw new ResourceNotFoundException("Subtrack name is required.");
		}
		if (subTrack.getStartDate() == null) {
			throw new ResourceNotFoundException("Subtrack start date is required.");
		}
		if (subTrack.getEndDate() == null) {
			throw new ResourceNotFoundException("Subtrack end date is required.");
		}
	}

	private void updateTraskSponsor(LearningTrackUpdateFileDto learningTrackDto, Long userId,
			LearningTrackEntity learningTrackEntity, List<TrackSponsor> trackSponsorsList) {
		for (int i = 0; i < learningTrackDto.getSponsor().size(); i++) {

			SponsorMaster sponsorMaster = this.sponserRepository
					.findById(learningTrackDto.getSponsor().get(i).getSponsorId())
					.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.SPONSER_NOT_FOUND));
			switch (learningTrackDto.getSponsor().get(i).getFlagId()) {
			case 1: {
				// for new track
				TrackSponsor newTrackSponsor = new TrackSponsor();
				newTrackSponsor.setSponsor(sponsorMaster);
				newTrackSponsor.setLearningTrack(learningTrackEntity);
				newTrackSponsor.setSponsorMessage(learningTrackDto.getSponsor().get(i).getSponsorMessage());
				newTrackSponsor.setCreatedBy(userId);
				trackSponsorsList.add(newTrackSponsor);
				break;
			}
			case 2: {
				TrackSponsor trackSponsor = this.trackSponsorRepository
						.findById(learningTrackDto.getSponsor().get(i).getTrackSponsorId())
						.orElseThrow(() -> new ResourceNotFoundException("Track Sponsor not fond"));
				// for update track sponsor
				trackSponsor.setSponsor(sponsorMaster);
				trackSponsor.setLearningTrack(learningTrackEntity);
				trackSponsor.setSponsorMessage(learningTrackDto.getSponsor().get(i).getSponsorMessage());
				trackSponsor.setUpdatedBy(userId);
				trackSponsorsList.add(trackSponsor);
				break;
			}
			case 3: {
				TrackSponsor trackSponsor = this.trackSponsorRepository
						.findById(learningTrackDto.getSponsor().get(i).getTrackSponsorId())
						.orElseThrow(() -> new ResourceNotFoundException("Track Sponsor not fond"));
				// delete track sponsor
				trackSponsor.setIsActive(false);
				trackSponsorsList.add(trackSponsor);
			}
			}
		}
	}

	@Override
	public void deleteLearningTrackById(Long learningTrackId, Long userId) {

		LearningTrackEntity learningTrackEntity = learningTrackRepository.findById(learningTrackId)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.LEARNING_TRACK_NOT_FOUND));
		learningTrackEntity.setIsActive(false);
		learningTrackEntity.setUpdatedBy(userId);
		List<SubTrackEntity> subTrackEntity = subTrackRepository.findByLearningTrackEntity(learningTrackEntity);
		for (int i = 0; i < subTrackEntity.size(); i++) {
			subTrackEntity.get(i).setIsActive(false);
			subTrackEntity.get(i).setUpdatedBy(userId);
		}
		List<UserTrackEntity> userTrackEntity = userTrackRepository.findByTrackId(learningTrackEntity);
		for (int i = 0; i < userTrackEntity.size(); i++) {
			userTrackEntity.get(i).setIsActive(false);
			userTrackEntity.get(i).setUpdatedBy(userId);
		}
		List<TrackLevelEntity> trackMappings = this.levelTrackReopsitory.findByLearningTrackId(learningTrackEntity);
		for (TrackLevelEntity map : trackMappings) {
			map.setIsActive(false);
			map.setUpdatedBy(userId);
		}

		learningTrackRepository.save(learningTrackEntity);
		subTrackRepository.saveAll(subTrackEntity);
		userTrackRepository.saveAll(userTrackEntity);
		levelTrackReopsitory.saveAll(trackMappings);

	}

	@Override
	public Page<IListLearningTrack> getAllLearningTracks(String search, Long userId, String pageNo, String pageSize,
			ArrayList<String> permissiosName, String fromAdmin) throws Exception {

		Page<IListLearningTrack> iListLearningTrack = null;

		String pageNumber = pageNo.isBlank() ? Constant.DEFAULT_PAGENUMBER : pageNo;
		String pages = pageSize.isBlank() ? Constant.DEFAULT_PAGESIZE : pageSize;
		Pageable pageable = new Pagination().getPagination(pageNumber, pages);
		if (pageNo.isBlank() && pageSize.isBlank()) {
			pageable = Pageable.unpaged();
		}

		if (permissiosName.contains("IsAdmin") && (!fromAdmin.isBlank() || !fromAdmin.isEmpty())) {
			iListLearningTrack = this.learningTrackRepository.findByNameContainingIgnoreCaseOrderByIdDesc(search,
					userId, pageable, IListLearningTrack.class);

		} else if (permissiosName.contains("IsUser")) {

			iListLearningTrack = this.learningTrackRepository.findByNameForUser(search, userId, pageable,
					IListLearningTrack.class);

		} else {
			String defaultId = "-0";
			iListLearningTrack = this.learningTrackRepository.findByNameContainingIgnoreCaseOrderByIdDesc(defaultId,
					userId, pageable, IListLearningTrack.class);
		}
		return iListLearningTrack;
	}

	@Override
	public LearningTrackDto addLearningTrackTrainerAndSponsor(LearningTrackDto learningTrackDto, MultipartFile file,
			MultipartFile banner, MultipartFile bannerCard, HttpServletRequest request, Long userId,
			MultipartFile nudgedFile) throws Exception {
		LearningTrackEntity learningTrackEntity = new LearningTrackEntity();
		LearningTrackEntity trackEntity = this.learningTrackRepository
				.findByNameIgnoreCaseAndIsActiveTrue(learningTrackDto.getName());
		if (trackEntity != null) {
			throw new ResourceNotFoundException(ErrorMessageCode.LEARNING_TRACK_ALREADY_EXIST);
		}

		learningTrackEntity.setName(learningTrackDto.getName());
		learningTrackEntity.setObjective(learningTrackDto.getObjective());
		learningTrackEntity.setCreatedBy(userId);
		learningTrackEntity.setSummary(learningTrackDto.getSummary());

		if (learningTrackDto.getStartDate() != null) {
			learningTrackEntity
					.setStartDate(GlobalFunctions.dateConvertIntoTimestamp(learningTrackDto.getStartDate(), 0, 0, 1));
		}
		if (learningTrackDto.getEndDate() != null) {
			learningTrackEntity
					.setEndDate(GlobalFunctions.dateConvertIntoTimestamp(learningTrackDto.getEndDate(), 23, 59, 59));
		}
		if (learningTrackDto.getEnrollStartDate() != null) {
			learningTrackEntity.setEnrollStartDate(
					GlobalFunctions.dateConvertIntoTimestamp(learningTrackDto.getEnrollStartDate(), 0, 0, 1));
		}
		if (learningTrackDto.getEnrollCloseDate() != null) {
			learningTrackEntity.setEnrollCloseDate(
					GlobalFunctions.dateConvertIntoTimestamp(learningTrackDto.getEnrollCloseDate(), 23, 59, 59));
		}

		dateValidations(learningTrackDto.getEnrollStartDate(), learningTrackDto.getEnrollCloseDate(),
				learningTrackDto.getStartDate(), learningTrackDto.getEndDate(), learningTrackEntity);

		if (banner != null && !banner.isEmpty()) {
			String originalName = banner.getOriginalFilename();
			if (Validator.isValidforImageFile(originalName)) {
				FileUploadEntity fileUploadEntity = fileUploadInterface.storeFile(banner, request);
				learningTrackEntity.setBannerFileId(fileUploadEntity);
			} else {
				throw new IllegalArgumentException(ErrorMessageCode.VALID_IMAGE);
			}
		}

		if (bannerCard != null && !bannerCard.isEmpty()) {
			String originalName = bannerCard.getOriginalFilename();
			if (Validator.isValidforImageFile(originalName)) {
				FileUploadEntity fileUploadEntity = fileUploadInterface.storeFile(bannerCard, request);
				learningTrackEntity.setBannerCard(fileUploadEntity);
			} else {
				throw new IllegalArgumentException(ErrorMessageCode.VALID_IMAGE);
			}
		}

		if (file != null && !file.isEmpty()) {
			if (Validator.isValidforPdf(file)) {
				FileUploadEntity fileUploadEntity = fileUploadInterface.storeFile(file, request);
				learningTrackEntity.setFileId(fileUploadEntity);
			} else {
				throw new IllegalArgumentException(ErrorMessageCode.PLEASE_UPLOAD_PDF);
			}
		}

		LearningTrackEntity learningTrack = learningTrackRepository.save(learningTrackEntity);

		List<TrackSponsor> trackSponsorsList = new ArrayList<>();
		List<TrackTrainer> trackTrainerList = new ArrayList<>();

		if (learningTrackDto.getSponsor() != null && !learningTrackDto.getSponsor().isEmpty()) {
			for (int i = 0; i < learningTrackDto.getSponsor().size(); i++) {
				if (learningTrackDto.getSponsor().get(i).getSponsorId() != null) {

					SponsorMaster sponsorMaster = this.sponserRepository
							.findById(learningTrackDto.getSponsor().get(i).getSponsorId())
							.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.SPONSER_NOT_FOUND));

					TrackSponsor trackSponsor = new TrackSponsor();
					trackSponsor.setSponsor(sponsorMaster);
					trackSponsor.setLearningTrack(learningTrackEntity);
					trackSponsor.setSponsorMessage(learningTrackDto.getSponsor().get(i).getSponsorMessage());
					trackSponsor.setCreatedBy(userId);
					trackSponsorsList.add(trackSponsor);
				}
			}
			this.trackSponsorRepository.saveAll(trackSponsorsList);
		}

		List<SubTrackEntity> subTrackEntitys = new ArrayList<>();
		ArrayList<SubTrackDtoForLearningTrack> subTrackList = learningTrackDto.getSubTrack();
		Set<String> subTrackNames = new HashSet<>();

		if (subTrackList != null && !subTrackList.isEmpty()) {
			for (int i = 0; i < subTrackList.size(); i++) {

				if (!learningTrackDto.getSubTrack().get(i).getName().isEmpty()
						|| learningTrackDto.getSubTrack().get(i).getStartDate() != null
						|| learningTrackDto.getSubTrack().get(i).getEndDate() != null) {

					if (!subTrackNames.add(learningTrackDto.getSubTrack().get(i).getName().toLowerCase())) {
						throw new ResourceNotFoundException("Sub-tracks cannot have same name");
					}
					SubTrackEntity subTrackEntity = new SubTrackEntity();
					subTrackEntity.setName(learningTrackDto.getSubTrack().get(i).getName());
					subTrackEntity.setStartDate(GlobalFunctions
							.dateConvertIntoTimestamp(learningTrackDto.getSubTrack().get(i).getStartDate(), 0, 0, 1));
					Date sDate = learningTrackDto.getSubTrack().get(i).getStartDate();
					Date eDate = learningTrackDto.getSubTrack().get(i).getEndDate();
					if (eDate != null && sDate != null && eDate.compareTo(sDate) >= 0) {
						subTrackEntity.setEndDate(GlobalFunctions.dateConvertIntoTimestamp(
								learningTrackDto.getSubTrack().get(i).getEndDate(), 23, 59, 59));
					} else {
						throw new ResourceNotFoundException(ErrorMessageCode.SUBTRACK_TRACK_DATE_VALIDATION);
					}
					subTrackEntity.setLearningTrackEntity(learningTrackEntity);
					subTrackEntity.setCreatedBy(userId);
					subTrackEntitys.add(subTrackEntity);
				}
			}

			this.subTrackRepository.saveAll(subTrackEntitys);

		}
		if (learningTrackDto.getTrainerId() != null && !learningTrackDto.getTrainerId().isEmpty()) {
			List<TrainersMasterEntity> trainersMasterEntity = this.trainersRepository
					.findByIdIn(learningTrackDto.getTrainerId());
			if (trainersMasterEntity.size() != learningTrackDto.getTrainerId().size()) {
				throw new ResourceNotFoundException(ErrorMessageCode.TRAINER_NOT_PRESENT);
			}
			for (int j = 0; j < trainersMasterEntity.size(); j++) {
				TrackTrainer trackTrainer = new TrackTrainer();
				trackTrainer.setLearningTrack(learningTrackEntity);
				trackTrainer.setTrainersMaster(trainersMasterEntity.get(j));
				trackTrainer.setCreatedBy(userId);
				trackTrainerList.add(trackTrainer);
			}

			this.trackTrainerRepository.saveAll(trackTrainerList);
		}
		if (learningTrackDto.getFunctionId() != null && !learningTrackDto.getFunctionId().isEmpty()) {
			ArrayList<GplFunctionEntity> functionEntities = this.gplFunctionRepository
					.findByIdIn(learningTrackDto.getFunctionId());
			if (functionEntities.size() != learningTrackDto.getFunctionId().size()) {
				throw new ResourceNotFoundException(ErrorMessageCode.GPL_FUNCTION_NOT_FOUND);
			}
			ArrayList<TrackGplFunctionEntity> TrackFunctionList = new ArrayList<TrackGplFunctionEntity>();
			for (int i = 0; i < functionEntities.size(); i++) {
				TrackGplFunctionEntity trackDepartmentEntity = new TrackGplFunctionEntity();
				trackDepartmentEntity.setLearningTrackEntity(learningTrackEntity);
				trackDepartmentEntity.setGplFunctionEntity(functionEntities.get(i));
				trackDepartmentEntity.setCreatedBy(userId);
				TrackFunctionList.add(trackDepartmentEntity);
			}
			trackGplFunctionRepository.saveAll(TrackFunctionList);
		}
		if (learningTrackDto.getLevelId() != null && !learningTrackDto.getLevelId().isEmpty()) {

			List<LevelEntity> levels = this.levelRepository.findAllById(learningTrackDto.getLevelId());

			if (levels.size() != learningTrackDto.getLevelId().size()) {
				throw new ResourceNotFoundException(ErrorMessageCode.LEVEL_NOT_FOUND);
			}
			List<TrackLevelEntity> levelTrackList = new ArrayList<>();

			for (int i = 0; i < levels.size(); i++) {
				TrackLevelEntity trackLevelEntity = new TrackLevelEntity();
				trackLevelEntity.setLearningTrackId(learningTrackEntity);
				trackLevelEntity.setLevelId(levels.get(i));
				levelTrackList.add(trackLevelEntity);
			}
			this.levelTrackReopsitory.saveAll(levelTrackList);
		}

		if (nudgedFile != null) {

			if (!nudgedFile.getOriginalFilename().endsWith(".csv")) {
				throw new ResourceNotFoundException(ErrorMessageCode.UPLOAD_CSV);

			}

			CSVReader csvReader1 = new CSVReader(new InputStreamReader(nudgedFile.getInputStream()));
			String[] headers = csvReader1.readNext();
			if (!"email".equalsIgnoreCase(headers[0].toString().trim().replaceAll("\\s+", ""))) {
				throw new ResourceNotFoundException("File doesn't contain the valid column");
			}

			if (headers.length > 1) {
				throw new ResourceNotFoundException(
						"The learning track file is not valid. The column name 'email' should have a size of 1");
			}

			InputStreamReader reader = new InputStreamReader(nudgedFile.getInputStream());
			BufferedReader bufferedReader = new BufferedReader(reader);
			CSVReader csvReader = new CSVReader(bufferedReader);

			List<String[]> records = csvReader.readAll();
			ArrayList<String> email = new ArrayList<>();
			for (int i = 1; i < records.size(); i++) {

				String[] record = records.get(i);
				for (int j = 0; j < record.length; j++) {

					email.add(record[j]);

				}

			}

			ArrayList<UserEntity> userEntity = userRepository.findByEmailIn(email);

			Optional<LearningTrackEntity> LearningTrack = learningTrackRepository.findById(learningTrack.getId());

			ArrayList<NudgedTracks> nudgedTracksList = new ArrayList<>();
			FileUploadEntity fileUploadEntity = this.fileUploadImpl.storeFile(nudgedFile, request);
			learningTrackEntity.setNudgedFileId(fileUploadEntity);
			for (int i = 0; i < userEntity.size(); i++)

			{
				NudgedTracks nudgedTracks = new NudgedTracks();
				nudgedTracks.setTrackId(LearningTrack.get());
				nudgedTracks.setCreatedBy(userId);
				nudgedTracks.setUserEntity(userEntity.get(i));

				nudgedTracksList.add(nudgedTracks);

			}

			nudgedTracksRepository.saveAll(nudgedTracksList);

		}

		return learningTrackDto;
	}

	private void dateValidations(Date enrollStartDate, Date enrollClosetDate, Date startDate, Date endDate,
			LearningTrackEntity learningTrackEntity) {

		if (enrollClosetDate != null) {
			if (enrollStartDate != null) {
				Date closeDate = enrollClosetDate;
				Date startDateFirst = enrollStartDate;
				if (closeDate.compareTo(startDateFirst) >= 0) {
					learningTrackEntity
							.setEnrollCloseDate(GlobalFunctions.dateConvertIntoTimestamp(enrollClosetDate, 23, 59, 59));
				} else {
					throw new IllegalArgumentException(ErrorMessageCode.LEARNING_TRACK_ENROLL_VALIDATION);
				}
			}
		}

		if (startDate != null) {
			if (endDate != null) {
				if (endDate.compareTo(startDate) >= 0) {
					learningTrackEntity.setEndDate(GlobalFunctions.dateConvertIntoTimestamp(endDate, 23, 59, 59));
				} else {
					throw new IllegalArgumentException(ErrorMessageCode.LEARNING_TRACK_DATE_VALIDATION);
				}
			}
			if (enrollClosetDate != null) {
				if (startDate.compareTo(enrollClosetDate) > 0) {
					learningTrackEntity.setStartDate(GlobalFunctions.dateConvertIntoTimestamp(startDate, 0, 0, 1));
				} else {
					throw new IllegalArgumentException("Enroll close date should be before start date");
				}
			}
			if (enrollStartDate != null) {
				if (startDate.compareTo(enrollStartDate) > 0) {
					learningTrackEntity
							.setEnrollStartDate(GlobalFunctions.dateConvertIntoTimestamp(enrollStartDate, 0, 0, 1));
				} else {
					throw new IllegalArgumentException("Enroll start date should be before start date");
				}
			}

		}

		if (endDate != null) {
			if (startDate != null) {
				if (endDate.compareTo(startDate) >= 0) {
					learningTrackEntity.setEndDate(GlobalFunctions.dateConvertIntoTimestamp(endDate, 23, 59, 59));
				} else {
					throw new IllegalArgumentException(ErrorMessageCode.LEARNING_TRACK_DATE_VALIDATION);
				}
			}
			if (enrollStartDate != null) {
				if (endDate.compareTo(enrollStartDate) > 0) {
					learningTrackEntity
							.setEnrollStartDate(GlobalFunctions.dateConvertIntoTimestamp(enrollStartDate, 0, 0, 1));
				} else {
					throw new IllegalArgumentException("End date should be before Enroll start date");
				}
			}
			if (enrollClosetDate != null) {
				if (endDate.compareTo(enrollClosetDate) > 0) {
					learningTrackEntity
							.setEnrollCloseDate(GlobalFunctions.dateConvertIntoTimestamp(enrollClosetDate, 23, 59, 59));
				} else {
					throw new IllegalArgumentException("End date should be before Enroll close date");
				}
			}
		}

	}

//	@Override
//	public List<LearningTrackDropdownList> findAllLearningTrackDropdown() {
//
//		List<LearningTrackDropdownList> list = this.learningTrackRepository
//				.findByOrderByIdDesc(LearningTrackDropdownList.class);
//		return list;
//	}

	@Override
	public IListLearningTrackDetail findById(Long trackId) {

		IListLearningTrackDetail detail = this.learningTrackRepository.findByIdAndIsActiveTrue(trackId,
				IListLearningTrackDetail.class);

		if (detail == null) {
			throw new ResourceNotFoundException(ErrorMessageCode.LEARNING_TRACK_NOT_FOUND);
		}
		return detail;

	}
//

	@SuppressWarnings("resource")
	@Override
	public Long learningTrackBulkUpload(MultipartFile multipartFile, Long userId, String moduleName)
			throws IOException, Exception {

		if (multipartFile.isEmpty()) {
			throw new IllegalArgumentException("File is empty");
		}

		if (moduleName.isEmpty()) {
			throw new IllegalArgumentException("ModuleName is empty");
		}

		List<LearningTrackBulkUploadTemporaryEntity> tempList = new ArrayList<>();
		BulkUploadInformation bulkUploadInformation = new BulkUploadInformation();
		bulkUploadInformation.setFileName(multipartFile.getOriginalFilename());
		bulkUploadInformation.setUserId(userId);
		ModuleMasterEntity module = this.moduleMasterRepository.findByModuleNameIgnoreCaseAndIsActiveTrue(moduleName);

		if (module == null) {
			throw new ResourceNotFoundException("Module not present");
		}

		bulkUploadInformation.setModuleId(module.getId());
		this.bulkUploadInformationRepository.saveAll(Arrays.asList(bulkUploadInformation));

		CSVReader csvReader1 = new CSVReader(new InputStreamReader(multipartFile.getInputStream()));
		String[] headers = csvReader1.readNext();
		ArrayList<String> arrayList = new ArrayList<>();
		String[] namesArray = { "TrackName", "functionname", "Summary", "EnrollmentstartDate", "TrackStartDate",
				"TrackEndDate", "Objective", "SubTrack1", "SubTrack2", "SubTrack3", "SubTrack4", "SubTrack5",
				"SubTrack6", "Sponsor1Names", "Sponsor1Profile", "Sponsor1Designation", "Sponsor2Names",
				"Sponsor2Profile", "Sponsor2Designation", "Trainer1Name", "Trainer1Bio", "Trainer1Designation",
				"Trainer2Name", "Trainer2Bio", "Trainer2Designation", "Trainer3Name", "Trainer3Bio",
				"Trainer3Designation", "Trainer4Name", "Trainer4Bio", "Trainer4Designation", "Trainer5Name",
				"Trainer5Bio", "Trainer5Designation", "Trainer6Name", "Trainer6Bio", "Trainer6Designation", "Level",
				"EnrollmentCloseDate" };

		List<String> names = Arrays.asList(namesArray);

		if (headers.length != names.size()) {
			throw new ResourceNotFoundException("File does not contain valid columns");
		}

		for (int s = 0; s < headers.length; s++) {

			if (headers[s] == null || headers[s].toString().isEmpty()) {
				throw new ResourceNotFoundException("Column '" + names + "' is empty at " + (s + 1));
			}

			arrayList.add(headers[s].toString().trim().replaceAll("\\s+", ""));
		}

		for (int n = 0; n < names.size(); n++) {

			if (!names.get(n).equalsIgnoreCase(arrayList.get(n))) {
				throw new ResourceNotFoundException(
						"learning track file is not valid at column name " + arrayList.get(n));
			}
		}

		// Loop through rows in Excel file and extract data
		InputStreamReader reader = new InputStreamReader(multipartFile.getInputStream());
		BufferedReader bufferedReader = new BufferedReader(reader);
		CSVReader csvReader = new CSVReader(bufferedReader);

		List<String[]> records = csvReader.readAll();

		for (int i = records.size() - 1; i >= 1; i--) {
			String[] record = records.get(i);
			boolean isEmpty = true;
			for (String cell : record) {
				if (cell != null && !cell.trim().isEmpty()) {
					isEmpty = false;
					break;
				}
			}

			ArrayList<String> subTrackList = new ArrayList<>();
			ArrayList<String> sponserList = new ArrayList<>();
			ArrayList<String> trainerList = new ArrayList<>();
			ArrayList<String> trackName = new ArrayList<>();

			if (!isEmpty) {
				LearningTrackBulkUploadTemporaryEntity tempTable = new LearningTrackBulkUploadTemporaryEntity();
				tempTable.setBulkId(bulkUploadInformation.getId());
				for (int j = 0; j < record.length; j++) {
					String cell = record[j];

					switch (j) {

					case 0: /// track name

						if (cell != null && !cell.isEmpty()) {

//							int count = (int) cell.chars().mapToObj(c -> (char) c).filter(Character::isLetterOrDigit)
//									.count();
							int count = (int) cell.chars().mapToObj(c -> (char) c).count();
							if (count >= 100) {
								throw new ResourceNotFoundException(ErrorMessageCode.LEARNING_TRACK_NAME);
							}

							if (!trackName.contains(cell) || trackName.isEmpty() || trackName == null) {
								tempTable.setTrackName(cell);
								trackName.add(cell);
							} else {
								throw new IllegalArgumentException(
										ErrorMessageCode.LEARNING_TRACK_ALREADY_EXIST + " " + cell);
							}

						} else {
							throw new ResourceNotFoundException("Learning track Cell at row " + i + " and column '"
									+ Constant.columnNames[j] + "' is empty.");
						}

						break;

					case 1: /// department

						String departmentNames = cell;

						if (cell == null || departmentNames.isBlank() || departmentNames.isBlank()) {
							throw new ResourceNotFoundException("Function name Cell at row " + i + " and column '"
									+ Constant.columnNames[j] + "' is not valid");

						}

						departmentNames = departmentNames.replaceAll(",\\s+", ",");

						String[] strArray = departmentNames.split(",");
						for (String string : strArray) {
							GplFunctionEntity departmentEntity = this.gplFunctionRepository
									.findByNameIgnoreCase(string);

							if (departmentEntity == null) {
								throw new ResourceNotFoundException("'" + string + "'" + " Function name Cell at row "
										+ +i + " and column '" + Constant.columnNames[j] + "' is not found ");

							}
						}

						String departmentName = "";
						for (int s = 0; s < strArray.length; s++) {
							if (s > 0) {

								departmentName += ", ";
							}

							departmentName += strArray[s];
						}

						tempTable.setDepartmentName(departmentName);
						break;

					case 2: // summary=description

						if (cell == null || cell.toString().isEmpty() || "".equals(cell.toString())) {

							throw new ResourceNotFoundException("Summary Cell at row " + i + " and column '"
									+ Constant.columnNames[j] + "' is empty.");
						} else {

//							int count = (int) cell.chars().mapToObj(c -> (char) c).filter(Character::isLetterOrDigit)
//									.count();
							int count = (int) cell.chars().mapToObj(c -> (char) c).count();

							if (count >= 500) {
								throw new ResourceNotFoundException(
										ErrorMessageCode.DESCRIPTION_NOT_EXCEED_FROM_500_CHARACTER);
							}

							tempTable.setDescription(cell);
						}

						break;

					case 3: // EnrollStartDate

						if (!cell.toString().isEmpty() && cell.toString() != "" && cell != null) {

							try {

								DateTimeFormatter formatter = new DateTimeFormatterBuilder().parseCaseInsensitive()
										.appendPattern("dd-MM-yyyy").toFormatter(Locale.ENGLISH);
								LocalDate localDate = LocalDate.parse(cell.toString(), formatter);
								Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()); // convert
																													// to
																													// Date

								Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(ZoneId.systemDefault())); // set

								calendar.setTime(date);
								calendar.set(Calendar.HOUR_OF_DAY, 13); // 1 PM
								calendar.set(Calendar.MINUTE, 0);
								calendar.set(Calendar.SECOND, 0);
								calendar.set(Calendar.MILLISECOND, 0);

								date = calendar.getTime();

								tempTable.setEnrollStartDate(GlobalFunctions.dateConvertIntoTimestamp(date, 0, 0, 1));

							} catch (IllegalArgumentException e) {
								throw new ResourceNotFoundException(
										"Invalid enroll start date at row " + i + ": " + cell);
							}
						} else {
							throw new ResourceNotFoundException("Enrollment startDate Cell at row " + i + " and column "
									+ Constant.columnNames[j] + " is empty.");
						}
						break;
					case 4: // start date
						Date enrollStartDate = tempTable.getEnrollStartDate();

						if (!cell.toString().isEmpty() && cell.toString() != "" && cell != null) {

							try {

								DateTimeFormatter formatter = new DateTimeFormatterBuilder().parseCaseInsensitive()
										.appendPattern("dd-MM-yyyy").toFormatter(Locale.ENGLISH);
								LocalDate localDate = LocalDate.parse(cell.toString(), formatter);
								Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()); // convert

								Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(ZoneId.systemDefault())); // set

								calendar.setTime(date);
								calendar.set(Calendar.HOUR_OF_DAY, 13); // 1 PM
								calendar.set(Calendar.MINUTE, 0);
								calendar.set(Calendar.SECOND, 0);
								calendar.set(Calendar.MILLISECOND, 0);

								date = calendar.getTime();

								if (enrollStartDate != null && date != null && enrollStartDate.compareTo(date) > 0) {
									throw new ResourceNotFoundException(
											"The enrollment start date should be before Trak start date,at row " + i
													+ " and column " + Constant.columnNames[j]);
								}
								tempTable.setStartDate(GlobalFunctions.dateConvertIntoTimestamp(date, 0, 0, 1));

							} catch (DateTimeParseException ex) {

								throw new ResourceNotFoundException("Invalid start date format, at row " + i
										+ " and column " + Constant.columnNames[j]
										+ ",Please provide a valid date in the format of 'dd-mm-yyyy'");
							}

						} else {
							throw new ResourceNotFoundException("Track startDate Cell at row " + i + " and column "
									+ Constant.columnNames[j] + " is empty.");
						}
						break;

					case 5: // Track end Date
						Date startDate = tempTable.getStartDate();
						if (!cell.toString().isEmpty() && cell.toString() != "" && cell != null) {

							try {
								DateTimeFormatter formatter = new DateTimeFormatterBuilder().parseCaseInsensitive()
										.appendPattern("dd-MM-yyyy").toFormatter(Locale.ENGLISH);
								LocalDate localDate = LocalDate.parse(cell.toString(), formatter);
								Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()); // convert
																													// to
								Calendar calendar = Calendar.getInstance();
								calendar.setTime(date);
								calendar.set(Calendar.HOUR_OF_DAY, 13); // 1 PM
								calendar.set(Calendar.MINUTE, 0);
								calendar.set(Calendar.SECOND, 0);
								calendar.set(Calendar.MILLISECOND, 0);

								date = calendar.getTime();
								if (startDate != null && date != null && startDate.compareTo(date) > 0) {
									throw new ResourceNotFoundException(
											"Track end date cannot be before start date,at row " + i + " and column "
													+ Constant.columnNames[j]);
								}

								tempTable.setEndDate(GlobalFunctions.dateConvertIntoTimestamp(date, 23, 59, 59));

							}

							catch (DateTimeParseException ex) {
								throw new ResourceNotFoundException("Invalid track end date format at row " + i
										+ " and column " + Constant.columnNames[j]
										+ ",Please provide a valid date in the format of 'dd-mm-yyyy'");
							}

						} else {
							throw new ResourceNotFoundException("track end date Cell at row " + i + " and column "
									+ Constant.columnNames[j] + " is empty.");
						}

						break;

					case 6: // objective

						if (cell != null && !cell.toString().isEmpty() && !"".equals(cell.toString())) {

							tempTable.setObjective(cell);

						} else {
							throw new ResourceNotFoundException("Objective Cell at row " + i + " and column '"
									+ Constant.columnNames[j] + "' is empty.");
						}

						break;

					case 7: /// subtrack1
						int count0 = (int) cell.chars().mapToObj(c -> (char) c).count();
						if (count0 >= 50) {
							throw new ResourceNotFoundException(ErrorMessageCode.SUBTRACK_NAME_VALIDATION);
						}
						if (cell == null || cell.isBlank() || cell.isEmpty()) {
							tempTable.setSubTrack1("");

						} else {
							subTrackList.add(cell.trim());
							tempTable.setSubTrack1(cell.trim());

						}
						break;

					case 8: // subtrack2
						int subcount1 = (int) cell.chars().mapToObj(c -> (char) c).count();
						if (subcount1 >= 50) {
							throw new ResourceNotFoundException(ErrorMessageCode.SUBTRACK_NAME_VALIDATION);
						}
						if (cell == null || cell.isBlank() || cell.isEmpty()) {
							tempTable.setSubTrack2("");

						} else {
							if (!subTrackList.contains(cell.trim())) {
								subTrackList.add(cell.trim());
								tempTable.setSubTrack2(cell.trim());

							} else {
								throw new IllegalArgumentException(
										ErrorMessageCode.SUBTRACK_ALREADY_ASSIGN + " " + cell);
							}
						}

						break;
					case 9: // subtrack3
						int subcount2 = (int) cell.chars().mapToObj(c -> (char) c).count();
						if (subcount2 >= 50) {
							throw new ResourceNotFoundException(ErrorMessageCode.SUBTRACK_NAME_VALIDATION);
						}
						if (cell == null || cell.isBlank() || cell.isEmpty()) {
							tempTable.setSubTrack3("");

						} else {
							if (!subTrackList.contains(cell.trim())) {
								subTrackList.add(cell.trim());
								tempTable.setSubTrack3(cell.trim());

							} else {
								throw new IllegalArgumentException(
										ErrorMessageCode.SUBTRACK_ALREADY_ASSIGN + " " + cell);
							}

//							tempTable.setSubTrack3(cell);
						}
						break;

					case 10: // subtrack 4
						int subcount3 = (int) cell.chars().mapToObj(c -> (char) c).count();
						if (subcount3 >= 50) {
							throw new ResourceNotFoundException(ErrorMessageCode.SUBTRACK_NAME_VALIDATION);
						}
						if (cell == null || cell.isBlank() || cell.isEmpty()) {
							tempTable.setSubTrack4("");

						} else {
							// tempTable.setSubTrack4(cell);
							if (!subTrackList.contains(cell.trim())) {
								subTrackList.add(cell.trim());
								tempTable.setSubTrack4(cell.trim());

							} else {
								throw new IllegalArgumentException(
										ErrorMessageCode.SUBTRACK_ALREADY_ASSIGN + " " + cell);
							}
						}
						break;

					case 11: /// sub track5
						int count4 = (int) cell.chars().mapToObj(c -> (char) c).count();
						if (count4 >= 50) {
							throw new ResourceNotFoundException(ErrorMessageCode.SUBTRACK_NAME_VALIDATION);
						}
						if (cell == null || cell.isBlank() || cell.isEmpty()) {
							tempTable.setSubTrack5("");

						} else {
//							tempTable.setSubTrack5(cell);
							if (!subTrackList.contains(cell.trim())) {
								subTrackList.add(cell.trim());
								tempTable.setSubTrack5(cell.trim());

							} else {
								throw new IllegalArgumentException(
										ErrorMessageCode.SUBTRACK_ALREADY_ASSIGN + " " + cell);
							}
						}
						break;

					case 12: // sub track6
						int count5 = (int) cell.chars().mapToObj(c -> (char) c).count();
						if (count5 >= 50) {
							throw new ResourceNotFoundException(ErrorMessageCode.SUBTRACK_NAME_VALIDATION);
						}
						if (cell == null || cell.isBlank() || cell.isEmpty()) {
							tempTable.setSubTrack6("");

						} else {
//							tempTable.setSubTrack6(cell);
							if (!subTrackList.contains(cell.trim())) {
								subTrackList.add(cell.trim());
								tempTable.setSubTrack6(cell.trim());

							} else {
								throw new IllegalArgumentException(
										ErrorMessageCode.SUBTRACK_ALREADY_ASSIGN + " " + cell);
							}
						}
						break;

					case 13: // Sponsor 1 name

						if (cell != null && !cell.toString().isEmpty() && !"".equals(cell.toString())) {
//							int count = (int) cell.chars().mapToObj(c -> (char) c).filter(Character::isLetterOrDigit)
//									.count();
							int count = (int) cell.chars().mapToObj(c -> (char) c).count();

							if (count >= 100) {
								throw new ResourceNotFoundException(
										"Sponsor 1 name should not be greater than 100 characters");
							}
							sponserList.add(cell.trim());
							tempTable.setSponsor1Names(cell.trim());

						} else {
							throw new ResourceNotFoundException("Sponsor 1 name Cell at row " + i + " and column "
									+ Constant.columnNames[j] + " is empty.");
						}
						break;

					case 14: // Sponsor 1 Introduction
						if (cell == null) {
							tempTable.setSponsor1Introduction("");

						} else {
//							int count = (int) cell.chars().mapToObj(c -> (char) c).count();
//
//							if (count >= 100) {
//								throw new ResourceNotFoundException(
//										"Sponsor 1 Introduction name should not be greater than 100 characters");
//							}
							tempTable.setSponsor1Introduction(cell);

						}
						break;

					case 15: // Sponsor 1 /// designation

						if (cell != null && !cell.toString().isEmpty() && !"".equals(cell.toString())) {
//							int count = (int) cell.chars().mapToObj(c -> (char) c).filter(Character::isLetterOrDigit)
//									.count();
							int count = (int) cell.chars().mapToObj(c -> (char) c).count();

							if (count >= 100) {
								throw new ResourceNotFoundException(
										" Sponsor 1 designation name should not be greater than 100 characters");
							}
							DesignationEntity entity = this.designationRepository.findByNameIgnoreCase(cell);

							if (entity == null) {
								DesignationEntity designationEntity = new DesignationEntity();
								designationEntity.setName(cell);
								designationEntity.setCreatedBy(userId);
								this.designationRepository.save(designationEntity);
								entity = designationEntity;
							}

							SponsorMaster sponsor = this.sponserRepository
									.findByNameIgnoreCase(tempTable.getSponsor1Names());

							if (sponsor == null) {
								sponsor = new SponsorMaster();
								sponsor.setName(tempTable.getSponsor1Names());
								sponsor.setDescription(tempTable.getSponsor1Introduction());
								sponsor.setDesignationId(entity);
								sponsor.setCreatedBy(userId);
								this.sponserRepository.save(sponsor);
							} else {
								sponsor.setDesignationId(entity);
								sponsor.setDescription(tempTable.getSponsor1Introduction());
								sponsor.setCreatedBy(userId);
								this.sponserRepository.save(sponsor);
							}

							if (entity != null) {
								tempTable.setSponsor1Designation(entity.getName());
							}
						} else {
							throw new ResourceNotFoundException("Sponsor 1 Designation Cell at row " + i
									+ " and column '" + Constant.columnNames[j] + "' is empty.");
						}
						break;

					case 16: // Sponsor 2 name
						if (cell != null && !cell.toString().trim().isEmpty() && !"".equals(cell.toString())) {
//							int count = (int) cell.chars().mapToObj(c -> (char) c).filter(Character::isLetterOrDigit)
//									.count();
							int count = (int) cell.chars().mapToObj(c -> (char) c).count();

							if (count >= 100) {
								throw new ResourceNotFoundException(
										" Sponsor 2 name should not be greater than 100 characters");
							}

							if (!sponserList.contains(cell.trim())) {
								sponserList.add(cell.trim());
								tempTable.setSponsor2Names(cell.trim());

							} else {
								throw new IllegalArgumentException(ErrorMessageCode.SPONSER_ALREADY_ASSIGN);
							}

						} else {
							continue;

						}
						break;

					case 17: // Sponsor 2 introduction
						if (cell == null) {
							tempTable.setSponsor2Introduction("");

						} else {
//							int count = (int) cell.chars().mapToObj(c -> (char) c).filter(Character::isLetterOrDigit)
//									.count();
//							int count = (int) cell.chars().mapToObj(c -> (char) c).count();
//
//							if (count >= 100) {
//								throw new ResourceNotFoundException(
//										" Sponsor 2 introduction name should not be greater than 100 characters");
//							}

							tempTable.setSponsor2Introduction(cell);

						}
						break;

					case 18: /// designation 2 sponsor
						if (tempTable.getSponsor2Names() == null || tempTable.getSponsor2Names().toString().isEmpty()) {
							continue;
						}
						if (cell != null && !cell.toString().isEmpty() && !"".equals(cell.toString())) {
//							int count = (int) cell.chars().mapToObj(c -> (char) c).filter(Character::isLetterOrDigit)
//									.count();
							int count = (int) cell.chars().mapToObj(c -> (char) c).count();

							if (count >= 100) {
								throw new ResourceNotFoundException(
										" Sponsor2 Designation name should not be greater than 100 characters");
							}
							DesignationEntity entity = this.designationRepository.findByNameIgnoreCase(cell);

							if (entity == null) {
								DesignationEntity designationEntity = new DesignationEntity();
								designationEntity.setName(cell);
								designationEntity.setCreatedBy(userId);
								this.designationRepository.save(designationEntity);
								entity = designationEntity;

							}

							SponsorMaster sponsor = this.sponserRepository
									.findByNameIgnoreCase(tempTable.getSponsor2Names());
							if (sponsor == null) {
								sponsor = new SponsorMaster();
								sponsor.setName(tempTable.getSponsor2Names());
								sponsor.setDesignationId(entity);
								sponsor.setDescription(tempTable.getSponsor2Introduction());
								sponsor.setCreatedBy(userId);
								this.sponserRepository.save(sponsor);
							} else {
								sponsor.setDesignationId(entity);
								sponsor.setCreatedBy(userId);
								sponsor.setDescription(tempTable.getSponsor2Introduction());
								this.sponserRepository.save(sponsor);
							}

							if (entity != null) {
								tempTable.setSponsor2Designation(entity.getName());
							}
						} else {

							throw new ResourceNotFoundException("Sponsor 2 Designation Cell at row " + i
									+ " and column '" + Constant.columnNames[j] + "' is empty.");
						}
						break;

					case 19: // Trainer 1 Name
						if (cell != null && !cell.toString().isEmpty() && !"".equals(cell.toString())) {

//							int count = (int) cell.chars().mapToObj(c -> (char) c).filter(Character::isLetterOrDigit)
//									.count();
							int count = (int) cell.chars().mapToObj(c -> (char) c).count();

							if (count >= 100) {
								throw new ResourceNotFoundException(
										" Trainer 1 Name  should not be greater than 100 characters");
							}

							trainerList.add(cell.trim());
							tempTable.setTrainerName1(cell.trim());

						} else {
							throw new ResourceNotFoundException("Trainer 1  Cell at row " + i + " and column '"
									+ Constant.columnNames[j] + "' is empty.");
						}
						break;

					case 20: // Trainer 1 Bio--desc
						if (cell == null) {
							tempTable.setTainerDescription1("");

						} else {
//							int count = (int) cell.chars().mapToObj(c -> (char) c).filter(Character::isLetterOrDigit)
//									.count();
//							int count = (int) cell.chars().mapToObj(c -> (char) c).count();
//
//							if (count >= 100) {
//								throw new ResourceNotFoundException(
//										" Trainer 1 Bio  should not be greater than 100 characters");
//							}
							tempTable.setTainerDescription1(cell);
						}
						break;

					case 21: /// Trainer1 Designation

						if (cell != null && !cell.toString().isEmpty() && !"".equals(cell.toString())) {
//							int count = (int) cell.chars().mapToObj(c -> (char) c).filter(Character::isLetterOrDigit)
//									.count();
							int count = (int) cell.chars().mapToObj(c -> (char) c).count();

							if (count >= 100) {
								throw new ResourceNotFoundException(
										" Trainer1 Designation  should not be greater than 100 characters");
							}
							DesignationEntity entity = this.designationRepository.findByNameIgnoreCase(cell);
							if (entity == null) {
								DesignationEntity designationEntity = new DesignationEntity();
								designationEntity.setName(cell);
								designationEntity.setCreatedBy(userId);
								this.designationRepository.save(designationEntity);
								entity = designationEntity;

							}
							TrainersMasterEntity masterEntity = this.trainersRepository
									.findByNameIgnoreCase(tempTable.getTrainerName1());
							if (masterEntity == null) {
								masterEntity = new TrainersMasterEntity();
								masterEntity.setName(tempTable.getTrainerName1());
								masterEntity.setDescription(tempTable.getTrainerDescription1());
								masterEntity.setDesignation(entity);
								masterEntity.setCreatedBy(userId);
								this.trainersRepository.save(masterEntity);
							} else {
								masterEntity.setDesignation(entity);
								masterEntity.setCreatedBy(userId);
								masterEntity.setDescription(tempTable.getTrainerDescription1());
								this.trainersRepository.save(masterEntity);
							}
							tempTable.setTrainer1Designation(entity.getName());
						}

						else {

							throw new ResourceNotFoundException("Trainer 1 Designation Cell at row " + i
									+ " and column '" + Constant.columnNames[j] + "' is empty.");

						}

						break;

					case 22: // Trainer 2 Name
						if (cell != null && !cell.toString().isEmpty() && !"".equals(cell.toString())) {
//							int count = (int) cell.chars().mapToObj(c -> (char) c).filter(Character::isLetterOrDigit)
//									.count();
							int count = (int) cell.chars().mapToObj(c -> (char) c).count();

							if (count >= 100) {
								throw new ResourceNotFoundException(
										" Trainer 2 Name  should not be greater than 100 characters");
							}

							if (!trainerList.contains(cell.trim())) {
								trainerList.add(cell.trim());
								tempTable.setTrainerName2(cell.trim());
							} else {
								throw new IllegalArgumentException(
										cell + " " + ErrorMessageCode.TRAINER_ALREADY_ASSIGN_TO_TRACK);
							}

						} else {
							continue;
						}
						break;

					case 23: // Trainer 2 Bio-desc
						if (cell == null) {
							tempTable.setTainerDescription2("");

						} else {
//							int count = (int) cell.chars().mapToObj(c -> (char) c).filter(Character::isLetterOrDigit)
//									.count();
//							int count = (int) cell.chars().mapToObj(c -> (char) c).count();
//
//							if (count >= 100) {
//								throw new ResourceNotFoundException(
//										" Trainer 2 Bio  should not be greater than 100 characters");
//							}

							tempTable.setTainerDescription2(cell);
						}
						break;
					case 24: /// Tr66ainer2 Designation
						if (tempTable.getTrainerName2() == null || tempTable.getTrainerName2().toString().isEmpty()) {
							continue;
						}
						if (cell != null && !cell.toString().isEmpty() && !"".equals(cell.toString())) {
//							int count = (int) cell.chars().mapToObj(c -> (char) c).filter(Character::isLetterOrDigit)
//									.count();
							int count = (int) cell.chars().mapToObj(c -> (char) c).count();

							if (count >= 100) {
								throw new ResourceNotFoundException(
										" Trainer2 Designation  should not be greater than 100 characters");
							}
							DesignationEntity entity = this.designationRepository.findByNameIgnoreCase(cell);
							if (entity == null) {
								DesignationEntity designationEntity = new DesignationEntity();
								designationEntity.setName(cell);
								designationEntity.setCreatedBy(userId);
								this.designationRepository.save(designationEntity);
								entity = designationEntity;

							}
							TrainersMasterEntity masterEntity = this.trainersRepository
									.findByNameIgnoreCase(tempTable.getTrainerName2());
							if (masterEntity == null) {
								masterEntity = new TrainersMasterEntity();
								masterEntity.setName(tempTable.getTrainerName2());
								masterEntity.setDescription(tempTable.getTrainerDescription2());
								masterEntity.setDesignation(entity);
								masterEntity.setCreatedBy(userId);
								this.trainersRepository.save(masterEntity);
							} else {
								masterEntity.setDesignation(entity);
								masterEntity.setCreatedBy(userId);
								masterEntity.setDescription(tempTable.getTrainerDescription2());
								this.trainersRepository.save(masterEntity);
							}
							tempTable.setTrainer2Designation(entity.getName());
						}

						else {

							throw new ResourceNotFoundException("Trainer 2 Designation Cell at row " + i
									+ " and column '" + Constant.columnNames[j] + "' is empty.");

						}

						break;

					case 25: // Trainer 3 Name
						if (cell != null && !cell.toString().isEmpty() && !"".equals(cell.toString())) {
//							int count = (int) cell.chars().mapToObj(c -> (char) c).filter(Character::isLetterOrDigit)
//									.count();
							int count = (int) cell.chars().mapToObj(c -> (char) c).count();

							if (count >= 100) {
								throw new ResourceNotFoundException(
										" Trainer 3 Name  should not be greater than 100 characters");
							}

							if (!trainerList.contains(cell.trim())) {
								trainerList.add(cell.trim());
								tempTable.setTrainerName3(cell.trim());
							} else {
								throw new IllegalArgumentException(
										cell + " " + ErrorMessageCode.TRAINER_ALREADY_ASSIGN_TO_TRACK);

							}

						} else {
							continue;
						}
						break;

					case 26: // Trainer 3 Bio
						if (cell == null) {
							tempTable.setTainerDescription3("");

						} else {

//							int count = (int) cell.chars().mapToObj(c -> (char) c).filter(Character::isLetterOrDigit)
//									.count();
//							int count = (int) cell.chars().mapToObj(c -> (char) c).count();
//
//							if (count >= 100) {
//								throw new ResourceNotFoundException(
//										" Trainer 3 Bio  should not be greater than 100 characters");
//							}
							tempTable.setTainerDescription3(cell);
						}
						break;

					case 27:/// Trainer3 Designation
						if (tempTable.getTrainerName3() == null || tempTable.getTrainerName3().toString().isEmpty()) {
							continue;
						}
						if (cell != null && !cell.toString().isEmpty() && !"".equals(cell.toString())) {
//							int count = (int) cell.chars().mapToObj(c -> (char) c).filter(Character::isLetterOrDigit)
//									.count();
							int count = (int) cell.chars().mapToObj(c -> (char) c).count();

							if (count >= 100) {
								throw new ResourceNotFoundException(
										" Trainer3 Designation  should not be greater than 100 characters");
							}
							DesignationEntity entity = this.designationRepository.findByNameIgnoreCase(cell);
							if (entity == null) {
								DesignationEntity designationEntity = new DesignationEntity();
								designationEntity.setName(cell);

								designationEntity.setCreatedBy(userId);
								this.designationRepository.save(designationEntity);
								entity = designationEntity;

							}
							TrainersMasterEntity masterEntity = this.trainersRepository
									.findByNameIgnoreCase(tempTable.getTrainerName3());
							if (masterEntity == null) {
								masterEntity = new TrainersMasterEntity();
								masterEntity.setName(tempTable.getTrainerName3());
								masterEntity.setDesignation(entity);
								masterEntity.setCreatedBy(userId);
								masterEntity.setDescription(tempTable.getTrainerDescription3());
								this.trainersRepository.save(masterEntity);
							} else {
								masterEntity.setDesignation(entity);
								masterEntity.setCreatedBy(userId);
								masterEntity.setDescription(tempTable.getTrainerDescription3());
								this.trainersRepository.save(masterEntity);
							}
							tempTable.setTrainer3Designation(entity.getName());
						}

						else {

							throw new ResourceNotFoundException("Trainer 3 Designation Cell at row " + i
									+ " and column '" + Constant.columnNames[j] + "' is empty.");

						}

						break;

					case 28: // trainer 4 name

						if (cell != null && !cell.toString().isEmpty() && !"".equals(cell.toString())) {
//							int count = (int) cell.chars().mapToObj(c -> (char) c).filter(Character::isLetterOrDigit)
//									.count();
							int count = (int) cell.chars().mapToObj(c -> (char) c).count();

							if (count >= 100) {
								throw new ResourceNotFoundException(
										" Trainer 4 Name  should not be greater than 100 characters");
							}
							// tempTable.setTrainerName4(cell);
							if (!trainerList.contains(cell.trim())) {
								trainerList.add(cell.trim());
								tempTable.setTrainerName4(cell.trim());
							} else {
								throw new IllegalArgumentException(
										cell + "" + ErrorMessageCode.TRAINER_ALREADY_ASSIGN_TO_TRACK);
							}

						} else {
							continue;
						}
						break;

					case 29: // trainer 4 bio-desc
						if (cell != null && !cell.toString().isEmpty() && !"".equals(cell.toString())) {

						}
//						int count = (int) cell.chars().mapToObj(c -> (char) c).filter(Character::isLetterOrDigit)
//								.count();
//						int count = (int) cell.chars().mapToObj(c -> (char) c).count();
//
//						if (count >= 100) {
//							throw new ResourceNotFoundException(
//									" Trainer 4 Bio  should not be greater than 100 characters");
//						}
						tempTable.setTainerDescription4(cell);
						break;

					case 30: // trainer 4 designtion
						if (tempTable.getTrainerName4() == null || tempTable.getTrainerName4().toString().isEmpty()) {
							continue;
						}

						if (cell != null && !cell.toString().isEmpty() && !"".equals(cell.toString())) {
//							int count1 = (int) cell.chars().mapToObj(c -> (char) c).filter(Character::isLetterOrDigit)
//									.count();
							int count1 = (int) cell.chars().mapToObj(c -> (char) c).count();

							if (count1 >= 100) {
								throw new ResourceNotFoundException(
										" Trainer4 Designation should not be greater than 100 characters");
							}
							DesignationEntity entity = this.designationRepository.findByNameIgnoreCase(cell);
							if (entity == null) {
								DesignationEntity designationEntity = new DesignationEntity();
								designationEntity.setName(cell);
								designationEntity.setCreatedBy(userId);
								this.designationRepository.save(designationEntity);
								entity = designationEntity;

							}
							TrainersMasterEntity masterEntity = this.trainersRepository
									.findByNameIgnoreCase(tempTable.getTrainerName4());
							if (masterEntity == null) {
								masterEntity = new TrainersMasterEntity();
								masterEntity.setName(tempTable.getTrainerName4());
								masterEntity.setDesignation(entity);
								masterEntity.setDescription(tempTable.getTrainerDescription4());
								masterEntity.setCreatedBy(userId);
								this.trainersRepository.save(masterEntity);
							} else {
								masterEntity.setDesignation(entity);
								masterEntity.setCreatedBy(userId);
								masterEntity.setDescription(tempTable.getTrainerDescription4());
								this.trainersRepository.save(masterEntity);
							}
							tempTable.setTrainer4Designation(entity.getName());
						}

						else {

							throw new ResourceNotFoundException("Trainer 4 Designation Cell at row " + i
									+ " and column '" + Constant.columnNames[j] + "' is empty.");

						}

						break;

					case 31: // trianer 5 name
						if (cell != null && !cell.toString().isEmpty() && !"".equals(cell.toString())) {

//							int count1 = (int) cell.chars().mapToObj(c -> (char) c).filter(Character::isLetterOrDigit)
//									.count();
							int count1 = (int) cell.chars().mapToObj(c -> (char) c).count();

							if (count1 >= 100) {
								throw new ResourceNotFoundException(
										" Trainer 5 Name should not be greater than 100 characters");
							}
							// tempTable.setTrainerName5(cell);
							if (!trainerList.contains(cell.trim())) {
								trainerList.add(cell.trim());
								tempTable.setTrainerName5(cell.trim());
							} else {
								throw new IllegalArgumentException(
										cell + "" + ErrorMessageCode.TRAINER_ALREADY_ASSIGN_TO_TRACK);
							}

						} else {
							continue;

						}
						break;

					case 32: // trainer 5 bio
						if (cell == null) {
							tempTable.setTainerDescription5("");

						} else {
//							int count1 = (int) cell.chars().mapToObj(c -> (char) c).filter(Character::isLetterOrDigit)
//									.count();
//							int count1 = (int) cell.chars().mapToObj(c -> (char) c).count();
//
//							if (count1 >= 100) {
//								throw new ResourceNotFoundException(
//										" Trainer 5 bio should not be greater than 100 characters");
//							}

							tempTable.setTainerDescription5(cell);
						}
						break;

					case 33: // trainer 5 designatin
						if (tempTable.getTrainerName5() == null || tempTable.getTrainerName5().toString().isEmpty()) {
							continue;
						}
						if (cell != null && !cell.toString().isEmpty() && !"".equals(cell.toString())) {
//							int count1 = (int) cell.chars().mapToObj(c -> (char) c).filter(Character::isLetterOrDigit)
//									.count();
							int count1 = (int) cell.chars().mapToObj(c -> (char) c).count();

							if (count1 >= 100) {
								throw new ResourceNotFoundException(
										" Trainer5  Designation should not be greater than 100 characters");
							}
							DesignationEntity entity = this.designationRepository.findByNameIgnoreCase(cell);
							if (entity == null) {
								DesignationEntity designationEntity = new DesignationEntity();
								designationEntity.setName(cell);
								designationEntity.setCreatedBy(userId);
								this.designationRepository.save(designationEntity);
								entity = designationEntity;

							}
							TrainersMasterEntity masterEntity = this.trainersRepository
									.findByNameIgnoreCase(tempTable.getTrainerName5());
							if (masterEntity == null) {
								masterEntity = new TrainersMasterEntity();
								masterEntity.setName(tempTable.getTrainerName5());
								masterEntity.setDesignation(entity);
								masterEntity.setCreatedBy(userId);
								masterEntity.setDescription(tempTable.getTrainerDescription5());
								this.trainersRepository.save(masterEntity);
							} else {
								masterEntity.setDesignation(entity);
								masterEntity.setCreatedBy(userId);
								masterEntity.setDescription(tempTable.getTrainerDescription5());
								this.trainersRepository.save(masterEntity);
							}
							tempTable.setTrainer5Designation(entity.getName());
						}

						else {

							throw new ResourceNotFoundException("Trainer 5 Designation Cell at row " + i
									+ " and column '" + Constant.columnNames[j] + "' is empty.");

						}

						break;

					case 34: // trainer 6 name
						if (cell != null && !cell.toString().isEmpty() && !"".equals(cell.toString())) {
//							int count1 = (int) cell.chars().mapToObj(c -> (char) c).filter(Character::isLetterOrDigit)
//									.count();
							int count1 = (int) cell.chars().mapToObj(c -> (char) c).count();

							if (count1 >= 100) {
								throw new ResourceNotFoundException(
										" Trainer 6 Name should not be greater than 100 characters");
							}
							// tempTable.setTrainerName6(cell);
							if (!trainerList.contains(cell.trim())) {
								trainerList.add(cell.trim());
								tempTable.setTrainerName6(cell.trim());
							} else {
								throw new IllegalArgumentException(
										cell + "" + ErrorMessageCode.TRAINER_ALREADY_ASSIGN_TO_TRACK);
							}

						} else {
							continue;
						}
						break;
					case 35: // trainer 6 bio
						if (cell == null) {
							tempTable.setTainerDescription6("");

						} else {
//							int count1 = (int) cell.chars().mapToObj(c -> (char) c).filter(Character::isLetterOrDigit)
//									.count();
//							int count1 = (int) cell.chars().mapToObj(c -> (char) c).count();
//
//							if (count1 >= 100) {
//								throw new ResourceNotFoundException(
//										" Trainer 6 Bio should not be greater than 100 characters");
//							}

							tempTable.setTainerDescription6(cell);
						}
						break;

					case 36: // trainer 6 designation
						if (tempTable.getTrainerName6() == null || tempTable.getTrainerName6().toString().isEmpty()) {
							continue;
						}

						if (cell != null && !cell.toString().isEmpty() && !"".equals(cell.toString())) {
//							int count1 = (int) cell.chars().mapToObj(c -> (char) c).filter(Character::isLetterOrDigit)
//									.count();

							int count1 = (int) cell.chars().mapToObj(c -> (char) c).count();

							if (count1 >= 100) {
								throw new ResourceNotFoundException(
										" Trainer6 Designation should not be greater than 100 characters");
							}
							DesignationEntity entity = this.designationRepository.findByNameIgnoreCase(cell);
							if (entity == null) {
								DesignationEntity designationEntity = new DesignationEntity();
								designationEntity.setName(cell);
								designationEntity.setCreatedBy(userId);
								this.designationRepository.save(designationEntity);
								entity = designationEntity;

							}
							TrainersMasterEntity masterEntity = this.trainersRepository
									.findByNameIgnoreCase(tempTable.getTrainerName6());
							if (masterEntity == null) {
								masterEntity = new TrainersMasterEntity();
								masterEntity.setName(tempTable.getTrainerName6());
								masterEntity.setDesignation(entity);
								masterEntity.setCreatedBy(userId);
								masterEntity.setDescription(tempTable.getTrainerDescription6());
								this.trainersRepository.save(masterEntity);
							} else {
								masterEntity.setDesignation(entity);
								masterEntity.setCreatedBy(userId);
								masterEntity.setDescription(tempTable.getTrainerDescription6());
								this.trainersRepository.save(masterEntity);
							}
							tempTable.setTrainer6Designation(entity.getName());
						}

						else {

							throw new ResourceNotFoundException("Trainer 6 Designation Cell at row " + i
									+ " and column '" + Constant.columnNames[j] + "' is empty.");

						}

						break;

					case 37:
						String level = cell;

						String levelName = level.replaceAll(",\\s+", ",");
						String[] levels = levelName.split(",");
						for (String string : levels) {

//							int count1 = (int) string.chars().mapToObj(c -> (char) c).filter(Character::isLetterOrDigit)
//									.count();
							int count1 = (int) cell.chars().mapToObj(c -> (char) c).count();

							if (count1 >= 100) {
								throw new ResourceNotFoundException(
										"Level name should not be greater than 100 characters");
							}
							LevelEntity entity = this.levelRepository.findByLevelNameIgnoreCase(string);

							if (entity == null) {
								throw new ResourceNotFoundException("'" + string + "'" + " Level  Cell at row " + +i
										+ " and column '" + Constant.columnNames[j] + "' is not found ");

							}
						}

						String emptyLevel = "";

						for (int s = 0; s < levels.length; s++) {

							if (s > 0) {

								emptyLevel += ", ";
							}

							emptyLevel += levels[s];
						}

						tempTable.setLevel(emptyLevel);
						break;

					case 38:

						Date date = tempTable.getEnrollStartDate();
						Instant instant = date.toInstant();
						LocalDate enrollStart = instant.atZone(ZoneId.systemDefault()).toLocalDate();

						if (!cell.toString().isEmpty() && cell.toString() != "" && cell != null) {
							try {
								Date date2 = tempTable.getStartDate();
								Date date3 = tempTable.getEndDate();

								DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
								LocalDate localDate1 = LocalDate.parse(cell.toString(), formatter1);
								Date date1 = Date.from(localDate1.atStartOfDay(ZoneId.systemDefault()).toInstant());

								if (date2 != null && date1 != null && date1.compareTo(date2) > 0
										&& !date2.equals(date1)) {
									throw new IllegalArgumentException(
											"Enrollment close date should before start date at row " + i
													+ " and column '" + Constant.columnNames[j] + "' is empty.");
								}

								if (date3 != null && date1 != null && date3.compareTo(date1) < 0) {
									throw new IllegalArgumentException(
											"Enrollment close date should before start date  at row " + i
													+ " and column '" + Constant.columnNames[j] + "' is empty.");
								}

								if (localDate1.isBefore(enrollStart)) {
									throw new IllegalArgumentException(
											"Enrollment close date should before start date  at row " + i
													+ " and column '" + Constant.columnNames[j] + "' is empty.");
								}

								Calendar calendar1 = Calendar.getInstance();
								calendar1.setTime(date1);
								calendar1.set(Calendar.HOUR_OF_DAY, 13);
								calendar1.set(Calendar.MINUTE, 0);
								calendar1.set(Calendar.SECOND, 0);
								calendar1.set(Calendar.MILLISECOND, 0);

								tempTable.setEnrollCloseDate(
										GlobalFunctions.dateConvertIntoTimestamp(calendar1.getTime(), 23, 59, 59));
							} catch (DateTimeParseException ex) {
								throw new ResourceNotFoundException("Invalid enroll close date format, at row " + i
										+ " and column " + Constant.columnNames[j]
										+ ",Please provide a valid date in the format of 'dd-mm-yyyy'");
							}

						} else {
							throw new ResourceNotFoundException("Enrollment Close Date Cell at row " + i
									+ " and column '" + Constant.columnNames[j] + "' is empty.");
						}
						break;

					}
				}

				tempList.add(tempTable);
			}

			this.learningTrackBulkUploadRepository.saveAll(tempList);
		}
		return bulkUploadInformation.getId();

	}

	@Override
	public void bulkUploadInLearningTrack(Long bulkId, Long userId) {

		List<LearningTrackBulkUploadTemporaryEntity> tempList = learningTrackBulkUploadRepository.findByBulkId(bulkId);

		for (int i = 0; i < tempList.size(); i++) {
			LearningTrackEntity database = this.learningTrackRepository
					.findByNameIgnoreCaseAndIsActiveTrue(tempList.get(i).getTrackName());

			if (database == null) {
				LearningTrackEntity trackEntity = new LearningTrackEntity();
				trackEntity.setName(tempList.get(i).getTrackName());
				trackEntity.setObjective(tempList.get(i).getObjective());
				trackEntity.setStartDate(tempList.get(i).getStartDate());
				trackEntity.setEndDate(tempList.get(i).getEndDate());
				trackEntity.setEndDate(tempList.get(i).getEndDate());
				trackEntity.setEnrollCloseDate(tempList.get(i).getEnrollCloseDate());
				trackEntity.setEnrollStartDate(tempList.get(i).getEnrollStartDate());

				trackEntity.setCreatedBy(userId);
				trackEntity.setSummary(tempList.get(i).getDescription());

				this.learningTrackRepository.save(trackEntity);

				String departmentName = tempList.get(i).getDepartmentName();
				String[] departmentNames = departmentName.split(",");
				for (String name : departmentNames) {
					// Find the department entity in the database
					GplFunctionEntity departmentEntity = this.gplFunctionRepository.findByNameIgnoreCase(name.trim());
					if (departmentEntity != null) {
						// Create a new TrackDepartmentEntity object for the department and learning
						// track
						TrackGplFunctionEntity trackDepartmentEntity = new TrackGplFunctionEntity();
						trackDepartmentEntity.setGplFunctionEntity(departmentEntity);
						trackDepartmentEntity.setLearningTrackEntity(trackEntity);
						// Save the TrackDepartmentEntity object to the database
						this.trackGplFunctionRepository.save(trackDepartmentEntity);
					}
				}

				bulkUploadInSubLearningTrack(userId, tempList.get(i), trackEntity);

				bulkUploadInSponsorEntity(userId, tempList.get(i), trackEntity);

				bulkUploadInTrainerEntity(userId, tempList.get(i), trackEntity);

				trackLevel(userId, tempList.get(i), trackEntity);

			} else {
				database.setObjective(tempList.get(i).getObjective());
				database.setName(tempList.get(i).getTrackName());
				database.setSummary(tempList.get(i).getDescription());

				database.setStartDate(tempList.get(i).getStartDate());
				database.setEndDate(tempList.get(i).getEndDate());
				database.setEnrollCloseDate(tempList.get(i).getEnrollCloseDate());

				database.setUpdatedBy(userId);
				this.learningTrackRepository.save(database);

				String departmentName = tempList.get(i).getDepartmentName();
				String[] departmentNames = departmentName.split(",");
				for (String name : departmentNames) {
					// Find the department entity in the database
					GplFunctionEntity departmentEntity = this.gplFunctionRepository.findByNameIgnoreCase(name.trim());

					List<TrackGplFunctionEntity> entity = this.trackGplFunctionRepository
							.findByGplFunctionEntityIdAndLearningTrackEntityId(departmentEntity.getId(),
									database.getId());

					if (entity.isEmpty()) {

						TrackGplFunctionEntity trackDepartmentEntity = new TrackGplFunctionEntity();
						trackDepartmentEntity.setGplFunctionEntity(departmentEntity);
						trackDepartmentEntity.setLearningTrackEntity(database);
						// Save the TrackDepartmentEntity object to the database
						this.trackGplFunctionRepository.save(trackDepartmentEntity);
					}
				}

			}
			bulkUploadInSubLearningTrack(userId, tempList.get(i), database);

			bulkUploadInSponsorEntity(userId, tempList.get(i), database);

			bulkUploadInTrainerEntity(userId, tempList.get(i), database);

			trackLevel(userId, tempList.get(i), database);

		}

		this.learningTrackBulkUploadRepository.saveAll(tempList);
	}

	// for Level
	public void trackLevel(Long userId, LearningTrackBulkUploadTemporaryEntity track, LearningTrackEntity trackEntity) {
		String[] level = track.getLevel().split(",");
		for (String string : level) {
			LevelEntity levelEntity = this.levelRepository.findByLevelNameIgnoreCase(string.trim());

			List<TrackLevelEntity> entities = this.levelTrackReopsitory.findByLearningTrackIdAndLevelId(trackEntity,
					levelEntity);

			if (entities.isEmpty()) {

				TrackLevelEntity entity = new TrackLevelEntity();
				entity.setLevelId(levelEntity);
				entity.setLearningTrackId(trackEntity);
				entity.setCreatedBy(userId);
				this.levelTrackReopsitory.save(entity);

			}

		}

	}

//for sub track
	public void bulkUploadInSubLearningTrack(Long userId, LearningTrackBulkUploadTemporaryEntity track,
			LearningTrackEntity trackEntity) {

		ArrayList<SubTrackEntity> list = new ArrayList<>();
		for (int i = 7; i < 13; i++) {
			int x = i % 7;

			String subTrackName = (String) track.getSubTrackList().get(x);

			if (subTrackName != null && !subTrackName.trim().isEmpty()) {
				SubTrackEntity database = this.subTrackRepository
						.findByLearningTrackEntityAndNameIgnoreCase(trackEntity, subTrackName);

				if (database == null) {
					SubTrackEntity subTrackEntity = new SubTrackEntity();
					subTrackEntity.setName(subTrackName);
					subTrackEntity.setLearningTrackEntity(trackEntity);
					subTrackEntity.setCreatedBy(userId);
					list.add(subTrackEntity);

				}
			}
		}

		this.subTrackRepository.saveAll(list);

	}

	// for sponsors
	public void bulkUploadInSponsorEntity(Long userId, LearningTrackBulkUploadTemporaryEntity track,
			LearningTrackEntity trackEntity) {

		List<TrackSponsor> trackSponsors = new ArrayList<>();

		for (String sponsorName : Arrays.asList(track.getSponsor1Names(), track.getSponsor2Names())) {
			if (sponsorName == null || sponsorName.isEmpty()) {
				continue;
			}
			if (trackEntity == null) {
				continue;
			}
			SponsorMaster sponsor = this.sponserRepository.findByNameIgnoreCase(sponsorName);

			List<TrackSponsor> existingTrackSponsor = trackSponsorRepository.findBySponsorAndLearningTrack(sponsor,
					trackEntity);

			// Create a new track sponsor entity and add it to the list
			if (existingTrackSponsor.isEmpty()) {
				TrackSponsor trackSponsor = new TrackSponsor();
				trackSponsor.setSponsor(sponsor);
				trackSponsor.setLearningTrack(trackEntity);
				trackSponsor.setCreatedBy(userId);
				trackSponsors.add(trackSponsor);
			}
		}

		if (!trackSponsors.isEmpty()) {
			trackSponsorRepository.saveAll(trackSponsors);
		}
	}

	// for trainer
	public void bulkUploadInTrainerEntity(Long userId, LearningTrackBulkUploadTemporaryEntity track,
			LearningTrackEntity trackEntity) {
		List<TrackTrainer> trackTrainers = new ArrayList<>();

		for (int i = 19; i < 37; i = i + 3) {

			int x = i % 19;
			String trainerName = (String) track.getTrainerList().get(x);

			if (trainerName == null || trainerName.isEmpty()) {
				// Trainer name is empty, skip this record
				continue;
			}
			if (trackEntity == null) {
				continue;
			}
			TrainersMasterEntity database = this.trainersRepository
					.findByNameIgnoreCase((String) track.getTrainerList().get(x));

			TrackTrainer existingTrackTrainer = trackTrainerRepository.findByLearningTrackAndTrainersMaster(trackEntity,
					database);
			if (existingTrackTrainer != null) {

			} else {
				// the TrackTrainer record does not exist, create a new one
				TrackTrainer newTrackTrainer = new TrackTrainer();
				newTrackTrainer.setLearningTrack(trackEntity);
				newTrackTrainer.setTrainersMaster(database);
				newTrackTrainer.setCreatedBy(userId);

				trackTrainers.add(newTrackTrainer);
			}
		}
		if (!trackTrainers.isEmpty()) {
			trackTrainerRepository.saveAll(trackTrainers);
		}
	}

	@Override
	public IListEnrollStatusCount getEnrollStatusCount() {
		IListEnrollStatusCount userTrackEntity = userTrackRepository.findByCountEnroll(IListEnrollStatusCount.class);
		return userTrackEntity;
	}

	@Override
	public IListUserDetails getUserEnroll(Long userId) {
		List<IListUserEnroll> iListUserEnroll = userTrackRepository.findByUserId(userId, IListUserEnroll.class);

		List<TrackList> TrackList = new ArrayList<TrackList>();

		IListUserDetails iListUserDto = new IListUserDetails();

		for (int i = 0; i < iListUserEnroll.size(); i++) {
			if (i == 0) {
				iListUserDto.setUserId(iListUserEnroll.get(i).getUserId());
				iListUserDto.setUserName(iListUserEnroll.get(i).getUserName());
			}

			if (iListUserEnroll.get(i).getSubTrackId() == null) {

				TrackList trackList = new TrackList();
				trackList.setTrackId(iListUserEnroll.get(i).getTrackId());
				trackList.setTrackName(iListUserEnroll.get(i).getTrackName());
				trackList.setStartDate(iListUserEnroll.get(i).getStartDate());
				trackList.setEndDate(iListUserEnroll.get(i).getEndDate());
				trackList.setDescription(iListUserEnroll.get(i).getSummary());
				trackList.setEnrollStatus(iListUserEnroll.get(i).getEnrollStatus());
				trackList.setTrackStatus(iListUserEnroll.get(i).getTrackStatus());
				if (iListUserEnroll.get(i).getImageUrl() == null) {
					trackList.setImageUrl(null);
				} else {
					trackList.setImageUrl(iListUserEnroll.get(i).getImageUrl());
				}
				if (iListUserEnroll.get(i).getFileUrl() == null) {
					trackList.setFileUrl(null);
				} else {
					trackList.setFileUrl(iListUserEnroll.get(i).getFileUrl());
				}
				List<SubTrackList> SubTrackList = new ArrayList<SubTrackList>();

				for (int j = 0; j < iListUserEnroll.size(); j++) {

					if (iListUserEnroll.get(j).getSubTrackId() != null
							&& iListUserEnroll.get(j).getTrackId().equals(iListUserEnroll.get(i).getTrackId())) {
						SubTrackList iListSubTrackDto = new SubTrackList();
						iListSubTrackDto.setSubTrackId(iListUserEnroll.get(j).getSubTrackId());
						iListSubTrackDto.setSubTrackName(iListUserEnroll.get(j).getSubTrackName());
						iListSubTrackDto.setPostAssesment(iListUserEnroll.get(j).getPostAssesmentScore());
						iListSubTrackDto.setPreAssesment(iListUserEnroll.get(j).getPreAssesmentScore());
//						iListSubTrackDto.setStartPemformer(iListUserEnroll.get(j).getIsStartPemformer());
						iListSubTrackDto.setSubEndDate(iListUserEnroll.get(j).getSubEndDate());
						iListSubTrackDto.setSubStartDate(iListUserEnroll.get(j).getSubStartDate());
						iListSubTrackDto.setCompleteDate(iListUserEnroll.get(j).getCompleteDate());
						if (iListUserEnroll.get(j).getImageUrl() == null) {
							trackList.setImageUrl(null);
						} else {
							iListSubTrackDto.setImageUrl(iListUserEnroll.get(j).getImageUrl());
						}
						if (iListUserEnroll.get(j).getFileUrl() == null) {
							trackList.setFileUrl(null);
						} else {
							iListSubTrackDto.setFileUrl(iListUserEnroll.get(j).getFileUrl());
						}
						SubTrackList.add(iListSubTrackDto);
					}

				}

				trackList.setSubTrackList(SubTrackList);
				TrackList.add(trackList);
			}

		}

		iListUserDto.setTrackList(TrackList);
		return iListUserDto;
	}

	@Override
	public void learningTrackIsVisibleOrNot(Long id, LearningTrackUpdateDto dto, Long userId) {

		LearningTrackEntity learningTrackEntity = learningTrackRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.LEARNING_TRACK_NOT_FOUND));
		if (dto.getIsVisible() == true) {
			if (learningTrackEntity.getSubTrackEntity().isEmpty()) {

				throw new ResourceNotFoundException("Cannot enable learning track because no subtrack is present");
			}
		}
		learningTrackEntity.setIsVisible(dto.getIsVisible());
		this.learningTrackRepository.save(learningTrackEntity);

	}

	@Override
	public void deleteMultipleTracksById(DeleteId ids, Long userId) {

		List<LearningTrackEntity> entity = learningTrackRepository.findAllById(ids.getIds());
		for (int i = 0; i < entity.size(); i++) {
			entity.get(i).setUpdatedBy(userId);

			List<SubTrackEntity> subTrackEntity = subTrackRepository.findByLearningTrackEntity(entity.get(i));
			for (int j = 0; j < subTrackEntity.size(); j++) {
				subTrackEntity.get(j).setIsActive(false);
				subTrackEntity.get(j).setUpdatedBy(userId);
			}
			List<UserTrackEntity> userTrackEntity = userTrackRepository.findByTrackId(entity.get(i));
			for (int j = 0; j < userTrackEntity.size(); j++) {
				userTrackEntity.get(j).setIsActive(false);
				userTrackEntity.get(j).setUpdatedBy(userId);
			}
			List<TrackLevelEntity> trackMappings = this.levelTrackReopsitory.findByLearningTrackId(entity.get(i));
			for (TrackLevelEntity map : trackMappings) {
				map.setIsActive(false);
				map.setUpdatedBy(userId);
			}

			subTrackRepository.saveAll(subTrackEntity);
			userTrackRepository.saveAll(userTrackEntity);
			levelTrackReopsitory.saveAll(trackMappings);
		}

		learningTrackRepository.deleteAllByIdIn(ids.getIds());

	}

	@Override
	public void updateIsVisibleForLearningTrackMultiSelect(VisibleContentDto isVisibleDto) {
		List<LearningTrackEntity> findAllByIds = this.learningTrackRepository.findAllById(isVisibleDto.getIds());

		if (findAllByIds.size() != isVisibleDto.getIds().size()) {
			throw new IllegalArgumentException(ErrorMessageCode.LEARNING_TRACK_NOT_FOUND);
		}
		if (isVisibleDto.getIsVisible() == true) {
			for (int i = 0; i < findAllByIds.size(); i++) {
				if (findAllByIds.get(i).getSubTrackEntity().isEmpty()) {
					throw new IllegalArgumentException(
							"Cannot enable '" + findAllByIds.get(i).getName() + "' because no subtrack is present");
				}
			}
		}
		this.learningTrackRepository.updateLearningTrackIsVisible(isVisibleDto.getIsVisible(), isVisibleDto.getIds());

	}

}
