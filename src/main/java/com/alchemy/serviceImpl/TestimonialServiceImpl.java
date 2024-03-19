package com.alchemy.serviceImpl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alchemy.dto.DeleteId;
import com.alchemy.dto.IsVisibleDto;
import com.alchemy.dto.TestimonialDto;
import com.alchemy.dto.VisibleContentDto;
import com.alchemy.entities.DesignationEntity;
import com.alchemy.entities.FileUploadEntity;
import com.alchemy.entities.GplFunctionEntity;
import com.alchemy.entities.TestimonialEntity;
import com.alchemy.exceptionHandling.ResourceNotFoundException;
import com.alchemy.iListDto.IListTestimonial;
import com.alchemy.repositories.DepartmentRepository;
import com.alchemy.repositories.DesignationRepository;
import com.alchemy.repositories.FileUploadRepository;
import com.alchemy.repositories.GplFunctionRepository;
import com.alchemy.repositories.TestimonialRepository;
import com.alchemy.serviceInterface.FileUploadInterface;
import com.alchemy.serviceInterface.TestimonialInterface;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.Pagination;
import com.alchemy.utils.Validator;

@Service
public class TestimonialServiceImpl implements TestimonialInterface {

	@Autowired
	private TestimonialRepository testimonialRepository;

	@Autowired
	DesignationRepository designationRepository;

	@Autowired
	FileUploadInterface fileUploadInterface;

	@Autowired
	FileUploadRepository fileUploadRepository;

	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private FileUploadImpl fileUploadImpl;

	@Autowired
	private GplFunctionRepository gplFunctionRepository;

	@Override
	public TestimonialDto addTestimonial(@Valid TestimonialDto testimonialDto, Long userId, MultipartFile file,
			HttpServletRequest request) throws Exception {
		DesignationEntity designationEntity = this.designationRepository.findById(testimonialDto.getDesignationId())
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.DESIGNATION_NOT_FOUND));

		TestimonialEntity userName = this.testimonialRepository.findByuserNameIgnoreCase(testimonialDto.getUserName());
		
		if (userName != null) {
			throw new ResourceNotFoundException(ErrorMessageCode.TESTIMONIAL_ALREADY_PRESENT);
		}
		TestimonialEntity testimonialEntity = new TestimonialEntity();
		testimonialEntity.setTestimonial(testimonialDto.getTestimonial());
		testimonialEntity.setIsVisible(testimonialDto.getIsVisible());
		testimonialEntity.setDesignation(designationEntity);

		testimonialEntity.setUserName(testimonialDto.getUserName());
		testimonialEntity.setDescription(testimonialDto.getDescription());
		testimonialEntity.setCreatedBy(userId);

		if (!file.isEmpty() && file != null) {
			String originalName = file.getOriginalFilename();
			if (Validator.isValidforImageFile(originalName)) {
				FileUploadEntity entity1 = this.fileUploadImpl.storeFile(file, request);
				testimonialEntity.setFileId(entity1);
			} else {
				throw new IllegalArgumentException(ErrorMessageCode.VALID_IMAGE);
			}
		} else {
			throw new IllegalArgumentException(ErrorMessageCode.FILE_REQUIRED);
		}

//		DepartmentEntity entity = this.departmentRepository.findById(testimonialDto.getDepartmentId())
//				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.DEPARTMENT_NOT_FOUND));

		GplFunctionEntity entity = this.gplFunctionRepository.findById(testimonialDto.getFunctionId())
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.GPL_FUNCTION_NOT_FOUND));

		testimonialEntity.setFunction(entity);

		testimonialRepository.save(testimonialEntity);
		return testimonialDto;

	}

	@Override
	public TestimonialDto editTestimonial(Long testimonialId, TestimonialDto testimonialDto, Long userId,
			MultipartFile file, HttpServletRequest request) throws Exception {
		TestimonialEntity testimonialEntity = this.testimonialRepository.findById(testimonialId)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.TESTIMONIAL_NOT_PRESENT));
		DesignationEntity designationEntity = this.designationRepository.findById(testimonialDto.getDesignationId())
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.DESIGNATION_NOT_FOUND));
		TestimonialEntity userName = this.testimonialRepository.findByuserNameIgnoreCase(testimonialDto.getUserName());

		if (userName != null) {
			if (userName.getId() != testimonialEntity.getId()) {
				throw new ResourceNotFoundException(ErrorMessageCode.TESTIMONIAL_ALREADY_PRESENT);
			}

		}
		testimonialEntity.setTestimonial(testimonialDto.getTestimonial());
		if (testimonialDto.getIsVisible() != null) {
			testimonialEntity.setIsVisible(testimonialDto.getIsVisible());
		}
		testimonialEntity.setDesignation(designationEntity);
		testimonialEntity.setUserName(testimonialDto.getUserName());

		testimonialEntity.setDescription(testimonialDto.getDescription());
		testimonialEntity.setUpdatedBy(userId);

		if (testimonialDto.isFileUpdate() == true) {
			if (file != null && !file.isEmpty()) {
				String originalName = file.getOriginalFilename();
				if (Validator.isValidforImageFile(originalName)) {
					this.fileUploadImpl.delete(testimonialEntity.getFileId().getId());
					FileUploadEntity entity = this.fileUploadImpl.storeFile(file, request);
					testimonialEntity.setFileId(entity);
				} else {
					throw new IllegalArgumentException(ErrorMessageCode.VALID_IMAGE);
				}
			} else {
				throw new IllegalArgumentException(ErrorMessageCode.FILE_REQUIRED);
			}
		}

		GplFunctionEntity entity = this.gplFunctionRepository.findById(testimonialDto.getFunctionId())
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.GPL_FUNCTION_NOT_FOUND));

		testimonialEntity.setFunction(entity);
		this.testimonialRepository.save(testimonialEntity);
		return testimonialDto;

	}

	@Override
	public void deleteTestimonial(Long testimonialId) {
		TestimonialEntity testimonialEntity = this.testimonialRepository.findById(testimonialId)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.TESTIMONIAL_NOT_PRESENT));
		testimonialEntity.setIsActive(false);
		this.testimonialRepository.save(testimonialEntity);
	}

	@Override
	public Page<IListTestimonial> getAllTestimonials(String search, String pageNo, String pageSize) {
		Page<IListTestimonial> iListTestimonial;

		Pageable pageable = new Pagination().getPagination(pageNo, pageSize);

		if (search == "" || search == null || search.length() == 0) {

			iListTestimonial = this.testimonialRepository.findByOrderByIdDesc(pageable, IListTestimonial.class);
		} else {
			iListTestimonial = this.testimonialRepository.findByTestimonialContainingIgnoreCase(search, pageable,
					IListTestimonial.class);
		}
		return iListTestimonial;
	}

	@Override
	public List<IListTestimonial> findAllTestimonial(Class<IListTestimonial> class1) {

		List<IListTestimonial> testimonialDto = this.testimonialRepository.findByOrderByIdDesc(IListTestimonial.class);
		return testimonialDto;
	}

	@Override
	public void updateTestimonialIsVisible(Long id, IsVisibleDto dto) {
		TestimonialEntity entity = this.testimonialRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.TESTIMONIAL_NOT_PRESENT));

		entity.setIsVisible(dto.getIsVisible());

		this.testimonialRepository.save(entity);

	}

	@Override
	public void deleteMultipleTestimonialById(DeleteId ids, Long userId) {

		List<TestimonialEntity> entity = testimonialRepository.findAllById(ids.getIds());
		for (int i = 0; i < entity.size(); i++) {
			entity.get(i).setUpdatedBy(userId);
		}

		testimonialRepository.deleteAllByIdIn(ids.getIds());

	}

	@Override
	public void updateTestimonialIsVisibleMultiSelect(VisibleContentDto visibleContentDto) {

		this.testimonialRepository.isVisibleTestimonialByIds(visibleContentDto.getIsVisible(),
				visibleContentDto.getIds());

	}

}
