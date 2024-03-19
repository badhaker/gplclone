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

public class CityMasterDto {

	@NotBlank(message = ErrorMessageCode.CITY_REQUIRED + "*" + ErrorMessageKey.USER_E031202)
	@NotEmpty(message = ErrorMessageCode.CITY_REQUIRED + "*" + ErrorMessageKey.USER_E031202)
	@NotNull(message = ErrorMessageCode.CITY_REQUIRED + "*" + ErrorMessageKey.USER_E031202)
	@Size(max = Constant.NAME_LENGTH, message = ErrorMessageCode.CHARACTER_VALIDATION_FOR_CITY + "*"
			+ ErrorMessageKey.CITY_E032603)
	@Pattern(regexp = Validator.CITY_PATTERN, message = "Special characters and numbers are not allowed." + "*"
			+ ErrorMessageKey.USER_E031202)
	private String city;

	public String getCity() {
		return city.trim();
	}

	public void setCity(String city) {
		this.city = city;
	}

}
