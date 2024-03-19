package com.alchemy.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alchemy.entities.CertificateMaster;
import com.alchemy.entities.FileUploadEntity;
import com.alchemy.entities.UserCertificateEntity;
import com.alchemy.entities.UserEntity;
import com.alchemy.iListDto.IListUserCertificate;

@Repository
public interface UserCertificateRepository extends JpaRepository<UserCertificateEntity, Long>{

	@Query(value = "select uc.id as Id ,uc.certificate_id as CertificateId, c.template_name as TemplateName , uc.user_id as UserId , u.user_name as UserName from user_certificate uc\r\n"
			+ "join certificate_master c on c.id=uc.certificate_id \r\n"
			+ "join users u on u.id=uc.user_id \r\n"
			+ "where uc.is_active =true order by uc.id desc ",nativeQuery = true)
	Page<IListUserCertificate> findByOrderByIdDesc(Pageable pageable, Class<IListUserCertificate> class1);
	
	@Query(value = "select uc.id as Id ,uc.certificate_id as CertificateId, c.template_name as TemplateName , uc.user_id as UserId , u.user_name as UserName from user_certificate uc\r\n"
			+ "join certificate_master c on c.id=uc.certificate_id \r\n"
			+ "join users u on u.id=uc.user_id \r\n"
			+ "where uc.is_active =true "
			+ "AND(:search = '' OR c.template_name ILIKE %:search% OR u.user_name ILIKE %:search%)", nativeQuery = true)
	Page<IListUserCertificate> findByUserCertificate(@Param("search") String search, Pageable pageable,
			Class<IListUserCertificate> class1);

	UserCertificateEntity findByUserIdAndCertificateId(UserEntity userId, FileUploadEntity certificateId);

}
