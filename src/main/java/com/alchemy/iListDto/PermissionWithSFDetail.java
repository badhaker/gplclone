package com.alchemy.iListDto;

import java.util.List;

public class PermissionWithSFDetail {

	private Long userId;
	private Long LevelId;
	private Long FunctionId;
	private String userName;
	private String levelName;
	private String functionName;
	private String employeeGrade;
	private String positionTitle;
	private String region;
	private String zone;
	private List<String> permissions;
	private String employeeEdp;
	private String departmentName;
	private Long departmentId;

	public PermissionWithSFDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PermissionWithSFDetail(Long userId, Long levelId, Long functionId, String userName, String levelName,
			String functionName, String employeeGrade, String positionTitle, String region, String zone,
			List<String> permissions) {
		super();
		this.userId = userId;
		LevelId = levelId;
		FunctionId = functionId;
		this.userName = userName;
		this.levelName = levelName;
		this.functionName = functionName;
		this.employeeGrade = employeeGrade;
		this.positionTitle = positionTitle;
		this.region = region;
		this.zone = zone;
		this.permissions = permissions;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getLevelId() {
		return LevelId;
	}

	public void setLevelId(Long levelId) {
		LevelId = levelId;
	}

	public Long getFunctionId() {
		return FunctionId;
	}

	public void setFunctionId(Long functionId) {
		FunctionId = functionId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getEmployeeGrade() {
		return employeeGrade;
	}

	public void setEmployeeGrade(String employeeGrade) {
		this.employeeGrade = employeeGrade;
	}

	public String getPositionTitle() {
		return positionTitle;
	}

	public void setPositionTitle(String positionTitle) {
		this.positionTitle = positionTitle;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getEmployeeEdp() {
		return employeeEdp;
	}

	public void setEmployeeEdp(String employeeEdp) {
		this.employeeEdp = employeeEdp;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

}
