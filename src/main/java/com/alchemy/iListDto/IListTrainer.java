package com.alchemy.iListDto;

import org.springframework.beans.factory.annotation.Value;

public interface IListTrainer {

	public Long getTrainerId();

	public String getTrainerName();

	public String getDescription();

	public Long getDesignationId();

	public String getDesignation();

	 @Value("#{@globalFunctions.getFileUrl(target.FileUrl)}")
	public String getFileUrl();

	public String getUserName();

	public Long getUserId();
}
