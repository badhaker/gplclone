package com.alchemy.dto;

import com.alchemy.entities.EnrollStatus;
import com.alchemy.utils.ValidEnum;

public class EnrollFilterDto {

	private String track; 
	private String email;
	private String name;
	private String status;
	public String getTrack() {
		return track;
	}
	public void setTrack(String track) {
		this.track = track;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	
	
	
}
