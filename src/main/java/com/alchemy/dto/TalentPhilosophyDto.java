package com.alchemy.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.ErrorMessageKey;

public class TalentPhilosophyDto {

	@NotBlank(message = ErrorMessageCode.NAME_REQUIRED + "*" + ErrorMessageKey.TALENT_PHILOSOPHY_E033503)
	@NotEmpty(message = ErrorMessageCode.NAME_REQUIRED + "*" + ErrorMessageKey.TALENT_PHILOSOPHY_E033503)
	@NotNull(message = ErrorMessageCode.NAME_REQUIRED + "*" + ErrorMessageKey.TALENT_PHILOSOPHY_E033503)
	@Size(max = Constant.NAME_LENGTH, message = ErrorMessageCode.CHARACTER_VALIDATION_FOR_NAME + "*"
			+ ErrorMessageKey.TALENT_PHILOSOPHY_E033503)
	private String name;

	@NotBlank(message = ErrorMessageCode.MESSAGE_REQUIRED + "*" + ErrorMessageKey.TALENT_PHILOSOPHY_E033503)
	@NotEmpty(message = ErrorMessageCode.MESSAGE_REQUIRED + "*" + ErrorMessageKey.TALENT_PHILOSOPHY_E033503)
	@NotNull(message = ErrorMessageCode.MESSAGE_REQUIRED + "*" + ErrorMessageKey.TALENT_PHILOSOPHY_E033503)
	private String chroMessage;

	private Boolean isFileUpdated;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getChroMessage() {
		return chroMessage;
	}

	public void setChroMessage(String chroMessage) {
		this.chroMessage = chroMessage;
	}

	public TalentPhilosophyDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Boolean getIsFileUpdated() {
		return isFileUpdated;
	}

	public void setIsFileUpdated(Boolean isFileUpdated) {
		this.isFileUpdated = isFileUpdated;
	}

}
