package com.alchemy.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.ErrorMessageKey;

public class ModuleDto {

	@NotBlank(message = ErrorMessageCode.MODULE_REQUIRED + "*" + ErrorMessageKey.MODULE_E033003)
	@NotEmpty(message = ErrorMessageCode.MODULE_REQUIRED + "*" + ErrorMessageKey.MODULE_E033003)
	@NotNull(message = ErrorMessageCode.MODULE_REQUIRED + "*" + ErrorMessageKey.MODULE_E033003)
	@Size(message = ErrorMessageCode.MODULE_ACCEPT_CHARACTER + "*" + ErrorMessageKey.MODULE_E033004)
	public String moduleName;

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
}
