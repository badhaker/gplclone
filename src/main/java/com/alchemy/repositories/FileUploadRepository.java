package com.alchemy.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alchemy.entities.FileUploadEntity;
import com.alchemy.iListDto.IFileInfoList;

@Repository
public interface FileUploadRepository extends JpaRepository<FileUploadEntity, Long> {

	void deleteByfilename(String filename);

	Page<IFileInfoList> findByOriginalNameContainingIgnoreCase(String search, Pageable pagingPageable,
			Class<IFileInfoList> class1);

	Page<IFileInfoList> findByOrderByIdDesc(Pageable pagingPageable, Class<IFileInfoList> class1);

}
