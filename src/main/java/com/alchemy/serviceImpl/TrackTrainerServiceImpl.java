package com.alchemy.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alchemy.dto.TrackTrainerDto;
import com.alchemy.entities.LearningTrackEntity;
import com.alchemy.entities.TrackTrainer;
import com.alchemy.entities.TrainersMasterEntity;
import com.alchemy.exceptionHandling.ResourceNotFoundException;
import com.alchemy.iListDto.IListTrackTrainer;
import com.alchemy.repositories.LearningTrackRepository;
import com.alchemy.repositories.TrackTrainerRepository;
import com.alchemy.repositories.TrainersMasterRepository;
import com.alchemy.serviceInterface.TrackTrainerInterface;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.Pagination;

@Service
public class TrackTrainerServiceImpl implements TrackTrainerInterface {

	@Autowired
	private TrackTrainerRepository trackTrainerRepository;

	@Autowired
	private TrainersMasterRepository trainersMasterRepository;

	@Autowired
	private LearningTrackRepository learningTrackRepository;

	@Override
	public TrackTrainerDto addTrainerToTrack(TrackTrainerDto trackTrainerDto, Long userId) {
		if (trackTrainerDto.getTrainerId().isEmpty()) {
			throw new IllegalArgumentException(ErrorMessageCode.TRAINER_MUST_BE_REQUIRED);
		}

		List<TrackTrainer> trackTrainer = this.trackTrainerRepository
				.findByLearningTrackId(trackTrainerDto.getLearningId());

		List<TrackTrainer> trackList = new ArrayList<>();
		LearningTrackEntity learningEntity = this.learningTrackRepository.findById(trackTrainerDto.getLearningId())
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.LEARNING_TRACK_NOT_FOUND));
		for (int i = 0; i < trackTrainerDto.getTrainerId().size(); i++) {

			TrainersMasterEntity masterEntity = trainersMasterRepository.findById(trackTrainerDto.getTrainerId().get(i))
					.orElseThrow(() -> new ResourceNotFoundException((ErrorMessageCode.TRAINER_NOT_PRESENT)));

			TrackTrainer trainer = new TrackTrainer();
			trainer.setTrainersMaster(masterEntity);
			trainer.setLearningTrack(learningEntity);
			trainer.setCreatedBy(userId);
			trackList.add(trainer);
		}
		this.trackTrainerRepository.deleteAll(trackTrainer);
		this.trackTrainerRepository.saveAll(trackList);
		return trackTrainerDto;

	}

	@Override
	public void deleteTrackTrainer(Long id) {
		this.trackTrainerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.TRACK_TRAINER_ID_NOT_FOUND));
		trackTrainerRepository.deleteById(id);
	}

	@Override
	public Page<IListTrackTrainer> findAllTrackTrainers(String pageNo, String pageSize) {
		Page<IListTrackTrainer> iListTrackTrainer;

		Pageable pageable = new Pagination().getPagination(pageNo, pageSize);

		iListTrackTrainer = this.trackTrainerRepository.findByOrderByIdDesc(pageable, IListTrackTrainer.class);

		return iListTrackTrainer;
	}

}
