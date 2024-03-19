package com.alchemy.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.ErrorMessageKey;

public class GplFunctionDto {
	@NotBlank(message = ErrorMessageCode.GPL_FUNCTION_REQUIRED + "*" + ErrorMessageKey.GPLFUNCTION_E031902)
	@NotEmpty(message = ErrorMessageCode.GPL_FUNCTION_REQUIRED + "*" + ErrorMessageKey.GPLFUNCTION_E031902)
	@NotNull(message = ErrorMessageCode.GPL_FUNCTION_REQUIRED + "*" + ErrorMessageKey.GPLFUNCTION_E031902)
	@Size(max = Constant.NAME_LENGTH, message = ErrorMessageCode.CHARACTER_VALIDATION_FOR_NAME + "*"
			+ ErrorMessageKey.GPLFUNCTION_E031902)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
