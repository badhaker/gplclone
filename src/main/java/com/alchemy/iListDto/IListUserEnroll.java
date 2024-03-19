package com.alchemy.iListDto;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

import com.alchemy.entities.EnrollStatus;
import com.alchemy.entities.Status;
import com.fasterxml.jackson.annotation.JsonFormat;

public interface IListUserEnroll {

	@Value("#{@globalFunctions.getFileUrl(target.OriginalName)}")
	public String getFileUrl();

	@Value("#{@globalFunctions.getFileUrl(target.OriginalImageName)}")
	public String getImageUrl();

	public Long getUserId();

	public String getUserName();

	public Long getTrackId();

	public String getTrackName();

	public Long getSubTrackId();

	public String getSubTrackName();

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	public Date getStartDate();

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	public Date getEndDate();

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	public Date getSubStartDate();

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	public Date getSubEndDate();

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	public Date getCompleteDate();

	public String getSummary();

	public EnrollStatus getEnrollStatus();

	public Status getTrackStatus();

	public Float getPreAssesmentScore();
	
	public Float getPostAssesmentScore();
}
