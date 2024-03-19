package com.alchemy.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.alchemy.entities.EnrollStatus;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.ErrorMessageKey;
import com.fasterxml.jackson.annotation.JsonFormat;

public class SubTrackDto {

	private Long id;

	@NotNull(message = ErrorMessageCode.SUBTRACK_NAME_REQUIRED + "*" + ErrorMessageKey.SUBTRACK_E033305)
	@NotBlank(message = ErrorMessageCode.SUBTRACK_NAME_REQUIRED + "*" + ErrorMessageKey.SUBTRACK_E033305)
	@NotEmpty(message = ErrorMessageCode.SUBTRACK_NAME_REQUIRED + "*" + ErrorMessageKey.SUBTRACK_E033305)
	@Size(max = 50, message = ErrorMessageCode.CHARACTER_VALIDATION_FOR_NAME + "*" + ErrorMessageKey.SUBTRACK_E033306)
	private String name;

	@NotNull(message = ErrorMessageCode.START_DATE_REQUIRED + "*" + ErrorMessageKey.SUBTRACK_E033305)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date startDate;

	@NotNull(message = ErrorMessageCode.END_DATE_REQUIRED + "*" + ErrorMessageKey.SUBTRACK_E033305)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date endDate;

	@NotNull(message = ErrorMessageCode.LEARNING_TRACK_REQUIRED + "*" + ErrorMessageKey.SUBTRACK_E033305)
	private Long learningTrackId;

	private EnrollStatus enrollStatus;

	// private Long uploadBrochure;

//	public SubTrackDto(Long uploadBrochure) {
//		super();
//		this.uploadBrochure = uploadBrochure;
//	}
//
//	public Long getUploadBrochure() {
//		return uploadBrochure;
//	}
//
//	public void setUploadBrochure(Long uploadBrochure) {
//		this.uploadBrochure = uploadBrochure;
//	}

	public Long getLearningTrackId() {
		return learningTrackId;
	}

	public void setLearningTrackId(Long learningTrackId) {
		this.learningTrackId = learningTrackId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public SubTrackDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EnrollStatus getEnrollStatus() {
		return enrollStatus;
	}

	public void setEnrollStatus(EnrollStatus enrollStatus) {
		this.enrollStatus = enrollStatus;
	}

}
