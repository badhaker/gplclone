package com.alchemy.dto;

import javax.validation.constraints.NotNull;

import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.ErrorMessageKey;

public class UserTrackDto {

	@NotNull(message = ErrorMessageCode.FIELD_REQUIRED + "*" + ErrorMessageKey.USERTRACK_E033402)
	private Long trackId;

	private String employeeLevel;

	private String department;
	
	public Long getTrackId() {
		return trackId;
	}

	public void setTrackId(Long trackId) {
		this.trackId = trackId;
	}

	public String getEmployeeLevel() {
		return employeeLevel;
	}

	public void setEmployeeLevel(String employeeLevel) {
		this.employeeLevel = employeeLevel;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

}
