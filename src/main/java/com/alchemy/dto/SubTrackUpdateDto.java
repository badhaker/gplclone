package com.alchemy.dto;

import java.sql.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.ErrorMessageKey;
import com.fasterxml.jackson.annotation.JsonFormat;

public class SubTrackUpdateDto {

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
}
