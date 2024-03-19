package com.alchemy.serviceInterface;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.alchemy.dto.CertificateRequestDto;
import com.alchemy.dto.CertificateResponseDto;
import com.alchemy.dto.UserCertificateRequestDto;
import com.alchemy.iListDto.IListUserCertificate;
import com.alchemy.iListDto.PdfUrlResponse;
public interface UserCertificateInterface {

	void add (UserCertificateRequestDto certificateRequest);
	
	Page<IListUserCertificate> getAllUserCertificates(String search, String pageNo, String pageSize)throws Exception;

	CertificateResponseDto getUserCertificate(Long learningTrackId, Long subTrackId, Long userId, HttpServletRequest request)
			throws Exception;

}
