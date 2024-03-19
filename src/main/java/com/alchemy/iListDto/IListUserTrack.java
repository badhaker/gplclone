package com.alchemy.iListDto;

import com.alchemy.entities.EnrollStatus;

public interface IListUserTrack {

	public Long getId();

	public Long getTrackId();

	public String getTrackName();

	public String getUserName();

	public Long getUserId();

	public EnrollStatus getEnrollStatus();

	public String getFunctionName();

	public String getEmail();

//	public Timestamp getEnrollTimeStamp();

	public Long getFunctionId();

	public String getEmployeeLevel();

	public String getEmployeeEdp();

	public String getZone();
	
	public String getRegion();

	public String getProject();
	
	public String getEmployeeGrade();

	public String getPositionTitle();
}
