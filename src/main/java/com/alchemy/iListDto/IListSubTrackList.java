package com.alchemy.iListDto;

import org.springframework.beans.factory.annotation.Value;

public interface IListSubTrackList {

	public Long getId();

	public String getName();

//	@JsonFormat(pattern = "yyyy-MM-dd")
	@Value("#{@globalFunctions.date(target.StartDate)}")
	public String getStartDate();

//	@JsonFormat(pattern = "yyyy-MM-dd")
	@Value("#{@globalFunctions.date(target.EndDate)}")
	public String getEndDate();
}
