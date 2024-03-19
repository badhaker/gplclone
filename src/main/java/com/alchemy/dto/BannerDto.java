package com.alchemy.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.alchemy.entities.ContentEnum;
import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.ErrorMessageKey;
import com.alchemy.utils.ValidEnum;

public class BannerDto {

	@NotNull(message = ErrorMessageCode.BANNER_NAME_REQUIRED + "*" + ErrorMessageKey.BANNER_E032103)
	@NotBlank(message = ErrorMessageCode.BANNER_NAME_REQUIRED + "*" + ErrorMessageKey.BANNER_E032103)
	@NotEmpty(message = ErrorMessageCode.BANNER_NAME_REQUIRED + "*" + ErrorMessageKey.BANNER_E032103)
	@Size(max = Constant.NAME_LENGTH, message = ErrorMessageCode.CHARACTER_VALIDATION_FOR_NAME + "*"
			+ ErrorMessageKey.BANNER_E032104)
	private String name;

	@NotNull(message = ErrorMessageCode.DISPLAY_ORDER_REQUIRED + "*" + ErrorMessageKey.BANNER_E032103)
	private Integer displayOrder;

	private Boolean isVisible;
//
//	@NotBlank(message = ErrorMessageCode.URL_REQUIRED + "*" + ErrorMessageKey.BANNER_E032103)
//	@NotEmpty(message = ErrorMessageCode.URL_REQUIRED + "*" + ErrorMessageKey.BANNER_E032103)
//	@NotNull(message = ErrorMessageCode.URL_REQUIRED + "*" + ErrorMessageKey.BANNER_E032103)
	@Size(max = Constant.IMAGE_URL_LENGTH, message = ErrorMessageCode.URI_SIZE + "*" + ErrorMessageKey.BANNER_E032104)
	private String url;

	@ValidEnum(enumClass = ContentEnum.class)
	private ContentEnum showContent;

	@Size(max = Constant.DESCRIPTION_LENGTH, message = ErrorMessageCode.CHARACTER_VALIDATION_FOR_DESCRIPTION + "*"
			+ ErrorMessageKey.BANNER_E032105)
	private String description;

	private Boolean isFileUpdated;

//	public Boolean getShowBanner() {
//		return showBanner;
//	}
//
//	public void setShowBanner(Boolean showBanner) {
//		this.showBanner = showBanner;
//	}

	public String getDescription() {
		return description.trim();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name.trim();
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public BannerDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ContentEnum getShowContent() {
		return showContent;
	}

	public void setShowContent(ContentEnum showContent) {
		this.showContent = showContent;
	}

	public Boolean getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}

	public Boolean getIsFileUpdated() {
		return isFileUpdated;
	}

	public void setIsFileUpdated(Boolean isFileUpdated) {
		this.isFileUpdated = isFileUpdated;
	}

}
