package com.alchemy.serviceInterface;

import org.springframework.stereotype.Service;

import com.alchemy.dto.MailTemplateDto;

@Service
public interface MailTemplateInterface {
	
	void addMailTemplate(MailTemplateDto mailTemplateDto);
}
