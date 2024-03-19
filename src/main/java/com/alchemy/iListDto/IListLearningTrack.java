package com.alchemy.iListDto;

import org.springframework.beans.factory.annotation.Value;

import com.alchemy.entities.EnrollStatus;
import com.alchemy.entities.Status;

public interface IListLearningTrack {

	public Long getId();

	public String getName();

//	@JsonFormat(pattern = "yyyy-MM-dd")
	@Value("#{@globalFunctions.date(target.StartDate)}")
	public String getStartDate();

//	@JsonFormat(pattern = "yyyy-MM-dd")
	@Value("#{@globalFunctions.date(target.EndDate)}")
	public String getEndDate();

	public String getSummary();

	public Boolean getIsVisible();

//	@JsonFormat(pattern = "yyyy-MM-dd")
	@Value("#{@globalFunctions.date(target.EnrollCloseDate)}")
	public String getEnrollCloseDate();

//	@JsonFormat(pattern = "yyyy-MM-dd")
	@Value("#{@globalFunctions.date(target.EnrollStartDate)}")
	public String getEnrollStartDate();

	@Value("#{@globalFunctions.getFileUrl(target.OriginalName)}")
	public String getFileUrl();

	@Value("#{@globalFunctions.getFileUrl(target.BannerUrl)}")
	public String getBannerUrl();

	@Value("#{@globalFunctions.getFileUrl(target.BannerCardUrl)}")
	public String getBannerCardUrl();

	@Value("#{@globalFunctions.getFileUrl(target.NudgedFileId)}")
	public String getNudgedFileId();

	public String getFunctionNames();

	public String getLevelNames();

	public EnrollStatus getEnrollStatus();

	public Status getStatus();

}
