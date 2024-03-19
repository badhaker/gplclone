package com.alchemy.dto;

import java.util.ArrayList;
import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.ErrorMessageKey;
import com.fasterxml.jackson.annotation.JsonFormat;

public class LearningTrackDto {

	@NotEmpty(message = ErrorMessageCode.NAME_REQUIRED + "*" + ErrorMessageKey.LEARNING_TRACK_E031703)
	@NotBlank(message = ErrorMessageCode.NAME_REQUIRED + "*" + ErrorMessageKey.LEARNING_TRACK_E031703)
	@NotNull(message = ErrorMessageCode.NAME_REQUIRED + "*" + ErrorMessageKey.LEARNING_TRACK_E031703)
	@Size(max = 100, message = ErrorMessageCode.LEARNING_TRACK_NAME + "*" + ErrorMessageKey.LEARNING_TRACK_E031703)
	private String name;

	private String objective;

//	@NotNull(message = ErrorMessageCode.START_DATE_REQUIRED + "*" + ErrorMessageKey.LEARNING_TRACK_E031703)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startDate;

//	@NotNull(message = ErrorMessageCode.END_DATE_REQUIRED + "*" + ErrorMessageKey.LEARNING_TRACK_E031703)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endDate;

//	@NotNull(message = ErrorMessageCode.ENROLL_DATE_REQUIRED + "*" + ErrorMessageKey.LEARNING_TRACK_E031703)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date enrollCloseDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date enrollStartDate;

	public Date getEnrollCloseDate() {
		return enrollCloseDate;
	}

	public void setEnrollCloseDate(Date enrollCloseDate) {
		this.enrollCloseDate = enrollCloseDate;
	}

	@Size(max = 500, message = ErrorMessageCode.DESCRIPTION_NOT_EXCEED_FROM_500_CHARACTER + "*"
			+ ErrorMessageKey.LEARNING_TRACK_E031703)
	private String summary;

	@Valid
	private ArrayList<TrackSponsorAddDto> sponsor;

	private ArrayList<Long> trainerId;

	private ArrayList<Long> functionId;

	private ArrayList<Long> levelId;

	@Valid
	private ArrayList<SubTrackDtoForLearningTrack> subTrack;

	public String getName() {
		return name.trim();
	}

	public void setName(String name) {
		this.name = name;
	}

	public LearningTrackDto() {
		super();
	}

	public LearningTrackDto(String name) {
		super();
		this.name = name;
	}

	public String getObjective() {
		return objective;
	}

	public void setObjective(String objective) {
		this.objective = objective;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public ArrayList<TrackSponsorAddDto> getSponsor() {
		return sponsor;
	}

	public void setSponsor(ArrayList<TrackSponsorAddDto> sponsor) {
		this.sponsor = sponsor;
	}

	public ArrayList<Long> getTrainerId() {
		return trainerId;
	}

	public void setTrainerId(ArrayList<Long> trainerId) {
		this.trainerId = trainerId;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public ArrayList<Long> getFunctionId() {
		return functionId;
	}

	public void setFunctionId(ArrayList<Long> functionId) {
		this.functionId = functionId;
	}

	public ArrayList<Long> getLevelId() {
		return levelId;
	}

	public void setLevelId(ArrayList<Long> levelId) {
		this.levelId = levelId;
	}

	public ArrayList<SubTrackDtoForLearningTrack> getSubTrack() {
		return subTrack;
	}

	public void setSubTrack(ArrayList<SubTrackDtoForLearningTrack> subTrack) {
		this.subTrack = subTrack;
	}

	public Date getEnrollStartDate() {
		return enrollStartDate;
	}

	public void setEnrollStartDate(Date enrollStartDate) {
		this.enrollStartDate = enrollStartDate;
	}

}
