package com.alchemy.dto;

public class CareerAspirationFilterDto {

	private String functionName;

	private String DepartmentName;

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getDepartmentName() {
		return DepartmentName;
	}

	public void setDepartmentName(String departmentName) {
		DepartmentName = departmentName;
	}

	public CareerAspirationFilterDto(String functionName, String departmentName) {
		super();
		this.functionName = functionName;
		DepartmentName = departmentName;
	}

}
