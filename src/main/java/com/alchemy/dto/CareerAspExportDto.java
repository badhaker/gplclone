package com.alchemy.dto;

import java.util.Date;

import com.alchemy.entities.CareerAspirationEnum;
import com.alchemy.utils.GlobalFunctions;

public class CareerAspExportDto {

	private Long userId;

	private String edp;

	private String name;

	private String email;

	private String role1;

	private String role2;

	private Long roleId1;

	private Long roleId2;

	private String functionName;

	private String departmentName;

	private String departmentName1;

	private String departmentName2;

	private Long departmentId1;

	private Long departmentId2;

	private String functionName1;

	private String functionName2;

	private Long functionId1;

	private Long functionId2;

	private String experience1;

	private String experience2;

	private Boolean nextCareerMove;

	private String careerFileURL;

	private String additionalDetails;

	private String lastUpdated;

	private String city1;

	private String City2;

	private String city3;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getEdp() {
		return edp;
	}

	public void setEdp(String edp) {
		this.edp = edp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole1() {
		return role1;
	}

	public void setRole1(String role1) {
		this.role1 = role1;
	}

	public String getRole2() {
		return role2;
	}

	public void setRole2(String role2) {
		this.role2 = role2;
	}

	public String getDepartmentName1() {
		return departmentName1;
	}

	public void setDepartmentName1(String departmentName1) {
		this.departmentName1 = departmentName1;
	}

	public String getDepartmentName2() {
		return departmentName2;
	}

	public void setDepartmentName2(String departmentName2) {
		this.departmentName2 = departmentName2;
	}

	public String getFunctionName1() {
		return functionName1;
	}

	public void setFunctionName1(String functionName1) {
		this.functionName1 = functionName1;
	}

	public String getFunctionName2() {
		return functionName2;
	}

	public void setFunctionName2(String functionName2) {
		this.functionName2 = functionName2;
	}

	public String getExperience1() {
		return experience1;
	}

	public void setExperience1(String experience1) {
		this.experience1 = experience1;
	}

	public String getExperience2() {
		return experience2;
	}

	public void setExperience2(String experience2) {
		this.experience2 = experience2;
	}

	public Boolean getNextCareerMove() {
		return nextCareerMove;
	}

	public void setNextCareerMove(Boolean nextCareerMove) {
		this.nextCareerMove = nextCareerMove;
	}

	public String getAdditionalDetails() {
		return additionalDetails;
	}

	public void setAdditionalDetails(String additionalDetails) {
		this.additionalDetails = additionalDetails;
	}

	public Long getRoleId1() {
		return roleId1;
	}

	public void setRoleId1(Long roleId1) {
		this.roleId1 = roleId1;
	}

	public Long getRoleId2() {
		return roleId2;
	}

	public void setRoleId2(Long roleId2) {
		this.roleId2 = roleId2;
	}

	public Long getDepartmentId1() {
		return departmentId1;
	}

	public void setDepartmentId1(Long departmentId1) {
		this.departmentId1 = departmentId1;
	}

	public Long getDepartmentId2() {
		return departmentId2;
	}

	public void setDepartmentId2(Long departmentId2) {
		this.departmentId2 = departmentId2;
	}

	public Long getFunctionId1() {
		return functionId1;
	}

	public void setFunctionId1(Long functionId1) {
		this.functionId1 = functionId1;
	}

	public Long getFunctionId2() {
		return functionId2;
	}

	public void setFunctionId2(Long functionId2) {
		this.functionId2 = functionId2;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getCareerFileURL() {
		return careerFileURL;
	}

	public void setCareerFileURL(String careerFileURL) {
		this.careerFileURL = careerFileURL;
	}

	public String getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {

		this.lastUpdated = GlobalFunctions.dateTimestamp(lastUpdated);
	}

	public CareerAspExportDto(Long userId, String edp, String name, String email, String role1, String role2,
			Long roleId1, Long roleId2, String functionName, String departmentName, String departmentName1,
			String departmentName2, Long departmentId1, Long departmentId2, String functionName1, String functionName2,
			Long functionId1, Long functionId2, String experience1, String experience2, Boolean nextCareerMove,
			CareerAspirationEnum gradeEnhancement, String careerFileURL, String additionalDetails, String lastUpdated) {
		super();
		this.userId = userId;
		this.edp = edp;
		this.name = name;
		this.email = email;
		this.role1 = role1;
		this.role2 = role2;
		this.roleId1 = roleId1;
		this.roleId2 = roleId2;
		this.functionName = functionName;
		this.departmentName = departmentName;
		this.departmentName1 = departmentName1;
		this.departmentName2 = departmentName2;
		this.departmentId1 = departmentId1;
		this.departmentId2 = departmentId2;
		this.functionName1 = functionName1;
		this.functionName2 = functionName2;
		this.functionId1 = functionId1;
		this.functionId2 = functionId2;
		this.experience1 = experience1;
		this.experience2 = experience2;
		this.nextCareerMove = nextCareerMove;
		this.careerFileURL = careerFileURL;
		this.additionalDetails = additionalDetails;
		this.lastUpdated = lastUpdated;
	}

	public CareerAspExportDto() {
		super();
	}

	public String getCity1() {
		return city1;
	}

	public void setCity1(String city1) {
		this.city1 = city1;
	}

	public String getCity2() {
		return City2;
	}

	public void setCity2(String city2) {
		City2 = city2;
	}

	public String getCity3() {
		return city3;
	}

	public void setCity3(String city3) {
		this.city3 = city3;
	}

	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public CareerAspExportDto(Long userId, String edp, String name, String email, String role1, String role2,
			Long roleId1, Long roleId2, String functionName, String departmentName, String departmentName1,
			String departmentName2, Long departmentId1, Long departmentId2, String functionName1, String functionName2,
			Long functionId1, Long functionId2, String experience1, String experience2, Boolean nextCareerMove,
			String careerFileURL, String additionalDetails, String lastUpdated, String city1, String city2,
			String city3) {
		super();
		this.userId = userId;
		this.edp = edp;
		this.name = name;
		this.email = email;
		this.role1 = role1;
		this.role2 = role2;
		this.roleId1 = roleId1;
		this.roleId2 = roleId2;
		this.functionName = functionName;
		this.departmentName = departmentName;
		this.departmentName1 = departmentName1;
		this.departmentName2 = departmentName2;
		this.departmentId1 = departmentId1;
		this.departmentId2 = departmentId2;
		this.functionName1 = functionName1;
		this.functionName2 = functionName2;
		this.functionId1 = functionId1;
		this.functionId2 = functionId2;
		this.experience1 = experience1;
		this.experience2 = experience2;
		this.nextCareerMove = nextCareerMove;
		this.careerFileURL = careerFileURL;
		this.additionalDetails = additionalDetails;
		this.lastUpdated = lastUpdated;
		this.city1 = city1;
		this.City2 = city2;
		this.city3 = city3;
	}

}
