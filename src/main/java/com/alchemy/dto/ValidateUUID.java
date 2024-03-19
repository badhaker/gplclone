package com.alchemy.dto;

import javax.validation.constraints.Pattern;

public class ValidateUUID {
	@Pattern(regexp = "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-4[a-fA-F0-9]{3}-[89aAbB][a-fA-F0-9]{3}-[a-fA-F0-9]{12}", message = "E422910*"
			+ "Invalid code")
	private String uuid;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
