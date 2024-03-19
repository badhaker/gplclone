package com.alchemy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alchemy.entities.MailTemplate;

@Repository
public interface MailRepository  extends JpaRepository<MailTemplate, Long> {

	MailTemplate findBytemplatename(String templatename);

	boolean existsByTemplatename(String templatename);

}
