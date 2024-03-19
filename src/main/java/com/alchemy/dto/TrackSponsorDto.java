package com.alchemy.dto;

import java.util.ArrayList;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.ErrorMessageKey;

public class TrackSponsorDto {

	private Long learningTrackId;

	private ArrayList<Long> sponsorId;

//	@NotBlank(message = ErrorMessageCode.SPONSOR_MESSAGE_REQUIRED + "*" + ErrorMessageKey.TRACKSPONSOR_E033105)
//	@NotEmpty(message = ErrorMessageCode.SPONSOR_MESSAGE_REQUIRED + "*" + ErrorMessageKey.TRACKSPONSOR_E033105)
//	@NotNull(message = ErrorMessageCode.SPONSOR_MESSAGE_REQUIRED + "*" + ErrorMessageKey.TRACKSPONSOR_E033105)
	private String sponsorMessage;

	public Long getLearningTrackId() {
		return learningTrackId;
	}

	public void setLearningTrackId(Long learningTrackId) {
		this.learningTrackId = learningTrackId;
	}

	public ArrayList<Long> getSponsorId() {
		return sponsorId;
	}

	public void setSponsorId(ArrayList<Long> sponsorId) {
		this.sponsorId = sponsorId;
	}

	public String getSponsorMessage() {
		return sponsorMessage;
	}

	public void setSponsorMessage(String sponsorMessage) {
		this.sponsorMessage = sponsorMessage;
	}

}
