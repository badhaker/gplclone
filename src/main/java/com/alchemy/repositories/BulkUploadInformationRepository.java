package com.alchemy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alchemy.entities.BulkUploadInformation;

@Repository
public interface BulkUploadInformationRepository extends JpaRepository<BulkUploadInformation, Long> {

}
