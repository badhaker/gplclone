package com.alchemy.iListDto;



import org.springframework.beans.factory.annotation.Value;



public interface IListUserCareerAspiration {

	public String getEdp();

	public String getName();

	public String getFunctionName();

	public String getEmail();

	public String getDepartmentName();

//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss ")
	@Value("#{@globalFunctions.dateTimestamp(target.LastUpdated)}")
	public String getLastUpdated();

}
