package com.alchemy.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alchemy.dto.MailTemplateDto;
import com.alchemy.entities.MailTemplate;
import com.alchemy.exceptionHandling.ResourceNotFoundException;
import com.alchemy.repositories.MailRepository;
import com.alchemy.serviceInterface.MailTemplateInterface;

@Service
public class MailTemplateServiceImpl implements MailTemplateInterface {

	@Autowired
	MailRepository mailRepository;

	@Override
	public void addMailTemplate(MailTemplateDto mailTemplateDto) {

		String templatename = mailTemplateDto.getTemplatename().trim();
		if (mailRepository.existsByTemplatename(templatename)) {
			throw new ResourceNotFoundException("Template already present");
		}

		MailTemplate mailTemplate = new MailTemplate();
		mailTemplate.setMailtemp(mailTemplateDto.getMailtemp());
		mailTemplate.setTemplatename(templatename);

		mailRepository.save(mailTemplate);
	}

}