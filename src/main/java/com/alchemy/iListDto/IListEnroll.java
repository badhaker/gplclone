package com.alchemy.iListDto;

import com.alchemy.entities.EnrollStatus;

public interface IListEnroll {

	public String getEmployeeEdp();

	public String getName();

	public String getEmail();

	public String getZone();

	public String getRegion();

	public String getProject();

	public String getEmployeeLevel();

	public String getEmployeeGrade();

	public String getPositionTitle();

	public String getDepartmentName();

	public String getFunctionName();

	public String getLearningTrackName();
	
	public EnrollStatus getEnrollmentStatus();

}
