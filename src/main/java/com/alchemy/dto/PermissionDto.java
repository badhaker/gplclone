package com.alchemy.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.ErrorMessageKey;

public class PermissionDto {

	@NotBlank(message = ErrorMessageCode.ACTION_NAME_REQUIRED + "*" + ErrorMessageKey.PERMISSION_E031304)
	@NotEmpty(message = ErrorMessageCode.ACTION_NAME_REQUIRED + "*" + ErrorMessageKey.PERMISSION_E031304)
	@NotNull(message = ErrorMessageCode.ACTION_NAME_REQUIRED + "*" + ErrorMessageKey.PERMISSION_E031304)
	private String actionName;

	private String description;

	@NotNull(message = ErrorMessageCode.MODULE_REQUIRED + "*" + ErrorMessageKey.PERMISSION_E031304)
	private Long moduleId;

	private String method;

	private String url;

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public PermissionDto(
			@NotBlank(message = "Action name required*GPL-E031304") @NotEmpty(message = "Action name required*GPL-E031304") @NotNull(message = "Action name required*GPL-E031304") String actionName,
			String description, Long moduleId) {
		super();
		this.actionName = actionName;
		this.description = description;
		this.moduleId = moduleId;
	}

	public PermissionDto() {
		super();
		// TODO Auto-generated constructor stub
	}

}
