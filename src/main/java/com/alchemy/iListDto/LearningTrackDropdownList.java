package com.alchemy.iListDto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public interface LearningTrackDropdownList {

	public Long getId();

	public String getName();

	public String getSummary();

	public String getFunctionNames();

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEnrollCloseDate();
}
