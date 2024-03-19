package com.alchemy.iListDto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public interface IListTrackSponsor {

	public Long getId();

	public String getSponsorName();

	public Long getSponsorId();

	public String getDescription();

	public Long getTrackId();

	public String getSponsorMessage();

	public String getLearningTrackName();

	public String getObjective();

	public String getCurriculum();

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	public Date getStartDate();

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	public Date getEndDate();

}
