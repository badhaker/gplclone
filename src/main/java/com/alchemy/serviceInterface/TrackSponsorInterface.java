package com.alchemy.serviceInterface;

import org.springframework.data.domain.Page;

import com.alchemy.dto.TrackSponsorDto;
import com.alchemy.iListDto.IListTrackSponsor;

public interface TrackSponsorInterface {

	Page<IListTrackSponsor> getAllTrackSponsor(String search, String pageNo, String pageSize);

	TrackSponsorDto addTrackSponsor(Long userId, TrackSponsorDto trackSponsorDto) throws Exception;

	void deleteTrackSponsor(Long id, Long userId);

	TrackSponsorDto addTrackSponsors(TrackSponsorDto trackSponsorDto, Long userId);

	TrackSponsorDto updateTrackSponsor(TrackSponsorDto trackSponsorDto, Long userId, Long id);

}
