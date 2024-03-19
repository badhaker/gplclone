package com.alchemy.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AttendanceDto {

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date date;
	
	//private String name;
	
	private Long userId;
	
//	private String email;
	
	private String zone;
	
	private String functionName;
	
	private Boolean starPerformer;
	
	private String trackName;
	
	private String subTrackName;
	
//	private Date fromDateOfAttendance;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date completeDateOfAttendance;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public Boolean getStarPerformer() {
		return starPerformer;
	}

	public void setStarPerformer(Boolean starPerformer) {
		this.starPerformer = starPerformer;
	}

	public String getTrackName() {
		return trackName;
	}

	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}

	public String getSubTrackName() {
		return subTrackName;
	}

	public void setSubTrackName(String subTrackName) {
		this.subTrackName = subTrackName;
	}

//	public Date getFromDateOfAttendance() {
//		return fromDateOfAttendance;
//	}
//
//	public void setFromDateOfAttendance(Date fromDateOfAttendance) {
//		this.fromDateOfAttendance = fromDateOfAttendance;
//	}

	public Date getCompleteDateOfAttendance() {
		return completeDateOfAttendance;
	}

	public void setCompleteDateOfAttendance(Date completeDateOfAttendance) {
		this.completeDateOfAttendance = completeDateOfAttendance;
	}

	public AttendanceDto(Date date, String name, Long userId, String email, String zone, String functionName,
			Boolean starPerformer, String trackName, String subTrackName,
			Date completeDateOfAttendance) {
		super();
		this.date = date;
	//	this.name = name;
		this.userId = userId;
	//	this.email = email;
		this.zone = zone;
		this.functionName = functionName;
		this.starPerformer = starPerformer;
		this.trackName = trackName;
		this.subTrackName = subTrackName;
		//this.fromDateOfAttendance = fromDateOfAttendance;
		this.completeDateOfAttendance = completeDateOfAttendance;
	}

	public AttendanceDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
