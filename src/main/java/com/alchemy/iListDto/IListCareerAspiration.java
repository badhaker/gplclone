package com.alchemy.iListDto;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.annotation.JsonFormat;

public interface IListCareerAspiration {

	public Long getUserId();

	public String getEdp();

	public String getName();

	public String getEmail();

	public String getRole1();

	public String getRole2();

	public Long getRoleId1();

	public Long getRoleId2();

	public String getFunctionName();

	public String getDepartmentName();

	public Long getDepartmentId1();

	public Long getDepartmentId2();

	public Long getFunctionId1();

	public Long getFunctionId2();

	public String getDepartmentName1();

	public String getDepartmentName2();

	public String getFunctionName1();

	public String getFunctionName2();

	public Boolean getNextCareerMove();

	@Value("#{@globalFunctions.getFileUrl(target.CareerFileURL)}")
	public String getCareerFileURL();

	public String getAdditionalDetails();

	public String getExperience1();

	public String getExperience2();

	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	public Date getLastUpdated();

	public String getCity1();

	public String getCity2();

	public String getCity3();

}
