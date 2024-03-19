package com.alchemy.dto;

import java.util.Date;

import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.ErrorMessageKey;
import com.fasterxml.jackson.annotation.JsonFormat;

public class SubTrackDtoForLearningTrack {

	/*
	 * @NotNull(message = ErrorMessageCode.SUBTRACK_NAME_REQUIRED + "*" +
	 * ErrorMessageKey.SUBTRACK_E033305)
	 *
	 * @NotBlank(message = ErrorMessageCode.SUBTRACK_NAME_REQUIRED + "*" +
	 * ErrorMessageKey.SUBTRACK_E033305)
	 *
	 * @NotEmpty(message = ErrorMessageCode.SUBTRACK_NAME_REQUIRED + "*" +
	 * ErrorMessageKey.SUBTRACK_E033305)
	 */
	@Size(max = 50, message = "Subtrack " + ErrorMessageCode.CHARACTER_VALIDATION_FOR_NAME + "*"
			+ ErrorMessageKey.SUBTRACK_E033306)
	private String name;

	/*
	 * @NotNull(message = "Sub track start Date required" + "*" +
	 * ErrorMessageKey.SUBTRACK_E033305)
	 */ @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	/*
	 * @NotNull(message = "Sub track end Date required" + "*" +
	 * ErrorMessageKey.SUBTRACK_E033305)
	 */ @DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endDate;

	private Integer flagId;

	private Long subTrackId;

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

	public Integer getFlagId() {
		return flagId;
	}

	public void setFlagId(Integer flagId) {
		this.flagId = flagId;
	}

	public Long getSubTrackId() {
		return subTrackId;
	}

	public void setSubTrackId(Long subTrackId) {
		this.subTrackId = subTrackId;
	}

}
