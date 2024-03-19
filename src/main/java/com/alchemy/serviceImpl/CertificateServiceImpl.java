package com.alchemy.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alchemy.dto.CertificateDto;
import com.alchemy.entities.CertificateMaster;
import com.alchemy.exceptionHandling.ResourceNotFoundException;
import com.alchemy.iListDto.IListCertificateTemp;
import com.alchemy.iListDto.IListSponsorDto;
import com.alchemy.repositories.CertificateRepository;
import com.alchemy.serviceInterface.CertificateInterface;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.Pagination;

@Service
public class CertificateServiceImpl implements CertificateInterface {

	@Autowired
	private CertificateRepository repo;
	
	@Override
	public CertificateDto editTemplate(Long id, CertificateDto templateDto, Long userId) {
	
		CertificateMaster template = this.repo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.TEMPLATE_NOT_FOUND));

		CertificateMaster certificateTemplate = repo.findBytemplateName(templateDto.getTemplateName());
		if (certificateTemplate != null && !certificateTemplate.getId().equals(id)) {
			throw new ResourceNotFoundException(ErrorMessageCode.TEMPLATE_ALREADY_PRESENT);
		}

		template.setTemplateBody(templateDto.getTemplateBody());
		template.setTemplateName(templateDto.getTemplateName());
		template.setUpdatedBy(userId);
		repo.save(template);
		return templateDto;
	}

	@Override
	public Page<IListCertificateTemp> templateList(String search, String pageSize, String pageNumber) {
		
		Page<IListCertificateTemp> page;
		Pageable pageable = new Pagination().getPagination(pageNumber, pageSize);

		if ((search == null) || (search == "") || (search.length() == 0)) {
			page = this.repo.findByOrderByIdDesc(pageable, IListCertificateTemp.class);

		} else {
			page = this.repo.findByTemplateNameContainingIgnoreCase(search, pageable, IListCertificateTemp.class);
		}
		return page;
	}

	@Override
	public void deleteTemplate(Long id) {
		this.repo.findById(id).orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.TEMPLATE_NOT_FOUND));
		this.repo.deleteById(id);
	}

	@Override
	public void addTemplate(CertificateDto templateDto, Long userId) {
		String templateName = templateDto.getTemplateName();
		if (repo.existsByTemplateName(templateName)) {
			throw new ResourceNotFoundException(ErrorMessageCode.TEMPLATE_ALREADY_PRESENT);
		}

		CertificateMaster template = new CertificateMaster();
		template.setTemplateBody(templateDto.getTemplateBody());
		template.setTemplateName(templateName);
		template.setCreatedBy(userId);

		repo.save(template);
		
	}

	@Override
	public List<IListCertificateTemp> findAllCertificates(Class<IListCertificateTemp> class1) {
		List<IListCertificateTemp> list = this.repo.findByOrderByIdDesc(class1);
		return list;
	}

	
	
}
