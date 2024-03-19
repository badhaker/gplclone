package com.alchemy.serviceInterface;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.alchemy.dto.CertificateDto;
import com.alchemy.iListDto.IListCertificateTemp;


@Service
public interface CertificateInterface {


	Page<IListCertificateTemp> templateList(String search, String pageSize, String pageNumber);

	void deleteTemplate(Long id);

	
	void addTemplate(CertificateDto templateDto, Long userId);

	CertificateDto editTemplate(Long id, CertificateDto templateDto, Long userId);

	List<IListCertificateTemp> findAllCertificates(Class<IListCertificateTemp> class1);
}
