package com.alchemy.dto;

public class TrackSponsorAddDto {

	private Long sponsorId;

	private Long trackSponsorId;

	private String sponsorMessage;

	private Integer flagId = 0;

	public Long getSponsorId() {
		return sponsorId;
	}

	public void setSponsorId(Long sponsorId) {
		this.sponsorId = sponsorId;
	}

	public String getSponsorMessage() {
		return sponsorMessage;
	}

	public void setSponsorMessage(String sponsorMessage) {
		this.sponsorMessage = sponsorMessage;
	}

	public Long getTrackSponsorId() {
		return trackSponsorId;
	}

	public void setTrackSponsorId(Long trackSponsorId) {
		this.trackSponsorId = trackSponsorId;
	}

	public Integer getFlagId() {
		return flagId;
	}

	public void setFlagId(Integer flagId) {
		this.flagId = flagId;
	}

}
