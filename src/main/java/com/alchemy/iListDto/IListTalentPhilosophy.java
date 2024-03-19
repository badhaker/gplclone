package com.alchemy.iListDto;

import org.springframework.beans.factory.annotation.Value;

public interface IListTalentPhilosophy {

	public Long getId();

	public String getChroMessage();

	public String getName();

	public Long getFileId();

	@Value("#{@globalFunctions.getFileUrl(target.FileUrl)}")
	public String getFileUrl();
}
