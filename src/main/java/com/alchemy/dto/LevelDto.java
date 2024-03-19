package com.alchemy.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.ErrorMessageKey;

public class LevelDto {

	@NotEmpty(message = ErrorMessageCode.LEVEL_NAME_REQUIRED + "*" + ErrorMessageKey.LEVEL_E031403)
	@NotBlank(message = ErrorMessageCode.LEVEL_NAME_REQUIRED + "*" + ErrorMessageKey.LEVEL_E031403)
	@NotNull(message = ErrorMessageCode.LEVEL_NAME_REQUIRED + "*" + ErrorMessageKey.LEVEL_E031403)
	@Size(max = Constant.NAME_LENGTH, message = ErrorMessageCode.CHARACTER_VALIDATION_FOR_NAME + "*"
			+ ErrorMessageKey.LEVEL_E031404)
	private String levelName;

	@Size(max = Constant.DESCRIPTION_LENGTH, message = ErrorMessageCode.CHARACTER_VALIDATION_FOR_DESCRIPTION + "*"
			+ ErrorMessageKey.LEVEL_E031404)
	private String description;

	public String getLevelName() {
		return levelName.trim();
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LevelDto(
			@NotEmpty(message = "Level name required*GPL-E031403") @NotBlank(message = "Level name required*GPL-E031403") @NotNull(message = "Level name required*GPL-E031403") String levelName,
			String description) {
		super();
		this.levelName = levelName;
		this.description = description;
	}

	public LevelDto() {
		super();
		// TODO Auto-generated constructor stub
	}

}
