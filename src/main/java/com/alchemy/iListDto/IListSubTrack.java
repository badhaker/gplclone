package com.alchemy.iListDto;

import org.springframework.beans.factory.annotation.Value;

public interface IListSubTrack {

	public Long getSubtrackId();

	public String getSubtrackName();

	public Long getLearningTrackId();

//	@JsonFormat(pattern = "yyyy-MM-dd")
	@Value("#{@globalFunctions.date(target.StartDate)}")
	public String getStartDate();

//	@JsonFormat(pattern = "yyyy-MM-dd")
	@Value("#{@globalFunctions.date(target.EndDate)}")
	public String getEndDate();

}
