package com.alchemy.iListDto;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.annotation.JsonFormat;

public interface IListFooterDocumentDto {
	public Long getId();

	public String getkey();

	@Value("#{@globalFunctions.getFileUrl(target.OriginalName)}")
	public String getOriginalName();

	public String getFileName();

	public String getText();

	public Boolean getFlag();

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getUpdatedAt();
}
