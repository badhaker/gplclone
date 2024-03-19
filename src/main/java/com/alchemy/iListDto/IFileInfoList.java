package com.alchemy.iListDto;

import org.springframework.beans.factory.annotation.Value;

public interface IFileInfoList {
	public Long getId();

	public String getFilename();

	@Value("#{@globalFunctions.getFileUrl(target.OriginalName)}")
	public String getOriginalName();

	public Long getSize();
}
