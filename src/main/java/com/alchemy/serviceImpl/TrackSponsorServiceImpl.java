package com.alchemy.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alchemy.dto.TrackSponsorDto;
import com.alchemy.entities.LearningTrackEntity;
import com.alchemy.entities.SponsorMaster;
import com.alchemy.entities.TrackSponsor;
import com.alchemy.exceptionHandling.ResourceNotFoundException;
import com.alchemy.iListDto.IListTrackSponsor;
import com.alchemy.repositories.LearningTrackRepository;
import com.alchemy.repositories.SponserRepository;
import com.alchemy.repositories.TrackSponsorRepository;
import com.alchemy.serviceInterface.TrackSponsorInterface;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.Pagination;

@Service
public class TrackSponsorServiceImpl implements TrackSponsorInterface {

	@Autowired
	private SponserRepository sponserRepo;

	@Autowired
	private LearningTrackRepository trackRepo;

	@Autowired
	private TrackSponsorRepository trackSponsorRepo;

	@Override
	public TrackSponsorDto addTrackSponsor(Long userId, TrackSponsorDto trackSponsorDto) throws Exception {
		if (trackSponsorDto.getSponsorId().isEmpty()) {
			throw new IllegalArgumentException(ErrorMessageCode.SPONSOR_MUST_BE_REQUIRED);
		}

		List<TrackSponsor> trackSponsor2 = this.trackSponsorRepo
				.findByLearningTrackId(trackSponsorDto.getLearningTrackId());

		List<TrackSponsor> trackSponsor1 = new ArrayList<>();
		for (int j = 0; j < trackSponsorDto.getSponsorId().size(); j++) {
			SponsorMaster sponsorMaster = this.sponserRepo.findById(trackSponsorDto.getSponsorId().get(j))
					.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.SPONSER_NOT_FOUND));
			LearningTrackEntity learningTrackEntity = this.trackRepo.findById(trackSponsorDto.getLearningTrackId())
					.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.LEARNING_TRACK_NOT_FOUND));

			TrackSponsor newTrackSponsor = new TrackSponsor();
			newTrackSponsor.setLearningTrack(learningTrackEntity);
			newTrackSponsor.setSponsor(sponsorMaster);
			newTrackSponsor.setCreatedBy(userId);
			trackSponsor1.add(newTrackSponsor);

		}
		this.trackSponsorRepo.deleteAll(trackSponsor2);
		this.trackSponsorRepo.saveAll(trackSponsor1);
		return trackSponsorDto;
	}

	@Override
	public void deleteTrackSponsor(Long id, Long userId) {
		TrackSponsor trackSponserEntity = trackSponsorRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.TRACKSPONSOR_NOT_FOUND));

		trackSponserEntity.setIsActive(false);
		trackSponserEntity.setUpdatedBy(userId);
		trackSponsorRepo.save(trackSponserEntity);

	}

	@Override
	public Page<IListTrackSponsor> getAllTrackSponsor(String search, String pageNo, String pageSize) {
		Page<IListTrackSponsor> iListTrackSponsor;
		Pageable pageable = new Pagination().getPagination(pageNo, pageSize);
		iListTrackSponsor = this.trackSponsorRepo.findAllList(search, pageable, IListTrackSponsor.class);

		return iListTrackSponsor;
	}

	@Override
	public TrackSponsorDto addTrackSponsors(TrackSponsorDto trackSponsorDto, Long userId) {

		for (int i = 0; i < trackSponsorDto.getSponsorId().size(); i++) {
			Long spId = trackSponsorDto.getSponsorId().get(i);

			TrackSponsor trackSponsor = this.trackSponsorRepo
					.findByLearningTrackIdAndSponsorId(trackSponsorDto.getLearningTrackId(), spId);
			if (trackSponsor != null) {
				throw new ResourceNotFoundException(ErrorMessageCode.TRACKSPONSOR_ALREADY_ASSIGNED);
			}

			TrackSponsor trackSponsors = new TrackSponsor();
			SponsorMaster sponsorMaster = sponserRepo.findById(trackSponsorDto.getSponsorId().get(i))
					.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.SPONSER_NOT_FOUND));
			trackSponsors.setSponsor(sponsorMaster);
			LearningTrackEntity entity = this.trackRepo.findById(trackSponsorDto.getLearningTrackId()).get();
			trackSponsors.setLearningTrack(entity);
			trackSponsors.setSponsorMessage(trackSponsorDto.getSponsorMessage());
			trackSponsors.setCreatedBy(userId);
			this.trackSponsorRepo.save(trackSponsors);
		}

		return trackSponsorDto;
	}

	@Override
	public TrackSponsorDto updateTrackSponsor(TrackSponsorDto trackSponsorDto, Long id, Long userId) {
		TrackSponsor sponsor = this.trackSponsorRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.TRACKSPONSOR_NOT_FOUND));
		
		
		for (int i = 0; i < trackSponsorDto.getSponsorId().size(); i++) {
			
			List<Long> trackSponsor = this.trackSponsorRepo
					.findSponsorIdByLearningTrackId(trackSponsorDto.getLearningTrackId());
			
			if (trackSponsor != null) {
				if (trackSponsorDto.getSponsorId().get(i) != sponsor.getSponsor_id()) {
					throw new ResourceNotFoundException(ErrorMessageCode.TRACKSPONSOR_ALREADY_ASSIGNED);
				}
			}
			
//			if (trackSponsor.contains(trackSponsorDto.getSponsorId().get(i))) {
//				throw new ResourceNotFoundException(ErrorMessageCode.TRACKSPONSOR_ALREADY_ASSIGNED);
//			}
			
			SponsorMaster sponsorMaster = sponserRepo.findById(trackSponsorDto.getSponsorId().get(i))
					.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.SPONSER_NOT_FOUND));
			sponsor.setSponsor(sponsorMaster);
			LearningTrackEntity entity = this.trackRepo.findById(trackSponsorDto.getLearningTrackId()).get();
			sponsor.setLearningTrack(entity);
			sponsor.setSponsorMessage(trackSponsorDto.getSponsorMessage());
			sponsor.setUpdatedBy(userId);
			this.trackSponsorRepo.save(sponsor);
		}

		return trackSponsorDto;
	}
}
