package com.alchemy.dto;

public class MailTemplateDto {

	private String mailtemp;

	private String templatename;

	public String getMailtemp() {
		return mailtemp;
	}

	public void setMailtemp(String mailtemp) {
		this.mailtemp = mailtemp;
	}

	public String getTemplatename() {
		return templatename;
	}

	public void setTemplatename(String templatename) {
		this.templatename = templatename;
	}

	public MailTemplateDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MailTemplateDto(String mailtemp, String templatename) {
		super();
		this.mailtemp = mailtemp;
		this.templatename = templatename;
	}

}
