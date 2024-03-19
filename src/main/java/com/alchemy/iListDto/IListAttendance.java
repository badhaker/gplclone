package com.alchemy.iListDto;

import java.util.Date;
import java.util.List;

import com.alchemy.entities.AttendanceStatus;
import com.fasterxml.jackson.annotation.JsonFormat;


public interface IListAttendance {

	public Long getId();
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate();
	
	public Long getUserId();
	
	public String getZone();
	
	public Long getFunctionId();
	
	public String getFunctionName();
	
	public Long getLearningTrackId();
	
	public Long getSubtrackId();
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getCompleteDateOfAttendance();
	
	public String getName();
	
	public String getEmail();
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatedAt();
	
	public Boolean getStarPerformer();	
	
	public String getEmployeeEdp();
	
	public String getLearningTrackName();
	
	public String getSubTrackName();
	
	public AttendanceStatus getAttendance() ;
	
	public Boolean getLockAttendance();
	public String getRegion();
	
	public String getEmployeeLevel();
	
	public String getGrade();
	
	public String getPosition();
	
	public Float getPostAssesmentScore();
	
	public Float getPreAssesmentScore();
	
}
