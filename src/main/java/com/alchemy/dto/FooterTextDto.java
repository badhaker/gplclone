package com.alchemy.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.ErrorMessageKey;

public class FooterTextDto {

	@NotNull(message = ErrorMessageCode.TEXT_REQUIRED + "*" + ErrorMessageKey.FOOTER_DOCUMENT_E0311101)
	@NotBlank(message = ErrorMessageCode.TEXT_REQUIRED + "*" + ErrorMessageKey.FOOTER_DOCUMENT_E0311101)
	@NotEmpty(message = ErrorMessageCode.TEXT_REQUIRED + "*" + ErrorMessageKey.FOOTER_DOCUMENT_E0311101)
	private String footerText;
	@NotNull(message = ErrorMessageCode.KEY_REQUIRED + "*" + ErrorMessageKey.FOOTER_DOCUMENT_E0311101)
	@NotBlank(message = ErrorMessageCode.KEY_REQUIRED + "*" + ErrorMessageKey.FOOTER_DOCUMENT_E0311101)
	@NotEmpty(message = ErrorMessageCode.KEY_REQUIRED + "*" + ErrorMessageKey.FOOTER_DOCUMENT_E0311101)
	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getFooterText() {
		return footerText;
	}

	public void setFooterText(String footerText) {
		this.footerText = footerText;
	}

}
