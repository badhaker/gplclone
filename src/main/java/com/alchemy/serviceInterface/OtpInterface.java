package com.alchemy.serviceInterface;

import java.util.Date;

import com.alchemy.entities.OtpEntity;

public interface OtpInterface {

	public OtpEntity saveOtp(String email, String otp1, Long userId, Date expiry) throws Exception;

}
