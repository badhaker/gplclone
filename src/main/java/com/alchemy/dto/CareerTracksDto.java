package com.alchemy.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.ErrorMessageKey;

public class CareerTracksDto {

	@NotEmpty(message = ErrorMessageCode.CAREER_TRACKS_NAME_REQUIRED + "*" + ErrorMessageKey.CAREERTRACKS_E033601)
	@NotBlank(message = ErrorMessageCode.CAREER_TRACKS_NAME_REQUIRED + "*" + ErrorMessageKey.CAREERTRACKS_E033601)
	@NotNull(message = ErrorMessageCode.CAREER_TRACKS_NAME_REQUIRED + "*" + ErrorMessageKey.CAREERTRACKS_E033601)
	@Size(max = 100, message = ErrorMessageCode.CAREER_TRACKS_NAME + "*" + ErrorMessageKey.CAREERTRACKS_E033605)
	public String name;

	private boolean fileUpdate;

	private boolean thumbnailFileUpdate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isFileUpdate() {
		return fileUpdate;
	}

	public void setFileUpdate(boolean fileUpdate) {
		this.fileUpdate = fileUpdate;
	}

	public boolean isThumbnailFileUpdate() {
		return thumbnailFileUpdate;
	}

	public void setThumbnailFileUpdate(boolean thumbnailFileUpdate) {
		this.thumbnailFileUpdate = thumbnailFileUpdate;
	}

	public CareerTracksDto(
			@NotEmpty(message = "career tracks name required*GPL-E033601") @NotBlank(message = "career tracks name required*GPL-E033601") @NotNull(message = "career tracks name required*GPL-E033601") @Size(max = 100, message = "Max 100 character accept.*GPL-E033606") String name,
			@NotEmpty(message = "Image url required*GPL-E033601") @NotBlank(message = "Image url required*GPL-E033601") @NotNull(message = "Image url required*GPL-E033601") @Size(max = 500, message = "Image url not exceed from 500 character*GPL-E033601") @Pattern(regexp = "^https?:\\/\\/(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}(?:\\/[\\w-]+)*\\/([\\w-]+\\.(?:jpg|gif|png))$", message = "Image url is invalid*GPL-E033601") String imageUrl) {
		super();
		this.name = name;
	}

	public CareerTracksDto() {
		super();
		// TODO Auto-generated constructor stub
	}

}
