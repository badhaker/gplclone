package com.alchemy.iListDto;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;

public interface IListLearningTrackDetail {

	public Long getId();

	public String getName();

	public String getObjective();

	public Long getEnrollCount();

//	@JsonFormat(pattern = "yyyy-MM-dd")
	@Value("#{@globalFunctions.date(target.StartDate)}")
	public String getStartDate();

//	@JsonFormat(pattern = "yyyy-MM-dd")
	@Value("#{@globalFunctions.date(target.EndDate)}")
	public String getEndDate();

	public Boolean getIsVisible();

	public String getSummary();

	public String getFileUrl();

	public String getBannerUrl();

	public String getBannerCardUrl();

	public List<IListTrackTrainerDetails> getTrackTrainer();

	public List<IListSubTrackList> getSubTrackEntity();

	public List<IListTrackSponsorDetail> getTrackSponsors();

	public List<IListTrackFunctionDetails> getGplFunctionEntities();

	public List<ITrackLevelDetail> getLevelEntities();

//	@JsonFormat(pattern = "yyyy-MM-dd")
	@Value("#{@globalFunctions.date(target.EnrollStartDate)}")
	public String getEnrollStartDate();

//	@JsonFormat(pattern = "yyyy-MM-dd")
	@Value("#{@globalFunctions.date(target.EnrollCloseDate)}")
	public String getEnrollCloseDate();

	public String getFileName();

	public String getNudgedFileUrl();
}
