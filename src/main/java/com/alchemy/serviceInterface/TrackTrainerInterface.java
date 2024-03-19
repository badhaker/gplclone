package com.alchemy.serviceInterface;

import org.springframework.data.domain.Page;

import com.alchemy.dto.TrackTrainerDto;
import com.alchemy.iListDto.IListTrackTrainer;

public interface TrackTrainerInterface {

	public TrackTrainerDto addTrainerToTrack(TrackTrainerDto trackTrainerDto, Long userId);

	public void deleteTrackTrainer(Long id);

	Page<IListTrackTrainer> findAllTrackTrainers(String pageNo, String pageSize);

}
