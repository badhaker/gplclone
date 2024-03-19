package com.alchemy.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alchemy.entities.CertificateMaster;
import com.alchemy.iListDto.IListCertificateTemp;

@Repository
public interface CertificateRepository extends JpaRepository<CertificateMaster, Long> {

	CertificateMaster findBytemplateName(String templatename);

	Page<IListCertificateTemp> findByOrderByIdDesc(Pageable pageable, Class<IListCertificateTemp> class1);
	
	List<IListCertificateTemp> findByOrderByIdDesc(Class<IListCertificateTemp> class1);

	boolean existsByTemplateName(String templatename);

	Page<IListCertificateTemp> findByTemplateNameContainingIgnoreCase(String search, Pageable pageable,
			Class<IListCertificateTemp> class1);
}
