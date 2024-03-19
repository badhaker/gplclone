package com.alchemy.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.ErrorMessageKey;
import com.alchemy.utils.Validator;

public class CertificateDto {

	private String templateBody;
	
	@NotBlank(message = ErrorMessageCode.TEMPLATE_NAME_REQUIRED + "*" + ErrorMessageKey.CERTIFICATE_E032303)
	@NotEmpty(message = ErrorMessageCode.TEMPLATE_NAME_REQUIRED + "*" + ErrorMessageKey.CERTIFICATE_E032303)
	@NotNull(message = ErrorMessageCode.TEMPLATE_NAME_REQUIRED + "*" + ErrorMessageKey.CERTIFICATE_E032303)
	@Size(max = Constant.NAME_LENGTH, message = ErrorMessageCode.CHARACTER_VALIDATION_FOR_NAME + "*"
			+ ErrorMessageKey.CERTIFICATE_E032303)
	@Pattern(regexp = Validator.NAME_PATTERN, message = ErrorMessageCode.INVALID_TEMPLATE_NAME + "*" + ErrorMessageKey.CERTIFICATE_E032303 )
	private String templateName;

	public String getTemplateBody() {
		return templateBody;
	}

	public void setTemplateBody(String templateBody) {
		this.templateBody = templateBody;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	
}
