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

public class TestimonialDto {

	@NotBlank(message = ErrorMessageCode.TESTIMONIAL_REQUIRED + "*" + ErrorMessageKey.TESTIMONIAL_E031803)
	@NotEmpty(message = ErrorMessageCode.TESTIMONIAL_REQUIRED + "*" + ErrorMessageKey.TESTIMONIAL_E031803)
	@NotNull(message = ErrorMessageCode.TESTIMONIAL_REQUIRED + "*" + ErrorMessageKey.TESTIMONIAL_E031803)
	@Size(max = 500, message = ErrorMessageCode.TESTIMONIAL_CHARACTER_REQUIRED + "*"
			+ ErrorMessageKey.TESTIMONIAL_E031803)
	private String testimonial;

	private Boolean isVisible;

	@NotNull(message = ErrorMessageCode.DESIGNATION_ID_REQUIRED + "*" + ErrorMessageKey.TESTIMONIAL_E031803)
	private Long designationId;

//	@NotBlank(message = ErrorMessageCode.DESCRIPTION_REQUIRED + "*" + ErrorMessageKey.TESTIMONIAL_E031803)
//	@NotEmpty(message = ErrorMessageCode.DESCRIPTION_REQUIRED + "*" + ErrorMessageKey.TESTIMONIAL_E031803)
//	@NotNull(message = ErrorMessageCode.DESCRIPTION_REQUIRED + "*" + ErrorMessageKey.TESTIMONIAL_E031803)
//	@Size(max = 500, message = ErrorMessageCode.DESCRIPTION_NOT_EXCEED_FROM_500_CHARACTER + "*"
//			+ ErrorMessageKey.TESTIMONIAL_E031803)
	private String description;

	@NotBlank(message = ErrorMessageCode.NAME_REQUIRED + "*" + ErrorMessageKey.TESTIMONIAL_E031804)
	@NotEmpty(message = ErrorMessageCode.NAME_REQUIRED + "*" + ErrorMessageKey.TESTIMONIAL_E031804)
	@NotNull(message = ErrorMessageCode.NAME_REQUIRED + "*" + ErrorMessageKey.TESTIMONIAL_E031804)

	@Size(max = Constant.NAME_LENGTH, message = ErrorMessageCode.CHARACTER_VALIDATION_FOR_NAME + "*"
			+ ErrorMessageKey.TESTIMONIAL_E031805)
	@Pattern(regexp = Validator.NAME_PATTERN, message = ErrorMessageCode.INVALID_USER_NAME + "*"
			+ ErrorMessageKey.TESTIMONIAL_E031804)
	private String userName;

	@NotNull(message = ErrorMessageCode.FUNCTION_ID_REQUIRED + "*" + ErrorMessageKey.TESTIMONIAL_E031803)
	private Long functionId;

	private boolean fileUpdate;

	public boolean isFileUpdate() {
		return fileUpdate;
	}

	public void setFileUpdate(boolean fileUpdate) {
		this.fileUpdate = fileUpdate;
	}

	public String getTestimonial() {
		return testimonial;
	}

	public void setTestimonial(String testimonial) {
		this.testimonial = testimonial;
	}

	public Boolean getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}

	public Long getDesignationId() {
		return designationId;
	}

	public void setDesignationId(Long designationId) {
		this.designationId = designationId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getFunctionId() {
		return functionId;
	}

	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}

	public TestimonialDto(
			@NotBlank(message = "Testimonial required*GPL-E031803") @NotEmpty(message = "Testimonial required*GPL-E031803") @NotNull(message = "Testimonial required*GPL-E031803") @Size(max = 500, message = "Maximun 500 character*GPL-E031803") String testimonial,
			Boolean isVisible, @NotNull(message = "Designation Id required*GPL-E031803") Long designationId,
			String description,
			@NotBlank(message = "Name required*GPL-E031804") @NotEmpty(message = "Name required*GPL-E031804") @NotNull(message = "Name required*GPL-E031804") @Size(max = 100, message = "Name should not be greater than 100 characters*GPL-E031805") @Pattern(regexp = "^[a-zA-Z0-9_()&\\s]+$", message = "User name must not contain special characters*GPL-E031804") String userName,
			@NotNull(message = "Department id required*GPL-E031803") Long functionId, boolean fileUpdate) {
		super();
		this.testimonial = testimonial;
		this.isVisible = isVisible;
		this.designationId = designationId;
		this.description = description;
		this.userName = userName;
		this.functionId = functionId;
		this.fileUpdate = fileUpdate;
	}

	public TestimonialDto() {
		super();
		// TODO Auto-generated constructor stub
	}

}
